from os import listdir
from os.path import basename
from os.path import isfile
from os.path import splitext
import pickle

from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.layers import Dropout
from keras.layers.convolutional import Convolution1D
from keras.layers.convolutional import MaxPooling1D
from keras.layers.embeddings import Embedding
from keras.preprocessing import sequence
from keras.preprocessing.text import Tokenizer
from keras.utils import np_utils

from Sastrawi.Stemmer.StemmerFactory import StemmerFactory

def train_model(train_x, train_y, intent_pool_size, max_string_length):
    """Trains LSTM net using preprocessed training data

    Parameters:
        --train_x: list of training sets in index form
        --train_y: list of training labels in one-hot matrix
        --intent_pool_size: number of intent classes
        --max_string_length: maximum padded string input length

    Returns:
        --model_cnn: keras sequential model object
    """
    embed_vec_length = 32
    dict_size = 30000

    model_cnn = Sequential()
    model_cnn.add(Embedding(dict_size, embed_vec_length, input_length=max_string_length))
    model_cnn.add(Convolution1D(nb_filter=32, filter_length=3, border_mode='same', activation='relu'))
    model_cnn.add(MaxPooling1D(pool_length=2))
    model_cnn.add(LSTM(100))
    model_cnn.add(Dense(intent_pool_size+1, activation='sigmoid'))
    model_cnn.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    print(model_cnn.summary())
    model_cnn.fit(train_x, train_y, nb_epoch=50, batch_size=3)

    return model_cnn

def train_model_mlp(train_x, train_y, intent_pool_size, max_string_length):
    """Trains MLP net using preprocessed training data

    Parameters:
        --train_x: list of training sets in index form
        --train_y: list of training labels in one-hot matrix
        --intent_pool_size: number of intent classes
        --max_string_length: maximum padded string input length

    Returns:
        --model_mlp: keras sequential model object
    """

    model_mlp = Sequential()
    model_mlp.add(Dense(1000, input_shape=(max_string_length,), activation='relu'))
    model_mlp.add(Dropout(0.2))
    model_mlp.add(Dense(800, activation='relu'))
    model_mlp.add(Dropout(0.2))
    model_mlp.add(Dense(intent_pool_size+1, activation='sigmoid'))
    model_mlp.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    print(model_mlp.summary())
    model_mlp.fit(train_x, train_y, nb_epoch=50, batch_size=3)

    return model_mlp

def create_train_db():
    """Creates training array from updated intent db files

    Return:
        --train_x: list of raw training strings
        --train_y: list of intent classes
        --intent_class: dictionary of intent classes
        --parent_dict: dictionary of parent intents
    """

    intent_dir = "db/intent"
    intent_class = {}
    parent_dict = {}

    train_x = []
    train_y = []

    class_idx = 1
    for filename in listdir("db/intent"):
        basename, ext = splitext(filename)
        if ext == ".rz":
            intent_class[basename] = class_idx
            parent_dict = update_parent_dict(basename,parent_dict)

            temp_x = open(intent_dir+"/"+filename,'r').read().splitlines()
            temp_y = [class_idx]*len(temp_x)
            train_x.extend(temp_x)
            train_y.extend(temp_y)
            class_idx += 1

    for k,v in parent_dict.items():
        if v == "None":
            parent_dict[k] = 0
        else:
            parent_dict[k] = intent_class[v]

    train_y = np_utils.to_categorical(train_y)

    return train_x, train_y, intent_class, parent_dict

def update_parent_dict(file_string, parent_dict):
    """Parse parent intent from filename

    Parameters:
        --file_string: filename string without file extension
        --parent_dict: parent dictionary object

    Returns:
        --out_dict: updated parent dictionary object

    Note: filenames are in granparents.parent.child.rz notation
    """
    out_dict = parent_dict

    head, tail = splitext(file_string)
    if tail == "":
        out_dict[head] = "None"
    else:
        out_dict[tail[1:]] = head

    return out_dict

def create_train_vocab():
    """Compile user-defined entities for preprocessing and dictionary update

    Returns:
        --entity_dict: Entity dictionary with structure [key: list of members]
    """

    entity_dir = "db/entity"
    entity_dict = {}

    for filename in listdir("db/entity"):
        basename, ext = splitext(filename)
        if ext == ".rz":
            members = open(entity_dir+"/"+filename,'r').read().splitlines()
            entity_dict[basename] = members

    pickle.dump(entity_dict, open(entity_dir+"/entity.pkl", "wb"))

    return entity_dict


def update_fetch_dict(entity_dict):
    """Update dictionary with user-defined entities and create tokenizer

    Parameters:
        --entity_dict: Dictionary of user-defined entities [key: list of members]

    Returns:
        --m_tokenizer: keras tokenizer updated with updated dictionary
    """

    corpus = pickle.load(open( "db/dict/dict.pkl", "rb" ))
    for key in entity_dict:
        corpus.append(key)

    #general entity
    corpus.append("<waktu>")
    corpus.append("<lokasi>")
    corpus.append("<nama>")

    m_tokenizer = Tokenizer()
    m_tokenizer.fit_on_texts(corpus)

    return m_tokenizer

def stem_sequence_train_db(train_x, m_tokenizer, max_string_length):
    """Stem strings in training db, produce equal-length sequences
        (list of list of dictionary indexes) for training

    Parameters:
        --train_x: training db (list of strings)
        --m_tokenizer: tokenizer trained on updated dictionary
        --max_string_length: max length of sequence for training

    Returns:
        --sequence_x: list of stemmed and padded sequences for training
    """
    stem_factory = StemmerFactory()
    m_stemmer = stem_factory.create_stemmer()

    for idx, item in enumerate(train_x):
        words = item.split()
        for idy, word in enumerate(words):
            if not word[0] == '<':
                 words[idy] = m_stemmer.stem(word)

        train_x[idx] = ' '.join(words)

    sequence_x = m_tokenizer.texts_to_sequences(train_x)
    sequence_x = sequence.pad_sequences(sequence_x, maxlen=max_string_length)

    return sequence_x

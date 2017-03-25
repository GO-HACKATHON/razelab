#!/usr/local/bin/python
import sys, getopt
import json
import training_core as tc
from keras.preprocessing import sequence
from keras.preprocessing.text import Tokenizer

def main(argv):
    max_string_length = 50

    train_x, train_y, intent_dic, parent_dic = tc.create_train_db()
    entity_dict = tc.create_train_vocab()
    m_tokenizer = tc.update_fetch_dict(entity_dict)
    sequence_x = tc.stem_sequence_train_db(train_x, m_tokenizer, max_string_length)

    model = tc.train_model(sequence_x,train_y,len(intent_dic), max_string_length)
    #model = tc.train_model_mlp(sequence_x,train_y,len(intent_dic), max_string_length)

    model_json = model.to_json()
    with open("model/model_structure.json", "w") as json_file:
        json_file.write(model_json)
    model.save_weights("model/model_weights.h5")

    #test_string = ["Tunjukkin trailernya dong"]
    #test_sequence = tc.stem_sequence_train_db(test_string, m_tokenizer, max_string_length)
    #result = model.predict(test_sequence)

if __name__ == "__main__":
    main(sys.argv[1:])

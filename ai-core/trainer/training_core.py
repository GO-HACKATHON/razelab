from os import listdir
from os.path import basename
from os.path import isfile
from os.path import splitext

import pickle

def training(trainX, trainY, testX, testY):
    """Trains LSTM net using updated datasets

    """

    print("Training Cuy")


def create_train_db():
    """Creates training array from updated intent db files

    Return:
        -train_x: list of raw training strings
        _train_y: list of intent classes

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

    return train_x, train_y, intent_class, parent_dict

def update_parent_dict(string, dic):
    out_dic = dic

    head, tail = splitext(string)
    if tail == "":
        out_dic[head] = "None"
    else:
        out_dic[tail[1:]] = head

    return out_dic

def create_train_vocab():
    """Creates custom training vocab for preprocessing

    """

    entity_dir = "db/entity"
    entity_dict = {}

    for filename in listdir("db/entity"):
        basename, ext = splitext(filename)
        if ext == ".rz":
            members = open(entity_dir+"/"+filename,'r').read().splitlines()
            entity_dict[basename] = members

    return entity_dict

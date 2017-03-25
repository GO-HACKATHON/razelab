from os import listdir
from os.path import basename
from os.path import isfile

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
    train_x = []
    train_y = []
    parent_dic = {}
    class_idx = 0

    for filename in listdir("db/intent"):
        if filename.endswith(".rz"):
            basefilename = basename(filename)
            intent_class[basefilename] = class_idx
            parent_dic = update_parent_dic(basefilename,parent_dic)

            temp_x = open(intent_dir+"/"+filename,'r').read().splitlines()
            temp_y = [class_idx]*len(temp_x)
            train_x.extend(temp_x)
            train_y.extend(temp_y)
            class_idx += 1

    return train_x, train_y, parent_dic

def update_parent_dic(string, dic):
    out_dic=dic

    return out_dic

def create_train_vocab():
    """Creates custom training vocab for preprocessing

    """

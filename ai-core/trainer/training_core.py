from os import listdir

import numpy as np
from keras.datasets import imdb
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.layers import Dropout
from keras.layers.convolutional import Convolution1D
from keras.layers.convolutional import MaxPooling1D
from keras.layers.embeddings import Embedding
from keras.preprocessing import sequence

def training(trainX, trainY, testX, testY):
    """Trains LSTM net using updated datasets

    """

    print("Training Cuy")


def create_train_db():
    """Creates training array from updated db files

    """

    for filename in os.listdir("db/intent"):
        if filename.endswith(".rz"):
            print(filename)

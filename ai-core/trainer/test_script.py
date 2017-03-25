#!/usr/local/bin/python
import sys, getopt
import json
import pickle
import training_core as tc
from keras.models import model_from_json

def main(argv):
    max_string_length = 50
    m_tokenizer = pickle.load(open("model/tokenizer.pkl", "rb" ))
    intent_class = pickle.load(open("model/intent_dict.pkl", "rb" ))

    test_string = ["hari Kamis ini ada film apa ya"]
    test_sequence = tc.stem_sequence_train_db(test_string, m_tokenizer, max_string_length)

    json_file = open('model/model_structure.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()

    loaded_model = model_from_json(loaded_model_json)
    loaded_model.load_weights("model/model_weights.h5")
    loaded_model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

    result = loaded_model.predict(test_sequence)
    print(result)
    print(intent_class)

if __name__ == "__main__":
    main(sys.argv[1:])

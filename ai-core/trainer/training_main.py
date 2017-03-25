#!/usr/local/bin/python
import sys, getopt
import json
import training_core as tc

def main(argv):
    tc.update_train_db(input_query)

if __name__ == "__main__":
    main(sys.argv[1:])

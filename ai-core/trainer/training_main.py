#!/usr/local/bin/python
import sys, getopt
import json
import training_core as tc

def main(argv):
    tc.create_train_db()

if __name__ == "__main__":
    main(sys.argv[1:])

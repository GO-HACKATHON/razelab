import re
import string
import os
import sys

def list_to_predefined(filename):
	output = ""
	with open(filename) as f:
		for line in f:
			output = output + (line.rstrip()).lower() + "|"
	return output

# final information consists of region, date, etc
info = {"nama":"", 
	"daerah":"", 
	"waktu":"", 
	"order":""
	}


# list of dictionaries
# daerah
region_file = "list_region.txt"
regions = "(" + list_to_predefined(region_file) + ")"
regex_region = re.compile(regions, re.IGNORECASE)


# from list of string into predefined regex with each line is lowered and separated by |


def tag_region(text):
	regionx_found = []
	found = regex_region.findall(text)
	for a in found:
		 if len(a) > 1:
		 	found = a

	regionx_found.append(found)
	# print(regionx_found)

	for regionx in regionx_found:
		info["daerah"] = regionx
		text = re.sub(regionx, '<daerah>', text)
	
	return text

def tag(text):
	result = tag_region(text)
	return result

def main():
	chat = "Aku mau beli burger di Meruya Selatan"
	result = tag(chat)
	print("result: ")
	print(result)
	print("info")
	print(info)


if __name__ == '__main__':
	main()
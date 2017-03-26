############
# preprocess.py
# Description:
#	This code is for preprocessing input text and parse
############

import re
import string
import os
import sys
import datetime
import pickle
import json
from datetime import timedelta

now = datetime.datetime.now()

def list_to_predefined(filename):
	output = "("
	with open(filename) as f:
		for line in f:
			output = output + (line.rstrip()) + "|"
	s = list(output)
	s[-1] = ")"
	output = "".join(s)
	return output


# final information consists of region, date, etc
info = {"nama":"",
	"lokasi":"",
	"waktu":"",
	}

# list of dictionaries
# daerah
region_file = "db/list_region.txt"
regions = list_to_predefined(region_file)

# nama orang
name_file = "db/list_name.txt"
names = list_to_predefined(name_file)
# names = "(\b" + list_to_predefined(name_file) + "\b)"
# names = "\bmau\b"
# print(names)

numbers = "(^a(?=\s)|satu|dua|tiga|empat|lima|enam|tujuh|delapan|sembilan|sepuluh| \
		sebelas|dua belas|tiga belas|empat belas|lima belas|enam belas|tujuh belas|delapan belas| \
		sembilan belas|dua puluh|tiga puluh|empat puluh|lima puluh|enam puluh|tujuh puluh| \
		delapan puluh|sembilan puluh|seratus|seribu)"
weekend = "(sabtu|minggu)"
day = "(senin|selasa|rabu|kamis|jumat|sabtu|minggu)"
month = "(januari|februari|maret|april|mei|juni|juli|agustus|september| \
          oktober|november|desember)"
rel_day = "(hari ini|kemarin|besok|malam ini|sekarang|lusa|minggu depan)"
dmy = "(tahun|hari|minggu|bulan)"
iso = "\d+[/-]\d+[/-]\d+ \d+:\d+:\d+\.\d+"
exp1 = "(sebelumnya|setelahnya|lalu|yang lalu|depan|lagi)"
regxp1 = "((\d+|(" + numbers + "[-\s]?)+) " + dmy + "s? " + exp1 + ")"

regex_region = re.compile(regions, re.IGNORECASE)
regex_nama = re.compile(names)
regex_time_rel = re.compile(rel_day, re.IGNORECASE)
regex_time_exp1 = re.compile(regxp1, re.IGNORECASE)
regex_time_day = re.compile(day, re.IGNORECASE)


hashweekdays = {
    'senin': 0,
    'selasa': 1,
    'rabu': 2,
    'kamis': 3,
    'jumat': 4,
    'sabtu': 5,
    'minggu': 6}

# Hash function for months to simplify the grounding task.
# [Jan..Dec] -> [1..12]
hashmonths = {
    'Januari': 1,
    'Februari': 2,
    'Maret': 3,
    'April': 4,
    'Mei': 5,
    'Juni': 6,
    'Juli': 7,
    'Agustus': 8,
    'September': 9,
    'Oktober': 10,
    'November': 11,
    'Desember': 12}

# Hash number in words into the corresponding integer value
def hashnum(number):
    if re.match(r'satu|^a\b', number, re.IGNORECASE):
        return 1
    if re.match(r'dua', number, re.IGNORECASE):
        return 2
    if re.match(r'tiga', number, re.IGNORECASE):
        return 3
    if re.match(r'empat', number, re.IGNORECASE):
        return 4
    if re.match(r'lima', number, re.IGNORECASE):
        return 5
    if re.match(r'enam', number, re.IGNORECASE):
        return 6
    if re.match(r'tujuh', number, re.IGNORECASE):
        return 7
    if re.match(r'delapan', number, re.IGNORECASE):
        return 8
    if re.match(r'sembilan', number, re.IGNORECASE):
        return 9
    if re.match(r'sepuluh', number, re.IGNORECASE):
        return 10
    if re.match(r'sebelas', number, re.IGNORECASE):
        return 11
    if re.match(r'dua belas', number, re.IGNORECASE):
        return 12
    if re.match(r'tiga belas', number, re.IGNORECASE):
        return 13
    if re.match(r'empat belas', number, re.IGNORECASE):
        return 14
    if re.match(r'lima belas', number, re.IGNORECASE):
        return 15
    if re.match(r'enam belas', number, re.IGNORECASE):
        return 16
    if re.match(r'tujuh belas', number, re.IGNORECASE):
        return 17
    if re.match(r'delapan belas', number, re.IGNORECASE):
        return 18
    if re.match(r'sembilan belas', number, re.IGNORECASE):
        return 19
    if re.match(r'dua puluh', number, re.IGNORECASE):
        return 20
    if re.match(r'tiga puluh', number, re.IGNORECASE):
        return 30
    if re.match(r'empat puluh', number, re.IGNORECASE):
        return 40
    if re.match(r'lima puluh', number, re.IGNORECASE):
        return 50
    if re.match(r'enam puluh', number, re.IGNORECASE):
        return 60
    if re.match(r'tujuh puluh', number, re.IGNORECASE):
        return 70
    if re.match(r'delapan puluh', number, re.IGNORECASE):
        return 80
    if re.match(r'sembilan puluh', number, re.IGNORECASE):
        return 90
    if re.match(r'seratus', number, re.IGNORECASE):
        return 100
    if re.match(r'seribu', number, re.IGNORECASE):
      return 1000

def find_time(timex, base_date=now):
	# print("timex:" + timex)
	date = base_date
	today = base_date.weekday()
	timex_val = 'UNKNOWN' # Default value
	timex_ori = timex   # Backup original timex for later substitution
	# If numbers are given in words, hash them into corresponding numbers.
	# eg. twenty five days ago --> 25 days ago
	if re.search(numbers, timex, re.IGNORECASE):
	    split_timex = re.split(r'\s(?=days?|months?|years?|weeks?)', \
	                                                      timex, re.IGNORECASE)
	    value = split_timex[0]
	    # print(value)
	    # unit = split_timex[1]
	    num_list = map(lambda s:hashnum(s),re.findall(numbers + '+', \
	                                  value, re.IGNORECASE))
	    timex = "sum(num_list)` + ' ' + unit"

	# If timex matches ISO format, remove 'time' and reorder 'date'
	if re.match(r'\d+[/-]\d+[/-]\d+ \d+:\d+:\d+\.\d+', timex):
	    dmy = re.split(r'\s', timex)[0]
	    dmy = re.split(r'/|-', dmy)
	    timex_val = str(dmy[2]) + '-' + str(dmy[1]) + '-' + str(dmy[0])

	# Specific dates
	elif re.match(r'\d{4}', timex):
	    timex_val = str(timex)

	# Relative dates
	elif re.match(r'malam ini|hari ini|sekarang', timex, re.IGNORECASE):
	    timex_val = str(base_date)
	    date = base_date
	# elif re.match()
	elif re.match(r'kemarin', timex, re.IGNORECASE):
		date = base_date + timedelta(days=-1)
		timex_val = str(date)
	elif re.match(r'lusa', timex, re.IGNORECASE):
		date = base_date + timedelta(days=2)
		timex_val = str(date)
	elif re.match(r'minggu depan', timex, re.IGNORECASE):
		date = base_date + timedelta(days=7)
		timex_val = str(date)
	elif re.match(r'besok', timex, re.IGNORECASE):
		date = base_date + timedelta(days=+1)
		timex_val = str(date)
	elif re.match(day, timex, re.IGNORECASE):
		day_no = hashweekdays[timex]
		if(day_no < today):
			day_no += 7
		date = base_date + timedelta(days=day_no - today)

	# Last, this, next week.
	elif re.match(r'minggu lalu', timex, re.IGNORECASE):
	    date = (base_date + timedelta(weeks=-1))

	elif re.match(r'minggu depan', timex, re.IGNORECASE):
	    date = base_date + timedelta(weeks=1)
	    timex_val = str(date)
	return date

def tag_region(text):
	regionx_found = []
	found = regex_region.findall(text)
	if(found):
		for a in found:
			if len(a) > 1:
				found = a
				info["lokasi"] = found
				text = re.sub(found, '<lokasi>', text)
	return text

def tag_name(text):
	names_found = []
	found = regex_nama.findall(text)
	# print("nama:")
	# print(found)
	if(found):
		for a in found:
			if len(a) > 1:
				found = a
				info["nama"] = found
				text = re.sub(found, '<nama>', text)
	return text

def tag_time(text):
	timex_found=[]
	found = regex_time_exp1.findall(text)
	# print(found)
	found = [a[0] for a in found if len(a) > 1]
	# print("reg1")
	# print(found)
	for timex in found:
		timex_found.append(timex)

	# today, tomorrow, etc
	found = regex_time_rel.findall(text)
	for timex in found:
		timex_found.append(timex)
		text = re.sub(timex, '<waktu>', text)

	found = regex_time_day.findall(text)
	for timex in found:
		timex_found.append(timex)
		text = re.sub(timex, '<waktu>', text)

	for timex in timex_found:
		info["waktu"] = str(find_time(timex))

	return text

def tag_entity(pk, text):
	for category in pk:
		entities = "("
		for entity in pk[category]:
			entities = entities + entity + "|"
		s = list(entities)
		s[-1] = ')'
		entities = "".join(s)
		entities_counts = "((\d+|(" + numbers + "[-\s]?)+) " + entities + ")"
		regex_ent_counts = re.compile(entities_counts, re.IGNORECASE)
		found = regex_ent_counts.findall(text)
		# print(category)
		# print(found)
		if(found):
			for a in found:
				if len(a)> 1:
					if(category in info):
						info[category] += ", " + a[0]
					else:
						info[category] = a[0]
					text = re.sub(a[0], "<"+category+">", text)
		# print(info)
		#without counting
		regex_ent = re.compile(entities, re.IGNORECASE)
		found = regex_ent.findall(text)
		if(found):
			for a in found:
				if len(a)> 1:
					if(category in info):
						info[category] += ", " +a
					else:
						info[category] = a
					text = re.sub(a, "<"+category+">", text)

	return text

# Input: user chat in string type
# Return: Output for training in deep learning
def tag(text):
	# load pickle file
	entity_file = open("db/entity/entity.pkl","rb")
	pk = pickle.load(entity_file)

	result = tag_region(text)
	result = tag_time(result)
	result = tag_name(result)
	result = tag_entity(pk,result)
	return result

def main():
	# chat = "nadiem mau pesan dua ayam kambing dan empat coca-cola di Kemang hari kamis"
	input_filename="usage/inputChat.txt"
	input_chat = ""
	with open(input_filename) as f:
		input_chat = f.read()


	result = tag(input_chat)
	print(result)

	output = json.dumps(info)
	output_file = "usage/outputChat.txt"
	f = open(output_file, 'w')
	f.write(output)

	print(output)



if __name__ == '__main__':
	main()

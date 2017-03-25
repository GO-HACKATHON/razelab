import re
import string
import os
import sys
import datetime
import pickle
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

# load pickle file
entity_file = open("entity.pkl","rb")
pk = pickle.load(entity_file)

# final information consists of region, date, etc
info = {"nama":"", 
	"lokasi":"", 
	"waktu":"", 
	}

# list of dictionaries
# daerah
region_file = "list_region.txt"
regions = list_to_predefined(region_file)

# nama orang
name_file = "list_name.txt"
names = list_to_predefined(name_file)
# names = "(\b" + list_to_predefined(name_file) + "\b)"
# names = "\bmau\b"
print(names)

numbers = "(^a(?=\s)|satu|dua|tiga|empat|lima|enam|tujuh|delapan|sembilan|sepuluh| \
		sebelas|dua belas|tiga belas|empat belas|lima belas|enam belas|tujuh belas|delapan belas| \
		sembilan belas|dua puluh|tiga puluh|empat puluh|lima puluh|enam puluh|tujuh puluh| \
		delapan puluh|sembilan puluh|seratus|seribu)"
weekend = "(sabtu|minggu)"
day = "(senin|selasa|rabu|kamis|jumat|sabtu|minggu)"
month = "(januari|februari|maret|april|mei|juni|juli|agustus|september| \
          oktober|november|desember)"
rel_day = "(hari ini|kemarin|besok|malam ini|sekarang)"
dmy = "(tahun|hari|minggu|bulan)"
iso = "\d+[/-]\d+[/-]\d+ \d+:\d+:\d+\.\d+"
exp1 = "(sebelumnya|setelahnya|lalu|yang lalu|depan|lagi)"
regxp1 = "((\d+|(" + numbers + "[-\s]?)+) " + dmy + "s? " + exp1 + ")"

regex_region = re.compile(regions, re.IGNORECASE)
regex_nama = re.compile(names, re.IGNORECASE)
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

	# # Month in the previous year.
	# elif re.match(r'last ' + month, timex, re.IGNORECASE):
	#     month = hashmonths[timex.split()[1]]
	#     timex_val = str(base_date.year - 1) + '-' + str(month)

	# # Month in the current year.
	# elif re.match(r'this ' + month, timex, re.IGNORECASE):
	#     month = hashmonths[timex.split()[1]]  
	#     timex_val = str(base_date.year) + '-' + str(month)

	# # Month in the following year.
	# elif re.match(r'next ' + month, timex, re.IGNORECASE):
	#     month = hashmonths[timex.split()[1]]
	#     timex_val = str(base_date.year + 1) + '-' + str(month)
	# elif re.match(r'last month', timex, re.IGNORECASE):

	#     # Handles the year boundary.
	#     if base_date.month == 1:
	#         timex_val = str(base_date.year - 1) + '-' + '12'
	#     else:
	#         timex_val = str(base_date.year) + '-' + str(base_date.month - 1)
	# elif re.match(r'this month', timex, re.IGNORECASE):
	#         timex_val = str(base_date.year) + '-' + str(base_date.month)
	# elif re.match(r'next month', timex, re.IGNORECASE):

	#     # Handles the year boundary.
	#     if base_date.month == 12:
	#         timex_val = str(base_date.year + 1) + '-' + '1'
	#     else:
	#         timex_val = str(base_date.year) + '-' + str(base_date.month + 1)
	# elif re.match(r'last year', timex, re.IGNORECASE):
	#     timex_val = str(base_date.year - 1)
	# elif re.match(r'this year', timex, re.IGNORECASE):
	#     timex_val = str(base_date.year)
	# elif re.match(r'next year', timex, re.IGNORECASE):
	#     timex_val = str(base_date.year + 1)
	# elif re.match(r'\d+ days? (ago|earlier|before)', timex, re.IGNORECASE):

	#     # Calculate the offset by taking '\d+' part from the timex.
	#     offset = int(re.split(r'\s', timex)[0])
	#     timex_val = str(base_date + RelativeDateTime(days=-offset))
	# elif re.match(r'\d+ days? (later|after)', timex, re.IGNORECASE):
	#     offset = int(re.split(r'\s', timex)[0])
	#     timex_val = str(base_date + RelativeDateTime(days=+offset))
	# elif re.match(r'\d+ weeks? (ago|earlier|before)', timex, re.IGNORECASE):
	#     offset = int(re.split(r'\s', timex)[0])
	#     year = (base_date + RelativeDateTime(weeks=-offset)).year
	#     week = (base_date + \
	#                     RelativeDateTime(weeks=-offset)).iso_week[1]
	#     timex_val = str(year) + 'W' + str(week)
	# elif re.match(r'\d+ weeks? (later|after)', timex, re.IGNORECASE):
	#     offset = int(re.split(r'\s', timex)[0])
	#     year = (base_date + RelativeDateTime(weeks=+offset)).year
	#     week = (base_date + RelativeDateTime(weeks=+offset)).iso_week[1]
	#     timex_val = str(year) + 'W' + str(week)
	# elif re.match(r'\d+ months? (ago|earlier|before)', timex, re.IGNORECASE):
	#     extra = 0
	#     offset = int(re.split(r'\s', timex)[0])

	#     # Checks if subtracting the remainder of (offset / 12) to the base month
	#     # crosses the year boundary.
	#     if (base_date.month - offset % 12) < 1:
	#         extra = 1

	#     # Calculate new values for the year and the month.
	#     year = str(base_date.year - offset // 12 - extra)
	#     month = str((base_date.month - offset % 12) % 12)

	#     # Fix for the special case.
	#     if month == '0':
	#         month = '12'
	#     timex_val = year + '-' + month
	# elif re.match(r'\d+ months? (later|after)', timex, re.IGNORECASE):
	#     extra = 0
	#     offset = int(re.split(r'\s', timex)[0])
	#     if (base_date.month + offset % 12) > 12:
	#         extra = 1
	#     year = str(base_date.year + offset // 12 + extra)
	#     month = str((base_date.month + offset % 12) % 12)
	#     if month == '0':
	#         month = '12'
	#     timex_val = year + '-' + month
	# elif re.match(r'\d+ years? (ago|earlier|before)', timex, re.IGNORECASE):
	#     offset = int(re.split(r'\s', timex)[0])
	#     timex_val = str(base_date.year - offset)
	# elif re.match(r'\d+ years? (later|after)', timex, re.IGNORECASE):
	#     offset = int(re.split(r'\s', timex)[0])
	#     timex_val = str(base_date.year + offset)

	# # Remove 'time' from timex_val.
	# # For example, If timex_val = 2000-02-20 12:23:34.45, then
	# # timex_val = 2000-02-20
	# timex_val = re.sub(r'\s.*', '', timex_val)

	# # Substitute tag+timex in the text with grounded tag+timex.
	# tagged_text = re.sub('<TIMEX2>' + timex_ori + '</TIMEX2>', '<TIMEX2 val=\"' \
	#     + timex_val + '\">' + timex_ori + '</TIMEX2>', tagged_text)


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
	print("nama:")
	print(found)
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
	# print("time")
	# print(found)
	for timex in found:
		timex_found.append(timex)

	found = regex_time_day.findall(text)
	for timex in found:
		timex_found.append(timex)

	for timex in timex_found:
		info["waktu"] = str(find_time(timex))
		text = re.sub(timex, '<waktu>', text)

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
		print(category)
		print(found)
		if(found):
			for a in found:
				if len(a)> 1:
					if(category in info):
						info[category] += ", " + a[0]
					else:
						info[category] = a[0]
					text = re.sub(a[0], "<"+category+">", text)
		print(info)
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

def tag(text):
	result = tag_region(text)
	result = tag_time(result)
	result = tag_name(result)
	result = tag_entity(pk,result)
	return result

def main():
	chat = "nadiem mau pesan dua ayam kambing dan empat coca-cola di Kemang hari kamis"
	print("input:")
	print(chat)
	result = tag(chat)
	print("result: ")
	print(result)
	print("info")
	print(info)


if __name__ == '__main__':
	main()
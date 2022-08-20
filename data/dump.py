import json
import os

path_dir = './amore_reviews'

file_list = os.listdir(path_dir)

for file_name in file_list:
    file_location = './amore_reviews/' + file_name
    with open(file_location, 'r') as fp:
        data = json.load(fp)

print(file_list)

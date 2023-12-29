import sys
import json
import configparser

json_file = sys.argv[1]

# Read the JSON file
with open(json_file, 'r') as json_in:
    json_data = json.load(json_in)

# Create a ConfigParser instance
config = configparser.ConfigParser()

# Populate the ConfigParser with data from the JSON file
for section, values in json_data.items():
    config.add_section(section)
    for key, value in values.items():
        config.set(section, key, value)

# Generate the output CFG file name
cfg_file = json_file.replace('.json', '.cfg')

# Save the CFG data to the generated file name
with open(cfg_file, 'w') as cfg_out:
    config.write(cfg_out)
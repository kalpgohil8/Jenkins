import configparser
import json
import sys
import os

cfgFile = sys.argv[1]
json_file = os.path.splitext(cfgFile)[0] + '.json'

# Read the configuration file
config = configparser.ConfigParser()
config.read(cfgFile)

# Convert the configuration to a dictionary
config_dict = {}
for section in config.sections():
    config_dict[section] = {}
    for key, value in config.items(section):
        config_dict[section][key] = value

# Convert the dictionary to JSON
json_data = json.dumps(config_dict, indent=4)

# Print or save the JSON data as needed
print(json_data)

# Save the JSON data to the generated file name
with open(json_file, 'w') as json_out:
    json_out.write(json_data)
import json
import sys

# Get the input data from sys.argv
input_string = sys.argv[1]
json_file = sys.argv[2] + 'input.json'

# Convert the input string to a dictionary
try:
    input_data = eval(input_string)
except Exception as e:
    print(f"Error parsing input string: {e}")
    sys.exit(1)

# Convert the dictionary to JSON
json_data = json.dumps(input_data, indent=4)

# Print or save the JSON data as needed
print(json_data)

with open(json_file, 'w') as json_out:
    json_out.write(json_data)
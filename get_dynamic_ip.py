import json
import sys

json_path = sys.argv[1]
mode = sys.argv[2]

f = open(json_path)
data = json.load(f)

def check_dynamic_ip(mode):
    return data["Input"][0]["SYNC_MODE_{}".format(mode)]["BoardIp_Dynamic"]
print(check_dynamic_ip(mode))
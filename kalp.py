import json

file_path = 'Json'

with open(file_path, 'r') as file:
    data = json.load(file)

calnax_setup = data.get("calnax_setup", {})
dut_host, dut_port = calnax_setup.get("dut", "").split()[1:3]
mcu_host, mcu_port = calnax_setup.get("mcu", "").split()[1:3]

uarts = data.get("boards", [{}])[0].get("uarts", [])

for uart in uarts:
    alias = uart.get("alias", "")
    if alias == "dut":
        uart["host"] = dut_host
        uart["port"] = dut_port
        uart["connection_url"] = f"{dut_host} {dut_port}"
    elif alias == "mcu":
        uart["host"] = mcu_host
        uart["port"] = mcu_port
        uart["connection_url"] = f"{mcu_host} {mcu_port}"

with open(file_path, 'w') as file1:
    json.dump(data, file1, indent=4)

with open(file_path, 'r') as file2:
    data1 = json.load(file2)

formated_data = json.dumps(data1, indent=4)
print(formated_data)

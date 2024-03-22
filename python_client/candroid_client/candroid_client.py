import time

import paho.mqtt.client as mqtt

import uds
from uds import Uds

import can


def on_connect(client, userdata, flags, reason_code, properties):
    print(f"Connected with result code {reason_code}")
    client.subscribe("announce/info")

# The callback for when a PUBLISH message is received from the server.


def on_message(client, userdata, msg):
    print(msg.topic+" "+str(msg.payload))


mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
mqttc.on_connect = on_connect
mqttc.on_message = on_message

can_filter = [False] * 2048


def filter_message(can_message) -> bool:
    if can_filter[can_message.arbitration_id]:
        return True
    else:
        return False


def mqtt_publish(topic, value):
    mqttc.publish(topic, value)
    print(topic + " " + value)


def decode_message(can_message):
    can_id = hex(can_message.arbitration_id)

    match can_id:
        case "0x107":
            mqtt_publish("ecu1/Min Cell Voltage",
                         str(int.from_bytes(can_message.data[0:2], "little")/10000))
            mqtt_publish("ecu1/Max Cell Voltage",
                         str(int.from_bytes(can_message.data[2:4], "little")/10000))
            mqtt_publish("ecu1/Avg Cell Voltage",
                         str(int.from_bytes(can_message.data[4:6], "little")/10000))
            mqtt_publish("ecu1/Pack Voltage",
                         str(int.from_bytes(can_message.data[6:8], "little")/1000))
        case "0x117":
            mqtt_publish("ecu1/Min Relative SOC",
                         str(int.from_bytes(can_message.data[0:1], "little")))
            mqtt_publish("ecu1/Max Relative SOC",
                         str(int.from_bytes(can_message.data[2:3], "little")))
            mqtt_publish("ecu1/Avg Relative SOC",
                         str(int.from_bytes(can_message.data[4:5], "little")))
            mqtt_publish("ecu1/CCL",
                         str(can_message.data[6]))
            mqtt_publish("ecu1/DCL",
                         str(can_message.data[7]))
        case "0x707":
            mqtt_publish("ecu1/Cell 1 Voltage",
                         str(int.from_bytes(can_message.data[0:2], "little") / 10000))
            mqtt_publish("ecu1/Cell 2 Voltage",
                         str(int.from_bytes(can_message.data[2:4], "little") / 10000))
            mqtt_publish("ecu1/Cell 3 Voltage",
                         str(int.from_bytes(can_message.data[4:6], "little") / 10000))
            mqtt_publish("ecu1/Cell 4 Voltage",
                         str(int.from_bytes(can_message.data[6:8], "little") / 10000))
        case "0x747":
            mqtt_publish("ecu1/MCU Temp",
                         str(can_message.data[0]))
            mqtt_publish("ecu1/Board Temp 1",
                         str(can_message.data[1]))
            mqtt_publish("ecu1/Board Temp 2",
                         str(can_message.data[2]))
            mqtt_publish("ecu1/Max Cell Temp",
                         str(can_message.data[3]))
            mqtt_publish("ecu1/Fan 1 Speed",
                         str(int.from_bytes(can_message.data[4:6], "little")))
            mqtt_publish("ecu1/Fan 2 Speed",
                         str(int.from_bytes(can_message.data[6:8], "little")))
        case _:
            mqtt_publish("announce/info", "Decode Error")


def add_filter(msg_id):  # Expects hex without 0x
    can_filter[int(msg_id, 16)]=True


def init_filter():
    add_filter("107")  # in Hex
    add_filter("117")  # in Hex
    add_filter("707")  # in Hex
    add_filter("747")  # in Hex


def main():
    print("Connecting to mqtt...")
    mqttc.connect("localhost")
    mqttc.publish("announce/info", "hello.py")

    init_filter()

    with can.interface.Bus(interface = 'socketcan', channel = 'can0') as bus:
        while (True):
            response=None

            response=bus.recv()

            print(response)

            if filter_message(response):
                decode_message(response)


if __name__ == "__main__":
    main()

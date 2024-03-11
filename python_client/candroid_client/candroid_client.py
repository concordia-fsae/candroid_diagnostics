import paho.mqtt.client as mqtt

def on_connect(client, userdata, flags, reason_code, properties):
    print(f"Connected with result code {reason_code}")
    client.subscribe("announce/info")

# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
    print(msg.topic+" "+str(msg.payload))


mqttc = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
mqttc.on_connect = on_connect
mqttc.on_message = on_message

def main():
    print("Connecting to mqtt...")
    mqttc.connect("localhost")
    mqttc.publish("announce/info", "hello.py")
    mqttc.loop_forever()


if __name__ == "__main__":
    main()

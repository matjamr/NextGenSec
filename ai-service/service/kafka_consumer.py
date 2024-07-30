import json
import threading

from kafka import KafkaConsumer

kafka_bootstrap_servers = 'localhost:9092'
kafka_topic = 'test'

consumer = KafkaConsumer(
    kafka_topic,
    group_id='my-group',
    bootstrap_servers=[kafka_bootstrap_servers],
    value_deserializer=lambda v: json.loads(v.decode('utf-8'))
)


def consume_kafka_messages():
    for message in consumer:
        print(f"Received message: {message.value}")


consumer_thread = threading.Thread(target=consume_kafka_messages)
consumer_thread.start()

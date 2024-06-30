import json
import threading

from kafka import KafkaConsumer

# Set up Kafka consumer configuration
kafka_bootstrap_servers = 'localhost:9092'  # Update with your Kafka broker address
kafka_topic = 'test'  # Update with your Kafka topic

consumer = KafkaConsumer(
    kafka_topic,
    group_id='my-group',  # Choose a unique group id for your consumer
    bootstrap_servers=[kafka_bootstrap_servers],
    value_deserializer=lambda v: json.loads(v.decode('utf-8'))
)

def consume_kafka_messages():
    for message in consumer:
        # Here, you can process the Kafka message as needed
        # For now, we'll just print the message to the console
        print(f"Received message: {message.value}")

# Start the Kafka consumer in a separate thread
consumer_thread = threading.Thread(target=consume_kafka_messages)
consumer_thread.start()

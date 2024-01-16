from kafka import KafkaProducer

from .service_class import Service
from flask import Blueprint, render_template, request, json, jsonify
from repository.Repository import Repository
from model.models import Device, VerificationData

import cv2


from utils.utils import predict_on_loaded_model_from_request


class FaceRecognitionService(Service):

    def __init__(self, repository: Repository):
        self.repository = repository

        self.loaded_face_recognizer = cv2.face.LBPHFaceRecognizer_create()
        self.loaded_face_recognizer.read("saved-models/face_recognition_model.yml")
        print("Model loaded successfully.")

        # kafka_bootstrap_servers = 'localhost:9092'
        # kafka_topic = 'test'
        #
        # producer = KafkaProducer(
        #     bootstrap_servers=[kafka_bootstrap_servers],
        #     value_serializer=lambda v: json.dumps(v).encode('utf-8')
        # )

        message_data = {"key": "key1", "value": "Hello, Kafka!"}
        # producer.send(kafka_topic, value=message_data)
        # producer.close()

    def validate(self):
        if 'file' not in request.files:
            return jsonify({"error": "No file part"})

        file = request.files['file']

        if file.filename == '':
            return jsonify({"error": "No selected file"})

        allowed_extensions = {'png', 'jpg', 'jpeg', 'gif'}
        if '.' in file.filename and file.filename.rsplit('.', 1)[1].lower() not in allowed_extensions:
            return jsonify({"error": "Invalid file extension"})

        if "device_id" not in request.form:
            return jsonify({"error": "No device_id in request"})

    def recognize_by_face_img(self):
        return predict_on_loaded_model_from_request(request.files['file'], self.loaded_face_recognizer)

    def do_service(self):
        self.validate()
        try:
            email: str = self.recognize_by_face_img()
        except Exception as e:
            print(e)
            return jsonify({"error": "invalid img sent"})

        print(email)
        device: Device = self.repository.get_device_by_id(request.form["device_id"])
        try:
            ret: list[VerificationStage] = self.repository.get_place_verification_data_by_place_id(device.place_id, email)
        except:
            return jsonify({"error": "invalid user"})

        if len(ret) <= 0:
            return jsonify({"error": "invalid auth invocation sent"})

        return jsonify(ret[0])

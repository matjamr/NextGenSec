from .service_class import Service
from flask import Blueprint, render_template, request, json, jsonify

import cv2
# from utils import predict_on_loaded_model

# loaded_face_recognizer = cv2.face.LBPHFaceRecognizer_create()
# loaded_face_recognizer.read("saved-models/face_recognition_model.yml")
# print("Model loaded successfully.")
#
# Example usage: Predict on a new image
# image_path_to_predict = "test-data/test1.jpg"
# predict_on_loaded_model(image_path_to_predict, loaded_face_recognizer)


class FaceRecognitionService(Service):

    def validate(self):
        if 'file' not in request.files:
            return jsonify({"error": "No file part"})

        file = request.files['file']

        if file.filename == '':
            return jsonify({"error": "No selected file"})

        allowed_extensions = {'png', 'jpg', 'jpeg', 'gif'}
        if '.' in file.filename and file.filename.rsplit('.', 1)[1].lower() not in allowed_extensions:
            return jsonify({"error": "Invalid file extension"})

    def do_service(self):
        self.validate()

        request.files['file'].save('uploaded_image.jpg')

        return jsonify({"result": "Image uploaded and processed successfully"})

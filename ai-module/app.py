from flask import Blueprint, render_template, request, json, Flask, jsonify
from model.context import Context
from service.service_class import Service
from service.face_recognition_service import FaceRecognitionService

import cv2
from utils.utils import predict_on_loaded_model

app = Flask(__name__)

face_recognition_service: Service = FaceRecognitionService()


@app.route('/verify', methods=['POST'])
def process_data_route():
    try:
        return face_recognition_service.do_service()
    except Exception as e:
        return jsonify({"error": str(e)})

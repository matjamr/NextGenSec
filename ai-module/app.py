from flask import Blueprint, render_template, request, json, Flask, jsonify
from model.context import Context
from service.service_class import Service
from service.face_recognition_service import FaceRecognitionService
from flask_sqlalchemy import SQLAlchemy
# from model.models import db
import cv2
from utils.utils import predict_on_loaded_model
from repository.Repository import Repository

app = Flask(__name__)



app.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://postgres:postgres@localhost:5432/postgres"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
# db.init_app(app)

userRepository: Repository = Repository(None)
face_recognition_service: Service = FaceRecognitionService(userRepository)

@app.route('/verify', methods=['POST'])
def process_data_route():
    try:
        return face_recognition_service.do_service()
    except Exception as e:
        return jsonify({"error": str(e)})

@app.route('/test', methods=['GET'])
def find_all():
    print(userRepository.get_all())
    return jsonify(userRepository.get_all())

# KURWA to zadziala tak ze walidacja jest po stronie AI microservice, natomiast ai microservice pierdolnie cos na kolejke i my pozniej na
# spring-backend to wczytamy. ale kurwa big brain
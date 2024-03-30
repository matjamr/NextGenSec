import psycopg2
from flask import Flask, jsonify, g

# from model.models import db
from repository.Repository import Repository
from service.face_recognition_service import FaceRecognitionService
from service.service_class import Service

app = Flask(__name__)



app.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://postgres:postgres@localhost:5432/postgres"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
# db.init_app(app)

userRepository: Repository = Repository(None)
face_recognition_service: Service = FaceRecognitionService(userRepository)

@app.before_request
def before_request():
    g.conn = psycopg2.connect(database="postgres",
                             host="localhost",
                             user="postgres",
                             password="postgres",
                             port="5432")

@app.after_request
def after_request(response):
    if g.conn is not None:
        print('closing connection')
        g.conn.close()
    return response

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

app.run()

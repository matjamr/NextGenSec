import cv2
import json
import numpy as np
import os
from training.models.Context import Context
from training.service.Service import Service
from training.utils.cv2_utils import prepare_training_data


class TrainingService(Service):

    def __init__(self, repository):
        self.__repository = repository

    def do_service(self, context: Context):
        faces, labels, email_id_map = prepare_training_data("training-data", context.to_be_processed)

        if len(faces) == 0 or len(labels) == 0:
            return

        print("Total faces: ", len(faces))
        print("Total labels: ", len(labels))

        face_recognizer = cv2.face.LBPHFaceRecognizer_create()
        face_recognizer.train(faces, np.array(labels))
        face_recognizer.save("saved-models/face_recognition_model.yml")
        self.save_email_id_map(email_id_map, "saved-models/email-id-map.json")

    def save_email_id_map(self, email_id_map, file_path):
        os.makedirs(os.path.dirname(file_path), exist_ok=True)
        with open(file_path, 'w') as f:
            json.dump(email_id_map, f)

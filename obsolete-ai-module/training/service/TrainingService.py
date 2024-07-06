import cv2
import numpy as np

from model.context import Context
from training.service.Service import Service
from training.utils.cv2_utils import prepare_training_data


class TrainingService(Service):

    def __init__(self, repository):
        self.__repository = repository

    def do_service(self, context: Context):
        faces, labels = prepare_training_data("training-data")

        print("Total faces: ", len(faces))
        print("Total labels: ", len(labels))

        face_recognizer = cv2.face.LBPHFaceRecognizer_create()
        face_recognizer.train(faces, np.array(labels))
        face_recognizer.save("saved-models/face_recognition_model.yml")

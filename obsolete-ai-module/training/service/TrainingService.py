import cv2
import numpy as np

from training.models.Context import Context
from training.service.Service import Service
from training.utils.cv2_utils import prepare_training_data


class TrainingService(Service):

    def __init__(self, repository):
        self.__repository = repository

    def do_service(self, context: Context):
        items = [x[0] for x in context.to_be_processed]
        faces, labels = prepare_training_data("training-data", items)

        if len(faces) == 0 or len(labels) == 0:
            return

        print("Total faces: ", len(faces))
        print("Total labels: ", len(labels))

        face_recognizer = cv2.face.LBPHFaceRecognizer_create()
        face_recognizer.train(faces, np.array(labels))
        face_recognizer.save("saved-models/face_recognition_model.yml")

import cv2
import os
import numpy as np
from training.utils import prepare_training_data
from training.db import fetch_sensitive_data, download_images, remove_assets
from training.models.SensitiveData import SensitiveData

print("fetching info from db.....")
sensitiveData: list[SensitiveData] = fetch_sensitive_data()

print("downloading imgs")
download_images(sensitiveData)

print("Preparing data...")
faces, labels = prepare_training_data("training-data")

print("Data prepared")
print("Deleting assets....")
remove_assets()

print("Total faces: ", len(faces))
print("Total labels: ", len(labels))


face_recognizer = cv2.face.LBPHFaceRecognizer_create()
face_recognizer.train(faces, np.array(labels))
face_recognizer.save("saved-models/face_recognition_model.yml")


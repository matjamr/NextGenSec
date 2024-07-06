import os

import cv2

from training.models.Context import Context
from training.service.Service import Service


class ValidationService(Service):

    def __init__(self):
        self.face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

    def detect_face(self, image):
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        faces = self.face_cascade.detectMultiScale(gray, 1.1, 4)
        return faces

    def extract_lbp_features(self, face):
        gray = cv2.cvtColor(face, cv2.COLOR_BGR2GRAY)
        lbp = cv2.calcHist([gray], [0], None, [256], [0, 256])
        return lbp.flatten()

    def compare_faces(self, features1, features2):
        return cv2.norm(features1, features2, cv2.NORM_L2)

    def do_service(self, context: Context):
        for folder_path, (email, image_files) in context.user_folders.items():
            face_encodings = []
            for image_file in image_files:
                image_path = os.path.join(folder_path, image_file + ".jpg")
                image = cv2.imread(image_path)
                faces = self.detect_face(image)

                if len(faces) > 0:
                    (x, y, w, h) = faces[0]
                    face = image[y:y + h, x:x + w]
                    face_encodings.append(self.extract_lbp_features(face))
                else:
                    face_encodings.append(None)
                    print(f"No face detected in {image_path}")

            if all(encoding is not None for encoding in face_encodings):
                print(f"Face similarity for user with email {email}:")
                for i in range(len(face_encodings)):
                    for j in range(i + 1, len(face_encodings)):
                        similarity = self.compare_faces(face_encodings[i], face_encodings[j])
                        threshold = 100

                        if similarity < threshold:
                            print(
                                f"  Images {image_files[i]} and {image_files[j]} are likely the same person (Distance: {similarity})")
                        else:
                            print(
                                f"  Images {image_files[i]} and {image_files[j]} are likely different people (Distance: {similarity})")
            else:
                print(f"Not all images have detectable faces for user with email {email}.")


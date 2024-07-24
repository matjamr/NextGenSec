import os
from deepface import DeepFace
from training.models.Context import Context
from training.service.Service import Service


class ValidationService(Service):

    def __init__(self):
        pass  # No initialization needed for DeepFace

    def detect_and_extract_face(self, image_path):
        try:
            face = DeepFace.detectFace(img_path=image_path, detector_backend='opencv')
            return face
        except Exception as e:
            print(f"No face detected in {image_path}: {str(e)}")
            return None

    def extract_features(self, image_path):
        try:
            embeddings = DeepFace.represent(img_path=image_path, model_name='VGG-Face', enforce_detection=True)
            if embeddings:
                return embeddings[0]["embedding"]
            return None
        except Exception as e:
            print(f"Could not extract features from {image_path}: {str(e)}")
            return None

    def compare_faces(self, features1, features2):
        try:
            result = DeepFace.verify(img1_path=features1, img2_path=features2, model_name='VGG-Face',
                                     distance_metric='cosine')
            return result['distance']
        except Exception as e:
            print(f"Error comparing faces: {str(e)}")
            return None

    def extract_faces(self, context: Context):
        extracted_faces = {}
        for folder_path, (email, data) in context.user_folders.items():
            faces = []
            for sensitive_data in data:
                image_path = os.path.join(folder_path, sensitive_data.image_id + ".jpg")
                face = self.detect_and_extract_face(image_path)
                if face is not None:
                    faces.append((sensitive_data, image_path))
                else:
                    context.to_be_rejected.append((email, sensitive_data.id))

            extracted_faces[email] = faces
        return extracted_faces

    def do_service(self, context: Context):
        extracted_faces = self.extract_faces(context)
        for email, faces in extracted_faces.items():
            face_encodings = []
            image_files = []
            for sens_data, image_path in faces:
                encoding = self.extract_features(image_path)

                face_encodings.append(encoding)
                image_files.append(image_path)

            print(f"Face similarity for user with email {email}:")
            should_be_processed = all(em != email for em, data in context.to_be_rejected)
            for i in range(len(face_encodings)):
                for j in range(i + 1, len(face_encodings)):
                    similarity = self.compare_faces(image_files[i], image_files[j])
                    if similarity is not None:
                        threshold = 0.4
                        if similarity < threshold:
                            print(
                                f"  Images {image_files[i]} and {image_files[j]} are likely the same person (Distance: {similarity})")
                        else:
                            print(
                                f"  Images {image_files[i]} and {image_files[j]} are likely different people (Distance: {similarity})")
                            should_be_processed = False
                            break

            if len(faces) == 0 or len(faces[0]) == 0:
                continue

            if should_be_processed:
                context.to_be_processed.append((email, faces[0][0].id))
            else:
                context.to_be_rejected.append((email, faces[0][0].id))

        # Remove duplicates
        context.to_be_rejected = list(set(context.to_be_rejected))
        context.to_be_processed = list(set(context.to_be_processed))

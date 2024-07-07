import os
from deepface import DeepFace
from training.models.Context import Context
from training.service.Service import Service

class ValidationService(Service):

    def __init__(self):
        pass  # No initialization needed for DeepFace

    def detect_and_extract_face(self, image_path):
        try:
            # Detect and extract face using DeepFace
            # analysis = DeepFace.analyze(img_path=image_path, actions=['age', 'gender', 'race', 'emotion'], enforce_detection=True)
            face = DeepFace.detectFace(img_path=image_path, detector_backend='opencv')
            return face
        except Exception as e:
            print(f"No face detected in {image_path}: {str(e)}")
            return None

    def extract_features(self, image_path):
        try:
            # Extract features using DeepFace embeddings
            embeddings = DeepFace.represent(img_path=image_path, model_name='VGG-Face', enforce_detection=True)
            # DeepFace.represent returns a list of dictionaries, we need the embedding part
            if embeddings:
                return embeddings[0]["embedding"]
            return None
        except Exception as e:
            print(f"Could not extract features: {str(e)}")
            return None

    def compare_faces(self, features1, features2):
        # Compare faces using cosine similarity
        return DeepFace.verify(img1_path=features1, img2_path=features2, model_name='VGG-Face', distance_metric='cosine')['distance']

    def extract_faces(self, context: Context):
        extracted_faces = {}
        for folder_path, (email, image_files) in context.user_folders.items():
            faces = []
            for image_file in image_files:
                image_path = os.path.join(folder_path, image_file + ".jpg")
                face = self.detect_and_extract_face(image_path)
                if face is not None:
                    faces.append((image_file, image_path))
            extracted_faces[email] = faces
        return extracted_faces

    def do_service(self, context: Context):
        extracted_faces = self.extract_faces(context)
        for email, faces in extracted_faces.items():
            face_encodings = []
            image_files = []
            for image_file, image_path in faces:
                encoding = self.extract_features(image_path)
                if encoding:
                    face_encodings.append(encoding)
                    image_files.append(image_path)

            if all(encoding is not None for encoding in face_encodings):
                print(f"Face similarity for user with email {email}:")
                should_be_processed = True
                for i in range(len(face_encodings)):
                    for j in range(i + 1, len(face_encodings)):
                        similarity = self.compare_faces(image_files[i], image_files[j])
                        threshold = 0.4

                        if similarity < threshold:
                            print(f"  Images {image_files[i]} and {image_files[j]} are likely the same person (Distance: {similarity})")
                        else:
                            print(f"  Images {image_files[i]} and {image_files[j]} are likely different people (Distance: {similarity})")
                            should_be_processed = False

                if should_be_processed:
                    context.emails_to_be_processed.append(email)
                else:
                    context.emails_to_be_rejected.append(email)
            else:
                print(f"Not all images have detectable faces for user with email {email}.")
                context.emails_to_be_rejected.append(email)

        context.emails_to_be_rejected = list(set(context.emails_to_be_rejected))

import cv2
import json
import numpy


def load_email_id_map(file_path):
    with open(file_path, 'r') as f:
        email_id_map = json.load(f)
    return email_id_map


email_id_map = load_email_id_map('saved-models/email-id-map.json')


def detect_face(img):
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")

    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.2, minNeighbors=5)

    if (len(faces) == 0):
        return None, None

    (x, y, w, h) = faces[0]

    return gray[y:y + w, x:x + h], faces[0]


def draw_rectangle(img, rect):
    (x, y, w, h) = rect
    cv2.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)


def draw_text(img, text, x, y):
    cv2.putText(img, text, (x, y), cv2.FONT_HERSHEY_PLAIN, 1.5, (0, 255, 0), 2)


def predict_on_loaded_model(image_path, recognizer):
    # img = cv2.imdecode(numpy.fromstring(request.files['file'].read(), numpy.uint8), cv2.IMREAD_UNCHANGED)

    test_img = cv2.imread(image_path)
    img = test_img.copy()
    face, rect = detect_face(img)
    label = recognizer.predict(face)

    subjects = ["", "jamr.mat@gmail.com", "Elvis Presley"]
    label_text = subjects[label[0]]

    draw_rectangle(img, rect)
    draw_text(img, label_text, rect[0], rect[1] - 5)

    return label_text


def predict_on_loaded_model_from_request(img, recognizer):
    img = cv2.imdecode(numpy.fromstring(img.read(), numpy.uint8), cv2.IMREAD_UNCHANGED)

    # test_img = cv2.imread(image_path)
    # img = test_img.copy()
    face, rect = detect_face(img)
    label = recognizer.predict(face)

    return email_id_map[str(label[0])]

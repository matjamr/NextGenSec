from training.models.Context import Context
from training.repository.Repository import Repository
from training.service.CleanupService import CleanupService
from training.service.FilesService import FilesService
from training.service.TrainingService import TrainingService

repository = Repository()

services_list = [
    FilesService(repository),
    TrainingService(repository),
    CleanupService()
]

context = Context()

# Execute all services
[service.do_service(context) for service in services_list]


# print("Data prepared")
# print("Deleting assets....")
# repository.remove_assets()
#
# print("Total faces: ", len(faces))
# print("Total labels: ", len(labels))
#
#
# face_recognizer = cv2.face.LBPHFaceRecognizer_create()
# face_recognizer.train(faces, np.array(labels))
# face_recognizer.save("saved-models/face_recognition_model.yml")


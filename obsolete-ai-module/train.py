from training.models.Context import Context
from training.repository.Repository import Repository
from training.service.CleanupService import CleanupService
from training.service.FilesService import FilesService
from training.service.TrainingService import TrainingService
from training.service.ValidationService import ValidationService

repository = Repository()

services_list = [
    FilesService(repository),
    ValidationService(),
    TrainingService(repository),
    CleanupService()
]

context = Context()

[service.do_service(context) for service in services_list]



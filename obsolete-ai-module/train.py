import os
from dotenv import load_dotenv

from training.models.Context import Context, RoboticAccount
from training.repository.Repository import Repository
from training.service.CleanupService import CleanupService
from training.service.FilesService import FilesService
from training.service.StartupService import StartupService
from training.service.TrainingService import TrainingService
from training.service.ValidationService import ValidationService

load_dotenv()

repository = Repository()

services_list = [
    StartupService(),
    FilesService(repository),
    ValidationService(),
    TrainingService(repository),
    CleanupService()
]

context = Context()

[service.do_service(context) for service in services_list]



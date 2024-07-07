import os
from dotenv import load_dotenv

from training.models.Context import Context, RoboticAccount
from training.repository.Repository import Repository
from training.service.CleanupService import CleanupService
from training.service.FilesService import FilesService
from training.service.TrainingService import TrainingService
from training.service.ValidationService import ValidationService

load_dotenv()

repository = Repository()

services_list = [
    FilesService(repository),
    ValidationService(),
    TrainingService(repository),
    CleanupService()
]

context = Context()
context.robotic_account = RoboticAccount(os.getenv("ROBOTIC_EMAIL"), os.getenv("ROBOTIC_PASSWORD"))


[service.do_service(context) for service in services_list]



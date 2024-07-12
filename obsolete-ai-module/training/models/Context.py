from training.models.SensitiveData import SensitiveData
from training.service.ApiClient import APIClient


class Context:
    def __init__(self):
        self.user_folders: dict[str: tuple[str, list[SensitiveData]]] = {}
        self.robotic_account: RoboticAccount
        self.user_service_api_client: APIClient
        self.orchestration_service_api_client: APIClient
        self.to_be_rejected: list[tuple[str, str]] = []
        self.to_be_processed: list[tuple[str, str]] = []


class RoboticAccount:
    def __init__(self, email: str, password: str, token: str):
        self.email: str = email
        self.password: str = password
        self.token: str = token
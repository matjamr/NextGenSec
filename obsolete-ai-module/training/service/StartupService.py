import os

from training.models.Context import Context, RoboticAccount
from training.service.Service import Service
from training.service.ApiClient import APIClient


class StartupService(Service):

    def do_service(self, context: Context):
        email: str = os.getenv("ROBOTIC_EMAIL")
        password: str = os.getenv("ROBOTIC_PASSWORD")
        user_service_api_client = APIClient(os.getenv("USER_SERVICE_URL"), {"source": "JWT"})

        response = user_service_api_client.post(endpoint="api/user/security/token",
                                                json={"email": email, "password": password})

        context.robotic_account = RoboticAccount(os.getenv("ROBOTIC_EMAIL"),
                                                 os.getenv("ROBOTIC_PASSWORD"),
                                                 response['token'])

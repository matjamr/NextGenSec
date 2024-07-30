import os
import requests
from itertools import groupby
from training.models.Context import Context
from training.models.SensitiveData import SensitiveData
from training.repository.Repository import Repository
from training.service.Service import Service


class FilesService(Service):

    def __init__(self, repository: Repository):
        self._repository = repository

    def do_service(self, context: Context):
        sensitive_data: list[SensitiveData] = self._repository.fetch_sensitive_data()

        user_folders: dict[str: list[str]] = self.__download_images(sensitive_data)
        context.user_folders = user_folders

    def __download_images(self, sensitive_data: list[SensitiveData]):
        sorted_data = sorted(sensitive_data, key=lambda x: x.email)
        print(sorted_data)

        grouped_data = {email: list(group) for email, group in groupby(sorted_data, key=lambda x: x.email)}
        user_folders: dict[str: tuple[str, list[SensitiveData]]] = {}

        for i, (user_email, data_list) in enumerate(grouped_data.items(), 1):
            user_folder = f"training-data/{user_email}"

            if not os.path.exists(user_folder):
                os.makedirs(user_folder)

            user_data = []
            for data in data_list:
                self.__download_image(data, i, user_email, user_folder)
                user_data.append(data)

            user_folders[user_folder] = (user_email, user_data)

        return user_folders

    def __download_image(self, data: SensitiveData, i, user_email, user_folder):
        image_url = f"http://localhost:8080/api/image/{data.image_id}"
        response = requests.get(image_url)

        if response.status_code == 200:
            image_path = os.path.join(user_folder, f"{data.image_id}.jpg")

            with open(image_path, 'wb') as image_file:
                image_file.write(response.content)

            print(f"Downloaded image for user {i}, email {user_email} to {image_path}")

        else:
            print(f"Failed to download image for {data.sensitive_data_id}, status code: {response.status_code}")

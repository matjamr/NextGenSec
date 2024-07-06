import os
from itertools import groupby

import requests

from model.context import Context
from training.models.SensitiveData import SensitiveData
from training.repository.Repository import Repository
from training.service.Service import Service


class FilesService(Service):

    def __init__(self, repository: Repository):
        self._repository = repository

    def do_service(self, context: Context):
        sensitive_data: list[SensitiveData] = self._repository.fetch_sensitive_data()
        self.__download_images(sensitive_data)

    def __download_images(self, sensitive_data: list[SensitiveData]):
        sorted_data = sorted(sensitive_data, key=lambda x: x.email)
        grouped_data = {email: list(group) for email, group in groupby(sorted_data, key=lambda x: x.email)}

        for i, (user_email, data_list) in enumerate(grouped_data.items(), 1):
            user_folder = f"training-data/s{i}"

            if not os.path.exists(user_folder):
                os.makedirs(user_folder)

            for data in data_list:
                self.__download_image(data, i, user_email, user_folder)

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

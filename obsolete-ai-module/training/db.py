import os
import psycopg2
import requests
import shutil
from itertools import groupby
from psycopg2.extras import RealDictCursor
from training.models.SensitiveData import SensitiveData

conn = psycopg2.connect(database="postgres",
                        host="localhost",
                        user="postgres",
                        password="postgres",
                        port="5432")
cursor = conn.cursor(cursor_factory=RealDictCursor)


def fetch_sensitive_data() -> list[SensitiveData]:
    cursor.execute("""
        SELECT u.id, u.email, usd.sensitive_data_id, sd.image_id, p.name FROM users u
        INNER JOIN users_sensitive_data usd on u.id = usd.user_id
        INNER JOIN sensitive_data sd on sd.id = usd.sensitive_data_id
        INNER JOIN product p on sd.product_id = p.id
    """)
    ret = cursor.fetchall()

    cursor.close()
    conn.close()

    return [SensitiveData(**x) for x in ret]


def download_images(sensitive_data: list[SensitiveData]):
    sorted_sensitive_data = sorted(sensitive_data, key=lambda x: x.email)
    grouped_data = {email: list(group) for email, group in groupby(sorted_sensitive_data, key=lambda x: x.email)}

    for i, (user, data_list) in enumerate(grouped_data.items(), 1):
        user_folder = f"training-data/s{i}"

        if not os.path.exists(user_folder):
            os.makedirs(user_folder)

        for data in data_list:
            image_url = f"http://localhost:8080/api/image/{data.image_id}"
            response = requests.get(image_url)

            image_path = os.path.join(user_folder, f"{data.sensitive_data_id}.jpg")
            with open(image_path, 'wb') as image_file:
                image_file.write(response.content)

            print(f"Downloaded image for user {i}, email {user} to {image_path}")


def remove_assets():
    base_directory = "training-data"

    directories = [d for d in os.listdir(base_directory) if
                   os.path.isdir(os.path.join(base_directory, d)) and d.startswith('s')]

    print("List of existing directories:")
    for directory in directories:
        print(directory)

    num_directories = len(directories)
    print(f"\nTotal number of directories: {num_directories}")

    for directory in directories:
        user_folder = os.path.join(base_directory, directory)

        if os.path.exists(user_folder):
            shutil.rmtree(user_folder)
            print(f"Removed directory {user_folder}")
        else:
            print(f"Directory {user_folder} does not exist")

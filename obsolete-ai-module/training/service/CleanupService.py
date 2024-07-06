import os
import shutil

from model.context import Context
from training.service.Service import Service


class CleanupService(Service):
    def do_service(self, context: Context):
        base_directory = "training-data"
        directories = [d for d in os.listdir(base_directory) if
                       os.path.isdir(os.path.join(base_directory, d)) and not d.startswith('.')]

        print("List of existing directories:")
        for directory in directories:
            print(directory)

        print(f"\nTotal number of directories: {len(directories)}")

        for directory in directories:
            user_folder = os.path.join(base_directory, directory)
            if os.path.exists(user_folder):
                shutil.rmtree(user_folder)
                print(f"Removed directory {user_folder}")
            else:
                print(f"Directory {user_folder} does not exist")

class Context:
    def __init__(self):
        self.user_folders: dict[str: tuple[str, list[str]]] = {}


class Context:
    def __init__(self):
        self.user_folders: dict[str: tuple[str, list[str]]] = {}
        self.robotic_account: RoboticAccount


class RoboticAccount:
    def __init__(self, email: str, password: str):
        self.email: str = email
        self.password: str = password
        self.token: str
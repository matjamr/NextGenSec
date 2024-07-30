from abc import ABC, abstractmethod


class Service(ABC):

    @abstractmethod
    def do_service(self):
        pass

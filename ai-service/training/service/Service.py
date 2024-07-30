from abc import ABC, abstractmethod

from training.models.Context import Context


class Service(ABC):

    @abstractmethod
    def do_service(self, context: Context):
        pass

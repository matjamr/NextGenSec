from abc import ABC, abstractmethod

from model.context import Context


class Service(ABC):

    @abstractmethod
    def do_service(self, context: Context):
        pass

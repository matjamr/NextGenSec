from dataclasses import dataclass


@dataclass
class SensitiveData:
    id: int
    email: str
    sensitive_data_id: str
    image_id: int
    name: str
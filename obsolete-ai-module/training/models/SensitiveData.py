from dataclasses import dataclass


@dataclass
class SensitiveData:
    id: int
    email: str
    sensitive_data_id: str
    image_title: str
    product_id: str
    image_id: str

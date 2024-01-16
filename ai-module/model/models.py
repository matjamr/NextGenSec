from dataclasses import dataclass
from datetime import datetime


@dataclass
class Device:
    id: int
    device_name: str
    installment_time: datetime
    place_id: int
    product_id: int


@dataclass
class VerificationData:
    place_id: int
    assignment_role: str
    user_id: int
    email: str
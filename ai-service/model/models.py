from dataclasses import dataclass
from datetime import datetime


@dataclass
class Device:
    id: str
    device_name: str
    installment_time: datetime
    place_id: str
    product_id: str


@dataclass
class VerificationData:
    place_id: str
    assignment_role: str
    user_id: str
    email: str

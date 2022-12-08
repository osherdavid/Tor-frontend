from dataclasses import dataclass
from datalite.constraints import Unique
from data_base.data_base_initializer import table_class


@table_class.table_class
@dataclass
class UserCredential:
    id: Unique[int]
    username: Unique[str]
    password: str
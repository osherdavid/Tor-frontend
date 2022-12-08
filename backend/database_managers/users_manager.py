from data_base.data_base import DataBase
from models.user_credentials import UserCredential

class UserManager(object):

    def __init__(self) -> None:
        self.db = DataBase()

    def add_user(self, username, password):
        self.db.add_to_table(UserCredential.__name__, username=username, password=password)

    
    def verify_user(self, username, password):
        res = self.db.get_from_table(UserCredential.__name__, username=username)
        print(res)
        user = UserCredential(*res[0])
        print(user, password)
        return user.password == password
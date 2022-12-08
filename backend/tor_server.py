from flask import Flask
from database_managers.users_manager import UserManager
from sqlite3 import IntegrityError
import json

app = Flask(__name__)
user_manager = UserManager()

try:
    user_manager.add_user('Admin', 'admin')
except IntegrityError:
    print("User exists.")


@app.route("/users/<username>/<password>")
def verify_user(username, password):
    return json.dumps(user_manager.verify_user(username, password))


@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

if __name__ == '__main__':
    app.run('0.0.0.0')
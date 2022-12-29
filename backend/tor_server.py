import traceback
from flask import Flask, request
from database_managers.users_manager import UserManager
from sqlite3 import IntegrityError
import json

app = Flask(__name__)
user_manager = UserManager()

users = {
    'admin': '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', #admin
    'Osher': '01880507f74b75c97d592c22682353f205312b56a0126d0b74cc284456b2e238' #osh
}

try:
    user_manager.add_user('Admin', 'admin')
except IntegrityError:
    print("User exists.")


@app.get("/users/<username>/<password>")
def verify_user(username, password):
    res = False
    if username in users:
        res = users[username] == password
    return json.dumps(res)

@app.get("/users/<username>")
def is_user_exist(username):
    res = json.dumps(username in users)
    return res

@app.post("/users/<username>/<password>")
def register_user(username, password):
    data = request.get_json()
    users[data['username']] = data['password']
    print(users)
    return json.dumps(True)

@app.get("/")
def hello_world():
    return "<p>Hello, World!</p>"

if __name__ == '__main__':
    app.run('0.0.0.0')
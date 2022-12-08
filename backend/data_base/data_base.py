import sqlite3 as sql

class DataBase(object):

    _DB_PATH = './tor.db'

    def execute_query(self, query):
        conn = sql.connect(self._DB_PATH)
        curser = conn.execute(query)
        res = curser.fetchall()
        conn.commit()
        conn.close()
        return res

    def get_from_table(self, table, **kwargs):
        condition = ' AND '.join([f'{key} == "{kwargs[key]}"' for key in kwargs.keys()])
        query = f"SELECT * FROM {table} WHERE {condition};"
        print(query)
        return self.execute_query(query)

    def add_to_table(self, table, **kwargs):
        return self.execute_query(f"INSERT INTO {table} ({', '.join(kwargs.keys())}) VALUES ({', '.join([repr(kwargs[key]) for key in kwargs.keys()])})")

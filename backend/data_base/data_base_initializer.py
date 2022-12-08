from typing import Union, Tuple, T, Dict, Optional, Any
from .data_base import DataBase

Unique = Union[Tuple[T], T]

class DataBaseInitializer(object):
    type_table: Dict[Optional[type], str] = {None: "NULL", int: "INTEGER", float: "REAL",
                                         str: "TEXT", bytes: "BLOB"}
    type_table.update({Unique[key]: f"{value} NOT NULL UNIQUE" for key, value in type_table.items()})
    
    def __init__(self) -> None:
        self.db = DataBase()
        
    def _convert_type(self, type_: Optional[type]) -> str:
        """
        Given a Python type, return the str name of its
        SQLlite equivalent.
        :param type_: A Python type, or None.
        :param type_overload: A type table to overload the custom type table.
        :return: The str name of the sql type.
        >>> _convert_type(int)
        "INTEGER"
        """
        try:
            return self.type_table[type_]
        except KeyError:
            raise TypeError("Requested type not in the default or overloaded type table.")
    
    def _get_default(self, default_object: object) -> str:
        """
        Check if the field's default object is filled,
        if filled return the string to be put in the,
        database.
        :param default_object: The default field of the field.
        :param type_overload: Type overload table.
        :return: The string to be put on the table statement,
        empty string if no string is necessary.
        """
        if type(default_object) in self.type_table:
            return f' DEFAULT {self._convert_sql_format(default_object)}'
        return ""

    def _convert_sql_format(self, value: Any) -> str:
        """
        Given a Python value, convert to string representation
        of the equivalent SQL datatype.
        :param value: A value, ie: a literal, a variable etc.
        :return: The string representation of the SQL equivalent.
        >>> _convert_sql_format(1)
        "1"
        >>> _convert_sql_format("John Smith")
        '"John Smith"'
        """
        if value is None:
            return "NULL"
        elif isinstance(value, str):
            return f'"{value}"'
        elif isinstance(value, bytes):
            return '"' + str(value).replace("b'", "")[:-1] + '"'
        else:
            return str(value)

    def table_class(self, class_):
        dataclass_keys : Dict = class_.__dataclass_fields__
        dataclass_keys.pop('id')
        fields = [class_.__dataclass_fields__[key] if class_.__dataclass_fields__[key] != 'id' else None for
                           key in class_.__dataclass_fields__.keys()]
        #fields.sort(key=lambda field: field.name)  # Since dictionaries *may* be unsorted.
        sql_fields = ', '.join(f"{field.name} {self._convert_type(field.type)}"
                            f"{self._get_default(field.default)}" for field in fields)
        sql_fields = "id INTEGER PRIMARY KEY AUTOINCREMENT, " + sql_fields
        print(f"CREATE TABLE IF NOT EXISTS {class_.__name__.lower()} ({sql_fields});")
        self.db.execute_query(f"CREATE TABLE IF NOT EXISTS {class_.__name__.lower()} ({sql_fields});")
        return class_

table_class = DataBaseInitializer()
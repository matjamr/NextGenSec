import os
import psycopg2
import requests
import shutil
from itertools import groupby
from psycopg2.extras import RealDictCursor
from training.models.SensitiveData import SensitiveData
from training.repository.queries import sensitive_data_query


class Repository:
    def __init__(self, db_name="db", host="localhost", user="postgres", password="postgres", port="5432"):
        self.conn = psycopg2.connect(
            database=db_name,
            host=host,
            user=user,
            password=password,
            port=port
        )
        self.cursor = self.conn.cursor(cursor_factory=RealDictCursor)

    def execute(self, query: str):
        self.cursor.execute(query)
        response = self.cursor.fetchall()
        self.cursor.close()
        self.conn.close()
        return response

    def fetch_sensitive_data(self) -> list[SensitiveData]:
        response = self.execute(sensitive_data_query)
        print(response)
        return [SensitiveData(**record) for record in response]


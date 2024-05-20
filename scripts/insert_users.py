import datetime
import random
from dataclasses import dataclass
from typing import Dict, List, Optional

from faker import Faker

import mysql.connector


# dotenv

import os
from pathlib import Path
from dotenv import load_dotenv

dir_path = Path(__file__).parent
app_path = dir_path / ".." / "chatapp"
load_dotenv(dotenv_path=app_path / ".env")

# Database connection
mydb = mysql.connector.connect(
    host=os.getenv("MYSQL_HOST"),
    user=os.getenv("MYSQL_USER"),
    password=os.getenv("MYSQL_PASSWORD"),
    database=os.getenv("MYSQL_DATABASE"),
    port=os.getenv("MYSQL_PORT"),
)

@dataclass
class User:
    first_name: str
    last_name: str
    email: str
    password: str
    is_admin: bool
    created_at: str
    last_connection: Optional[str]
    failed_connection_attempts: Optional[int]
    is_locked: bool
    locked_at: Optional[str]

    def to_sql(self) -> str:
        return (
            f"INSERT INTO users (first_name, last_name, email, password, is_admin, created_at, last_connection, failed_connection_attempts, is_locked, locked_at)\n"
            f"VALUES ('{self.first_name}', '{self.last_name}', '{self.email}', '{self.password}', {self.is_admin}, '{self.created_at}', {self.last_connection}, {self.failed_connection_attempts}, {self.is_locked}, {self.locked_at});"
        )


class UserGenerator:
    """
    Generate fake user data for a database.

    Attributes:
        fake: A Faker instance to generate fake data.

    Methods:
        generate_user: Generate a fake user.
        to_sql: Convert a user to a SQL insert statement.
    """

    MAX_FAILED_CONNECTION_ATTEMPTS = 3

    def __init__(self):
        self.fake = Faker()

    def __next__(self) -> Dict:
        first_name = self.fake.first_name()
        last_name = self.fake.last_name()
        email = self.fake.unique.email()
        password = f"{{noop}}{self.fake.password()}"
        is_admin = random.choice([0, 1])
        created_at = self.fake.date_time_between(
            start_date=datetime.datetime(2020, 1, 1), end_date="now"
        ).strftime("%Y-%m-%d %H:%M:%S")
        last_connection = (
            self.fake.date_time_between(
                start_date=datetime.datetime(2020, 1, 1), end_date="now"
            ).strftime("%Y-%m-%d %H:%M:%S")
            if random.choice([0, 1])
            else "NULL"
        )
        failed_connection_attempts = (
            random.randint(0, self.MAX_FAILED_CONNECTION_ATTEMPTS)
            if random.choice([0, 1])
            else "NULL"
        )
        is_locked = (
            1
            if failed_connection_attempts == self.MAX_FAILED_CONNECTION_ATTEMPTS
            else 0
        )
        locked_at = (
            self.fake.date_time_between(
                start_date=datetime.datetime(2020, 1, 1), end_date="now"
            ).strftime("%Y-%m-%d %H:%M:%S")
            if is_locked
            else "NULL"
        )

        return User(
            first_name,
            last_name,
            email,
            password,
            is_admin,
            created_at,
            last_connection,
            failed_connection_attempts,
            is_locked,
            locked_at,
        )

    def __iter__(self):
        return self


def insert_users(file_path, num_users):
    user_generator = UserGenerator()
    # Generate 100 users and write them to a file
    with open(file_path, "w") as f:
        for _ in range(num_users):
            user = next(user_generator)
            f.write(user.to_sql())
            f.write("\n")



def main() -> None:
    insert_users("insert_users.sql", 100)
    // ajouter Delete * from users
    with open("insert_users.sql", "r") as f:
        print(f.read())
    


if __name__ == "__main__":
    main()

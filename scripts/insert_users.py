import datetime
import os
import random
from dataclasses import dataclass
from pathlib import Path
from typing import Dict, List, Optional

import mysql.connector
from dotenv import load_dotenv
from faker import Faker

DIR_PATH = Path(__file__).parent
APP_PATH = DIR_PATH / ".." / "chatapp"
load_dotenv(dotenv_path=APP_PATH / ".env")

# Database connection
DB_CONFIG = {
    "host": os.getenv("MYSQL_HOST"),
    "user": os.getenv("MYSQL_USER"),
    "password": os.getenv("MYSQL_PASSWORD"),
    "database": os.getenv("MYSQL_DATABASE"),
    "port": os.getenv("MYSQL_PORT"),
}


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
        return f"""
            INSERT INTO users (first_name, last_name, email, password, is_admin, created_at, last_connection, failed_connection_attempts, is_locked, locked_at)
            VALUES ('{self.first_name}', '{self.last_name}', '{self.email}', '{self.password}', {self.is_admin}, '{self.created_at}',
                {f"'{self.last_connection}'" if self.last_connection else "NULL"}, {self.failed_connection_attempts if self.failed_connection_attempts else "NULL"},
                {self.is_locked}, {f"'{self.locked_at}'" if self.locked_at else "NULL"});
        """.replace(
            "\n", ""
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
            else None
        )
        failed_connection_attempts = (
            random.randint(0, self.MAX_FAILED_CONNECTION_ATTEMPTS)
            if random.choice([0, 1])
            else None
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
            else None
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


def main() -> None:
    num_users = 100
    admin_user = User(
        first_name="Admin",
        last_name="User",
        email="admin@example.com",
        password="{noop}admin",
        is_admin=True,
        created_at=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        last_connection=None,
        failed_connection_attempts=None,
        is_locked=False,
        locked_at=None,
    )
    demo_user = User(
        first_name="Demo",
        last_name="User",
        email="demo@example.com",
        password="{noop}demo",
        is_admin=False,
        created_at=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        last_connection=None,
        failed_connection_attempts=None,
        is_locked=False,
        locked_at=None,
    )
    with mysql.connector.connect(**DB_CONFIG) as conn:
        with conn.cursor() as cursor:
            cursor.execute("SET FOREIGN_KEY_CHECKS=0;")
            cursor.execute("TRUNCATE TABLE users;")

            cursor.execute(admin_user.to_sql())
            cursor.execute(demo_user.to_sql())

            for _ in range(num_users):
                user = next(UserGenerator())
                cursor.execute(user.to_sql())

            cursor.execute("SET FOREIGN_KEY_CHECKS=1;")
            conn.commit()

    print(f"Succesfully inserted {num_users + 2} users.")


if __name__ == "__main__":
    main()

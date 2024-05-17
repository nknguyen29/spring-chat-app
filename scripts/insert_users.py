import random
from faker import Faker
import datetime

# Initialiser Faker
fake = Faker()

# Générer 100 entrées d'utilisateurs aléatoires
users = []
for i in range(1, 101):
    id = i
    email = fake.email()
    first_name = fake.first_name()
    last_name = fake.last_name()
    is_admin = random.choice([0, 1])
    password = fake.password()
    created_at = fake.date_time_between(datetime.datetime(2020, 1, 1), 'now').strftime('%Y-%m-%d %H:%M:%S')
    last_connection = fake.date_time_between(datetime.datetime(2020, 1, 1), 'now').strftime('%Y-%m-%d %H:%M:%S') if random.choice([True, False]) else 'NULL'
    failed_connection_attempts = random.randint(0, 5)
    is_locked = 1 if failed_connection_attempts >= 3 else 0

    users.append((id, email, first_name, is_admin, last_name, password, created_at, last_connection, failed_connection_attempts, is_locked))

# Générer les instructions SQL d'insertion
insert_statements = []
for user in users:
    insert_statement = f"""
    INSERT INTO users (id, email, first_name, is_admin, last_name, password, created_at, last_connection, failed_connection_attempts, is_locked) 
    VALUES ({user[0]}, '{user[1]}', '{user[2]}', {user[3]}, '{user[4]}', '{user[5]}', '{user[6]}', {f"'{user[7]}'" if user[7] != 'NULL' else 'NULL'}, {user[8]}, {user[9]});
    """
    insert_statements.append(insert_statement.strip())

# Enregistrer les instructions SQL dans un fichier .txt
with open('insert_users.sql', 'w') as file:
    for statement in insert_statements:
        file.write(statement + '\n')

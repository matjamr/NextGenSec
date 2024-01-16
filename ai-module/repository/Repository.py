from model.models import Device, VerificationData

import psycopg2
from psycopg2.extras import RealDictCursor

conn = psycopg2.connect(database="postgres",
                        host="localhost",
                        user="postgres",
                        password="postgres",
                        port="5432")
cursor = conn.cursor(cursor_factory=RealDictCursor)


class Repository:
    def __init__(self, db):
        self.__db__ = db

    def get_all(self):
        cursor.execute("SELECT * FROM places")
        return cursor.fetchall()

    def get_device_by_id(self, id: int) -> Device:
        cursor.execute("SELECT * FROM device d WHERE d.id=" + str(id))
        ret = cursor.fetchone()
        return Device(**ret)

    def get_place_verification_data_by_place_id(self, place_id: int, email: str) -> list[VerificationData]:
        try:
            print("""
                SELECT 
                    p.id as place_id,  
                    upa.assignment_role as assignment_role,
                    u.id as user_id,
                    u.email as email
                FROM places p INNER JOIN places_authorized_users pau on p.id = pau.places_id
                INNER JOIN user_place_assignment upa on pau.authorized_users_id = upa.id
                INNER JOIN users u on upa.user_id = u.id
                WHERE p.id = """ + str(place_id) + """
                && u.email = \'""" + email + "\'")

            cursor.execute("""
                SELECT 
                    p.id as place_id,  
                    upa.assignment_role as assignment_role,
                    u.id as user_id,
                    u.email as email
                FROM 
                    places p 
                    INNER JOIN places_authorized_users pau on p.id = pau.places_id
                    INNER JOIN user_place_assignment upa on pau.authorized_users_id = upa.id
                    INNER JOIN users u on upa.user_id = u.id
                WHERE 
                    p.id = """ + str(place_id) + """ AND u.email = \'""" + email + "\';"
                           )

            ret = cursor.fetchall()
            return [VerificationData(**x) for x in ret]
        except Exception as e:
            print(e)
            conn.commit()
            raise Exception("no usero")

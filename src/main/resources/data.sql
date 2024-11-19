INSERT INTO city (name) VALUES ('New York') ON CONFLICT DO NOTHING;
INSERT INTO city (name) VALUES ('Los Angeles') ON CONFLICT DO NOTHING;
INSERT INTO city (name) VALUES ('San Diego') ON CONFLICT DO NOTHING;

INSERT INTO person (name, surname, age, phone_number, city_id)
VALUES
    ('John', 'Doe', 30, '1234567890', 1),
    ('Jane', 'Smith', 25, '0987654321', 1),
    ('Alice', 'Johnson', 40, '1122334455', 2),
    ('Dug', 'Brown', 35, '6677889900', 2),
    ('Tom', 'DeLonge', 50, '1122334455', 3),
    ('Mark', 'Hoppus', 50, '6677889900', 3)
ON CONFLICT DO NOTHING;
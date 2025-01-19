CREATE TABLE IF NOT EXISTS problem_area_type (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(20) NOT NULL UNIQUE
);

INSERT INTO problem_area_type (id, name)
VALUES
    (1, 'Боржевик'),
    (2, 'Пожар'),
    (3, 'Свалка');

CREATE TABLE IF NOT EXISTS elimination_method (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(20) NOT NULL UNIQUE,
    problem_area_type_id INT NOT NULL,
    FOREIGN KEY (problem_area_type_id) REFERENCES problem_area_type (id)
);

INSERT INTO elimination_method (name, problem_area_type_id)
VALUES
    ('Химический', 1),
    ('Механический', 1),
    ('Биолгически', 1),
    ('Механически водой', 2),
    ('Изоляция от воздуха', 2),
    ('Вывоз мусора', 3);

CREATE TABLE IF NOT EXISTS land_type (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(20) NOT NULL UNIQUE
);

INSERT INTO land_type (id, name)
VALUES
    (1, 'Сельскохозяйственный'),
    (2, 'Жилой'),
    (3, 'Промышленный'),
    (4, 'Коммерческий'),
    (5, 'Развлекательный');

CREATE TABLE IF NOT EXISTS work_stage (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(20) NOT NULL UNIQUE
);

INSERT INTO work_stage (id, name)
VALUES
    (1, 'Создано'),
    (2, 'В работе'),
    (3, 'Выполнено');

CREATE TABLE IF NOT EXISTS geospatial (
    id uuid NOT NULL PRIMARY KEY,
    x_coordinate FLOAT NOT NULL,
    y_coordinate FLOAT NOT NULL,
    square FLOAT,
    owner varchar(255) NOT NULL,
    land_type_id INT NOT NULL,
    contracting_organization varchar(255) NOT NULL,
    work_stage_id INT NOT NULL,
    elimination_method_id INT NOT NULL,
    images JSONB,
    problem_area_type_id INT NOT NULL,
    comment varchar(255) NOT NULL,
    related_task_id uuid,
    coordinates JSONB,
    FOREIGN KEY (elimination_method_id) REFERENCES elimination_method  (id),
    FOREIGN KEY (problem_area_type_id) REFERENCES problem_area_type (id),
    FOREIGN KEY (land_type_id) REFERENCES land_type (id),
    FOREIGN KEY (work_stage_id) REFERENCES work_stage (id)
);

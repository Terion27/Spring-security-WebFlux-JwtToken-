
CREATE TABLE users
(
    id bigserial primary key,
    username varchar(64),
    password varchar(64),
    email varchar(64),
    firstName varchar(64),
    lastName varchar(64),
    telephone varchar(64),
    registrationDate timestamp,
    status boolean,
    visibility boolean,
    role varchar(64)
);

CREATE TABLE CUSTOMER
(
    id        serial PRIMARY KEY,
    firstName VARCHAR(50) UNIQUE  NOT NULL,
    lastName  VARCHAR(50)         NOT NULL,
    email     VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE CUSTOMER
(
    id        serial PRIMARY KEY,
    firstName VARCHAR(50) UNIQUE  NOT NULL,
    lastName  VARCHAR(50)         NOT NULL,
    email     VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO CUSTOMER
VALUES (123, 'Scott', 'Tiger', 'scott.tiger@in.com');
INSERT INTO CUSTOMER
VALUES (456, 'John', 'Doe', 'John.Doe@out.com');

select *
from CUSTOMER;

CREATE TABLE IF NOT EXISTS tb_employees(
    name varchar(30) not null,
    lastname varchar(30) not null,
    document_type varchar(25) not null,
    document_number varchar(20) not null,
    birth_date date not null,
    entailment_date date not null,
    role varchar(50) not null,
    salary double not null,
    constraint pk_constrain primary key(document_number)
);
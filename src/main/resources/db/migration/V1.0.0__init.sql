create table user_account
(
    id                   uuid                  not null primary key,
    first_name           varchar(50)           not null,
    last_name            varchar(80)           not null,
    username             varchar(36)           not null,
    email                varchar(255)          not null,
    age                  integer               not null,
    password             varchar(255)          not null,
    active               boolean               not null,
    version              integer               not null,
    created_at           timestamp             not null,
    modified_at          timestamp             not null,
    constraint UQ__USERNAME unique (username),
    constraint UQ__EMAIL unique (email)
);
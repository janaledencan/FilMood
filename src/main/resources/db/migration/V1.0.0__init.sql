create table user_account
(
    id                   uuid                  not null primary key,
    first_name           varchar(50)           not null,
    last_name            varchar(80)           not null,
    username             varchar(36)           not null,
    email                varchar(255)          not null,
    age                  integer               not null,
    gender               varchar(10)           not null,
    password             varchar(255)          not null,
    active               boolean               not null,
    version              integer               not null,
    created_at           timestamp             not null,
    modified_at          timestamp             not null,
    constraint UQ__USERNAME unique (username),
    constraint UQ__EMAIL unique (email)
);

create table genre
(
    id                   uuid                  not null primary key,
    name                 varchar(20)           not null,
    genre_id             integer               not null,
    version              integer               not null,
    created_at           timestamp             not null,
    modified_at          timestamp             not null,
    constraint UQ__GENRE_ID unique (genre_id)
);

create table movie
(
    id                   uuid                  not null primary key,
    movie_id             integer               not null,
    user_id              uuid                  not null,
    genres               varchar(100)          not null,
    title                varchar(100)          not null,
    year                 integer               not null,
    vote_average         real                  not null,
    user_rating          integer               not null,
    poster_path          varchar(100),
    version              integer               not null,
    created_at           timestamp             not null,
    modified_at          timestamp             not null,
    constraint FK__MOVIE__USER_ID foreign key (user_id) references user_account (id)
);
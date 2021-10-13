create table petprojectschema.user
(
    id          int8 generated by default as identity primary key,
    email       varchar(255) not null unique,
    password    varchar(255) not null,
    name        varchar(255) not null,
    lastname    varchar(255) not null,
    phone       varchar(255) not null,
    birthday    timestamp    not null,
    status      varchar(255),
    address     varchar(255),
    about       text

);

create table petprojectschema.article
(
    id          int8 generated by default as identity primary key,
    title       varchar(255) not null,
    author      petprojectschema.user not null,
    date        timestamp not null,
    text        text not null

);

create table petprojectschema.comment
(
    id          int8 generated by default as identity primary key,
    text        text not null,
    author      petprojectschema.user not null,
    date        timestamp not null

);

create table petprojectschema.user_and_article_marks
(
    user_id     int8 not null,
    article_id  int8 not null,
    mark        int not null,
    primary key (article_id, user_id)
);

create table petprojectschema.article_and_comment
(
    article_id     int8 not null,
    comment_id  int8 not null,
    primary key (comment_id, article_id)
);

create table petprojectschema.user_and_comment_likes
(
    user_id     int8 not null,
    comment_id  int8 not null,
    primary key (comment_id, user_id)
);

create table petprojectschema.user_and_comment_dislikes
(
    user_id     int8 not null,
    comment_id  int8 not null,
    primary key (comment_id, user_id)
);
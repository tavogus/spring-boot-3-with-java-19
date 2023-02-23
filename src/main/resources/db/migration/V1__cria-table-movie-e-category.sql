create table category
(
    id   bigint      not null auto_increment,
    name varchar(100) not null,
    primary key (id)
);

create table movie
(
    id             bigint      not null auto_increment,
    title          varchar(60) not null,
    category_id    bigint      not null,
    insertion_date datetime    not null,
    release_date   int         not null,
    author         varchar(60) not null,
    synopsis       text        not null,
    primary key (id)
);

create table tv_show
(
    id             bigint      not null auto_increment,
    title          varchar(60) not null,
    category_id    bigint      not null,
    insertion_date datetime    not null,
    release_date   int         not null,
    author         varchar(60) not null,
    synopsis       text        not null,
    seasons        int         not null,
    primary key (id)
);

create table actor
(
    id   bigint      not null auto_increment,
    name varchar(100) not null,
    primary key (id)
);

create table movie_actor
(
    movie_id bigint not null,
    actor_id bigint not null
);

create table tvshow_actor
(
    tvshow_id bigint not null,
    actor_id  bigint not null
);

create table movie_tags
(
    movie_id bigint not null,
    tags     text   not null
);

create table tv_show_tags
(
    tv_show_id bigint not null,
    tags       text   not null
);

alter table movie_actor
    add constraint pk_movieactor
        primary key (movie_id, actor_id);

alter table movie
    add constraint fk_movie_category
        foreign key (category_id) references category (id);

alter table tv_show
    add constraint fk_tv_show_category
        foreign key (category_id) references category (id);

alter table movie_actor
    add constraint fk_movieactor_movie
        foreign key (movie_id) references movie (id);

alter table movie_actor
    add constraint fk_movieactor_actor
        foreign key (actor_id) references actor (id);

alter table movie_tags
    add constraint fk_movie_tags
        foreign key (movie_id) references movie (id);

alter table tv_show_tags
    add constraint fk_tvshow_tags
        foreign key (tv_show_id) references tv_show (id);
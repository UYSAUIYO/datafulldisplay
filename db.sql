create table full_coordinate
(
    coordinate_id    int auto_increment
        primary key,
    x                int                     null,
    y                int                     null,
    coordinate_local char                    null,
    coordinate_type  varchar(6) default 'ok' null
);

create table full_humidity
(
    humidity_id    int auto_increment
        primary key,
    hum            double null,
    humidity_local char   null
);

create table full_light
(
    light_id    int auto_increment
        primary key,
    light       double null,
    light_local char   null
);

create table full_light
(
    light_id    int auto_increment
        primary key,
    light       double null,
    light_local char   null
);

create table full_temperature
(
    temperature_id    int auto_increment
        primary key,
    tem               double null,
    temperature_local char   null
);

create table full_person_num
(
    num_id int auto_increment
        primary key,
    num int null,
    upload_time timestamp default current_timestamp
);
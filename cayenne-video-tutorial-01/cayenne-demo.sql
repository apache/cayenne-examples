create table cayenne.gallery (gallery_id int not null auto_increment, gallery_name varchar(100) not null, primary key (gallery_id)) engine=innodb;
create table cayenne.artist (artist_id bigint not null auto_increment, artist_name char(254) not null, date_of_birth date null, primary key (artist_id)) engine=innodb;
create table cayenne.exhibit (closing_date datetime not null, exhibit_id int not null auto_increment, gallery_id int not null, opening_date datetime not null, primary key (exhibit_id)) engine=innodb;
create table cayenne.painting (artist_id bigint null, estimated_price decimal(16, 2) null, painting_description varchar(255) null, painting_id int not null auto_increment, painting_title varchar(255) not null, primary key (painting_id)) engine=innodb;
create table cayenne.painting_info (image_blob longblob null, painting_id int not null, review longtext null, primary key (painting_id)) engine=innodb;
create table cayenne.painting_exhibit (exhibit_id int not null, painting_id int not null, primary key (exhibit_id, painting_id)) engine=innodb;

alter table cayenne.exhibit add foreign key (gallery_id) references cayenne.gallery (gallery_id);
alter table cayenne.painting add foreign key (artist_id) references cayenne.artist (artist_id);
alter table cayenne.painting_info add foreign key (painting_id) references cayenne.painting (painting_id);
alter table cayenne.painting_exhibit add foreign key (exhibit_id) references cayenne.exhibit (exhibit_id);
alter table cayenne.painting_exhibit add foreign key (painting_id) references cayenne.painting (painting_id);

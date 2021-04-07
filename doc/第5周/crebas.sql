/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/4/1 20:37:43                            */
/*==============================================================*/


drop table if exists admin;

drop table if exists check_tab;

drop table if exists class_tab;

drop table if exists dic_data;

drop table if exists dic_type;

drop table if exists exp;

drop table if exists menu;

drop table if exists role_tab;

drop table if exists sign_in;

drop table if exists sign_in_memory;

drop table if exists task;

drop table if exists task_memory;

drop table if exists user_tab;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   id                   int not null,
   name                 varchar(10),
   account              varchar(10),
   password             varchar(20),
   primary key (id)
);

/*==============================================================*/
/* Table: check_tab                                               */
/*==============================================================*/
create table check_tab
(
   id                   int not null,
   user_tab_check_tab_id     int,
   type                 int,
   thirdparty           int,
   identify             varchar(20),
   passwordtoken        varchar(20),
   creationdate         datetime,
   creater              int,
   modifydate           datetime,
   modifier             int,
   primary key (id)
);

/*==============================================================*/
/* Table: class_tab                                              */
/*==============================================================*/
create table class_tab
(
   classid              int not null,
   classname            varchar(16),
   teachername          varchar(16),
   teacherid            int,
   type                 varchar(16),
   master               varchar(16),
   people_count         int,
   location             varchar(32),
   classtime            varchar(16),
   college              varchar(32),
   primary key (classid)
);

/*==============================================================*/
/* table: dic_data                                               */
/*==============================================================*/
create table dic_data
(
   id                   int not null auto_increment,
   dic_type             varchar(20),
   dic_key              varchar(20),
   dic_value            varchar(20),
   defaultvalue         int(1),
   primary key (id)
);

/*==============================================================*/
/* table: dic_type                                               */
/*==============================================================*/
create table dic_type
(
   id                   int not null auto_increment,
   paraname             varchar(20),
   code                 int(20),
   description          varchar(20),
   identity             int(20),
   creator              int(20),
   creatordate          datetime,
   modificationdate     datetime,
   modifier             int(20),
   primary key (id)
);

/*==============================================================*/
/* table: exp                                                   */
/*==============================================================*/
create table exp
(
   id              varchar(10) not null,
   name                 varchar(10),
   expvalue             integer(10),
   classid              int,
   primary key (id)
);

/*==============================================================*/
/* table: menu                                                  */
/*==============================================================*/
create table menu
(
   id                   integer not null,
   parentmenu_number     integer(10) not null,
   menuname             varchar(15) not null,
   button_english_logo    varchar(15) not null,
   button_chinese_logo    varchar(15),
   is_menu               integer(1),
   creator              varchar(15),
   creationdate         datetime,
   modificationdate     datetime,
   modifier             varchar(15),
   primary key (id)
);

/*==============================================================*/
/* table: role_tab                                               */
/*==============================================================*/
create table role_tab
(
   id                   int not null,
   name                 varchar(10),
   menu_id               int,
   primary key (id)
);

/*==============================================================*/
/* table: sign_in                                                */
/*==============================================================*/
create table sign_in
(
   userid               int not null,
   sign_in_type           int,
   enddate              datetime,
   publisher            varchar(15) not null,
   classid              int,
   startdate            datetime,
   primary key (userid)
);

/*==============================================================*/
/* table: sign_in_memory                                          */
/*==============================================================*/
create table sign_in_memory
(
   id                   int not null,
   startdate            datetime,
   enddate             datetime,
   sign_in_type           varchar(20),
   number               varchar(10),
   state                int,
   name                 varchar(20),
   primary key (id)
);

/*==============================================================*/
/* table: task                                                  */
/*==============================================================*/
create table task
(
   id                   int not null,
   userid               int,
   grade                int,
   task                 varchar(1000),
   class_id              int,
   team                 int,
   deadline             datetime,
   primary key (id)
);

/*==============================================================*/
/* table: task_memory                                            */
/*==============================================================*/
create table task_memory
(
   id                   int not null,
   userid               int not null,
   is_participate        int,
   grade                int,
   task                 varchar(1000),
   classid              int,
   team                 int,
   deadline             datetime,
   primary key (id)
);

/*==============================================================*/
/* table: user_tab                                               */
/*==============================================================*/
create table user_tab
(
   id                   int not null,
   number               varchar(10),
   password             varchar(20),
   name                 varchar(20),
   image                mediumblob,
   nickname             varchar(20),
   sex                  int,
   school               varchar(20),
   department           varchar(30),
   master               varchar(40),
   phonenumber          varchar(11),
   role                 varchar(20),
   createdate           datetime,
   creator              int,
   modifier             int,
   modify_date           datetime,
   primary key (id)
);

alter table check_tab add constraint fk_reference_5 foreign key (user_tab_check_tab_id)
      references user_tab (id) on delete restrict on update restrict;

alter table sign_in add constraint fk_reference_3 foreign key (userid)
      references user_tab (id) on delete restrict on update restrict;

alter table sign_in_memory add constraint fk_reference_4 foreign key (id)
      references user_tab (id) on delete restrict on update restrict;

alter table task add constraint fk_reference_1 foreign key (userid)
      references user_tab (id) on delete restrict on update restrict;

alter table task_memory add constraint fk_reference_2 foreign key (userid)
      references user_tab (id) on delete restrict on update restrict;


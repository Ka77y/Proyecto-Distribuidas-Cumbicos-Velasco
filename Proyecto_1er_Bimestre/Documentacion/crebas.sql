/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     23/01/2016 Pata  8:41:38                     */
/*==============================================================*/


drop index RELATIONSHIP_1_FK;

drop index CLIENTE_PK;

drop table CLIENTE;

drop index RELATIONSHIP_3_FK;

drop index REGISTROS_PK;

drop table REGISTROS;

drop index RELATIONSHIP_2_FK;

drop index SALDOS_PK;

drop table SALDOS;

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE (
   CEDULA               CHAR(10)             not null,
   ID_SALDO             CHAR(10)             null,
   NOMBRE               CHAR(10)             null,
   APELLIDO             CHAR(10)             null,
   TELEFONO             INT4                 null,
   DIRECCION            CHAR(20)             null,
   CONTRASENA           CHAR(20)             null,
   constraint PK_CLIENTE primary key (CEDULA)
);

/*==============================================================*/
/* Index: CLIENTE_PK                                            */
/*==============================================================*/
create unique index CLIENTE_PK on CLIENTE (
CEDULA
);

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_1_FK on CLIENTE (
ID_SALDO
);

/*==============================================================*/
/* Table: REGISTROS                                             */
/*==============================================================*/
create table REGISTROS (
   ID_REGISTROS         CHAR(10)             not null,
   CEDULA               CHAR(10)             null,
   TIPO_TRANSACCION     INT4                 null,
   FECHA                DATE                 null,
   MONTO                DECIMAL(10,3)        null,
   constraint PK_REGISTROS primary key (ID_REGISTROS)
);

/*==============================================================*/
/* Index: REGISTROS_PK                                          */
/*==============================================================*/
create unique index REGISTROS_PK on REGISTROS (
ID_REGISTROS
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_3_FK on REGISTROS (
CEDULA
);

/*==============================================================*/
/* Table: SALDOS                                                */
/*==============================================================*/
create table SALDOS (
   ID_SALDO             CHAR(10)             not null,
   CEDULA               CHAR(10)             null,
   SALDO                DECIMAL(10,3)        null,
   constraint PK_SALDOS primary key (ID_SALDO)
);

/*==============================================================*/
/* Index: SALDOS_PK                                             */
/*==============================================================*/
create unique index SALDOS_PK on SALDOS (
ID_SALDO
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on SALDOS (
CEDULA
);

alter table CLIENTE
   add constraint FK_CLIENTE_RELATIONS_SALDOS foreign key (ID_SALDO)
      references SALDOS (ID_SALDO)
      on delete restrict on update restrict;

alter table REGISTROS
   add constraint FK_REGISTRO_RELATIONS_CLIENTE foreign key (CEDULA)
      references CLIENTE (CEDULA)
      on delete restrict on update restrict;

alter table SALDOS
   add constraint FK_SALDOS_RELATIONS_CLIENTE foreign key (CEDULA)
      references CLIENTE (CEDULA)
      on delete restrict on update restrict;


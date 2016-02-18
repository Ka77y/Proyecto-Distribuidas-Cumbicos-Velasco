CREATE TABLE CLIENTE
(
  id_cliente SERIAL,
  cedula integer NOT NULL,
  nombre character(30) NOT NULL,
  telefono character(12),
  direccion character(30),
  password character(20) NOT NULL,
  CONSTRAINT pk_cliente1 PRIMARY KEY (id_cliente)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE CLIENTE
  OWNER TO postgres;
CREATE UNIQUE INDEX pk_cliente
  ON CLIENTE(id_cliente);

------------------------------------------------------------------------
CREATE TABLE TIPO_TRANSACCION
(
  id_tipo SERIAL,
  nombre character(30) NOT NULL,
  CONSTRAINT pk_tipo_transaccion PRIMARY KEY (id_tipo)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE TIPO_TRANSACCION
  OWNER TO postgres;
CREATE UNIQUE INDEX pk_tipo_transaccion1
  ON TIPO_TRANSACCION(id_tipo);

------------------------------------------------------------------------
CREATE TABLE TRANSACCION
(
  id_transaccion SERIAL,
  id_cliente integer NOT NULL,
  id_tipo integer NOT NULL,
  monto numeric(10,3),
  saldo numeric(10,3),
  fecha timestamp without time zone DEFAULT now(),  
  CONSTRAINT pk_transaccion PRIMARY KEY (id_transaccion),  
  CONSTRAINT fk_transaccion_relations_cliente FOREIGN KEY (id_cliente)
      REFERENCES CLIENTE (id_cliente) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,

  CONSTRAINT fk_transaccion_relations_tipo FOREIGN KEY (id_tipo)
      REFERENCES TIPO_TRANSACCION (id_tipo) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE TRANSACCION
  OWNER TO postgres;
CREATE UNIQUE INDEX transacciones_pk
  ON TRANSACCION(id_transaccion);
  
CREATE INDEX relationship_1_fk
  ON CLIENTE(id_cliente);

CREATE INDEX relationship_2_fk
  ON TIPO_TRANSACCION(id_tipo);

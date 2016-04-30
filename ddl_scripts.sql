-- Disclaimer: this file does not define settings of your JDBC connection. See @file persistence.xml

-- This file contains all of the DDL commands required for a proper work of this application.
-- If you are installing this project on a fresh server, please firstly run those commands on your database.
-- Version of SQL used in the development: Oracle Database 10g Enterprise Edition Release 10.2.0.1.0

-- Business logic: User performs login operation with a pair of login and password. Name displayed in a system
-- may be different if so is specified during the registration process or edit account information process.
-- Users(user_id /* generated automatically, primary key */, login /* unique, not null */, user_name, password /* not null */
BEGIN
  EXECUTE IMMEDIATE 'drop table Users';
  EXCEPTION
  WHEN OTHERS THEN
  IF sqlcode != -942 /* ORA-00942: table or view does not exist */
  THEN
    RAISE;
  END IF;
END;

CREATE TABLE Users (
  user_id   NUMBER(10, 0) PRIMARY KEY,
  login     VARCHAR2(200) UNIQUE NOT NULL,
  user_name VARCHAR2(200),
  password  VARCHAR2(200)        NOT NULL
);

BEGIN
  EXECUTE IMMEDIATE 'drop sequence user_ids_seq';
  EXCEPTION
  WHEN OTHERS THEN
  IF sqlcode != -2289 /* ORA-02289: sequence does not exist */
  THEN
    RAISE;
  END IF;
END;

CREATE SEQUENCE user_ids_seq;

-- Use of certain fields and constraints for Connections table is necessary due to API of ssh module.
-- e.g. Fields login, password, host are required -> corresponding table columns are marked as not null.
-- Connections(connection_id /* generated automatically, primary key */, user_id /* foreign key referencing Users(user_id) */,
-- login /* not null */, password /* not null */, host_name /* not null */, port)
BEGIN
  EXECUTE IMMEDIATE 'drop table Connections';
  EXCEPTION
  WHEN OTHERS THEN
  IF sqlcode != -942 /* ORA-00942: table or view does not exist */
  THEN
    RAISE;
  END IF;
END;

CREATE TABLE Connections (
  connection_id NUMBER(10, 0) PRIMARY KEY,
  user_id       NUMBER(10, 0) REFERENCES Users (user_id) NOT NULL,
  login         VARCHAR2(200)                            NOT NULL,
  password      VARCHAR2(200)                            NOT NULL,
  host_name     VARCHAR2(200)                            NOT NULL,
  port          NUMBER(5, 0) DEFAULT 22
);

BEGIN
  EXECUTE IMMEDIATE 'drop sequence connection_ids_seq';
  EXCEPTION
  WHEN OTHERS THEN
  IF sqlcode != -2289 /* ORA-02289: sequence does not exist */
  THEN
    RAISE;
  END IF;
END;

CREATE SEQUENCE connection_ids_seq;

CREATE OR REPLACE TRIGGER connection_null_port_trig
BEFORE UPDATE ON Connections
FOR EACH ROW
  BEGIN
    IF :new.port IS NULL
    THEN
      :new.port := 22;
    END IF;
  END;

-- Further additions to the tables structure must include creating new tables. Do not modify existing tables!

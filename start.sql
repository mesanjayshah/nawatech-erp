CREATE DATABASE db_master;

USE db_master;

CREATE TABLE IF NOT EXISTS master_tenant (
                              tenant_id VARCHAR(100) PRIMARY KEY,
                              db_url VARCHAR(255),
                              db_username VARCHAR(100),
                              db_password VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS permission_template (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS role_template (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS role_permissions (
                                                role_id BIGINT NOT NULL,
                                                permission_id BIGINT NOT NULL,
                                                PRIMARY KEY (role_id, permission_id)
);
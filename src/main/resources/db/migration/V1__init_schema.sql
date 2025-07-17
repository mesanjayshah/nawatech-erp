-- db/migration/V1__init_schema.sql
CREATE TABLE IF NOT EXISTS permissions (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS role_permissions (
                                                role_id BIGINT NOT NULL,
                                                permission_id BIGINT NOT NULL,
                                                PRIMARY KEY (role_id, permission_id)
    );

-- Insert permissions
INSERT INTO permissions (name) VALUES ('dashboard:read');
INSERT INTO permissions (name) VALUES ('user:write');
INSERT INTO permissions (name) VALUES ('product:read');
INSERT INTO permissions (name) VALUES ('product:create');
INSERT INTO permissions (name) VALUES ('product:update');
INSERT INTO permissions (name) VALUES ('product:delete');

-- Insert roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');

-- Map roles to permissions
-- ADMIN: All permissions
INSERT INTO role_permissions (role_id, permission_id) SELECT 1, id FROM permissions;

-- USER: Limited permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, id FROM permissions WHERE name IN ('dashboard:read', 'product:read');
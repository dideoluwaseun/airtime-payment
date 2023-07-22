
CREATE TABLE application_users (
                          id bigint AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(255) NOT NULL UNIQUE,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL
)
ENGINE=InnoDB;

CREATE TABLE user_roles (
                            user_id bigint NOT NULL,
                            role ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL,
                            PRIMARY KEY (user_id, role),
                            FOREIGN KEY (user_id) REFERENCES application_users(id)
)
ENGINE=InnoDB;
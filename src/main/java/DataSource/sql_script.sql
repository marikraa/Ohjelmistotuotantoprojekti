DROP DATABASE IF EXISTS notes_app_db;
CREATE DATABASE IF NOT EXISTS notes_app_db;
USE notes_app_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_picture_url VARCHAR(255),
    language_code VARCHAR(2) DEFAULT 'EN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    body TEXT,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notification_time TIMESTAMP,
    notification_shown BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, ALTER ON notes_app_db.* TO 'appuser'@'localhost';
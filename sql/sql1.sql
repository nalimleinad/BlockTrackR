CREATE SCHEMA `minecraft` ;
CREATE USER 'minecraft'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `minecraft`.* TO 'minecraft'@'localhost';

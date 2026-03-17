CREATE DATABASE IF NOT EXISTS vehiclemanager;
CREATE DATABASE IF NOT EXISTS rentalmanager;
CREATE DATABASE IF NOT EXISTS maintenancemanager;

CREATE USER IF NOT EXISTS 'ridehub'@'%' IDENTIFIED BY 'ridehub123';

GRANT ALL PRIVILEGES ON vehiclemanager.* TO 'ridehub'@'%';
GRANT ALL PRIVILEGES ON rentalmanager.* TO 'ridehub'@'%';
GRANT ALL PRIVILEGES ON maintenancemanager.* TO 'ridehub'@'%';

FLUSH PRIVILEGES;

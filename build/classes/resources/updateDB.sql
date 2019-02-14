use U04FGv;

ALTER TABLE address MODIFY COLUMN addressId INT(10) auto_increment;
ALTER TABLE address MODIFY COLUMN lastUpdate timestamp default current_timestamp;
ALTER TABLE appointment MODIFY COLUMN appointmentId INT(10) auto_increment;
ALTER TABLE appointment ADD COLUMN type TEXT;
ALTER TABLE appointment ADD COLUMN userId INT not null;
ALTER TABLE city MODIFY COLUMN cityId INT(10) auto_increment;
ALTER TABLE city MODIFY COLUMN lastUpdate timestamp default current_timestamp;
ALTER TABLE country MODIFY COLUMN countryId INT(10) auto_increment;
ALTER TABLE country MODIFY COLUMN lastUpdate timestamp default current_timestamp;

ALTER TABLE customer MODIFY COLUMN customerId INT(10) auto_increment;
ALTER TABLE customer MODIFY COLUMN lastUpdate timestamp default current_timestamp;
ALTER TABLE user MODIFY COLUMN userId INT(10) auto_increment;
ALTER TABLE user MODIFY COLUMN active TINYINT not null;
ALTER TABLE user MODIFY COLUMN lastUpdate timestamp default current_timestamp();
DROP TABLE incrementtypes;
DROP TABLE reminder;
ALTER TABLE city ADD CONSTRAINT FK_CountryCity FOREIGN KEY (countryId) REFERENCES country(countryId);
ALTER TABLE address ADD CONSTRAINT FK_CityAddress FOREIGN KEY (cityId) REFERENCES city(cityId);
ALTER TABLE customer ADD CONSTRAINT FK_AddressCustomer FOREIGN KEY (addressId) REFERENCES address(addressId);
ALTER TABLE appointment ADD CONSTRAINT FK_UserAppointment FOREIGN KEY (userId) REFERENCES user(userId);
ALTER TABLE appointment ADD CONSTRAINT FK_CustomerAppointment FOREIGN KEY (customerId) REFERENCES customer(customerId);


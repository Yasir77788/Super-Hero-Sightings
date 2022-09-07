DROP DATABASE IF EXISTS superHeroSightingsTestDB;
CREATE DATABASE superHeroSightingsTestDB;

USE superHeroSightingsTestDB;

CREATE TABLE  Super(
    superId INT PRIMARY KEY AUTO_INCREMENT,
    superName VARCHAR (25) NOT NULL,
    superDescription VARCHAR(100) NOT NULL,
    superPower VARCHAR(25) NOT NULL,
    superStatus VARCHAR(20) NOT NULL
);


CREATE TABLE Location (
    locationId INT PRIMARY KEY AUTO_INCREMENT,
    locationName VARCHAR(25) NOT NULL,
    locationDescription VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(30) NOT NULL,
    state CHAR(2) NOT NULL,
    zipcode CHAR(5),
    locationLat CHAR(10) NOT NULL,
    locationLong CHAR(10) NOT NULL
);

CREATE TABLE Organization (
    orgId INT PRIMARY KEY AUTO_INCREMENT,
    orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(100) NOT NULL,
    orgPhone CHAR(15),
    orgEmail VARCHAR(50),
    heroOrVillainOrg VARCHAR(10) NOT NULL,
    locationId INT NOT NULL,

    FOREIGN KEY fk_location_org(locationId) REFERENCES Location(locationId)
);

CREATE TABLE Super_Org_Bridge (
    superId INT NOT NULL,
    orgId INT NOT NULL,

    primary key (superId, orgId),
    FOREIGN KEY fk_super_ref (superId) REFERENCES Super(superId),
    FOREIGN KEY fk_org_ref (orgId) REFERENCES Organization(orgId)

);

CREATE TABLE Sighting (

    sightingId INT PRIMARY KEY AUTO_INCREMENT,
    sightingDate TIMESTAMP NOT NULL,

    superId INT NOT NULL,
    locationId INT NOT NULL,

    FOREIGN KEY fk_sight_Location(locationId) REFERENCES Location(locationId),
    FOREIGN KEY fk_sight_super(superId) REFERENCES Super(superId)
);

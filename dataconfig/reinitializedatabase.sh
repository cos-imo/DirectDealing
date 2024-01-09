#!/bin/bash

# Se déplacer dans le répertoire où se trouve la base de données
cd ../src/main/resources/eu/telecomnancy/labfx/database

# Lancer l'interface SQLite3
sqlite3 data.db <<EOF
-- Commandes SQL
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Ressource;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Message;

CREATE TABLE User (
    User_id INTEGER PRIMARY KEY AUTOINCREMENT,
    First_Name TEXT NOT NULL,
    Last_Name TEXT NOT NULL,
    Mail TEXT NOT NULL,
    Password TEXT NOT NULL,
    Photo_profil BLOB,
    Wallet INTEGER DEFAULT 100,
    Note INTEGER
);

CREATE TABLE Ressource (
    Ressource_id INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT NOT NULL,
    Desc TEXT NOT NULL,
    DateDebut DATE NOT NULL,
    DateFin DATE NOT NULL,
    LocalisationLongitude FLOAT NOT NULL,
    LocalisationLatitude FLOAT NOT NULL,
    type INTEGER NOT NULL,
    Prix INTEGER NOT NULL
);

CREATE TABLE Event (
    Event_id INTEGER PRIMARY KEY AUTOINCREMENT,
    type INTEGER NOT NULL,
    Name TEXT NOT NULL,
    preteur_id INTEGER REFERENCES User(User_id) NOT NULL,
    acheteur_id INTEGER REFERENCES User(User_id),
    Recurrence INTEGER NOT NULL,
    DateDebut DATE NOT NULL,
    DateFin DATE NOT NULL,
    Prix INTEGER NOT NULL
);

CREATE TABLE Message (
    Message_id INTEGER PRIMARY KEY AUTOINCREMENT,
    Event_lie_id INTEGER REFERENCES Event(Event_id) NOT NULL,
    Sender_id INTEGER REFERENCES User(User_id) NOT NULL,
    Receiver_id INTEGER REFERENCES User(User_id) NOT NULL,
    Contenu TEXT NOT NULL
);

INSERT INTO User (First_Name, Last_Name, Mail, Password) VALUES ("a", "a", "a", "a");

.exit
EOF

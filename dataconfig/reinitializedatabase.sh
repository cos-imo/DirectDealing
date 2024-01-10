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
    Note INTEGER,
    VuePref TEXT CHECK (VuePref IN ('Mois', 'Semaine', 'Jour')) DEFAULT 'Mois'
);

CREATE TABLE Ressource (
    Ressource_id INTEGER PRIMARY KEY AUTOINCREMENT,
    Owner_id INTEGER NOT NULL,
    Name TEXT NOT NULL,
    Desc TEXT NOT NULL,
    DateDebut INTEGER NOT NULL, -- timestamp
    DateFin INTEGER NOT NULL, -- timestamp
    LocalisationLongitude FLOAT NOT NULL,
    LocalisationLatitude FLOAT NOT NULL,
    type BOOLEAN NOT NULL,
    Prix INTEGER NOT NULL,
    Image BLOB,
    FOREIGN KEY (Owner_id) REFERENCES User(User_id)
);

CREATE TABLE Event (
    Event_id INTEGER PRIMARY KEY AUTOINCREMENT,
    Ressource_id INTEGER NOT NULL,
    isObjet BOOLEAN NOT NULL,
    Name TEXT NOT NULL,
    preteur_id INTEGER NOT NULL,
    acheteur_id INTEGER,
    Recurrence INTEGER NOT NULL,
    DateDebut INTEGER NOT NULL, -- timestamp
    DateFin INTEGER NOT NULL, -- timestamp
    Prix INTEGER NOT NULL,
    FOREIGN KEY (Ressource_id) REFERENCES Ressource(Ressource_id),
    FOREIGN KEY (preteur_id) REFERENCES User(User_id),
    FOREIGN KEY (acheteur_id) REFERENCES User(User_id)
);


CREATE TABLE Message (
    Message_id INTEGER PRIMARY KEY AUTOINCREMENT,
    Event_lie_id INTEGER REFERENCES Event(Event_id) NOT NULL,
    Sender_id INTEGER REFERENCES User(User_id) NOT NULL,
    Receiver_id INTEGER REFERENCES User(User_id) NOT NULL,
    Date INTEGER NOT NULL, -- timestamp
    Contenu TEXT NOT NULL
);

INSERT INTO User (First_Name, Last_Name, Mail, Password) VALUES ("a", "a", "a", "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb");
INSERT INTO User (First_Name, Last_Name, Mail, Password) VALUES ("b", "b", "b", "b");
INSERT INTO Ressource (Owner_id, Name, Desc, DateDebut, DateFin, LocalisationLongitude, LocalisationLatitude, type, Prix) VALUES (1, "ordinateur", "un ordinateur", strftime('%s', '2024-01-13 08:00:00'), strftime('%s', '2024-01-15 20:00:00'), 0, 0, 1, 20);
INSERT INTO Ressource (Owner_id, Name, Desc, DateDebut, DateFin, LocalisationLongitude, LocalisationLatitude, type, Prix) VALUES (2, "aide pendant la coding week", "aide pendant la coding week", strftime('%s', '2024-01-08 08:00:00'), strftime('%s', '2024-01-12 18:00:00'), 0, 0, 0, 100);
INSERT INTO Event (isObjet, Name, preteur_id, acheteur_id, Recurrence, DateDebut, DateFin, Prix, Ressource_id) VALUES (0, "aide pendant la coding week", 1, 2, 0, strftime('%s', '2024-01-08 08:00:00'), strftime('%s', '2024-01-12 18:00:00'), 100, 2);
INSERT INTO Event (isObjet, Name, preteur_id, acheteur_id, Recurrence, DateDebut, DateFin, Prix, Ressource_id) VALUES (1, "ordi", 2, 1, 0, strftime('%s', '2024-01-13 08:00:00'), strftime('%s', '2024-01-15 20:00:00'), 20, 1);
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (1, 1, 2, strftime('%s', '2024-01-07 08:00:03'), "Bonjour, je suis intéressé par votre offre");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (1, 2, 1, strftime('%s', '2024-01-07 08:30:27'), "Bonjour, pas de soucis, je vous l'apporte Samedi matin");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 2, 1, strftime('%s', '2024-01-12 18:00:00'), "Bonjour, j'ai vu votre annonce, je suis intéressé");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 1, 2, strftime('%s', '2024-01-12 20:00:00'), "Bonjour, pas de soucis, ou dois-je venir ?");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 2, 1, strftime('%s', '2024-01-12 20:04:46'), "Je vous envoie mon adresse par mail");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 1, 2, strftime('%s', '2024-01-12 20:05:10'), "Ok, à demain");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 1, 2, strftime('%s', '2024-01-12 20:05:10'), "Ok, à demain");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 1, 2, strftime('%s', '2024-01-12 20:05:10'), "Ok, à demain");
INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (2, 1, 2, strftime('%s', '2024-01-12 20:05:10'), "Ok, à demain");


.exit
EOF

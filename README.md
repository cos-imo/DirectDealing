# Coding Week - DirectDealing

[![Static Badge](https://img.shields.io/badge/Télécom-Projet_scolaire-purple)](https://telecomnancy.univ-lorraine.fr)
[![Static Badge](https://img.shields.io/badge/Langage-Java-blue?logo=data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiA/PjwhRE9DVFlQRSBzdmcgIFBVQkxJQyAnLS8vVzNDLy9EVEQgU1ZHIDEuMS8vRU4nICAnaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkJz48c3ZnIGhlaWdodD0iNTEycHgiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB2ZXJzaW9uPSIxLjEiIHZpZXdCb3g9IjAgMCA1MTIgNTEyIiB3aWR0aD0iNTEycHgiIHhtbDpzcGFjZT0icHJlc2VydmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiPjxnIGlkPSJfeDMxXzgxLWphdmEiPjxnPjxwYXRoIGQ9Ik0zMzMuMjgzLDMwNy4xMTdjOC44MDctNi4wMiwyMS4wMjMtMTEuMjMsMjEuMDIzLTExLjIzcy0zNC43NjgsNi4yOS02OS4zNTcsOS4xNjUgICAgYy00Mi4zMTUsMy41MDMtODcuNzc1LDQuMjIxLTExMC41OTUsMS4xNjdjLTUzLjk5Ni03LjE4NywyOS42NDctMjcuMDQ0LDI5LjY0Ny0yNy4wNDRzLTMyLjQzMy0yLjE1NC03Mi40MTMsMTcuMDcgICAgQzg0LjQyMiwzMTkuMDY2LDI0OC4zODMsMzI5LjQ4NywzMzMuMjgzLDMwNy4xMTd6IiBzdHlsZT0iZmlsbDojNTM4MkExOyIvPjxwYXRoIGQ9Ik0yNTYuNTYsMjc4LjI3N2MtMTcuMDctMzguMzYyLTc0LjY1OS03Mi4wNTQsMC0xMzAuOTlDMzQ5LjcyNyw3My43OTcsMzAxLjkzLDI2LDMwMS45MywyNiAgICBjMTkuMzE2LDc1LjkxNy02Ny45Miw5OC45MTctOTkuNDU2LDE0Ni4wODRDMTgxLjAwMSwyMDQuMzM3LDIxMi45ODYsMjM4LjkyNywyNTYuNTYsMjc4LjI3N3oiIHN0eWxlPSJmaWxsOiNGODk4MUQ7Ii8+PHBhdGggZD0iTTM1OS41MTgsMTE5Ljk3NWMwLjA5LDAtMTU3LjQwMywzOS4zNTEtODIuMjA1LDEyNS45NThjMjIuMTkxLDI1LjUxNi01Ljg0LDQ4LjUxNi01Ljg0LDQ4LjUxNiAgICBzNTYuMzMyLTI5LjEwOCwzMC40NTctNjUuNDk1QzI3Ny43NjIsMTk0Ljk5MywyNTkuMjU0LDE3OC4xMDMsMzU5LjUxOCwxMTkuOTc1eiIgc3R5bGU9ImZpbGw6I0Y4OTgxRDsiLz48cGF0aCBkPSJNMzU0LjAzOSwzNjIuOTk5Yy0wLjQ0OSwxLjA3OC0xLjc5NywyLjI0Ny0xLjc5NywyLjMzNiAgICBjMTE1LjI2Ni0zMC4yNzcsNzIuODYxLTEwNi44MjQsMTcuNzg3LTg3LjQxNmMtNC44NTIsMS43MDctNy4zNjUsNS42Ni03LjM2NSw1LjY2czMuMDUzLTEuMjU5LDkuODgzLTIuNjk2ICAgIEM0MDAuMzk2LDI3NS4wNDQsNDQwLjM3NywzMTguMTY4LDM1NC4wMzksMzYyLjk5OUwzNTQuMDM5LDM2Mi45OTl6IiBzdHlsZT0iZmlsbDojNTM4MkExOyIvPjxwYXRoIGQ9Ik0zOTYuNDQzLDQxOC45NzFjMCwwLDEzLjAyNywxMC42OTItMTQuMjg1LDE5LjA0N2MtNTIuMDE4LDE1LjcyMi0yMTYuMzM5LDIwLjQ4My0yNjEuOTc5LDAuNjMgICAgYy0xNi40NDEtNy4wOTksMTQuMzc0LTE3LjA3MiwyNC4wNzgtMTkuMTM3YzEwLjA2MS0yLjE1NywxNS45MDEtMS43OTksMTUuOTAxLTEuNzk5Yy0xOC4yMzgtMTIuODQ3LTExNy45NjMsMjUuMjQ3LTUwLjY3MSwzNi4xMTkgICAgQzI5Mi45NDUsNDgzLjY1Nyw0NDQuMDYxLDQ0MC40NDMsMzk2LjQ0Myw0MTguOTcxTDM5Ni40NDMsNDE4Ljk3MXoiIHN0eWxlPSJmaWxsOiM1MzgyQTE7Ii8+PHBhdGggZD0iTTE5NS41NTcsMzgxLjc3NmMtNzAuNzA2LDE5Ljc2Niw0My4wMzUsNjAuNTU1LDEzMy4wNTUsMjIuMDExICAgIGMtMTQuNzMyLTUuNzQ4LTI1LjMzNC0xMi4zOTctMjUuMzM0LTEyLjM5N2MtNDAuMTYsNy42MzctNTguNzU2LDguMTc1LTk1LjIzMyw0LjA0MyAgICBDMTc3Ljk0OCwzOTIuMDE5LDE5NS41NTcsMzgxLjc3NiwxOTUuNTU3LDM4MS43NzZMMTk1LjU1NywzODEuNzc2eiIgc3R5bGU9ImZpbGw6IzUzODJBMTsiLz48cGF0aCBkPSJNMzU3LjA5Miw0NjkuMTAzYy03MC43MDUsMTMuMjk2LTE1Ny45NDEsMTEuNzcxLTIwOS42MDIsMy4yMzNjMC0wLjA4OCwxMC42MDIsOC43MTYsNjUuMDQ2LDEyLjIyICAgIGM4Mi44MzQsNS4zMDIsMjEwLjA1MS0yLjk2NiwyMTMuMDE2LTQyLjEzNkM0MjUuNTUzLDQ0Mi40Miw0MTkuODAzLDQ1Ny4yNDUsMzU3LjA5Miw0NjkuMTAzTDM1Ny4wOTIsNDY5LjEwM3oiIHN0eWxlPSJmaWxsOiM1MzgyQTE7Ii8+PHBhdGggZD0iTTMxNy45MjIsMzQzLjE0NGMtNTMuMTg4LDEwLjI0My04NC4wMDMsOS45NzMtMTIyLjkwNCw1LjkzICAgIGMtMzAuMDk4LTMuMTQ1LTEwLjQyMi0xNy42OTgtMTAuNDIyLTE3LjY5OGMtNzcuOTgyLDI1Ljg3NCw0My4zMDQsNTUuMTY0LDE1Mi4yODEsMjMuMjY5ICAgIEMzMjUuMjg5LDM1MC42MDEsMzE3LjkyMiwzNDMuMTQ0LDMxNy45MjIsMzQzLjE0NHoiIHN0eWxlPSJmaWxsOiM1MzgyQTE7Ii8+PC9nPjwvZz48ZyBpZD0iTGF5ZXJfMSIvPjwvc3ZnPg==)](https://www.java.com/fr/)
[![Static Badge](https://img.shields.io/badge/Framework-JavaFX-blue?logo=JavaFX)](https://openjfx.io)

## Sommaire
1. Sommaire
2. Présentation
3. Installation
4. Utilisation

## Présentation
Projet développé dans le cadre de notre scolarité à [Télécom Nancy](https://telecomnancy.univ-lorraine.fr) (2ème année - 1er semestre).  
Extrait du sujet:

> TelecomNancy DirectDealing est une application d’économie circulaire en ligne permettant à des personnes de prêter/emprunter du matériel (...) et/ou de proposer/demander des services (...)

La présente application est constituée d'une interface implémentée en JavaFX ainsi que d'une base de donnée sqlite.
La vidéo suivante présente les principales fonctionnalités de l'application:
[![Vidéo de présentation](https://img.youtube.com/vi/DZXy6CB0uWA/0.jpg)](https://www.youtube.com/watch?v=DZXy6CB0uWA)   
[![Static Badge](https://img.shields.io/badge/Voir_sur_Youtube-red?logo=youtube)](https://www.youtube.com/embed/DZXy6CB0uWA?si=FyCGJZeUcE6SaMW6&amp;controls=0)

## Installation

## Installation
##### Téléchargement
###### Par SSH
```
git@gitlab.telecomnancy.univ-lorraine.fr:pcd2k24/codingweek-24.git
```
###### Par HTTPS
```
https://gitlab.telecomnancy.univ-lorraine.fr/pcd2k24/codingweek-24.git
```

##### Compilation
Depuis la racine du projet (dossier `codingweek-24`), utilisez gradlew pour installer les dépendances requises:
```
./gradlew clean build
```
**Note**: Il y a alors un message d'erreur  `BUILD FAILED` que nous ne parvenons pas à faire disparaître, cependant cela ne semble pas gêner le bon déroulement de la suite

##### Création du jar
Depuis la racine du projet (dossier `codingweek-24`) créer un jar à l'aide de gradlew:
```
./gradlew jar
```

#### Lancement du jar:
Vous pouvez désormais lancer le jar, en spécifiant la localisation de javafx:
```
java --module-path /chemin/vers/le/javafx/sdk/lib --add-modules javafx.controls,javafx.fxml -jar build/libs/nomdujar.jar
```

## Utilisation
### Connexion
Lors de son exécution, l'application s'ouvre sur une page de connexion. Afin de tester l'application, vous pouvez vous connecter à l'aide du couple adresse email `a` et du mot de passe `a`.
### Création de compte
Vous pouvez créer un compte depuis la page d'accueil.   
Chaque nouveau se verra crédité d'un solde de 100 florains.
### Déconnexion
Vous pouvez vous déconnecter de l'application à l'aide du bouton présent sur le menu
### Onglet 'Mon Compte'
Un Onglet `Mon Compte` est disponible dans la barre de menu. Celui-ci donne accès à un menu permettant de modifier et d'afficher ses informations personnelles
### Ajout d'un Objet/Services
Afin d'ajouter un Objet à prêter ou de proposer un service, vous pouvez vous servir du bouton `Proposer` de la page d'accueil.  
Note: Il n'est pas encore possible d'ajouter/modifier la ou les photos d'une annonce.
### Visualisation des annonces
Afin de visualiser la liste des annonces déjà publiées, vous pouvez consulter la liste des annnonces depuis le bouton `Rechercher` de la page d'accueil.  
Note: La base de donnée présente sur le dépôt ne contient actuellement pas d'annonces
### Messagerie
Une messagerie est disponible sur l'application. S'il n'est pas encore possible d'envoyer/recevoir des messages, vous pouvez d'ores et déjà consulter la liste des messages existants depuis l'onglet (enveloppe).  
Note: La base de données ne contient pas de message. Vous pouvez en ajouter à l'aide de sqlite.
### Calendrier (visualisation)
Il est possible de visualiser le calendrier (bien que celui-ci ne soit pas fonctionnel) depuis l'onglet correspondant.

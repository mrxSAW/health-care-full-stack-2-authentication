# HealthCare+ Full Stack

HealthCare+ est une application full stack destinée à la gestion des activités d'un établissement sanitaire.

Elle permet de gérer :

- les patients
- les médecins
- les rendez-vous
- les dossiers médicaux
- les utilisateurs
- l'authentification et les rôles

Le projet est composé de deux parties principales :

- Backend : API REST avec Spring Boot
- Frontend : interface web avec React

## Objectif du Projet

L'objectif est de proposer une plateforme simple et moderne permettant à une clinique ou à un établissement sanitaire de centraliser la gestion des données médicales.

L'application permet aux utilisateurs autorisés d'interagir avec les données via une interface web connectée à l'API HealthCare+.

## Fonctionnalités Principales

### Authentification

- Connexion avec email et mot de passe
- Génération d'un token JWT
- Protection des routes côté frontend
- Sécurisation des endpoints côté backend
- Gestion des rôles :
  - ADMIN
  - MEDECIN
  - PATIENT

### Gestion des Patients

- Afficher la liste des patients
- Consulter les détails d'un patient
- Ajouter un patient
- Modifier un patient
- Supprimer un patient

### Gestion des Médecins

- Afficher la liste des médecins
- Consulter les détails d'un médecin
- Ajouter un médecin
- Modifier un médecin
- Supprimer un médecin

### Gestion des Rendez-vous

- Afficher la liste des rendez-vous
- Consulter les détails d'un rendez-vous
- Ajouter un rendez-vous
- Modifier un rendez-vous
- Supprimer un rendez-vous

### Gestion des Dossiers Médicaux

- Afficher la liste des dossiers médicaux
- Consulter les détails d'un dossier médical
- Ajouter un dossier médical
- Modifier un dossier médical
- Supprimer un dossier médical

### Tableau de Bord

Le tableau de bord affiche des statistiques générales :

- nombre de patients
- nombre de médecins
- nombre de rendez-vous
- nombre de dossiers médicaux

### Page d'Accueil

La page d'accueil présente les principales fonctionnalités de l'application.

### Page À Propos

La page À propos décrit le projet, ses fonctionnalités et les technologies utilisées.

## Technologies Utilisées

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Flyway
- MapStruct
- Lombok
- Redis
- Swagger / OpenAPI
- Maven
- Docker

### Frontend

- React
- Vite
- React Router
- Axios
- React Hook Form
- Yup
- CSS

## Architecture du Projet

```
health-care-full-stack/
├── backend-spring/
│   └── HealthCARE-App-mrx-part4/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/example/HealthCareApp/
│       │   │   │       ├── Config/
│       │   │   │       ├── Controller/
│       │   │   │       ├── DTO/
│       │   │   │       ├── Entity/
│       │   │   │       ├── Exception/
│       │   │   │       ├── Mapper/
│       │   │   │       ├── Repository/
│       │   │   │       ├── Security/
│       │   │   │       └── Service/
│       │   │   └── resources/
│       │   │       ├── application.properties
│       │   │       └── db/migration/
│       │   └── test/
│       ├── pom.xml
│       ├── Dockerfile
│       └── docker-compose.yml
│
└── frontend-react/
    ├── src/
    │   ├── api/
    │   ├── components/
    │   ├── pages/
    │   ├── services/
    │   ├── App.jsx
    │   ├── main.jsx
    │   └── index.css
    ├── package.json
    └── vite.config.js
Backend
Configuration
Le backend utilise par défaut :
Port : 8080
Base de données : MySQL
Nom de base : medical_db
Les principales propriétés sont dans :
backend-spring/HealthCARE-App-mrx-part4/src/main/resources/application.properties
Exemple :
spring.datasource.url=jdbc:mysql://localhost:3306/medical_db
spring.datasource.username=root
spring.datasource.password=123456789
server.port=8080
Lancement du Backend
Aller dans le dossier backend :
cd backend-spring/HealthCARE-App-mrx-part4
Lancer l'application :
mvn spring-boot:run
Swagger est disponible sur :
http://localhost:8080/swagger-ui.html
Frontend
Installation
Aller dans le dossier frontend :
cd frontend-react
Installer les dépendances :
npm install
Lancement
Lancer le serveur de développement :
npm run dev
L'application sera disponible sur le port affiché par Vite, par exemple :
http://localhost:5174
Connexion Frontend / Backend
Le frontend utilise Axios pour consommer l'API backend.
La configuration se trouve dans :
frontend-react/src/api/axiosConfig.js
Exemple :
baseURL: "http://localhost:8080"
CORS
Le backend autorise les appels depuis le frontend grâce à une configuration CORS.
Exemple :
.allowedOrigins("http://localhost:5174")
CORS est aussi activé dans Spring Security avec :
http.cors(cors -> {});
Authentification
L'utilisateur se connecte via :
POST /auth/login
Exemple de body :
{
  "email": "ADMIN1@example.com",
  "password": "1234"
}
Réponse attendue :
{
  "token": "...",
  "userId": 1,
  "role": "ADMIN"
}
Le token est stocké côté frontend dans le localStorage.
Les routes privées sont protégées par :
ProtectedRoute.jsx
Rôles
L'application utilise trois rôles :
ADMIN
MEDECIN
PATIENT
Les autorisations sont gérées côté backend avec Spring Security.
Routes Frontend
/login
/home
/dashboard
/patients
/medecins
/rendez-vous
/dossiers-medicaux
/about
Endpoints Principaux Backend
Authentification
POST /auth/register
POST /auth/login
Patients
GET /patients
GET /patients/{id}
POST /patients
PUT /patients/{id}
DELETE /patients/{id}
Médecins
GET /medcins
GET /medcins/{id}
POST /medcins
PUT /medcins/{id}
DELETE /medcins/{id}
Rendez-vous
GET /rendezvous
POST /rendezvous
PUT /rendezvous/{id}
DELETE /rendezvous/{id}
Dossiers Médicaux
GET /dossiers
GET /dossiers/{id}
POST /dossiers
PUT /dossiers/{id}
DELETE /dossiers/{id}
Validation des Formulaires
Le frontend utilise :
React Hook Form
Yup
Les formulaires validés sont :
PatientForm.jsx
MedecinForm.jsx
RendezVousForm.jsx
DossierMedicalForm.jsx
Responsive Design
L'interface est responsive :
navbar avec menu hamburger sur petit écran
grilles adaptatives
pages centrées
formulaires simples
tableaux lisibles

Tests Conseillés
Avant la livraison, vérifier :
connexion utilisateur
déconnexion
protection des routes privées
affichage des listes
ajout des données
modification des données
suppression avec confirmation
validation des formulaires
affichage responsive
Auteur

# ARQAS MOHAMED
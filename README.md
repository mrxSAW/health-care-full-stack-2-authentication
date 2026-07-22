
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

L'application peut être lancée complètement avec Docker Compose :

- MySQL
- Redis
- Backend Spring Boot
- Frontend React/Nginx

## Objectif du Projet

L'objectif est de proposer une plateforme simple et moderne permettant à une clinique ou à un établissement sanitaire de centraliser la gestion des données médicales.

L'application permet aux utilisateurs autorisés d'interagir avec les données via une interface web connectée à l'API HealthCare+.

## Fonctionnalités Principales

### Authentification

- Inscription utilisateur
- Connexion avec email et mot de passe
- Génération d'un token JWT
- Stockage du token côté frontend
- Conservation de la session après actualisation
- Déconnexion avec suppression de session
- Protection des routes côté frontend
- Sécurisation des endpoints côté backend
- Gestion des rôles :
  - ADMIN
  - MEDECIN
  - PATIENT

### Sécurité Frontend

- Axios Request Interceptor
- Axios Response Interceptor
- Gestion centralisée des erreurs API
- Auth Guard
- Role Guard
- Routes privées
- Contrôle d'accès selon le rôle
- Session utilisateur

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
- Télécharger un dossier médical en PDF

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
- Nginx
- Docker

### DevOps

- Docker
- Docker Compose
- MySQL Container
- Redis Container
- Backend Container
- Frontend Container

## Architecture du Projet

```txt
health-care-full-stack-2-authentication/
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
│       └── README.md
│
├── frontend-react/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── utils/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   └── README.md
│
├── docker-compose.yml
└── README.md
```

## Lancement Complet avec Docker Compose

Depuis la racine du projet :

```bash
docker compose up --build
```

Cette commande lance automatiquement :

- MySQL
- Redis
- Backend Spring Boot
- Frontend React/Nginx

## Services Docker

### MySQL

```txt
Container : healthcare-mysql
Port local : 3307
Port interne : 3306
Base de données : medical_db
Utilisateur : root
Mot de passe : 123456789
```

### Redis

```txt
Container : healthcare-redis1
Port local : 6379
Port interne : 6379
```

### Backend

```txt
Container : healthcare-backend
Port : 8080
URL : http://localhost:8080
Swagger : http://localhost:8080/swagger-ui.html
```

### Frontend

```txt
Container : healthcare-frontend
Port : 3000
URL : http://localhost:3000
```

## Arrêter l'Application

```bash
docker compose down
```

## Supprimer les Données MySQL

Attention : cette commande supprime le volume MySQL.

```bash
docker compose down -v
```

## Vérifier les Conteneurs

```bash
docker compose ps
```

## Voir les Logs

Tous les services :

```bash
docker compose logs
```

Backend seulement :

```bash
docker compose logs backend
```

Frontend seulement :

```bash
docker compose logs frontend
```

MySQL seulement :

```bash
docker compose logs mysql
```

Redis seulement :

```bash
docker compose logs redis
```

## Tester Redis

```bash
docker compose exec redis redis-cli ping
```

Résultat attendu :

```txt
PONG
```

## Configuration Backend

Le backend utilise par défaut :

```txt
Port : 8080
Base de données : MySQL
Nom de base : medical_db
Redis : redis:6379
```

Les principales propriétés sont dans :

```txt
backend-spring/HealthCARE-App-mrx-part4/src/main/resources/application.properties
```

Exemple :

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/medical_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:123456789}

spring.data.redis.host=${REDIS_HOST:localhost}
spring.data.redis.port=${REDIS_PORT:6379}

server.port=${PORT:8080}
```

Avec Docker Compose, les variables d'environnement configurent automatiquement MySQL et Redis.

## Configuration Frontend

Le frontend utilise Axios pour consommer l'API backend.

La configuration se trouve dans :

```txt
frontend-react/src/api/axiosConfig.js
```

Exemple :

```js
baseURL: "http://localhost:8080";
```

## CORS

Le backend doit autoriser les appels depuis le frontend.

Origines utiles :

```txt
http://localhost:5173
http://localhost:5174
http://localhost:3000
```

## Authentification

L'utilisateur se connecte via :

```txt
POST /auth/login
```

Exemple de body :

```json
{
  "email": "ADMIN1@example.com",
  "password": "1234"
}
```

Réponse attendue :

```json
{
  "token": "...",
  "userId": 1,
  "role": "ADMIN"
}
```

Le token est stocké côté frontend dans le `localStorage`.

## Inscription

L'utilisateur peut s'inscrire via :

```txt
POST /auth/register
```

Par défaut, le frontend crée un utilisateur avec le rôle :

```txt
PATIENT
```

## Routes Frontend

```txt
/login
/register
/home
/dashboard
/patients
/medecins
/rendez-vous
/dossiers-medicaux
/about
/unauthorized
```

## Endpoints Principaux Backend

### Authentification

```txt
POST /auth/register
POST /auth/login
```

### Patients

```txt
GET /patients
GET /patients/{id}
POST /patients
PUT /patients/{id}
DELETE /patients/{id}
```

### Médecins

```txt
GET /medcins
GET /medcins/{id}
POST /medcins
PUT /medcins/{id}
DELETE /medcins/{id}
```

### Rendez-vous

```txt
GET /rendezvous
POST /rendezvous
PUT /rendezvous/{id}
DELETE /rendezvous/{id}
```

### Dossiers Médicaux

```txt
GET /dossiers
GET /dossiers/{id}
POST /dossiers
PUT /dossiers/{id}
DELETE /dossiers/{id}
```

## Tests Conseillés

Avant la livraison, vérifier :

- lancement avec Docker Compose
- connexion utilisateur
- inscription utilisateur
- déconnexion
- protection des routes privées
- contrôle d'accès par rôle
- affichage des listes
- ajout des données
- modification des données
- suppression avec confirmation
- validation des formulaires
- gestion des erreurs API
- connexion Redis
- connexion MySQL
- affichage responsive

## Auteur

ARQAS MOHAMED
```
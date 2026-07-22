# HealthCare+ Frontend

Interface web de l'application HealthCare+ permettant de gérer les patients, les médecins, les rendez-vous et les dossiers médicaux d'un établissement sanitaire.

## Fonctionnalités

- Authentification avec token JWT
- Inscription utilisateur
- Connexion utilisateur
- Déconnexion utilisateur
- Gestion de session avec `localStorage`
- Conservation de la session après actualisation
- Axios Request Interceptor
- Axios Response Interceptor
- Gestion centralisée des erreurs API
- Protection des routes privées
- Contrôle d'accès selon le rôle
- Page d'accueil
- Tableau de bord
- Gestion des patients
- Gestion des médecins
- Gestion des rendez-vous
- Gestion des dossiers médicaux
- Page À propos
- Page accès refusé
- Header et footer présents sur les pages
- Interface responsive avec menu hamburger
- Messages d'erreur et validations de formulaires
- Déploiement frontend avec Docker et Nginx

## Gestion des Patients

L'application permet de :

- afficher la liste des patients
- consulter les détails d'un patient
- ajouter un patient
- modifier un patient
- supprimer un patient

L'accès est contrôlé selon le rôle utilisateur.

## Gestion des Médecins

L'application permet de :

- afficher la liste des médecins
- consulter les détails d'un médecin
- ajouter un médecin
- modifier un médecin
- supprimer un médecin

L'accès est contrôlé selon le rôle utilisateur.

## Gestion des Rendez-vous

L'application permet de :

- afficher la liste des rendez-vous
- consulter les détails d'un rendez-vous
- ajouter un rendez-vous
- modifier un rendez-vous
- supprimer un rendez-vous

L'accès est contrôlé selon le rôle utilisateur.

## Gestion des Dossiers Médicaux

L'application permet de :

- afficher la liste des dossiers médicaux
- consulter les détails d'un dossier médical
- ajouter un dossier médical
- modifier un dossier médical
- supprimer un dossier médical
- télécharger un dossier médical en PDF

L'accès est contrôlé selon le rôle utilisateur.

## Technologies Utilisées

- React
- React Router
- Axios
- React Hook Form
- Yup
- Vite
- CSS
- Docker
- Nginx

## Structure du Projet

```txt
src/
├── api/
│   └── axiosConfig.js
├── components/
│   ├── Footer.jsx
│   ├── Navbar.jsx
│   ├── ProtectedRoute.jsx
│   ├── RoleGuard.jsx
│   ├── PatientForm.jsx
│   ├── MedecinForm.jsx
│   ├── RendezVousForm.jsx
│   └── DossierMedicalForm.jsx
├── pages/
│   ├── Home.jsx
│   ├── Dashboard.jsx
│   ├── Patients.jsx
│   ├── Medecins.jsx
│   ├── RendezVous.jsx
│   ├── DossiersMedicaux.jsx
│   ├── About.jsx
│   ├── Login.jsx
│   ├── Register.jsx
│   ├── Unauthorized.jsx
│   └── NotFound.jsx
├── services/
│   ├── authService.js
│   ├── patientService.js
│   ├── medecinService.js
│   ├── rendezVousService.js
│   └── dossierMedicalService.js
├── utils/
│   ├── session.js
│   └── errorHandler.js
├── App.jsx
├── main.jsx
└── index.css
Installation
Installer les dépendances :
npm install
Lancement du Projet
Lancer le frontend :
npm run dev
L'application sera disponible sur :
http://localhost:5174
ou sur le port affiché dans le terminal Vite.
Connexion avec le Backend
Le frontend consomme l'API REST HealthCare+ disponible par défaut sur :
http://localhost:8080
La configuration Axios se trouve dans :
src/api/axiosConfig.js
Exemple :
baseURL: "http://localhost:8080"
Authentification
L'utilisateur se connecte via :
POST /auth/login
Après connexion, l'application récupère :
le token JWT
l'identifiant utilisateur
le rôle utilisateur
Ces informations sont ensuite enregistrées dans la session frontend.
Inscription
L'utilisateur peut créer un compte depuis la page :
/register
La page utilise l'endpoint backend :
POST /auth/register
Par défaut, l'utilisateur inscrit reçoit le rôle :
PATIENT
Gestion de Session
Les informations de session sont stockées dans le localStorage.
Données stockées :
token
userId
role
La gestion de session est centralisée dans :
src/utils/session.js
La session est conservée après actualisation de la page.
Lors de la déconnexion, les informations suivantes sont supprimées :
token JWT
identifiant utilisateur
rôle utilisateur
Axios Interceptors
La configuration Axios est centralisée dans :
src/api/axiosConfig.js
Request Interceptor
Chaque requête envoyée à l'API ajoute automatiquement le token JWT dans le header :
Authorization: Bearer TOKEN
Cela évite de répéter la configuration dans chaque appel API.
Response Interceptor
Les erreurs API sont gérées de manière centralisée.
Erreurs prises en charge :
400 : données invalides
401 : session expirée ou token invalide
403 : accès refusé
404 : ressource introuvable
500 : erreur serveur
En cas d'erreur 401, la session est supprimée et l'utilisateur est redirigé vers :
/login
Gestion Centralisée des Erreurs
Les messages d'erreurs API sont centralisés dans :
src/utils/errorHandler.js
Ce fichier transforme les erreurs techniques envoyées par l'API en messages plus clairs pour l'utilisateur.
Routes Protégées
Les routes privées sont protégées avec le composant :
src/components/ProtectedRoute.jsx
Si l'utilisateur n'est pas connecté, il est redirigé vers la page de connexion.
Contrôle d'Accès par Rôle
Les routes selon rôle sont protégées avec :
src/components/RoleGuard.jsx
L'application utilise trois rôles :
ADMIN
MEDECIN
PATIENT
Exemple d'accès :
ADMIN : accès complet
MEDECIN : accès aux patients, rendez-vous et dossiers médicaux selon autorisations
PATIENT : accès aux médecins, rendez-vous et dossiers médicaux selon autorisations
Routes Frontend
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
Validation des Formulaires
Tous les formulaires utilisent :
React Hook Form
Yup
Les validations sont présentes dans les composants :
PatientForm.jsx
MedecinForm.jsx
RendezVousForm.jsx
DossierMedicalForm.jsx
Déploiement Frontend avec Docker
Le frontend peut être buildé et servi avec Nginx.
Fichiers Docker ajoutés :
Dockerfile
nginx.conf
.dockerignore
Build de l'image Docker
Depuis le dossier frontend-react :
docker build -t healthcare-frontend .
Lancement du conteneur
docker run --name healthcare-frontend -p 3000:80 healthcare-frontend
L'application sera disponible sur :
http://localhost:3000
Remarque Docker et Backend
Le frontend Docker sert uniquement l'application React avec Nginx.
Le backend Spring Boot doit être lancé séparément et accessible sur :
http://localhost:8080
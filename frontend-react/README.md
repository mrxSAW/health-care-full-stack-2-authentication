# HealthCare+ Frontend

Interface web de l'application HealthCare+ permettant de gérer les patients, les médecins, les rendez-vous et les dossiers médicaux d'un établissement sanitaire.

## Fonctionnalités

- Authentification avec token JWT
- Protection des routes privées
- Page d'accueil
- Tableau de bord
- Gestion des patients
- Gestion des médecins
- Gestion des rendez-vous
- Gestion des dossiers médicaux
- Page À propos
- Interface responsive avec menu hamburger
- Messages d'erreur et validations de formulaires

## Gestion des Patients

L'application permet de :

- afficher la liste des patients
- consulter les détails d'un patient
- ajouter un patient
- modifier un patient
- supprimer un patient

## Gestion des Médecins

L'application permet de :

- afficher la liste des médecins
- consulter les détails d'un médecin
- ajouter un médecin
- modifier un médecin
- supprimer un médecin

## Gestion des Rendez-vous

L'application permet de :

- afficher la liste des rendez-vous
- consulter les détails d'un rendez-vous
- ajouter un rendez-vous
- modifier un rendez-vous
- supprimer un rendez-vous

## Gestion des Dossiers Médicaux

L'application permet de :

- afficher la liste des dossiers médicaux
- consulter les détails d'un dossier médical
- ajouter un dossier médical
- modifier un dossier médical
- supprimer un dossier médical

## Technologies Utilisées

- React
- React Router
- Axios
- React Hook Form
- Yup
- Vite
- CSS

## Structure du Projet

```txt
src/
├── api/
│   └── axiosConfig.js
├── components/
│   ├── Navbar.jsx
│   ├── ProtectedRoute.jsx
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
│   └── NotFound.jsx
├── services/
│   ├── authService.js
│   ├── patientService.js
│   ├── medecinService.js
│   ├── rendezVousService.js
│   └── dossierMedicalService.js
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
Après connexion, le token JWT est stocké dans le localStorage.
Les routes privées sont protégées avec le composant :
ProtectedRoute.jsx
Si l'utilisateur n'est pas connecté, il est redirigé vers la page de connexion.
Validation des Formulaires
Tous les formulaires utilisent :
React Hook Form
Yup
Les validations sont présentes dans les composants :
PatientForm.jsx
MedecinForm.jsx
RendezVousForm.jsx
DossierMedicalForm.jsx
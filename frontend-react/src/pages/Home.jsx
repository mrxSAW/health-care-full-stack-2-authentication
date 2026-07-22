import { Link } from "react-router-dom";
import { getRole } from "../services/authService";

function Home() {
  const role = getRole();

  const canSeePatients = role === "ADMIN" || role === "MEDECIN";
  const canSeeMedecins = role === "ADMIN" || role === "PATIENT";
  const canSeeRendezVous =
    role === "ADMIN" || role === "MEDECIN" || role === "PATIENT";
  const canSeeDossiers =
    role === "ADMIN" || role === "MEDECIN" || role === "PATIENT";

  return (
    <main className="page">
      <section className="home-hero">
        <h1>Bienvenue sur HealthCare+</h1>

        <p>
          HealthCare+ est une application web pour gérer les patients, les
          médecins, les rendez-vous et les dossiers médicaux d'un établissement
          sanitaire.
        </p>

        <Link to="/dashboard" className="save-button">
          Accéder au tableau de bord
        </Link>
      </section>

      <section className="features-grid">
        {canSeePatients && (
          <div className="feature-card">
            <h2>Patients</h2>
            <p>Afficher et gérer les informations des patients.</p>
            <Link to="/patients">Voir les patients</Link>
          </div>
        )}

        {canSeeMedecins && (
          <div className="feature-card">
            <h2>Médecins</h2>
            <p>Consulter les médecins et leurs spécialités.</p>
            <Link to="/medecins">Voir les médecins</Link>
          </div>
        )}

        {canSeeRendezVous && (
          <div className="feature-card">
            <h2>Rendez-vous</h2>
            <p>Planifier et suivre les rendez-vous médicaux.</p>
            <Link to="/rendez-vous">Voir les rendez-vous</Link>
          </div>
        )}

        {canSeeDossiers && (
          <div className="feature-card">
            <h2>Dossiers médicaux</h2>
            <p>Consulter et gérer les dossiers médicaux.</p>
            <Link to="/dossiers-medicaux">Voir les dossiers</Link>
          </div>
        )}
      </section>
    </main>
  );
}

export default Home;
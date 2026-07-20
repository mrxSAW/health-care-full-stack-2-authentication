import { Link } from "react-router-dom";

function Home() {
  return (
    <main className="page">
      <section className="home-hero">
        <h1>Bienvenue sur HealthCare+</h1>

        <p>
          HealthCare+ est une application web pour gérer les patients, les
          médecins, les rendez-vous et les dossiers médicaux d'une etablissement sanitaire.
        </p>

        <Link to="/dashboard" className="save-button">
          Accéder au tableau de bord
        </Link>
      </section>

      <section className="features-grid">
        <div className="feature-card">
          <h2>Patients</h2>
          <p>Afficher, ajouter, modifier et supprimer les patients.</p>
          <Link to="/patients">Voir les patients</Link>
        </div>

        <div className="feature-card">
          <h2>Médecins</h2>
          <p>Gérer les médecins et leurs spécialités.</p>
          <Link to="/medecins">Voir les médecins</Link>
        </div>

        <div className="feature-card">
          <h2>Rendez-vous</h2>
          <p>Planifier et suivre les rendez-vous médicaux.</p>
          <Link to="/rendez-vous">Voir les rendez-vous</Link>
        </div>

        <div className="feature-card">
          <h2>Dossiers médicaux</h2>
          <p>Consulter et gérer les dossiers médicaux des patients.</p>
          <Link to="/dossiers-medicaux">Voir les dossiers</Link>
        </div>
      </section>
    </main>
  );
}

export default Home;
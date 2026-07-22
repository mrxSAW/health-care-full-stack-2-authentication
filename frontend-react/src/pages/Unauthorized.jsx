import { Link } from "react-router-dom";

function Unauthorized() {
  return (
    <main className="page">
      <section className="unauthorized-box">
        <h1>Accès refusé</h1>

        <p>
          Vous n'avez pas l'autorisation nécessaire pour accéder à cette page.
        </p>

        <Link to="/dashboard" className="save-button">
          Retour au tableau de bord
        </Link>
      </section>
    </main>
  );
}

export default Unauthorized;
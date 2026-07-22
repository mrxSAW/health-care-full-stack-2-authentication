import { useEffect, useState } from "react";
import { getRole } from "../services/authService";
import { getErrorMessage } from "../utils/errorHandler";
import { getAll as getPatients } from "../services/patientService";
import { getAll as getMedecins } from "../services/medecinService";
import { getAll as getRendezVous } from "../services/rendezVousService";
import { getAll as getDossiers } from "../services/dossierMedicalService";

function Dashboard() {
  const role = getRole();

  const [stats, setStats] = useState({
    patients: 0,
    medecins: 0,
    rendezVous: 0,
    dossiers: 0,
  });

  const [error, setError] = useState("");

  useEffect(() => {
    async function loadStats() {
      try {
        const requests = [];

        if (role === "ADMIN" || role === "MEDECIN") {
          requests.push(getPatients());
        } else {
          requests.push(Promise.resolve(null));
        }

        if (role === "ADMIN" || role === "PATIENT") {
          requests.push(getMedecins());
        } else {
          requests.push(Promise.resolve(null));
        }

        requests.push(getRendezVous());
        requests.push(getDossiers());

        const [patientsData, medecinsData, rendezVousData, dossiersData] =
          await Promise.all(requests);

        setStats({
          patients: patientsData ? getTotal(patientsData) : 0,
          medecins: medecinsData ? getTotal(medecinsData) : 0,
          rendezVous: getTotal(rendezVousData),
          dossiers: getTotal(dossiersData),
        });
      } catch (error) {
        setError(getErrorMessage(error));
      }
    }

    loadStats();
  }, [role]);

  function getTotal(data) {
    if (data?.totalElements !== undefined) {
      return data.totalElements;
    }

    if (Array.isArray(data)) {
      return data.length;
    }

    return 0;
  }

  return (
    <main className="page">
      <h1>Tableau de bord</h1>

      <p>Bienvenue sur le tableau de bord de HealthCare+.</p>

      {role && <p className="info-message">Rôle connecté : {role}</p>}

      {error && <p className="error-message">{error}</p>}

      <section className="stats-grid">
        {(role === "ADMIN" || role === "MEDECIN") && (
          <div className="stat-card">
            <h2>{stats.patients}</h2>
            <p>Patients</p>
          </div>
        )}

        {(role === "ADMIN" || role === "PATIENT") && (
          <div className="stat-card">
            <h2>{stats.medecins}</h2>
            <p>Médecins</p>
          </div>
        )}

        <div className="stat-card">
          <h2>{stats.rendezVous}</h2>
          <p>Rendez-vous</p>
        </div>

        <div className="stat-card">
          <h2>{stats.dossiers}</h2>
          <p>Dossiers médicaux</p>
        </div>
      </section>
    </main>
  );
}

export default Dashboard;
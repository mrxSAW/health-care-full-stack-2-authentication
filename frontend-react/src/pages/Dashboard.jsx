import { useEffect, useState } from "react";
import { getAll as getPatients } from "../services/patientService";
import { getAll as getMedecins } from "../services/medecinService";
import { getAll as getRendezVous } from "../services/rendezVousService";
import { getAll as getDossiers } from "../services/dossierMedicalService";

function Dashboard() {
  const [stats, setStats] = useState({
    patients: 0,medecins: 0,
    rendezVous: 0,dossiers: 0,});

  const [error, setError] = useState("");

  useEffect(() => { loadStats();}, []);

  async function loadStats() {
    try {
      const patientsData = await getPatients();
      const medecinsData = await getMedecins();
      const rendezVousData = await getRendezVous();
      const dossiersData = await getDossiers();

      setStats({
        patients: getTotal(patientsData),medecins: getTotal(medecinsData),
        rendezVous: getTotal(rendezVousData),dossiers: getTotal(dossiersData),
      });
    } catch (err) {
      console.error(err);
      setError("Impossible de charger les statistiques");
    }
  }

  function getTotal(data) {
    if (data.totalElements !== undefined) {
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

      {error && <p className="error-message">{error}</p>}

      <section className="stats-grid">
        <div className="stat-card">
          <h2>{stats.patients}</h2>
          <p>Patients</p>
        </div>

        <div className="stat-card">
          <h2>{stats.medecins}</h2>
          <p>Médecins</p>
        </div>

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
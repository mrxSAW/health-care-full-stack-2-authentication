import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getById } from "../services/patientService";
import { getErrorMessage } from "../utils/errorHandler";
import { getByPatientId } from "../services/dossierMedicalService";


function PatientDetails() {
  const { id } = useParams();

  const [patient, setPatient] = useState(null);
  const [error, setError] = useState("");
  const [dossier, setDossier] = useState(null);
  const [dossierMessage, setDossierMessage] = useState("");

  useEffect(() => {
    async function loadPatient() {
      try {
        const data = await getById(id);
        setPatient(data);
        try {
                const dossierData = await getByPatientId(id);
                setDossier(dossierData);
              } catch {
              setDossierMessage("Aucune information médicale disponible.");
                        }
      } catch (error) {
        setError(getErrorMessage(error));
      }
    }

    loadPatient();
  }, [id]);

  if (error) {
    return (
      <main className="page">
        <p className="error-message">{error}</p>

        <Link to="/patients" className="cancel-button">
          Retour
        </Link>
      </main>
    );
  }

  if (!patient) {
    return (
      <main className="page">
        <p className="info-message">Chargement du patient...</p>
      </main>
    );
  }

  return (
    <main className="page">
      <section className="details-box">
        <div className="details-header">
          <h1>Détails du patient</h1>

          <Link to="/patients" className="cancel-button">
            Retour
          </Link>
        </div>

        <h2>Informations personnelles</h2>

        <p>
          <strong>Nom :</strong> {patient.nom}
        </p>

        <p>
          <strong>Prénom :</strong> {patient.prenom}
        </p>

        <p>
          <strong>Date de naissance :</strong> {patient.dateNaissance}
        </p>

        <h2>Coordonnées</h2>

        <p>
          <strong>Email :</strong> {patient.email}
        </p>

        <p>
          <strong>Téléphone :</strong> {patient.telephone}
        </p>

       <h2>Informations médicales disponibles</h2>

            {dossier ? (
             <>
                        <p>
                           <strong>Diagnostic :</strong> {dossier.diagnostic}
                        </p>

                         <p>
                          <strong>Observation :</strong> {dossier.observation}
                          </p>

                        <p>
                        <strong>Date création :</strong> {dossier.dateCreation}
                          </p>
              </>
                  ) : (
                       <p className="empty-message">{dossierMessage}</p>
                          )}
      </section>
    </main>
  );
}

export default PatientDetails;
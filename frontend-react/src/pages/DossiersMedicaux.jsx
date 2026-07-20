import { useEffect, useState } from "react";
import { getAll,create,update,remove,downloadPdf} from "../services/dossierMedicalService";
import DossierMedicalForm from "../components/DossierMedicalForm";

function DossiersMedicaux() {
  const [dossiers, setDossiers] = useState([]);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [dossierToEdit, setDossierToEdit] = useState(null);
  const [dossierToDelete, setDossierToDelete] = useState(null);
  const [selectedDossier, setSelectedDossier] = useState(null);

  useEffect(() => {
    loadDossiers();
  }, []);

  async function loadDossiers() {
    try {
      const data = await getAll();
      setDossiers(data.content || data);
    } catch (err) {
      console.error(err);
      setError("Impossible de charger les dossiers médicaux");
    }
  }

  async function handleSaveDossier(dossierData) {
    try {
      if (dossierToEdit) {
        await update(dossierToEdit.id, dossierData);
      } else {
        await create(dossierData);
      }

      setShowForm(false);
      setDossierToEdit(null);
      loadDossiers();
    } catch (err) {
      console.error(err);
      setError("Impossible d'enregistrer ce dossier médical");
    }
  }

  function openAddForm() {
    setDossierToEdit(null);
    setShowForm(true);
  }

  function openEditForm(dossier) {
    setDossierToEdit(dossier);
    setShowForm(true);
  }

  function openDeleteConfirmation(dossier) {
    setDossierToDelete(dossier);
  }

  function closeDeleteConfirmation() {
    setDossierToDelete(null);
  }

  async function confirmDelete() {
    try {
      await remove(dossierToDelete.id);
      setDossierToDelete(null);
      loadDossiers();
    } catch (err) {
      console.error(err);
      setError("Impossible de supprimer ce dossier médical");
    }
  }

  return (
    <main className="page">
      <div className="page-header">
        <h1>Liste des dossiers médicaux</h1>

        <button type="button" className="save-button" onClick={openAddForm}>
          Ajouter dossier
        </button>
      </div>

      {error && <p className="error-message">{error}</p>}

      {showForm && (
        <DossierMedicalForm
          initialData={dossierToEdit}
          onSubmit={handleSaveDossier}
          onCancel={() => {
            setShowForm(false);
            setDossierToEdit(null);
          }}
        />
      )}

      <table className="simple-table">
        <thead>
          <tr>
            <th>Patient</th>
            <th>Diagnostic</th>
            <th>Observation</th>
            <th>Date création</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {dossiers.map((dossier) => (
            <tr key={dossier.id}>
              <td>{dossier.patientNom || dossier.patientId}</td>
              <td>{dossier.diagnostic}</td>
              <td>{dossier.observation}</td>
              <td>{dossier.dateCreation}</td>
              
              <td>

                <button type="button"   className="details-button"
                  onClick={() => setSelectedDossier(dossier)}
                > Détails </button>

                <button type="button" className="edit-button"
                  onClick={() => openEditForm(dossier)}
                > Modifier </button>

                <button type="button" className="delete-button"
                  onClick={() => openDeleteConfirmation(dossier)}
                > Supprimer </button>

               <button type="button" className="details-button"
                onClick={() => downloadPdf(dossier.id)} > PDF  </button>

              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {dossierToDelete && (
        <div className="confirm-overlay">
          <div className="confirm-box">
            <h2>Confirmer la suppression</h2>

            <p>Voulez-vous vraiment supprimer ce dossier médical ?</p>

            <div className="confirm-actions">
              <button
                type="button"
                className="cancel-button"
                onClick={closeDeleteConfirmation}
              >
                Annuler
              </button>

  <button type="button" className="delete-button" onClick={confirmDelete}> Confirmer  </button>
            </div>
          </div>
        </div>
      )}

      {selectedDossier && (
        <div className="details-box">
          <div className="details-header">
            <h2>Détails du dossier médical</h2>

             <button type="button"  className="cancel-button"
              onClick={() => setSelectedDossier(null)}> Fermer </button>
          </div>

          <p>
            <strong>Diagnostic :</strong> {selectedDossier.diagnostic}
          </p>
          <p>
            <strong>Observation :</strong> {selectedDossier.observation}
          </p>
          <p>
            <strong>Date de création :</strong> {selectedDossier.dateCreation}
          </p>
          <p>
            <strong>Patient :</strong>{" "}
            {selectedDossier.patientNom || selectedDossier.patientId}
          </p>
        </div>
      )}
    </main>
  );
}

export default DossiersMedicaux;
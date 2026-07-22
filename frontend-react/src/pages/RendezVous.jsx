import { useEffect, useState } from "react";
import { getRole } from "../services/authService";
import { getErrorMessage } from "../utils/errorHandler";
import { getAll, create, update, remove } from "../services/rendezVousService";
import RendezVousForm from "../components/RendezVousForm";

function RendezVous() {
  const role = getRole();

  const canCreate = role === "ADMIN";
  const canEdit = role === "ADMIN" || role === "MEDECIN";
  const canDelete = role === "ADMIN";

  const [rendezVous, setRendezVous] = useState([]);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [rendezVousToEdit, setRendezVousToEdit] = useState(null);
  const [rendezVousToDelete, setRendezVousToDelete] = useState(null);
  const [selectedRendezVous, setSelectedRendezVous] = useState(null);

  useEffect(() => {
    loadRendezVous();
  }, []);

  async function loadRendezVous() {
    try {
      const data = await getAll();
      setRendezVous(data.content || data);
    } catch (error) {
      setError(getErrorMessage(error));
    }
  }

  async function handleSaveRendezVous(rendezVousData) {
    try {
      if (rendezVousToEdit) {
        await update(rendezVousToEdit.id, rendezVousData);
      } else {
        await create(rendezVousData);
      }

      setShowForm(false);
      setRendezVousToEdit(null);
      loadRendezVous();
    } catch (error) {
      setError(getErrorMessage(error));
    }
  }

  function openAddForm() {
    setRendezVousToEdit(null);
    setShowForm(true);
  }

  function openEditForm(item) {
    setRendezVousToEdit(item);
    setShowForm(true);
  }

  function openDeleteConfirmation(item) {
    setRendezVousToDelete(item);
  }

  function closeDeleteConfirmation() {
    setRendezVousToDelete(null);
  }

  async function confirmDelete() {
    try {
      await remove(rendezVousToDelete.id);
      setRendezVousToDelete(null);
      loadRendezVous();
    } catch (error) {
      setError(getErrorMessage(error));
    }
  }

  return (
    <main className="page">
      <div className="page-header">
        <h1>Liste des rendez-vous</h1>

        {canCreate && (
          <button type="button" className="save-button" onClick={openAddForm}>
            Ajouter rendez-vous
          </button>
        )}
      </div>

      {error && <p className="error-message">{error}</p>}

      {showForm && (
        <RendezVousForm
          initialData={rendezVousToEdit}
          onSubmit={handleSaveRendezVous}
          onCancel={() => {
            setShowForm(false);
            setRendezVousToEdit(null);
          }}
        />
      )}

      <table className="simple-table">
        <thead>
          <tr>
            <th>Date</th>
            <th>Statut</th>
            <th>Patient</th>
            <th>Médecin</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {rendezVous.map((item) => (
            <tr key={item.id}>
              <td>{item.dateRendezVous}</td>
              <td>{item.statut}</td>
              <td>{item.patientNom || item.patientId}</td>
              <td>{item.medcinNom || item.medcinId}</td>
              <td>
                <button
                  type="button"
                  className="details-button"
                  onClick={() => setSelectedRendezVous(item)}
                >
                  Détails
                </button>

                {canEdit && (
                  <button
                    type="button"
                    className="edit-button"
                    onClick={() => openEditForm(item)}
                  >
                    Modifier
                  </button>
                )}

                {canDelete && (
                  <button
                    type="button"
                    className="delete-button"
                    onClick={() => openDeleteConfirmation(item)}
                  >
                    Supprimer
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {rendezVousToDelete && (
        <div className="confirm-overlay">
          <div className="confirm-box">
            <h2>Confirmer la suppression</h2>

            <p>Voulez-vous vraiment supprimer ce rendez-vous ?</p>

            <div className="confirm-actions">
              <button
                type="button"
                className="cancel-button"
                onClick={closeDeleteConfirmation}
              >
                Annuler
              </button>

              <button
                type="button"
                className="delete-button"
                onClick={confirmDelete}
              >
                Confirmer
              </button>
            </div>
          </div>
        </div>
      )}

      {selectedRendezVous && (
        <div className="details-box">
          <div className="details-header">
            <h2>Détails du rendez-vous</h2>

            <button
              type="button"
              className="cancel-button"
              onClick={() => setSelectedRendezVous(null)}
            >
              Fermer
            </button>
          </div>

          <p>
            <strong>Date :</strong> {selectedRendezVous.dateRendezVous}
          </p>
          <p>
            <strong>Statut :</strong> {selectedRendezVous.statut}
          </p>
          <p>
            <strong>Patient :</strong>{" "}
            {selectedRendezVous.patientNom || selectedRendezVous.patientId}
          </p>
          <p>
            <strong>Médecin :</strong>{" "}
            {selectedRendezVous.medcinNom || selectedRendezVous.medcinId}
          </p>
        </div>
      )}
    </main>
  );
}

export default RendezVous;
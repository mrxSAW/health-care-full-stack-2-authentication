import { useEffect, useState } from "react";
import { getRole } from "../services/authService";
import { getErrorMessage } from "../utils/errorHandler";
import { getAll, create, update, remove } from "../services/medecinService";
import MedecinForm from "../components/MedecinForm";

function Medecins() {
  const role = getRole();

  const canCreate = role === "ADMIN";
  const canEdit = role === "ADMIN";
  const canDelete = role === "ADMIN";

  const [medecins, setMedecins] = useState([]);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [medecinToEdit, setMedecinToEdit] = useState(null);
  const [medecinToDelete, setMedecinToDelete] = useState(null);
  const [selectedMedecin, setSelectedMedecin] = useState(null);

  useEffect(() => {
    loadMedecins();
  }, []);

  async function loadMedecins() {
    try {
      const data = await getAll();
      setMedecins(data.content || data);
    } catch (error) {
      setError(getErrorMessage(error));
    }
  }

  async function handleSaveMedecin(medecinData) {
    try {
      if (medecinToEdit) {
        await update(medecinToEdit.id, medecinData);
      } else {
        await create(medecinData);
      }

      setShowForm(false);
      setMedecinToEdit(null);
      loadMedecins();
    } catch (error) {
      setError(getErrorMessage(error));
    }
  }

  function openAddForm() {
    setMedecinToEdit(null);
    setShowForm(true);
  }

  function openEditForm(medecin) {
    setMedecinToEdit(medecin);
    setShowForm(true);
  }

  function openDeleteConfirmation(medecin) {
    setMedecinToDelete(medecin);
  }

  function closeDeleteConfirmation() {
    setMedecinToDelete(null);
  }

  async function confirmDelete() {
    try {
      await remove(medecinToDelete.id);
      setMedecinToDelete(null);
      loadMedecins();
    } catch (error) {
      setError(getErrorMessage(error));
    }
  }

  return (
    <main className="page">
      <div className="page-header">
        <h1>Liste des médecins</h1>

        {canCreate && (
          <button type="button" className="save-button" onClick={openAddForm}>
            Ajouter médecin
          </button>
        )}
      </div>

      {error && <p className="error-message">{error}</p>}

      {showForm && (
        <MedecinForm
          initialData={medecinToEdit}
          onSubmit={handleSaveMedecin}
          onCancel={() => {
            setShowForm(false);
            setMedecinToEdit(null);
          }}
        />
      )}

      <table className="simple-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Spécialité</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {medecins.map((medecin) => (
            <tr key={medecin.id}>
              <td>{medecin.id}</td>
              <td>{medecin.nom}</td>
              <td>{medecin.specialite}</td>
              <td>{medecin.email}</td>
              <td>{medecin.telephone}</td>
              <td>
                <button
                  type="button"
                  className="details-button"
                  onClick={() => setSelectedMedecin(medecin)}
                >
                  Détails
                </button>

                {canEdit && (
                  <button
                    type="button"
                    className="edit-button"
                    onClick={() => openEditForm(medecin)}
                  >
                    Modifier
                  </button>
                )}

                {canDelete && (
                  <button
                    type="button"
                    className="delete-button"
                    onClick={() => openDeleteConfirmation(medecin)}
                  >
                    Supprimer
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {medecinToDelete && (
        <div className="confirm-overlay">
          <div className="confirm-box">
            <h2>Confirmer la suppression</h2>

            <p>
              Voulez-vous vraiment supprimer le médecin{" "}
              <strong>{medecinToDelete.nom}</strong> ?
            </p>

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

      {selectedMedecin && (
        <div className="details-box">
          <div className="details-header">
            <h2>Détails du médecin</h2>

            <button
              type="button"
              className="cancel-button"
              onClick={() => setSelectedMedecin(null)}
            >
              Fermer
            </button>
          </div>

          <p>
            <strong>Nom :</strong> {selectedMedecin.nom}
          </p>
          <p>
            <strong>Spécialité :</strong> {selectedMedecin.specialite}
          </p>
          <p>
            <strong>Email :</strong> {selectedMedecin.email}
          </p>
          <p>
            <strong>Téléphone :</strong> {selectedMedecin.telephone}
          </p>
        </div>
      )}
    </main>
  );
}

export default Medecins;
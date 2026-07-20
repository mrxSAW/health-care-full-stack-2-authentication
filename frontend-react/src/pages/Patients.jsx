import { useEffect, useState } from "react";
import { getAll, create, update, remove } from "../services/patientService";
import PatientForm from "../components/PatientForm";

function Patients() {
  const [patients, setPatients] = useState([]);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [patientToDelete, setPatientToDelete] = useState(null);
  const [selectedPatient, setSelectedPatient] = useState(null);
  const [patientToEdit, setPatientToEdit] = useState(null);

  useEffect(() => {
    loadPatients();
  }, []);

  async function loadPatients() {
    try {
      const data = await getAll();
      setPatients(data.content || data);
    } catch (err) {
      console.error(err);
      setError("Impossible de charger les patients");
    }
  }

  async function handleSavePatient(patientData) {
    try {
      if (patientToEdit) {
        await update(patientToEdit.id, patientData);
      } else {
        await create(patientData);
      }

      setShowForm(false);
      setPatientToEdit(null);
      loadPatients();
    } catch (err) {
      console.error(err);
      setError("Impossible d'enregistrer ce patient");
    }
  }

  function openAddForm() {
    setPatientToEdit(null);
    setShowForm(true);
  }

  function openEditForm(patient) {
    setPatientToEdit(patient);
    setShowForm(true);
  }

  function openDeleteConfirmation(patient) {
    setPatientToDelete(patient);
  }

  function closeDeleteConfirmation() {
    setPatientToDelete(null);
  }

  async function confirmDelete() {
    try {
      await remove(patientToDelete.id);
      setPatientToDelete(null);
      loadPatients();
    } catch (err) {
      console.error(err);
      setError("Impossible de supprimer ce patient");
    }
  }

  return (
    <main className="page">
      <div className="page-header">
        <h1>Liste des patients</h1>

        <button type="button" className="save-button" onClick={openAddForm}>
          Ajouter patient
        </button>
      </div>

      {error && <p className="error-message">{error}</p>}

      {showForm && (
        <PatientForm initialData={patientToEdit} onSubmit={handleSavePatient}
          onCancel={() => {  
            setShowForm(false);
            setPatientToEdit(null);
          }}
        />
      )}

      <table className="simple-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {patients.map((patient) => (
            <tr key={patient.id}>
              <td>{patient.id}</td>
              <td>{patient.nom}</td>
              <td>{patient.prenom}</td>
              <td>{patient.email}</td>
              <td>{patient.telephone}</td>
              <td>
                <button
                  type="button"
                  className="details-button"
                  onClick={() => setSelectedPatient(patient)}
                >
                  Détails
                </button>

                <button
                  type="button"
                  className="edit-button"
                  onClick={() => openEditForm(patient)}
                >
                  Modifier
                </button>

                <button
                  type="button"
                  className="delete-button"
                  onClick={() => openDeleteConfirmation(patient)}
                >
                  Supprimer
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {patientToDelete && (
        <div className="confirm-overlay">
          <div className="confirm-box">
            <h2>Confirmer la suppression</h2>

            <p>
              Voulez-vous vraiment supprimer le patient{" "}
              <strong>
                {patientToDelete.nom} {patientToDelete.prenom}
              </strong>
              ?
            </p>

            <div className="confirm-actions">
              <button
                type="button"  className="cancel-button"
                onClick={closeDeleteConfirmation}
              >
                Annuler
              </button>

              <button
                type="button"   className="delete-button"
                onClick={confirmDelete}
              >
                Confirmer
              </button>
            </div>
          </div>
        </div>
      )}

      {selectedPatient && (
        <div className="details-box">
          <div className="details-header">
            <h2>Détails du patient</h2>

            <button
              type="button"  className="cancel-button"
              onClick={() => setSelectedPatient(null)}
            >
              Fermer
            </button>
          </div>

          <p>
            <strong>Nom :</strong> {selectedPatient.nom}
          </p>
          <p>
            <strong>Prénom :</strong> {selectedPatient.prenom}
          </p>
          <p>
            <strong>Email :</strong> {selectedPatient.email}
          </p>
          <p>
            <strong>Téléphone :</strong> {selectedPatient.telephone}
          </p>
          <p>
            <strong>Date de naissance :</strong>{" "} {selectedPatient.dateNaissance}
          </p>
        </div>
      )}
    </main>
  );
}

export default Patients;
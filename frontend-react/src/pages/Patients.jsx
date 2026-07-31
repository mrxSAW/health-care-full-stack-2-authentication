import { useEffect, useState } from "react";
import { getRole } from "../services/authService";
import { getErrorMessage } from "../utils/errorHandler";
import { getAll,searchByNom, create, update, remove } from "../services/patientService";
import PatientForm from "../components/PatientForm";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";

function Patients() {
  const role = getRole();

  const canCreate = role === "ADMIN";
  const canEdit = role === "ADMIN";
  const canDelete = role === "ADMIN";

  const [patients, setPatients] = useState([]);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [patientToDelete, setPatientToDelete] = useState(null);
  const [selectedPatient, setSelectedPatient] = useState(null);
  const [patientToEdit, setPatientToEdit] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortDirection, setSortDirection] = useState("asc");


  useEffect(() => {
    loadPatients();
  }, [searchTerm, sortDirection]);



  async function loadPatients() {
    try {
      setError("");

      const data = searchTerm.trim() ? await searchByNom(searchTerm, 0, 10, sortDirection): await getAll(0, 10, sortDirection);
      setPatients(data.content || data||[]);
    } catch (error) {
      const message = getErrorMessage(error);
       setError(message);
       toast.error(message);
    }
  }

  async function handleSavePatient(patientData) {
    try {
      if (patientToEdit) {
        await update(patientToEdit.id, patientData);
        toast.success("Patient modifié avec succès.");
      } else {
        await create(patientData);
        toast.success("Patient ajouté avec succès.");
      }

      setShowForm(false);
      setPatientToEdit(null);
      loadPatients();
    } catch (error) {
      const message=getErrorMessage(error);
      setError(messageb);
      toast.error(message)
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
      toast.success("Patient supprimé avec succès.");
      setPatientToDelete(null);
      loadPatients();
    } catch (error) {
      const message = getErrorMessage(error);
    setError(message);
    toast.error(message);
    }
  }

  return (
    <main className="page">
      <div className="page-header">
        <h1>Liste des patients</h1>

        {canCreate && (
          <button type="button" className="save-button" onClick={openAddForm}>
            Ajouter patient
          </button>
        )}
      </div>

      {error && <p className="error-message">{error}</p>}

      {showForm && (
        <PatientForm initialData={patientToEdit} onSubmit={handleSavePatient}
          onCancel={() => { setShowForm(false); setPatientToEdit(null); }}
        />
      )}
         
  <div className="filters-bar">

       <input  type="text" placeholder="Rechercher un patient par nom..." value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />

     <select value={sortDirection} onChange={(e) => setSortDirection(e.target.value)} >
       <option value="asc">A → Z</option>
       <option value="desc">Z → A</option>
      </select>

  </div>

{patients.length === 0 && !error && (<p className="empty-message">Aucun patient trouvé.</p> )}
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
                
                <Link to={`/patients/${patient.id}`} className="details-button">
                      Détails
                </Link>

                {canEdit && (
                  <button
                    type="button"
                    className="edit-button"
                    onClick={() => openEditForm(patient)}
                  >
                    Modifier
                  </button>
                )}

                {canDelete && (
                  <button
                    type="button"
                    className="delete-button"
                    onClick={() => openDeleteConfirmation(patient)}
                  >
                    Supprimer
                  </button>
                )}
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

      {selectedPatient && (
        <div className="details-box">
          <div className="details-header">
            <h2>Détails du patient</h2>

            <button
              type="button"
              className="cancel-button"
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
            <strong>Date de naissance :</strong>{" "}
            {selectedPatient.dateNaissance}
          </p>
        </div>
      )}
    </main>
  );
}

export default Patients;
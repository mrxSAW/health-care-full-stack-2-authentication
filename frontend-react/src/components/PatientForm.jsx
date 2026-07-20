import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const patientSchema = yup.object({
  nom: yup.string().required("Le nom est obligatoire"),
  prenom: yup.string().required("Le prénom est obligatoire"),
  email: yup.string().email("Email invalide").required("L'email est obligatoire"),
  telephone: yup.string().required("Le téléphone est obligatoire"),
  dateNaissance: yup.string().required("La date de naissance est obligatoire"),
});

function PatientForm({ onSubmit, onCancel, initialData }) {
  const {register,handleSubmit,reset,formState: { errors },} = useForm(
    {
       resolver: yupResolver(patientSchema),
      defaultValues: {nom: "",prenom: "",email: "",telephone: "",dateNaissance: "",},
     }
);

  useEffect(() => {
    if (initialData) {
      reset({
        nom: initialData.nom || "",
        prenom: initialData.prenom || "",
        email: initialData.email || "",
        telephone: initialData.telephone || "",
        dateNaissance: initialData.dateNaissance || "",
      });
    }
  }, [initialData, reset]);

  return (
    <div className="form-box">
      <h2>{initialData ? "Modifier un patient" : "Ajouter un patient"}</h2>

      <form onSubmit={handleSubmit(onSubmit)} className="crud-form">
        <div className="form-group">
          <label>Nom</label>
          <input type="text" {...register("nom")} />
          {errors.nom && <p className="field-error">{errors.nom.message}</p>}
        </div>

        <div className="form-group">
          <label>Prénom</label>
          <input type="text" {...register("prenom")} />
          {errors.prenom && (
            <p className="field-error">{errors.prenom.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>Email</label>
          <input type="email" {...register("email")} />
          {errors.email && (
            <p className="field-error">{errors.email.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>Téléphone</label>
          <input type="text" {...register("telephone")} />
          {errors.telephone && (
            <p className="field-error">{errors.telephone.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>Date de naissance</label>
          <input type="date" {...register("dateNaissance")} />
          {errors.dateNaissance && (
            <p className="field-error">{errors.dateNaissance.message}</p>
          )}
        </div>

        <div className="form-actions">
          <button type="button" className="cancel-button" onClick={onCancel}>
            Annuler
          </button>

          <button type="submit" className="save-button">
            {initialData ? "Modifier" : "Enregistrer"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default PatientForm;
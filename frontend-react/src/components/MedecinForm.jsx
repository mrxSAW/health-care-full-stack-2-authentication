import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const medecinSchema = yup.object({
  nom: yup.string().required("Le nom est obligatoire"),
  specialite: yup.string().required("La spécialité est obligatoire"),
  email: yup.string().email("Email invalide").required("L'email est obligatoire"),
  telephone: yup.string().required("Le téléphone est obligatoire"),
});

function MedecinForm({ onSubmit, onCancel, initialData }) {
  const {register,handleSubmit,reset,formState: { errors }  } = useForm({
    resolver: yupResolver(medecinSchema),
    defaultValues: {nom: "",specialite: "",email: "",telephone: ""},
  });

  useEffect(() => {
    if (initialData) {
      reset({
        nom: initialData.nom || "",
        specialite: initialData.specialite || "",
        email: initialData.email || "",
        telephone: initialData.telephone || "",
      });
    }
  }, [initialData, reset]);

  return (
    <div className="form-box">
      <h2>{initialData ? "Modifier un médecin" : "Ajouter un médecin"}</h2>

      <form onSubmit={handleSubmit(onSubmit)} className="crud-form">
        <div className="form-group">
          <label>Nom</label>
          <input type="text" {...register("nom")} />
          {errors.nom && <p className="field-error">{errors.nom.message}</p>}
        </div>

        <div className="form-group">
          <label>Spécialité</label>
          <input type="text" {...register("specialite")} />
          {errors.specialite && (
            <p className="field-error">{errors.specialite.message}</p>
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

export default MedecinForm;
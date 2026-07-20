import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const dossierSchema = yup.object({
  diagnostic: yup.string().required("Le diagnostic est obligatoire"),
  observation: yup.string().required("L'observation est obligatoire"),
  dateCreation: yup.string().required("La date de création est obligatoire"),
  patientId: yup.number().typeError("L'id du patient doit etre un nombre").required("L'id du patient est obligatoire"),
});

function DossierMedicalForm({ onSubmit, onCancel, initialData }) {
  const {register,handleSubmit,reset,formState: { errors },} = useForm({
         resolver: yupResolver(dossierSchema),
    defaultValues: {diagnostic: "",observation: "",dateCreation: "",
      patientId: "",
    },
  });

  useEffect(() => {
    if (initialData) {
      reset({
        diagnostic: initialData.diagnostic || "",
        observation: initialData.observation || "",
        dateCreation: initialData.dateCreation || "",
        patientId: initialData.patientId || "",
      });
    }
  }, [initialData, reset]);

  return (
    <div className="form-box">
      <h2>
        {initialData ? "Modifier un dossier médical": "Ajouter un dossier médical"}
      </h2>

      <form onSubmit={handleSubmit(onSubmit)} className="crud-form">
        <div className="form-group">
          <label>Diagnostic</label>
          <input type="text" {...register("diagnostic")} />
          {errors.diagnostic && (
            <p className="field-error">{errors.diagnostic.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>Observation</label>
          <textarea rows="4" {...register("observation")} />
          {errors.observation && (
            <p className="field-error">{errors.observation.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>Date de création</label>
          <input type="date" {...register("dateCreation")} />
          {errors.dateCreation && (
            <p className="field-error">{errors.dateCreation.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>ID Patient</label>
          <input type="number" {...register("patientId")} />
          {errors.patientId && (
            <p className="field-error">{errors.patientId.message}</p>
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

export default DossierMedicalForm;
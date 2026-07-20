import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const rendezVousSchema = yup.object({
  dateRendezVous: yup.string().required("La date du rendez-vous est obligatoire"),
  statut: yup.string().required("Le statut est obligatoire"),
  patientId: yup.number().typeError("L'id du patient doit etre un nombre").required("L'id du patient est obligatoire"),
  medcinId: yup.number().typeError("L'id du médecin doit etre un nombre").required("L'id du médecin est obligatoire"),
});

function RendezVousForm({ onSubmit, onCancel, initialData }) {
  const { register,handleSubmit,reset,formState: { errors },} = useForm(
    {
    resolver: yupResolver(rendezVousSchema),
    defaultValues: {dateRendezVous: "",statut: "",patientId: "",medcinId: "",},
  });

  useEffect(() => {
    if (initialData) {
      reset({
        dateRendezVous: initialData.dateRendezVous || "",
        statut: initialData.statut || "",
        patientId: initialData.patientId || "",
        medcinId: initialData.medcinId || "",
      });
    }
  }, [initialData, reset]);

  return (
    <div className="form-box">
      <h2>
        {initialData ? "Modifier un rendez-vous" : "Ajouter un rendez-vous"}
      </h2>

      <form onSubmit={handleSubmit(onSubmit)} className="crud-form">
        <div className="form-group">
          <label>Date du rendez-vous</label>
          <input type="datetime-local" {...register("dateRendezVous")} />
          {errors.dateRendezVous && (
            <p className="field-error">{errors.dateRendezVous.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>Statut</label>
          <select {...register("statut")}>
            <option value="">Choisir un statut</option>
            <option value="PLANIFIE">Planifié</option>
            <option value="CONFIRME">Confirmé</option>
            <option value="ANNULE">Annulé</option>
          </select>
          {errors.statut && (
            <p className="field-error">{errors.statut.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>ID Patient</label>
          <input type="number" {...register("patientId")} />
          {errors.patientId && (
            <p className="field-error">{errors.patientId.message}</p>
          )}
        </div>

        <div className="form-group">
          <label>ID Médecin</label>
          <input type="number" {...register("medcinId")} />
          {errors.medcinId && (
            <p className="field-error">{errors.medcinId.message}</p>
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

export default RendezVousForm;
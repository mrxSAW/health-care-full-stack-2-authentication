export function getErrorMessage(error) {
  const status = error.response?.status;
  const apiMessage = error.response?.data?.message;

  if (apiMessage) {
    return apiMessage;
  }

  if (status === 400) {
    return "Données invalides. Veuillez vérifier les informations saisies.";
  }

  if (status === 401) {
    return "Votre session a expiré. Veuillez vous reconnecter.";
  }

  if (status === 403) {
    return "Accès refusé. Vous n'avez pas l'autorisation nécessaire.";
  }

  if (status === 404) {
    return "Ressource introuvable.";
  }

  if (status >= 500) {
    return "Erreur serveur. Veuillez réessayer plus tard.";
  }

  if (error.request) {
    return "Impossible de contacter le serveur.";
  }

  return "Une erreur inattendue est survenue.";
}
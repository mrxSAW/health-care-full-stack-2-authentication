import { Navigate, Outlet, useLocation } from "react-router-dom";

function ProtectedRoute() {
  const token = localStorage.getItem("token");
  const location = useLocation();

  if (!token) {
    return (
      <Navigate
        to="/login"
        replace
        state={{
          message: "Veuillez vous connecter d'abord",
          from: location.pathname,
        }}
      />
    );
  }

  return <Outlet />;  //composont la ou la page demander et enregistrer et il serturne apres la virification du connection la location veulez avent de virifier le token 
}

export default ProtectedRoute;
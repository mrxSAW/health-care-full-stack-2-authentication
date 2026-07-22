import { Navigate, Outlet, useLocation } from "react-router-dom";
import { isAuthenticated } from "../services/authService";

function ProtectedRoute() {
  const location = useLocation();

  if (!isAuthenticated()) {
    return (
      <Navigate
        to="/login"
        replace
        state={{
          message: "Veuillez vous connecter d'abord.",
          from: location.pathname,
        }}
      />
    );
  }

  return <Outlet />;
}

export default ProtectedRoute;
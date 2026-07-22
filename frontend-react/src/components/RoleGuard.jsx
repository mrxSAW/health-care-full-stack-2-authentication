import { Navigate, Outlet } from "react-router-dom";
import { hasRole } from "../services/authService";

function RoleGuard({ allowedRoles }) {
  if (!hasRole(allowedRoles)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return <Outlet />;
}

export default RoleGuard;
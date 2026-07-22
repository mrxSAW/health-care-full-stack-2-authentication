import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { getRole, isAuthenticated, logout } from "../services/authService";

function Navbar() {
  const navigate = useNavigate();
  const connected = isAuthenticated();
  const role = getRole();
  const [menuOpen, setMenuOpen] = useState(false);

  function handleLogout() {
    logout();
    setMenuOpen(false);
    navigate("/login");
  }

  function closeMenu() {
    setMenuOpen(false);
  }

  const canSeePatients = role === "ADMIN" || role === "MEDECIN";
  const canSeeMedecins = role === "ADMIN" || role === "PATIENT";
  const canSeeRendezVous =
    role === "ADMIN" || role === "MEDECIN" || role === "PATIENT";
  const canSeeDossiers =
    role === "ADMIN" || role === "MEDECIN" || role === "PATIENT";

  return (
    <nav className="navbar">
      <h2>HealthCare+</h2>

      <button
        type="button"
        className="menu-button"
        onClick={() => setMenuOpen(!menuOpen)}
      >
        ☰
      </button>

      <div className={menuOpen ? "nav-links open" : "nav-links"}>
        {connected && (
          <>
            <NavLink to="/home" onClick={closeMenu}>
              Accueil
            </NavLink>

            <NavLink to="/dashboard" onClick={closeMenu}>
              Tableau de bord
            </NavLink>

            {canSeePatients && (
              <NavLink to="/patients" onClick={closeMenu}>
                Patients
              </NavLink>
            )}

            {canSeeMedecins && (
              <NavLink to="/medecins" onClick={closeMenu}>
                Médecins
              </NavLink>
            )}

            {canSeeRendezVous && (
              <NavLink to="/rendez-vous" onClick={closeMenu}>
                Rendez-vous
              </NavLink>
            )}

            {canSeeDossiers && (
              <NavLink to="/dossiers-medicaux" onClick={closeMenu}>
                Dossiers médicaux
              </NavLink>
            )}

            <span className="role-badge">{role}</span>
          </>
        )}

        <NavLink to="/about" onClick={closeMenu}>
          À propos
        </NavLink>

        {connected ? (
          <button type="button" className="logout-button" onClick={handleLogout}>
            Déconnexion
          </button>
        ) : (
          <>
            <NavLink to="/login" onClick={closeMenu}>
              Connexion
            </NavLink>

            <NavLink to="/register" onClick={closeMenu}>
              Inscription
            </NavLink>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
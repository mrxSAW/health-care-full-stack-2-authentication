import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { logout, isAuthenticated } from "../services/authService";

function Navbar() {
  const navigate = useNavigate();
  const connected = isAuthenticated();
  const [menuOpen, setMenuOpen] = useState(false);

  function handleLogout() {
    logout();
    setMenuOpen(false);
    navigate("/login");
  }

  function closeMenu() {
    setMenuOpen(false);
  }

  return (
    <nav className="navbar">
      <h2>HealthCare+</h2>

      <button
        type="button" className="menu-button"  onClick={() => setMenuOpen(!menuOpen)}
       > ☰ </button>

      <div className={menuOpen ? "nav-links open" : "nav-links"}>
        {connected && (
          <>
            <NavLink to="/home" onClick={closeMenu}>
              Accueil
            </NavLink>
            <NavLink to="/dashboard" onClick={closeMenu}>
              Tableau de bord
            </NavLink>
            <NavLink to="/patients" onClick={closeMenu}>
              Patients
            </NavLink>
            <NavLink to="/medecins" onClick={closeMenu}>
              Médecins
            </NavLink>
            <NavLink to="/rendez-vous" onClick={closeMenu}>
              Rendez-vous
            </NavLink>
            <NavLink to="/dossiers-medicaux" onClick={closeMenu}>
              Dossiers médicaux
            </NavLink>
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
          <NavLink to="/login" onClick={closeMenu}>
            Connexion
          </NavLink>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
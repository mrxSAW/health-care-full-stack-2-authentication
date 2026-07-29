import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import ProtectedRoute from "./components/ProtectedRoute";
import RoleGuard from "./components/RoleGuard";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Patients from "./pages/Patients";
import Medecins from "./pages/Medecins";
import RendezVous from "./pages/RendezVous";
import DossiersMedicaux from "./pages/DossiersMedicaux";
import About from "./pages/About";
import NotFound from "./pages/NotFound";
import Home from "./pages/Home";
import Unauthorized from "./pages/Unauthorized";


function App() {
  return (
    <BrowserRouter>
      <div className="app-layout">
        <Navbar />

        <div className="app-content">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/about" element={<About />} />
            <Route path="/unauthorized" element={<Unauthorized />} />

            <Route element={<ProtectedRoute />}>
              <Route path="/" element={<Navigate to="/home" replace />} />
              <Route path="/home" element={<Home />} />
              <Route  element={ <RoleGuard allowedRoles={["ADMIN", "MEDECIN", "PATIENT"]} /> } >
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/rendez-vous" element={<RendezVous />} />
                <Route path="/dossiers-medicaux" element={<DossiersMedicaux />} />
              </Route>
              
              <Route element={<RoleGuard allowedRoles={["ADMIN", "MEDECIN"]} />}>
                <Route path="/patients" element={<Patients />} />
              </Route>

              <Route element={<RoleGuard allowedRoles={["ADMIN", "PATIENT"]} />}>
                <Route path="/medecins" element={<Medecins />} />
              </Route>
            </Route>

            <Route path="*" element={<NotFound />} />
          </Routes>
        </div>

        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
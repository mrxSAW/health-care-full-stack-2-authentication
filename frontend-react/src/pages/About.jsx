function About() {
  return (
    <main className="page">
      <section className="about-section">
        <h1>À propos de HealthCare+</h1>

        <p>
          HealthCare+ est une application web destinée à faciliter la gestion
          des activités d'une clinique ou d'un établissement sanitaire.
        </p>

        <p>
          Elle permet de gérer les patients, les médecins, les rendez-vous et
          les dossiers médicaux à partir d'une interface simple et moderne.
        </p>

        <h2>Fonctionnalités principales</h2>

        <ul>
          <li>Gestion des patients</li>
          <li>Gestion des médecins</li>
          <li>Gestion des rendez-vous</li>
          <li>Gestion des dossiers médicaux</li>
          <li>Tableau de bord avec statistiques</li>
          <li>Authentification sécurisée</li>
        </ul>

        <h2>Technologies utilisées</h2>

        <ul>
          <li>React</li>
          <li>React Router</li>
          <li>Axios</li>
          <li>React Hook Form</li>
          <li>Yup</li>
          <li>API REST HealthCare+</li>
        </ul>
      </section>
    </main>
  );
}

export default About;
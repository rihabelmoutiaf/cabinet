package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Medecin;
import ma.project.dentalTech.entities.enums.Sexe;
import ma.project.dentalTech.configuration.SessionFactory;
import ma.project.dentalTech.repository.modules.users.api.MedecinRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinRepositoryImpl implements MedecinRepository {

    @Override
    public Medecin save(Medecin medecin) {
        Connection c = null;
        try {
            c = SessionFactory.getInstance().getConnection();
            c.setAutoCommit(false);

            // 1. Insérer dans utilisateurs
            String sqlUser = """
                INSERT INTO utilisateurs(
                    type_user, nom, prenom, email, tel, adresse, sexe, 
                    date_naissance, login, mot_de_passe, actif, role_id
                ) VALUES('MEDECIN',?,?,?,?,?,?,?,?,?,?,?)
                """;

            long userId;
            try (PreparedStatement ps = c.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, medecin.getNom());
                ps.setString(2, medecin.getPrenom());
                ps.setString(3, medecin.getEmail());
                ps.setString(4, medecin.getTelephone());
                ps.setString(5, medecin.getAdresse());
                ps.setString(6, medecin.getSexe() != null ? medecin.getSexe().name() : null);

                if (medecin.getDateNaissance() != null) {
                    ps.setDate(7, Date.valueOf(medecin.getDateNaissance()));
                } else {
                    ps.setNull(7, Types.DATE);
                }

                ps.setString(8, medecin.getLogin());
                ps.setString(9, medecin.getMotDePasse());
                ps.setBoolean(10, medecin.isActive());
                ps.setLong(11, medecin.getRoleId());

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        userId = keys.getLong(1);
                        medecin.setId(userId);
                    } else {
                        throw new SQLException("Échec de création, aucun ID généré");
                    }
                }
            }

            // 2. Insérer dans staff
            String sqlStaff = """
                INSERT INTO staff(utilisateur_id, cin, salaire, prime, date_embauche, date_depart)
                VALUES(?,?,?,?,?,?)
                """;

            try (PreparedStatement ps = c.prepareStatement(sqlStaff)) {
                ps.setLong(1, userId);
                ps.setString(2, medecin.getCin());
                ps.setDouble(3, medecin.getSalaire() != null ? medecin.getSalaire() : 0.0);
                ps.setDouble(4, medecin.getPrime() != null ? medecin.getPrime() : 0.0);

                if (medecin.getDateEmbauche() != null) {
                    ps.setDate(5, Date.valueOf(medecin.getDateEmbauche()));
                } else {
                    ps.setNull(5, Types.DATE);
                }

                if (medecin.getDateDepart() != null) {
                    ps.setDate(6, Date.valueOf(medecin.getDateDepart()));
                } else {
                    ps.setNull(6, Types.DATE);
                }

                ps.executeUpdate();
            }

            // 3. Insérer dans medecins
            String sqlMedecin = """
                INSERT INTO medecins(
                    utilisateur_id, specialite, numero_ordre, numero_inpe,
                    annees_experience, langues_parles, taux_horaire, pourcentage_consultation
                ) VALUES(?,?,?,?,?,?,?,?)
                """;

            try (PreparedStatement ps = c.prepareStatement(sqlMedecin)) {
                ps.setLong(1, userId);
                ps.setString(2, medecin.getSpecialite());
                ps.setString(3, null); // numero_ordre - à ajouter dans l'entité Medecin si nécessaire
                ps.setString(4, null); // numero_inpe - à ajouter dans l'entité Medecin si nécessaire
                ps.setInt(5, 0); // annees_experience
                ps.setString(6, null); // langues_parles
                ps.setDouble(7, 0.0); // taux_horaire
                ps.setDouble(8, 0.0); // pourcentage_consultation

                ps.executeUpdate();
            }

            c.commit();
            return medecin;

        } catch (SQLException e) {
            if (c != null) {
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la création du médecin", e);
        } finally {
            if (c != null) {
                try {
                    c.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Medecin update(Medecin medecin) {
        Connection c = null;
        try {
            c = SessionFactory.getInstance().getConnection();
            c.setAutoCommit(false);

            // 1. Mettre à jour utilisateurs
            String sqlUser = """
                UPDATE utilisateurs SET 
                    nom=?, prenom=?, email=?, tel=?, adresse=?, sexe=?,
                    date_naissance=?, login=?, actif=?, updated_at=CURRENT_TIMESTAMP
                WHERE id=?
                """;

            try (PreparedStatement ps = c.prepareStatement(sqlUser)) {
                ps.setString(1, medecin.getNom());
                ps.setString(2, medecin.getPrenom());
                ps.setString(3, medecin.getEmail());
                ps.setString(4, medecin.getTelephone());
                ps.setString(5, medecin.getAdresse());
                ps.setString(6, medecin.getSexe() != null ? medecin.getSexe().name() : null);

                if (medecin.getDateNaissance() != null) {
                    ps.setDate(7, Date.valueOf(medecin.getDateNaissance()));
                } else {
                    ps.setNull(7, Types.DATE);
                }

                ps.setString(8, medecin.getLogin());
                ps.setBoolean(9, medecin.isActive());
                ps.setLong(10, medecin.getId());

                ps.executeUpdate();
            }

            // 2. Mettre à jour staff
            String sqlStaff = """
                UPDATE staff SET 
                    cin=?, salaire=?, prime=?, date_embauche=?, date_depart=?
                WHERE utilisateur_id=?
                """;

            try (PreparedStatement ps = c.prepareStatement(sqlStaff)) {
                ps.setString(1, medecin.getCin());
                ps.setDouble(2, medecin.getSalaire() != null ? medecin.getSalaire() : 0.0);
                ps.setDouble(3, medecin.getPrime() != null ? medecin.getPrime() : 0.0);

                if (medecin.getDateEmbauche() != null) {
                    ps.setDate(4, Date.valueOf(medecin.getDateEmbauche()));
                } else {
                    ps.setNull(4, Types.DATE);
                }

                if (medecin.getDateDepart() != null) {
                    ps.setDate(5, Date.valueOf(medecin.getDateDepart()));
                } else {
                    ps.setNull(5, Types.DATE);
                }

                ps.setLong(6, medecin.getId());

                ps.executeUpdate();
            }

            // 3. Mettre à jour medecins
            String sqlMedecin = "UPDATE medecins SET specialite=? WHERE utilisateur_id=?";

            try (PreparedStatement ps = c.prepareStatement(sqlMedecin)) {
                ps.setString(1, medecin.getSpecialite());
                ps.setLong(2, medecin.getId());

                ps.executeUpdate();
            }

            c.commit();
            return medecin;

        } catch (SQLException e) {
            if (c != null) {
                try {
                    c.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la mise à jour du médecin", e);
        } finally {
            if (c != null) {
                try {
                    c.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM utilisateurs WHERE id=? AND type_user='MEDECIN'";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du médecin", e);
        }
    }

    @Override
    public Medecin findById(Long id) {
        String sql = """
            SELECT u.*, s.cin, s.salaire, s.prime, s.date_embauche, s.date_depart,
                   m.specialite
            FROM utilisateurs u
            LEFT JOIN staff s ON s.utilisateur_id = u.id
            LEFT JOIN medecins m ON m.utilisateur_id = u.id
            WHERE u.id=? AND u.type_user='MEDECIN'
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMedecin(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du médecin", e);
        }
    }

    @Override
    public List<Medecin> findAll() {
        String sql = """
            SELECT u.*, s.cin, s.salaire, s.prime, s.date_embauche, s.date_depart,
                   m.specialite
            FROM utilisateurs u
            LEFT JOIN staff s ON s.utilisateur_id = u.id
            LEFT JOIN medecins m ON m.utilisateur_id = u.id
            WHERE u.type_user='MEDECIN'
            ORDER BY u.nom, u.prenom
            """;

        List<Medecin> medecins = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }

            return medecins;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des médecins", e);
        }
    }

    @Override
    public Medecin findByNumeroOrdre(String numeroOrdre) {
        String sql = """
            SELECT u.*, s.cin, s.salaire, s.prime, s.date_embauche, s.date_depart,
                   m.specialite
            FROM utilisateurs u
            LEFT JOIN staff s ON s.utilisateur_id = u.id
            LEFT JOIN medecins m ON m.utilisateur_id = u.id
            WHERE m.numero_ordre=?
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, numeroOrdre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMedecin(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par numéro d'ordre", e);
        }
    }

    @Override
    public Medecin findByNumeroInpe(String numeroInpe) {
        String sql = """
            SELECT u.*, s.cin, s.salaire, s.prime, s.date_embauche, s.date_depart,
                   m.specialite
            FROM utilisateurs u
            LEFT JOIN staff s ON s.utilisateur_id = u.id
            LEFT JOIN medecins m ON m.utilisateur_id = u.id
            WHERE m.numero_inpe=?
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, numeroInpe);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMedecin(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par numéro INPE", e);
        }
    }

    @Override
    public List<Medecin> findBySpecialite(String specialite) {
        String sql = """
            SELECT u.*, s.cin, s.salaire, s.prime, s.date_embauche, s.date_depart,
                   m.specialite
            FROM utilisateurs u
            LEFT JOIN staff s ON s.utilisateur_id = u.id
            LEFT JOIN medecins m ON m.utilisateur_id = u.id
            WHERE m.specialite=? AND u.type_user='MEDECIN'
            ORDER BY u.nom, u.prenom
            """;

        List<Medecin> medecins = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, specialite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medecins.add(mapMedecin(rs));
                }
            }

            return medecins;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par spécialité", e);
        }
    }

    @Override
    public List<Medecin> findAllActive() {
        String sql = """
            SELECT u.*, s.cin, s.salaire, s.prime, s.date_embauche, s.date_depart,
                   m.specialite
            FROM utilisateurs u
            LEFT JOIN staff s ON s.utilisateur_id = u.id
            LEFT JOIN medecins m ON m.utilisateur_id = u.id
            WHERE u.type_user='MEDECIN' AND u.actif=TRUE
            ORDER BY u.nom, u.prenom
            """;

        List<Medecin> medecins = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }

            return medecins;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des médecins actifs", e);
        }
    }

    @Override
    public boolean existsByNumeroOrdre(String numeroOrdre) {
        String sql = "SELECT 1 FROM medecins WHERE numero_ordre=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, numeroOrdre);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du numéro d'ordre", e);
        }
    }

    @Override
    public boolean existsByNumeroInpe(String numeroInpe) {
        String sql = "SELECT 1 FROM medecins WHERE numero_inpe=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, numeroInpe);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du numéro INPE", e);
        }
    }

    @Override
    public void updateMedecinInfo(Medecin medecin) {
        String sql = """
            UPDATE medecins SET 
                specialite=?
            WHERE utilisateur_id=?
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, medecin.getSpecialite());
            ps.setLong(2, medecin.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour des infos médecin", e);
        }
    }

    // Méthode helper pour mapper un ResultSet vers un Medecin
    private Medecin mapMedecin(ResultSet rs) throws SQLException {
        Medecin medecin = new Medecin();

        // Données de utilisateurs
        medecin.setId(rs.getLong("id"));
        medecin.setNom(rs.getString("nom"));
        medecin.setPrenom(rs.getString("prenom"));
        medecin.setEmail(rs.getString("email"));
        medecin.setTelephone(rs.getString("tel"));
        medecin.setAdresse(rs.getString("adresse"));

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null && !sexeStr.isEmpty()) {
            medecin.setSexe(Sexe.valueOf(sexeStr));
        }

        if (rs.getDate("date_naissance") != null) {
            medecin.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
        }

        medecin.setLogin(rs.getString("login"));
        medecin.setMotDePasse(rs.getString("mot_de_passe"));
        medecin.setActive(rs.getBoolean("actif"));
        medecin.setRoleId(rs.getLong("role_id"));

        if (rs.getDate("date_derniere_connexion") != null) {
            medecin.setDateDerniereConnexion(rs.getDate("date_derniere_connexion").toLocalDate());
        }

        if (rs.getTimestamp("created_at") != null) {
            medecin.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }

        if (rs.getTimestamp("updated_at") != null) {
            medecin.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        // Données de staff
        medecin.setCin(rs.getString("cin"));
        medecin.setSalaire(rs.getDouble("salaire"));
        medecin.setPrime(rs.getDouble("prime"));

        if (rs.getDate("date_embauche") != null) {
            medecin.setDateEmbauche(rs.getDate("date_embauche").toLocalDate());
        }

        if (rs.getDate("date_depart") != null) {
            medecin.setDateDepart(rs.getDate("date_depart").toLocalDate());
        }

        // Données de medecins
        medecin.setSpecialite(rs.getString("specialite"));

        return medecin;
    }
}
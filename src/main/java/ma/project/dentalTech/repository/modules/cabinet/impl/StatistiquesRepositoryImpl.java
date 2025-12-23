package ma.project.dentalTech.repository.modules.cabinet.impl;

import ma.project.dentalTech.entities.cabinet.Statistiques;
import ma.project.dentalTech.repository.modules.cabinet.api.StatistiquesRepository;

import java.sql.*;

public class StatistiquesRepositoryImpl implements StatistiquesRepository {

    private Connection connection;

    public StatistiquesRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Statistiques calculer(Long cabinetId, java.time.LocalDate dateDebut, java.time.LocalDate dateFin) {

        Statistiques stats = new Statistiques(cabinetId, dateDebut, dateFin);

        try {
            // Total Charges
            String sqlCharges = """
                SELECT SUM(montant) AS total_charges, COUNT(*) AS nb_charges
                FROM charges c
                JOIN utilisateur u ON c.utilisateur_id = u.id
                WHERE u.cabinet_id = ? AND c.date BETWEEN ? AND ?
                """;
            try (PreparedStatement ps = connection.prepareStatement(sqlCharges)) {
                ps.setLong(1, cabinetId);
                ps.setTimestamp(2, Timestamp.valueOf(dateDebut.atStartOfDay()));
                ps.setTimestamp(3, Timestamp.valueOf(dateFin.atTime(23,59,59)));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    stats.setTotalCharges(rs.getDouble("total_charges"));
                    stats.setNombreCharges(rs.getInt("nb_charges"));
                }
            }

            // Total Revenues
            String sqlRevenues = """
                SELECT SUM(montant) AS total_revenues, COUNT(*) AS nb_revenues
                FROM revenues r
                JOIN utilisateur u ON r.utilisateur_id = u.id
                WHERE u.cabinet_id = ? AND r.date BETWEEN ? AND ?
                """;
            try (PreparedStatement ps = connection.prepareStatement(sqlRevenues)) {
                ps.setLong(1, cabinetId);
                ps.setTimestamp(2, Timestamp.valueOf(dateDebut.atStartOfDay()));
                ps.setTimestamp(3, Timestamp.valueOf(dateFin.atTime(23,59,59)));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    stats.setTotalRevenues(rs.getDouble("total_revenues"));
                    stats.setNombreRevenues(rs.getInt("nb_revenues"));
                }
            }

            // Calcul du bénéfice
            stats.setBenefice(stats.getTotalRevenues() - stats.getTotalCharges());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
}

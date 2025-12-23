package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Certificat;
import ma.project.dentalTech.repository.modules.dossierMedical.api.CertificatRepository;
import ma.project.dentalTech.repository.modules.dossierMedical.api.CertificatRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatRepositoryImpl implements CertificatRepository {

    private Connection connection;

    public CertificatRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    // ================== CRUD ==================

    @Override
    public void create(Certificat cert) {
        String sql = "INSERT INTO certificat (patient_id, utilisateur_id, date_emission, type_certificat, duree_en_jours, motif) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, cert.getPatientId());
            ps.setLong(2, cert.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(cert.getDateEmission()));
            ps.setString(4, cert.getTypeCertificat());
            ps.setInt(5, cert.getDureeEnJours());
            ps.setString(6, cert.getMotif());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) cert.setId(rs.getLong(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Certificat> findById(Long id) {
        String sql = "SELECT * FROM certificat WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapToCertificat(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Certificat> findAll() {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM certificat ORDER BY date_emission DESC";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(mapToCertificat(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Certificat cert) {
        String sql = "UPDATE certificat SET patient_id = ?, utilisateur_id = ?, date_emission = ?, type_certificat = ?, duree_en_jours = ?, motif = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cert.getPatientId());
            ps.setLong(2, cert.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(cert.getDateEmission()));
            ps.setString(4, cert.getTypeCertificat());
            ps.setInt(5, cert.getDureeEnJours());
            ps.setString(6, cert.getMotif());
            ps.setLong(7, cert.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Certificat cert) {
        if (cert != null && cert.getId() != null) {
            deleteById(cert.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM certificat WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================== Méthodes spécifiques ==================

    @Override
    public List<Certificat> findByPatientId(Long patientId) {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM certificat WHERE patient_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapToCertificat(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Certificat> findByUtilisateurId(Long utilisateurId) {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM certificat WHERE utilisateur_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapToCertificat(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Certificat> findByDateEmissionBetween(LocalDateTime debut, LocalDateTime fin) {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM certificat WHERE date_emission BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapToCertificat(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================== Mapping ==================

    private Certificat mapToCertificat(ResultSet rs) throws SQLException {
        Certificat cert = new Certificat();
        cert.setId(rs.getLong("id"));
        cert.setPatientId(rs.getLong("patient_id"));
        cert.setUtilisateurId(rs.getLong("utilisateur_id"));
        cert.setDateEmission(rs.getTimestamp("date_emission").toLocalDateTime());
        cert.setTypeCertificat(rs.getString("type_certificat"));
        cert.setDureeEnJours(rs.getInt("duree_en_jours"));
        cert.setMotif(rs.getString("motif"));
        return cert;
    }
}

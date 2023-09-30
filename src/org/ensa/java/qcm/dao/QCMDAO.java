package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ensa.java.qcm.models.Professeur;
import org.ensa.java.qcm.models.QCM;

public class QCMDAO {
	private Connection connection;
	private ProfesseurDAO professeurDAO;

	public QCMDAO(Connection connection) {
		this.connection = connection;
		this.professeurDAO = new ProfesseurDAO(connection);
	}

	// Méthode pour insérer un QCM
	public void insertQCM(QCM qcm) {
		String insertQuery = "INSERT INTO QCMs (titre, professeur_id, cibleNiveau, cibleFiliere) VALUES (?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setString(1, qcm.getTitre());
			preparedStatement.setInt(2, qcm.getProfesseur().getId());
			preparedStatement.setString(3, qcm.getCibleNiveau());
			preparedStatement.setString(4, qcm.getCibleFiliere());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer un QCM par son ID
	public QCM getQCMById(int id) {
		String selectQuery = "SELECT * FROM QCMs WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String titre = resultSet.getString("titre");
				int professeurId = resultSet.getInt("professeur_id");
				String cibleNiveau = resultSet.getString("cibleNiveau");
				String cibleFiliere = resultSet.getString("cibleFiliere");
				// Vous devrez récupérer le professeur associé par son ID
				Professeur professeur = professeurDAO.getProfesseurById(professeurId);
				return new QCM(id, titre, professeur, cibleNiveau, cibleFiliere);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Méthode pour mettre à jour un QCM
	public void updateQCM(QCM qcm) {
		String updateQuery = "UPDATE QCMs SET titre = ?, professeur_id = ?, cibleNiveau = ?, cibleFiliere = ?, qcmDate = ? WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setString(1, qcm.getTitre());
			preparedStatement.setInt(2, qcm.getProfesseur().getId());
			preparedStatement.setString(3, qcm.getCibleNiveau());
			preparedStatement.setString(4, qcm.getCibleFiliere());
			preparedStatement.setInt(5, qcm.getId());
			preparedStatement.setString(6, qcm.getQCMDate().toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour supprimer un QCM par son ID
	public void deleteQCM(int id) {
		String deleteQuery = "DELETE FROM QCMs WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer la liste de tous les QCMs
	public List<QCM> getAllQCMs() {
		List<QCM> qcms = new ArrayList<>();
		String selectAllQuery = "SELECT * FROM QCMs";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String titre = resultSet.getString("titre");
				int professeurId = resultSet.getInt("professeur_id");
				String cibleNiveau = resultSet.getString("cibleNiveau");
				String cibleFiliere = resultSet.getString("cibleFiliere");
				// Vous devrez récupérer le professeur associé par son ID
				Professeur professeur = professeurDAO.getProfesseurById(professeurId);
				qcms.add(new QCM(id, titre, professeur, cibleNiveau, cibleFiliere));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qcms;
	}
}

package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ensa.java.qcm.models.Etudiant;
import org.ensa.java.qcm.models.Professeur;

public class ProfesseurDAO {
	private Connection connection;

	public ProfesseurDAO(Connection connection) {
		this.connection = connection;
	}

	public void insertProfesseur(Professeur professeur) {
		String insertQuery = "INSERT INTO Professeurs (nom, specialite) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setString(1, professeur.getNom());
			preparedStatement.setString(2, professeur.getSpecialite());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Professeur getProfesseurById(int id) {
		String selectQuery = "SELECT * FROM Professeurs WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String nom = resultSet.getString("nom");
				String specialite = resultSet.getString("specialite");
				return new Professeur(id, nom, specialite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Professeur getProfesseurByUserId(int userId) {
		String selectQuery = "SELECT * FROM Professeurs WHERE user_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String filiere = resultSet.getString("specialite");
				return new Professeur(id, nom, filiere);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Professeur getProfesseurByNom(String nom) {
		String selectQuery = "SELECT * FROM Professeurs WHERE nom = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setString(1, nom);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String specialite = resultSet.getString("specialite");
				return new Professeur(id, nom, specialite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Professeur> getAllProfesseurs() {
		List<Professeur> professeurs = new ArrayList<>();
		String selectAllQuery = "SELECT * FROM Professeurs";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String specialite = resultSet.getString("specialite");
				professeurs.add(new Professeur(id, nom, specialite));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return professeurs;
	}

	public void updateProfesseur(Professeur professeur) {
		String updateQuery = "UPDATE Professeurs SET nom = ?, specialite = ? WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setString(1, professeur.getNom());
			preparedStatement.setString(2, professeur.getSpecialite());
			preparedStatement.setInt(3, professeur.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteProfesseur(int id) {
		String deleteQuery = "DELETE FROM Professeurs WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

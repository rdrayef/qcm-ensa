package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ensa.java.qcm.models.Etudiant;

public class EtudiantDAO {
	private Connection connection;

	public EtudiantDAO(Connection connection) {
		this.connection = connection;
	}

	// Méthode pour insérer un étudiant
	public void insertEtudiant(Etudiant etudiant) {
		String insertQuery = "INSERT INTO Etudiants (nom, filiere, niveau) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setString(1, etudiant.getNom());
			preparedStatement.setString(2, etudiant.getFiliere());
			preparedStatement.setString(3, etudiant.getNiveau());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer un étudiant par son ID
	public Etudiant getEtudiantById(int id) {
		String selectQuery = "SELECT * FROM Etudiants WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String nom = resultSet.getString("nom");
				String filiere = resultSet.getString("filiere");
				String niveau = resultSet.getString("niveau");
				return new Etudiant(id, nom, filiere, niveau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Etudiant getEtudiantByUserId(int userId) {
		String selectQuery = "SELECT * FROM Etudiants WHERE user_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				System.out.println("++++++"+id);
				String nom = resultSet.getString("nom");
				String filiere = resultSet.getString("filiere");
				String niveau = resultSet.getString("niveau");
				return new Etudiant(id, nom, filiere, niveau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Etudiant getEtudiantByNom(String nom) {
		String selectQuery = "SELECT * FROM Etudiants WHERE nom = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setString(1, nom);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String filiere = resultSet.getString("filiere");
				String niveau = resultSet.getString("niveau");
				return new Etudiant(id, nom, filiere, niveau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Méthode pour mettre à jour un étudiant
	public void updateEtudiant(Etudiant etudiant) {
		String updateQuery = "UPDATE Etudiants SET nom = ?, filiere = ?, niveau = ? WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setString(1, etudiant.getNom());
			preparedStatement.setString(2, etudiant.getFiliere());
			preparedStatement.setString(3, etudiant.getNiveau());
			preparedStatement.setInt(4, etudiant.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour supprimer un étudiant par son ID
	public void deleteEtudiant(int id) {
		String deleteQuery = "DELETE FROM Etudiants WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer la liste de tous les étudiants
	public List<Etudiant> getAllEtudiants() {
		List<Etudiant> etudiants = new ArrayList<>();
		String selectAllQuery = "SELECT * FROM Etudiants";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String filiere = resultSet.getString("filiere");
				String niveau = resultSet.getString("niveau");
				etudiants.add(new Etudiant(id, nom, filiere, niveau));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return etudiants;
	}
}

package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ensa.java.qcm.models.Etudiant;
import org.ensa.java.qcm.models.QCM;
import org.ensa.java.qcm.models.ResultatExamen;

public class ResultatExamenDAO {
	private Connection connection;
	private QCMDAO qcmDAO;
	private EtudiantDAO etudiantDAO;
	private ProfesseurDAO professeurDAO;

	public ResultatExamenDAO(Connection connection) {
		this.connection = connection;
		this.qcmDAO = new QCMDAO(connection);
		this.etudiantDAO = new EtudiantDAO(connection);
		this.professeurDAO = new ProfesseurDAO(connection);

	}

	// Méthode pour enregistrer un résultat d'examen
	public void enregistrerResultat(ResultatExamen resultat) {
		String insertQuery = "INSERT INTO ResultatsExamen (etudiant_id, qcm_id, score) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setInt(1, resultat.getEtudiant().getId());
			preparedStatement.setInt(2, resultat.getQCM().getId());
			preparedStatement.setInt(3, resultat.getScore());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer les résultats d'un étudiant pour un QCM spécifique
	public List<ResultatExamen> getResultatsPourEtudiantEtQCM(int etudiantId, int qcmId) {
		List<ResultatExamen> resultats = new ArrayList<>();
		String selectQuery = "SELECT * FROM ResultatsExamen WHERE etudiant_id = ? AND qcm_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, etudiantId);
			preparedStatement.setInt(2, qcmId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int score = resultSet.getInt("score");
				// Vous devrez récupérer les objets Etudiant et QCM associés par leurs ID
				Etudiant etudiant = etudiantDAO.getEtudiantById(etudiantId);
				QCM qcm = qcmDAO.getQCMById(qcmId);
				resultats.add(new ResultatExamen(etudiant, qcm, score));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultats;
	}

	// Méthode pour supprimer un résultat d'examen
	public void supprimerResultat(int resultatId) {
		String deleteQuery = "DELETE FROM ResultatsExamen WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, resultatId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ResultatExamen> getResultatsByEtudiant(int etudiantId) {
		List<ResultatExamen> resultats = new ArrayList<>();
		String selectQuery = "SELECT * FROM ResultatsExamen WHERE etudiant_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, etudiantId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int qcmId = resultSet.getInt("qcm_id");
				int score = resultSet.getInt("score");

				ResultatExamen resultatExamen = new ResultatExamen(id, etudiantDAO.getEtudiantById(etudiantId),
						qcmDAO.getQCMById(qcmId), score);
				resultats.add(resultatExamen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultats;
	}

	public List<ResultatExamen> getResultatsByProfessor(int profId) {
		List<ResultatExamen> resultats = new ArrayList<>();
		String selectQuery = "SELECT * FROM ResultatsExamen r,qcms q WHERE r.qcm_id = q.id AND  q.professeur_id = ? ORDER BY q.titre";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, profId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int qcmId = resultSet.getInt("qcm_id");
				int score = resultSet.getInt("score");
				int etdId = resultSet.getInt("etudiant_id");
				ResultatExamen resultatExamen = new ResultatExamen(id, etudiantDAO.getEtudiantById(etdId),
						qcmDAO.getQCMById(qcmId), score);
				resultats.add(resultatExamen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultats;
	}

	public List<ResultatExamen> getResultatsByQCM(int qcmId) {
		List<ResultatExamen> resultats = new ArrayList<>();
		String selectQuery = "SELECT * FROM ResultatsExamen WHERE qcm_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, qcmId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int etudiantId = resultSet.getInt("etudiant_id");
				int score = resultSet.getInt("score");

				ResultatExamen resultatExamen = new ResultatExamen(id, etudiantDAO.getEtudiantById(etudiantId),
						qcmDAO.getQCMById(qcmId), score);
				resultats.add(resultatExamen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultats;
	}
}

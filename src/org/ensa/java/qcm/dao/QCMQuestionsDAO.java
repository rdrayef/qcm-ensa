package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ensa.java.qcm.models.Question;

public class QCMQuestionsDAO {
	private Connection connection;

	public QCMQuestionsDAO(Connection connection) {
		this.connection = connection;
	}

	// Méthode pour ajouter une question à un QCM
	public void addQuestionToQCM(int qcmId, int questionId) {
		String insertQuery = "INSERT INTO QCMQuestions (qcm_id, question_id) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setInt(1, qcmId);
			preparedStatement.setInt(2, questionId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer la liste des questions associées à un QCM
	public List<Question> getQuestionsForQCM(int qcmId) {
		List<Question> questions = new ArrayList<>();
		String selectQuery = "SELECT question_id FROM QCMQuestions WHERE qcm_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, qcmId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int questionId = resultSet.getInt("question_id");
				QuestionDAO questionDAO = new QuestionDAO(connection);
				Question question = questionDAO.getQuestionById(questionId);
				if (question != null) {
					questions.add(question);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}

	// Méthode pour supprimer une question d'un QCM
	public void removeQuestionFromQCM(int qcmId, int questionId) {
		String deleteQuery = "DELETE FROM QCMQuestions WHERE qcm_id = ? AND question_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, qcmId);
			preparedStatement.setInt(2, questionId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

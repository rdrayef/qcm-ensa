package org.ensa.java.qcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ensa.java.qcm.models.Question;

public class QuestionDAO {
	private Connection connection;

	public QuestionDAO(Connection connection) {
		this.connection = connection;
	}

	// Méthode pour insérer une question
	public void insertQuestion(Question question) {
		String insertQuery = "INSERT INTO Questions (description, bonneReponse) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setString(1, question.getDescription());
			preparedStatement.setInt(2, question.getBonneReponse());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer une question par son ID
	public Question getQuestionById(int id) {
		String selectQuery = "SELECT * FROM Questions WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String description = resultSet.getString("description");
				int bonneReponse = resultSet.getInt("bonneReponse");
				Question q=new Question(id, description, bonneReponse);
				q.addChoix(resultSet.getString("opt1"));
				q.addChoix(resultSet.getString("opt2"));
				q.addChoix(resultSet.getString("opt3"));
				return q;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Méthode pour mettre à jour une question
	public void updateQuestion(Question question) {
		String updateQuery = "UPDATE Questions SET description = ?, bonneReponse = ? WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setString(1, question.getDescription());
			preparedStatement.setInt(2, question.getBonneReponse());
			preparedStatement.setInt(3, question.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour supprimer une question par son ID
	public void deleteQuestion(int id) {
		String deleteQuery = "DELETE FROM Questions WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour récupérer la liste de toutes les questions
	public List<Question> getAllQuestions() {
		List<Question> questions = new ArrayList<>();
		String selectAllQuery = "SELECT * FROM Questions";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String description = resultSet.getString("description");
				int bonneReponse = resultSet.getInt("bonneReponse");
				questions.add(new Question(id, description, bonneReponse));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
}

package org.ensa.java.qcm.business;

import java.util.List;

import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.dao.QuestionDAO;
import org.ensa.java.qcm.models.Question;

public class QuestionService {
	private QuestionDAO questionDAO;

	public QuestionService(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public void creerQuestion(Question question) throws ValidationException {
		// Validation des données
		if (question == null) {
			throw new ValidationException("La question ne peut pas être null.");
		}

		String description = question.getDescription();
		List<String> choix = question.getChoix();
		int bonneReponse = question.getBonneReponse();

		if (description == null || description.trim().isEmpty()) {
			throw new ValidationException("La description de la question est requise.");
		}

		if (choix == null || choix.size() < 2) {
			throw new ValidationException("Il doit y avoir au moins deux choix pour la question.");
		}

		if (bonneReponse < 0 || bonneReponse >= choix.size()) {
			throw new ValidationException("L'index de la bonne réponse est invalide.");
		}

		// Logique métier : Vous pouvez ajouter des vérifications spécifiques ici.

		questionDAO.insertQuestion(question);
	}

	public Question getQuestionById(int id) throws ValidationException {
		// Validation des données
		if (id <= 0) {
			throw new ValidationException("ID de question invalide.");
		}

		// Logique métier : Récupérer la question par son ID.
		return questionDAO.getQuestionById(id);
	}

	public List<Question> getAllQuestions() {
		// Logique métier : Récupérer toutes les questions.
		return questionDAO.getAllQuestions();
	}

	public void mettreAJourQuestion(Question question) throws ValidationException {
		// Validation des données
		if (question == null) {
			throw new ValidationException("La question ne peut pas être nulle.");
		}

		int id = question.getId();
		String description = question.getDescription();
		List<String> choix = question.getChoix();
		int bonneReponse = question.getBonneReponse();

		if (id <= 0) {
			throw new ValidationException("ID de question invalide.");
		}

		if (description == null || description.trim().isEmpty()) {
			throw new ValidationException("La description de la question est requise.");
		}

		if (choix == null || choix.size() < 2) {
			throw new ValidationException("Il doit y avoir au moins deux choix pour la question.");
		}

		if (bonneReponse < 0 || bonneReponse >= choix.size()) {
			throw new ValidationException("L'index de la bonne réponse est invalide.");
		}

		// Logique métier : Vous pouvez ajouter des vérifications spécifiques ici.

		questionDAO.updateQuestion(question);
	}

	public void supprimerQuestion(int id) throws ValidationException {
		// Validation des données
		if (id <= 0) {
			throw new ValidationException("ID de question invalide.");
		}

		// Logique métier : Supprimer la question par son ID.
		questionDAO.deleteQuestion(id);
	}
}

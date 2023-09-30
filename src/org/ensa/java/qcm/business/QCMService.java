package org.ensa.java.qcm.business;

import java.util.List;

import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.dao.DatabaseManager;
import org.ensa.java.qcm.dao.QCMDAO;
import org.ensa.java.qcm.dao.QCMQuestionsDAO;
import org.ensa.java.qcm.models.QCM;
import org.ensa.java.qcm.models.Question;

public class QCMService {
	private QCMDAO qcmDAO;
	private QCMQuestionsDAO qcmQuestionsDAO;

	public QCMService() {
		this.qcmDAO = new QCMDAO(DatabaseManager.getConnection());
		this.qcmQuestionsDAO = new QCMQuestionsDAO(DatabaseManager.getConnection());
	}

	public void creerQCM(QCM qcm) throws ValidationException {
		// Validation des données
		if (qcm == null) {
			throw new ValidationException("Le QCM ne peut pas être nul.");
		}

		String titre = qcm.getTitre();
		int professeurId = qcm.getProfesseur().getId();

		if (titre == null || titre.trim().isEmpty()) {
			throw new ValidationException("Le titre du QCM est requis.");
		}

		if (professeurId <= 0) {
			throw new ValidationException("ID de professeur invalide.");
		}

		// Logique métier : Vous pouvez ajouter des vérifications spécifiques ici.

		qcmDAO.insertQCM(qcm);
	}

	public QCM getQCMById(int id) throws ValidationException {
		// Validation des données
		if (id <= 0) {
			throw new ValidationException("ID de QCM invalide.");
		}

		// Logique métier : Récupérer le QCM par son ID.
		return qcmDAO.getQCMById(id);
	}

	public List<QCM> getAllQCMs() {
		// Logique métier : Récupérer tous les QCMs.
		return qcmDAO.getAllQCMs();
	}

	public void mettreAJourQCM(QCM qcm) throws ValidationException {
		// Validation des données
		if (qcm == null) {
			throw new ValidationException("Le QCM ne peut pas être nul.");
		}

		int id = qcm.getId();
		String titre = qcm.getTitre();
		int professeurId = qcm.getProfesseur().getId();

		if (id <= 0) {
			throw new ValidationException("ID de QCM invalide.");
		}

		if (titre == null || titre.trim().isEmpty()) {
			throw new ValidationException("Le titre du QCM est requis.");
		}

		if (professeurId <= 0) {
			throw new ValidationException("ID de professeur invalide.");
		}

		qcmDAO.updateQCM(qcm);
	}

	public void supprimerQCM(int id) throws ValidationException {
		// Validation des données
		if (id <= 0) {
			throw new ValidationException("ID de QCM invalide.");
		}

		// Logique métier : Supprimer le QCM par son ID.
		qcmDAO.deleteQCM(id);
	}

	public List<Question> getQCMQuestions(int id) {
		return qcmQuestionsDAO.getQuestionsForQCM(id);
	}
}

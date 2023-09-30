package org.ensa.java.qcm.business;

import java.util.List;

import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.dao.DatabaseManager;
import org.ensa.java.qcm.dao.ResultatExamenDAO;
import org.ensa.java.qcm.models.ResultatExamen;

public class ResultatExamenService {
	private ResultatExamenDAO resultatExamenDAO;

	public ResultatExamenService() {
		this.resultatExamenDAO = new ResultatExamenDAO(DatabaseManager.getConnection());
	}

	public void enregistrerResultat(ResultatExamen resultatExamen) throws ValidationException {
		// Validation des données
		if (resultatExamen == null) {
			throw new ValidationException("Le résultat de l'examen ne peut pas être nul.");
		}

		int etudiantId = resultatExamen.getEtudiant().getId();
		int qcmId = resultatExamen.getQCM().getId();
		int score = resultatExamen.getScore();

		if (etudiantId <= 0) {
			throw new ValidationException("ID d'étudiant invalide.");
		}

		if (qcmId <= 0) {
			throw new ValidationException("ID de QCM invalide.");
		}

		if (score < 0) {
			throw new ValidationException("Score invalide. doit etre 0 ou plus");
		}

		// Logique métier : Vous pouvez ajouter des vérifications spécifiques ici.

		resultatExamenDAO.enregistrerResultat(resultatExamen);
	}

	public List<ResultatExamen> getResultatsByEtudiant(int etudiantId) throws ValidationException {
		// Validation des données
		if (etudiantId <= 0) {
			throw new ValidationException("ID d'étudiant invalide.");
		}

		// Logique métier : Récupérer les résultats d'un étudiant.
		return resultatExamenDAO.getResultatsByEtudiant(etudiantId);
	}

	public List<ResultatExamen> getResultatsByQCM(int qcmId) throws ValidationException {
		// Validation des données
		if (qcmId <= 0) {
			throw new ValidationException("ID de QCM invalide.");
		}

		// Logique métier : Récupérer les résultats d'un QCM.
		return resultatExamenDAO.getResultatsByQCM(qcmId);
	}
}

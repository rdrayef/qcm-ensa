package org.ensa.java.qcm.business;

import java.util.List;

import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.dao.DatabaseManager;
import org.ensa.java.qcm.dao.EtudiantDAO;
import org.ensa.java.qcm.models.Etudiant;

public class EtudiantService {
	private EtudiantDAO etudiantDAO;

	public EtudiantService() {
		this.etudiantDAO = new EtudiantDAO(DatabaseManager.getConnection());
	}

	public void inscrireEtudiant(Etudiant etudiant) throws ValidationException {
		// Validation des données
		if (etudiant == null) {
			throw new ValidationException("L'étudiant ne peut pas être nul.");
		}

		String nom = etudiant.getNom();
		String filiere = etudiant.getFiliere();
		String niveau = etudiant.getNiveau();

		if (nom == null || nom.trim().isEmpty()) {
			throw new ValidationException("Le nom de l'étudiant est requis.");
		}

		if (filiere == null || filiere.trim().isEmpty()) {
			throw new ValidationException("La filière de l'étudiant est requise.");
		}

		if (niveau == null || niveau.trim().isEmpty()) {
			throw new ValidationException("Le niveau de l'étudiant est requis.");
		}

		// Logique métier : vérifier si l'étudiant est déjà inscrit
		Etudiant existingEtudiant = etudiantDAO.getEtudiantByNom(nom);

		if (existingEtudiant != null) {
			throw new ValidationException("Cet étudiant est déjà inscrit.");
		}

		// Enregistrer l'étudiant
		etudiantDAO.insertEtudiant(etudiant);
	}

	public Etudiant getEtudiantById(int id) throws ValidationException {
		// Validation des données
		if (id <= 0) {
			throw new ValidationException("ID d'étudiant invalide.");
		}

		// Logique métier : récupérer l'étudiant par son ID
		return etudiantDAO.getEtudiantById(id);
	}

	public Etudiant getEtudiantByUserId(int userId) throws ValidationException {
		// Validation des données
		System.out.println(userId);
		if (userId <= 0) {
			throw new ValidationException("ID d'étudiant invalide.");
		}

		// Logique métier : récupérer l'étudiant par son ID
		return etudiantDAO.getEtudiantByUserId(userId);
	}

	public List<Etudiant> getAllEtudiants() {
		// Logique métier : récupérer tous les étudiants
		return etudiantDAO.getAllEtudiants();
	}

	public void mettreAJourEtudiant(Etudiant etudiant) throws ValidationException {
		// Validation des données
		if (etudiant == null) {
			throw new ValidationException("L'étudiant ne peut pas être nul.");
		}

		int id = etudiant.getId();
		String nom = etudiant.getNom();
		String filiere = etudiant.getFiliere();
		String niveau = etudiant.getNiveau();

		if (id <= 0) {
			throw new ValidationException("ID d'étudiant invalide.");
		}

		if (nom == null || nom.trim().isEmpty()) {
			throw new ValidationException("Le nom de l'étudiant est requis.");
		}

		if (filiere == null || filiere.trim().isEmpty()) {
			throw new ValidationException("La filière de l'étudiant est requise.");
		}

		if (niveau == null || niveau.trim().isEmpty()) {
			throw new ValidationException("Le niveau de l'étudiant est requis.");
		}

		// Logique métier : vérifier si l'étudiant existe et mettre à jour
		etudiantDAO.updateEtudiant(etudiant);
	}

	public void desinscrireEtudiant(int id) throws ValidationException {
		// Validation des données
		if (id <= 0) {
			throw new ValidationException("ID d'étudiant invalide.");
		}

		// Logique métier : vérifier si l'étudiant existe et le supprimer
		etudiantDAO.deleteEtudiant(id);
	}
}

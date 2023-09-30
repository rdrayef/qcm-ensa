package org.ensa.java.qcm.business;

import java.util.List;

import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.dao.ProfesseurDAO;
import org.ensa.java.qcm.models.Professeur;

public class ProfesseurService {
    private ProfesseurDAO professeurDAO;

    public ProfesseurService(ProfesseurDAO professeurDAO) {
        this.professeurDAO = professeurDAO;
    }

    public void creerProfesseur(Professeur professeur) throws ValidationException {
        // Validation des données
        if (professeur == null) {
            throw new ValidationException("Le professeur ne peut pas être nul.");
        }

        String nom = professeur.getNom();
        String specialite = professeur.getSpecialite();

        if (nom == null || nom.trim().isEmpty()) {
            throw new ValidationException("Le nom du professeur est requis.");
        }

        if (specialite == null || specialite.trim().isEmpty()) {
            throw new ValidationException("La spécialité du professeur est requise.");
        }

        // Logique métier (exemple : vérifier si le professeur existe déjà)
        Professeur existingProfesseur = professeurDAO.getProfesseurByNom(nom);

        if (existingProfesseur != null) {
            throw new ValidationException("Un professeur avec ce nom existe déjà.");
        }

        // Enregistrer le professeur
        professeurDAO.insertProfesseur(professeur);
    }

    public Professeur getProfesseurParID(int id) {
        return professeurDAO.getProfesseurById(id);
    }

    public List<Professeur> getAllProfesseurs() {
        return professeurDAO.getAllProfesseurs();
    }

    public void modifierProfesseur(Professeur professeur) throws ValidationException {
        // Validation des données
        if (professeur == null) {
            throw new ValidationException("Le professeur ne peut pas être nul.");
        }

        int id = professeur.getId();
        String nom = professeur.getNom();
        String specialite = professeur.getSpecialite();

        if (id <= 0) {
            throw new ValidationException("ID du professeur invalide.");
        }

        if (nom == null || nom.trim().isEmpty()) {
            throw new ValidationException("Le nom du professeur est requis.");
        }

        if (specialite == null || specialite.trim().isEmpty()) {
            throw new ValidationException("La spécialité du professeur est requise.");
        }

        // Logique métier (exemple : vérifier si le professeur existe déjà)
        Professeur existingProfesseur = professeurDAO.getProfesseurByNom(nom);

        if (existingProfesseur != null && existingProfesseur.getId() != id) {
            throw new ValidationException("Un autre professeur avec ce nom existe déjà.");
        }

        // Mettre à jour le professeur
        professeurDAO.updateProfesseur(professeur);
    }

    public void supprimerProfesseur(int id) {
        professeurDAO.deleteProfesseur(id);
    }
}


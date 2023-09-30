package org.ensa.java.qcm.models;

import java.util.Date;

public class QCM {
	private int id;
	private String titre;
	private Professeur professeur;
	private String cibleNiveau;
	private String cibleFiliere;
	private Date QCMDate;

	public QCM(int id, String titre, Professeur professeur, String cibleNiveau, String cibleFiliere) {
		this.id = id;
		this.titre = titre;
		this.professeur = professeur;
		this.cibleNiveau = cibleNiveau;
		this.cibleFiliere = cibleFiliere;
	}

	public int getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Professeur getProfesseur() {
		return professeur;
	}

	public void setProfesseurId(Professeur professeur) {
		this.professeur = professeur;
	}

	public String getCibleNiveau() {
		return cibleNiveau;
	}

	public void setCibleNiveau(String cibleNiveau) {
		this.cibleNiveau = cibleNiveau;
	}

	public String getCibleFiliere() {
		return cibleFiliere;
	}

	public void setCibleFiliere(String cibleFiliere) {
		this.cibleFiliere = cibleFiliere;
	}

	public Date getQCMDate() {
		return QCMDate;
	}

	public void setQCMDate(Date qCMDate) {
		QCMDate = qCMDate;
	}

}

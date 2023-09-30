package org.ensa.java.qcm.models;

public class Professeur {
	private int id;
	private String nom;
	private String specialite;

	public Professeur(int id, String nom, String specialite) {
		this.id = id;
		this.nom = nom;
		this.specialite = specialite;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
}

package org.ensa.java.qcm.models;

import java.util.List;
import java.util.Vector;

public class Question {
	private int id;
	private String description;
	private List<String> choix;
	private int bonneReponse;

	public Question(int id, String description, int bonneReponse) {
		this.id = id;
		this.description = description;
		this.bonneReponse = bonneReponse;
		this.choix = new Vector<String>();
	}

	public Question(int id, String description, int bonneReponse, List<String> choix) {
		this.id = id;
		this.description = description;
		this.bonneReponse = bonneReponse;
		this.choix = choix;
	}

	public boolean addChoix(String choix) {
		return this.choix.add(choix);
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBonneReponse() {
		return bonneReponse;
	}

	public void setBonneReponse(int bonneReponse) {
		this.bonneReponse = bonneReponse;
	}

	public List<String> getChoix() {
		return choix;
	}

	public void setChoix(List<String> choix) {
		this.choix = choix;
	}

	public void setId(int id) {
		this.id = id;
	}

}

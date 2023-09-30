package org.ensa.java.qcm.models;

public class QCMQuestions {
	private int qcmId;
	private int questionId;

	public QCMQuestions(int qcmId, int questionId) {
		this.qcmId = qcmId;
		this.questionId = questionId;
	}

	public int getQcmId() {
		return qcmId;
	}

	public int getQuestionId() {
		return questionId;
	}
}

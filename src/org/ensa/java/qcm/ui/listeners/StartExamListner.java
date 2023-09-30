package org.ensa.java.qcm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import org.ensa.java.qcm.business.EtudiantService;
import org.ensa.java.qcm.business.QCMService;
import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.models.Etudiant;
import org.ensa.java.qcm.models.QCM;
import org.ensa.java.qcm.models.Question;
import org.ensa.java.qcm.models.User;
import org.ensa.java.qcm.ui.QCMAppUI;
import org.ensa.java.qcm.ui.exam.ExamUI;

public class StartExamListner implements ActionListener {
	private QCMAppUI parent;
	private QCMService qcmService;
	private EtudiantService etudiantService;

	private User authUser;

	public StartExamListner(QCMAppUI parent) {
		this.parent = parent;
		this.qcmService = new QCMService();
		this.etudiantService = new EtudiantService();
		this.authUser = parent.getAuthUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("student".equals(this.authUser.getRole())) {
			try {
				Etudiant candidat = etudiantService.getEtudiantByUserId(authUser.getId());
				List<QCM> availableQCMs = qcmService.getAllQCMs();
				QCM qcmToPass = null;
				for (QCM qcm : availableQCMs) {
					if (qcm.getCibleFiliere().equals(candidat.getFiliere())
							&& qcm.getCibleNiveau().equals(candidat.getNiveau())) {
						qcmToPass = qcm;
						break;
					}
				}

				if (qcmToPass != null) {
					qcmToPass.setQCMDate(new Date());
					qcmService.mettreAJourQCM(qcmToPass);
					List<Question> questions = qcmService.getQCMQuestions(qcmToPass.getId());
					ExamUI examUI = new ExamUI(questions, candidat, qcmToPass);
					examUI.setVisible(true);
					parent.setVisible(false);
				}
			} catch (ValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}

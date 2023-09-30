package org.ensa.java.qcm.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import org.ensa.java.qcm.business.EtudiantService;
import org.ensa.java.qcm.business.ProfesseurService;
import org.ensa.java.qcm.business.ResultatExamenService;
import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.models.Etudiant;
import org.ensa.java.qcm.models.Professeur;
import org.ensa.java.qcm.models.ResultatExamen;
import org.ensa.java.qcm.models.User;
import org.ensa.java.qcm.ui.QCMAppUI;
import org.ensa.java.qcm.ui.exam.ViewResultUI;

public class ViewResultListener implements ActionListener {
	private QCMAppUI parent;
	private ResultatExamenService service;
	private User authUser;

	public ViewResultListener(QCMAppUI parent) {
		this.parent = parent;
		this.service = new ResultatExamenService();
		this.authUser = parent.getAuthUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("student".equals(authUser.getRole())) {
			List<ResultatExamen> results = displayStudentResults();
			ViewResultUI resultUI = new ViewResultUI(results,authUser);
			resultUI.setVisible(true);
			resultUI.setLocationRelativeTo(null);
			parent.dispose();
		} else {
			List<ResultatExamen> results = displayProfessorResults();
			ViewResultUI resultUI = new ViewResultUI(results,authUser);
			resultUI.setVisible(true);
			resultUI.setLocationRelativeTo(null);
			parent.dispose();
		}

	}

	private List<ResultatExamen> displayStudentResults() {
		List<ResultatExamen> studentResults = new Vector<>();
		try {
			Etudiant etd = new EtudiantService().getEtudiantByUserId(authUser.getId());
			studentResults = service.getResultatsByEtudiant(etd.getId());
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		return studentResults;
	}

	private List<ResultatExamen> displayProfessorResults() {
		List<ResultatExamen> studentsResults = new Vector<>();
		try {
			Professeur prof = new ProfesseurService().getProfesseurParUserID(authUser.getId());
			studentsResults = service.getResultatsByProf(prof.getId());
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		return studentsResults;
	}

}

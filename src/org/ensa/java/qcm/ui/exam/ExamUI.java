package org.ensa.java.qcm.ui.exam;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.ensa.java.qcm.business.ResultatExamenService;
import org.ensa.java.qcm.business.exceptions.ValidationException;
import org.ensa.java.qcm.models.Etudiant;
import org.ensa.java.qcm.models.QCM;
import org.ensa.java.qcm.models.Question;
import org.ensa.java.qcm.models.ResultatExamen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExamUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<Question> questions;
	private Etudiant etudiant;
	private QCM qcm;
	private int currentQuestionIndex;
	private JLabel questionLabel;
	private JRadioButton option1RadioButton;
	private JRadioButton option2RadioButton;
	private JRadioButton option3RadioButton;
	private JButton nextButton;
	private JButton submitButton;
	private JLabel timerLabel;
	private Timer questionTimer;
	private ButtonGroup buttonGroup;
	private JPanel resultsPanel;
	private JPanel questionPanel;
	private Container contentPane;
	private int score = 0;

	private int timeLeft; // Time left for the current question in seconds

	public ExamUI(List<Question> questions, Etudiant etudiant, QCM qcm) {
		this.questions = questions;
		this.etudiant = etudiant;
		this.qcm = qcm;
		this.currentQuestionIndex = 0;

		setTitle("Exam");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(10, 10));

		questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(4, 1, 5, 5));
		questionPanel.setBorder(new TitledBorder("Question"));
		questionPanel.setBackground(Color.WHITE);

		questionLabel = new JLabel();
		option1RadioButton = new JRadioButton();
		option2RadioButton = new JRadioButton();
		option3RadioButton = new JRadioButton();

		buttonGroup = new ButtonGroup();
		buttonGroup.add(option1RadioButton);
		buttonGroup.add(option2RadioButton);
		buttonGroup.add(option3RadioButton);

		questionLabel.setBackground(Color.WHITE);
		questionLabel.setOpaque(true);

		option1RadioButton.setBackground(Color.WHITE);
		option1RadioButton.setOpaque(true);

		option2RadioButton.setBackground(Color.WHITE);
		option2RadioButton.setOpaque(true);

		option3RadioButton.setBackground(Color.WHITE);
		option3RadioButton.setOpaque(true);

		questionPanel.add(questionLabel);
		questionPanel.add(option1RadioButton);
		questionPanel.add(option2RadioButton);
		questionPanel.add(option3RadioButton);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		nextButton = new JButton("Next");
		submitButton = new JButton("Submit");
		buttonPanel.add(nextButton);
		buttonPanel.add(submitButton);

		contentPane.add(questionPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		timerLabel = new JLabel("Timer: 15s");
		contentPane.add(timerLabel, BorderLayout.NORTH);

		submitButton.setVisible(false);

		// Initialize the timer for the first question
		timeLeft = 15;
		updateTimerLabel();

		questionTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timeLeft > 0) {
					timeLeft--;
					updateTimerLabel();
				} else {
					// Time's up for the current question
					questionTimer.stop();
					nextQuestion();
				}
			}
		});

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				score = calculateScore();
				nextQuestion();
			}
		});

		showCurrentQuestion();

		resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(2, 1, 5, 5));
		resultsPanel.setBorder(new TitledBorder("Results"));
		resultsPanel.setBackground(Color.WHITE);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ResultatExamenService service = new ResultatExamenService();
				score = calculateScore();
				questionTimer.stop();
				timerLabel.setVisible(false);
				ResultatExamen result = new ResultatExamen(getEtudiant(), getQcm(), getScore());
				try {
					service.enregistrerResultat(result);
				} catch (ValidationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				displayResults(score);
			}
		});
	}

	private void showCurrentQuestion() {
		if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
			Question currentQuestion = questions.get(currentQuestionIndex);
			questionLabel.setText(currentQuestion.getDescription());
			List<String> choices = currentQuestion.getChoix();
			System.out.println(choices.size());
			option1RadioButton.setText(choices.get(0));
			option2RadioButton.setText(choices.get(1));
			option3RadioButton.setText(choices.get(2));

			// Start the timer for the current question
			startTimer();
		}
	}

	private void updateTimerLabel() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("s's'");
		timerLabel.setText("Timer: " + dateFormat.format(timeLeft * 1000));
	}

	private void startTimer() {
		timeLeft = 15;
		updateTimerLabel();
		questionTimer.start();
	}

	private void nextQuestion() {
		if (currentQuestionIndex < questions.size() - 1) {
			currentQuestionIndex++;
			showCurrentQuestion();
		}

		if (currentQuestionIndex == questions.size() - 1) {
			nextButton.setVisible(false);
			submitButton.setVisible(true);
		}
	}

	private void displayResults(int score) {
		questionPanel.setVisible(false);
		resultsPanel.removeAll();

		// Create a panel for the score and additional information
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

		JLabel scoreLabel = new JLabel("Your score for this QCM is: " + score);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Change font and style

		// You can add more components to display additional information if needed
		// For example, feedback or a summary of the exam results
		JLabel feedbackLabel = new JLabel("Great job! You've completed the exam.");
		feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		scorePanel.add(scoreLabel);
		scorePanel.add(feedbackLabel);

		// Center align the components
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		resultsPanel.add(scorePanel);

		// Show the results in the JPanel
		resultsPanel.revalidate();
		resultsPanel.repaint();

		contentPane.add(resultsPanel, BorderLayout.CENTER);

		// Hide the submit button
		submitButton.setVisible(false);
	}

	private int calculateScore() {
		Question question = questions.get(currentQuestionIndex);

		int correctAnswerIndex = question.getBonneReponse(); // Assuming bonneReponse is a 0-based index

		int selectedAnswerIndex = getSelectedAnswerIndex(question);

		if (selectedAnswerIndex == correctAnswerIndex) {
			// The selected answer is correct
			score += 1; // Assign 1 point for each correct answer
		}

		return score;
	}

	private int getSelectedAnswerIndex(Question question) {
		if (option1RadioButton.isSelected()) {
			return 1;
		} else if (option2RadioButton.isSelected()) {
			return 2;
		} else if (option3RadioButton.isSelected()) {
			return 3;
		}
		return -1;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public QCM getQcm() {
		return qcm;
	}

	public int getScore() {
		return score;
	}

}

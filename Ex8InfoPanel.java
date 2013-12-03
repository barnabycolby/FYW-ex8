package ex8;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Ex8InfoPanel extends JPanel {
	private int score;
	private final JLabel scoreLabel;
	private final Ex8Grid gridPanel;
	private final JFrame frame;
	private final JLabel levelLabel;
	private int level;
	private final JLabel[] highScores;

	public Ex8InfoPanel(JFrame frame, Ex8Grid gridPanel) {
		this.gridPanel = gridPanel;
		this.frame = frame;

		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);

		// Create new game button
		JButton newGameButton = new JButton("New Game");
		newGameButton.setAlignmentX(CENTER_ALIGNMENT);
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getGridPanel().reset();
				getGridPanel().start();
				setLevel(1);
				setScore(0);
				getFrame().requestFocus();
			}

		});

		// Add some spacing between the elements
		this.add(Box.createRigidArea(new Dimension(0, 20)));

		this.add(newGameButton);

		// Add some spacing between the elements
		this.add(Box.createRigidArea(new Dimension(0, 20)));

		this.levelLabel = new JLabel("Level: 1");
		this.setLevel(1);
		this.getLevelLabel().setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.getLevelLabel());

		// Add some spacing between the elements
		this.add(Box.createRigidArea(new Dimension(0, 20)));

		this.scoreLabel = new JLabel("Score: 0");
		this.scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.setScore(0);
		this.updateScoreLabel();

		this.add(this.getScoreLabel());

		// Add some spacing between the elements
		this.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel highscoreLabel = new JLabel("Highscores");
		highscoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(highscoreLabel);
		this.highScores = new JLabel[5];
		for (int i = 0; i < this.getHighScores().length; i++) {
			this.getHighScores()[i] = new JLabel();
			this.getHighScores()[i].setAlignmentX(CENTER_ALIGNMENT);
			this.add(this.getHighScores()[i]);
		}
		this.updateHighScores();
	}

	private void updateHighScores() {
		String[] scores = this.readHighScores();
		for (int i = 0; i < scores.length; i++) {
			this.getHighScores()[i].setText("" + (i + 1) + ". " + scores[i]);
		}
	}

	private String[] readHighScores() {
		File file = new File("highscores");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			@SuppressWarnings("resource")
			BufferedReader b = new BufferedReader(new FileReader(file));
			String[] returnArray = { "", "", "", "", "" };
			String line = "";
			for (int i = 0; (line = b.readLine()) != null && i < 5; i++) {
				returnArray[i] = line;
			}

			return returnArray;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public int getScore() {
		return score;
	}

	public void addPointsToScore(int points) {
		this.setScore(this.getScore() + points);
		this.updateScoreLabel();
	}

	public void setScore(int score) {
		this.score = score;
		if (this.getScore() > this.getLevel() * 1000) {
			this.setLevel(this.getLevel() + 1);
			this.updateLevelLabel();
		}
		this.updateScoreLabel();
	}

	private void updateScoreLabel() {
		this.getScoreLabel().setText("Score: " + this.getScore());
		this.getScoreLabel().repaint();
	}

	private void updateLevelLabel() {
		this.getLevelLabel().setText("Level: " + this.getLevel());
		this.getLevelLabel().repaint();
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public Ex8Grid getGridPanel() {
		return gridPanel;
	}

	public JFrame getFrame() {
		return frame;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		this.getGridPanel().getTimer().setDelay(this.getInterval());
	}

	public JLabel getLevelLabel() {
		return levelLabel;
	}

	public int getInterval() {
		return (500 - ((this.getLevel() - 1) * 50));
	}

	public JLabel[] getHighScores() {
		return highScores;
	}

	public void storeHighscore() {
		String[] highscores = this.readHighScores();
		for (int i = 0; i < highscores.length; i++) {
			if (highscores[i].equals("")
					|| this.getScore() > Integer.parseInt(highscores[i])) {
				// Shift rest of array down one
				for (int j = i + 1; j < highscores.length; j++) {
					if (j + 1 < highscores.length) {
						highscores[j] = highscores[j + 1];
					}
				}

				// Store new score
				highscores[i] = "" + this.getScore();
				break;
			}
		}

		this.writeHighScores(highscores);
		this.updateHighScores();
	}

	private void writeHighScores(String[] highscoresArray) {
		File file = new File("highscores");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// Overwrite file
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
			for (int i = 0; i < highscoresArray.length; i++) {
				bw.write(highscoresArray[i]);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

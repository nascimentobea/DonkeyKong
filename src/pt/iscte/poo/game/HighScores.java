package pt.iscte.poo.game;

import java.io.*;
import java.util.*;

import pt.iscte.poo.gui.ImageGUI;

public class HighScores {
	private static HighScores INSTANCE;
	private PriorityQueue<Integer> scores;

	private HighScores() {
		scores = new PriorityQueue<>((a1, a2) -> Integer.compare(a1, a2));
		inFile();
	}

	public static HighScores getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HighScores();
		}
		return INSTANCE;
	}

	public void inFile() {
		try (Scanner s = new Scanner(new File("highscores.txt"))) {
			while (s.hasNextInt())
				scores.add(s.nextInt());
		} catch (FileNotFoundException e) {
			System.err.println("Error: File not found");
		}
	}

	private void addScore() {
		scores.add(ImageGUI.getInstance().getTicks());
		while (scores.size() > 10)
			scores.poll();
		updateFile();
	}

	public void updateFile() {
		try (PrintWriter writer = new PrintWriter(new File("highscores.txt"))) {
			for (Integer score : scores) {
				writer.println(score);
			}
		} catch (IOException e) {
			System.err.println("Error updating file.");
		}
	}

	public String showScores() {
		int place = 1;
		String result = "";
		for (int score : scores) {
			result += place + ". " + score + " seconds\n";
			place++;
		}
		return result;
	}

	public void updateAndShowScoresBoard() {
		addScore();
		String scoresText = "Your time is: " + ImageGUI.getInstance().getTicks() + "\n\nTop Scores: \n" + showScores();
		ImageGUI.getInstance().showMessage("Top Scores", scoresText);
	}
}

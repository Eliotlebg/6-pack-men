package fr.imt.albi.pacman.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Score {

	/* Le fichier contenant le score */
	private static final String SCOREFILE = "./resources/score.score";

	public static String getScore() {
		String str = "0";
		try {
			InputStream ips = new FileInputStream(SCOREFILE);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			str = br.readLine();
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture du score : " + exception.getMessage());
		}
		return str;
	}

	public static void setScore(String score) {
		try {
			FileWriter fw = new FileWriter(SCOREFILE);
			fw.write(score);
			fw.close();
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture du score : " + exception.getMessage());
		}
	}
}

package backend.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;

import org.apache.log4j.Logger;

public class LogController {
	
	/**
	 * Classe permettant de manipuler le fichier app.log créé par log4j.
	 */
	
	private Logger logger;
	
	/**
	 * Constructeur
	 * @param log
	 */
	public LogController(Logger log) {
		logger = log;
	}
	
	/**
	 * Méthode permettant de récuperer le fichier app.log
	 * présent à la racine du projet.
	 * @return logs
	 */
	public String getLog() {
		String data = "";
		try {
			File myObj = new File("app.log");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data += myReader.nextLine() + "/";
			}
			myReader.close();
		} catch (FileNotFoundException e) {
		  System.out.println("An error occurred. Check the logs for more infos");
		  logger.error(e);
		  return null;
		}

		return data;
	}
	
}
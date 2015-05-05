package edu.ncsu.csc316.wdhouseh.spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpellChecker {

	private int dictionarySize;
	private int inputSize;
	private int misspelledWords;
	private HashTable dictionary;

	public SpellChecker() {
		dictionarySize = 0;
		inputSize = 0;
		misspelledWords = 0;
		dictionary = new HashTable();
	}

	public void userInterface() {
		Scanner console = new Scanner(System.in);
		System.out.println("Dictionary File: ");
		String dictionaryFile = console.next();
		System.out.println("User File: ");
		String userFile = console.next();
		System.out.println("Output File: ");
		String outputFile = console.next();
		try {
			PrintWriter wr = new PrintWriter(new File(outputFile));
			buildDictionary(dictionaryFile);		
			wr = readDocument(userFile, wr);
			printOutput(wr);
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		console.close();
	}

	public void buildDictionary(String dictionaryFile) {
		Scanner input;
		try {
			input = new Scanner(new File(dictionaryFile));
			while (input.hasNext()) {
				String entry = input.next();
				dictionary.insert(entry);
				dictionarySize++;
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	public PrintWriter readDocument(String userFile, PrintWriter wr) {
		try {
			Scanner input = new Scanner(new File(userFile));				
			while (input.hasNext()) {
				String word = input.next();
				for (int i = 0; i < word.length(); i++) {
					if((word.charAt(i) < 'A' && word.charAt(i) > '9') || word.charAt(i) < '0' || word.charAt(i) > 'z' || (word.charAt(i) > 'Z' && word.charAt(i) < 'a')) {
						if (word.charAt(i) != '\'') {
							word = word.substring(0, i) + word.substring(i + 1, word.length());
							i--;
						}
					}
				}
				String originalWord = word;
				inputSize++;
				while (!dictionary.lookup(word)) {
					if (word.charAt(0) >= 65 && word.charAt(0) <= 90) {
						word = word.toLowerCase();
					} else if (word.endsWith("‘s")) {
						word = word.substring(0, word.length() - 2);
					} else if (word.endsWith("s")) {
						word = word.substring(0, word.length() - 1);
					} else if (word.endsWith("ed")) {
						word = word.substring(0, word.length() - 2);
						if (!dictionary.lookup(word)) {
							word += "e";
						}
					} else if (word.endsWith("er")) {
						word = word.substring(0, word.length() - 2);
						if (!dictionary.lookup(word)) {
							word += "e";
						}
					} else if (word.endsWith("ing")) {
						word = word.substring(0, word.length() - 3);
						if (!dictionary.lookup(word)) {
							word += "e";
						}
					} else if (word.endsWith("ly")) {
						word = word.substring(0, word.length() - 2);
					} else {
						misspelledWords++;
						wr.print(originalWord + "\n");
						break;
					}
				}			
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wr;
	}

	public void printOutput(PrintWriter wr) {
		wr.printf("%d Words in the Dictionary.\n", dictionarySize);
		wr.printf("%d Words in the User Input File.\n", inputSize);
		wr.printf("%d Misspelled Words.\n", misspelledWords);
		wr.printf("%d Total Probes.\n", dictionary.getTotalProbes());
		int probesPerWord = dictionary.getTotalProbes() / inputSize;
		wr.printf("%d Probes Per Word.\n", probesPerWord);
		wr.printf("%d Probes Per Lookup.\n", dictionary.getProbesPerLookup());
	}

	public static void main(String[] args) {
		SpellChecker s = new SpellChecker();
		s.userInterface();
	}
}

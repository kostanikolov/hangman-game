package hangman.models;

import hangman.io.ConsoleOutputWriter;
import hangman.util.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hangman {
    private ConsoleOutputWriter writer;

    private int attempts;

    private int points;

    public Hangman() {
        this.attempts = 10;
        this.points = 0;
        this.writer = new ConsoleOutputWriter();
    }

    public int getAttempts() {
        return this.attempts;
    }
	
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

    public void decreaseAttempts() {
        this.attempts--;
    }

    public int getPoints() {
        return this.points;
    }

    public void increasePoints() {
        this.points++;
    }

    public boolean guessedWord(StringBuilder wordBeforeHiding, StringBuilder hiddenWord) {
        return wordBeforeHiding.toString().equals(hiddenWord.toString());
    }

    /*
        Chooses a category
        from the dictionary.
    */
    public String chooseCategory(String inputCategory) throws IOException {
        File currentDir = new File("");
        String path = currentDir.getAbsolutePath()
                + Constants.CATEGORIES_FILE_LOCAL_PATH;

        Path categoriesPath = Paths.get(path);
        List<String> categories = Files.readAllLines(categoriesPath);

        List<String> temp = new ArrayList<>();

        List<String> footballTeamsCat = new ArrayList<>();
        List<String> booksCat = new ArrayList<>();
        List<String> programmingPrinciplesCat = new ArrayList<>();

        for (String category : categories) {
            switch (category) {
                case Constants.FOOTBALL_TEAMS_CATEGORY:
                    temp = footballTeamsCat;
                    break;
                case Constants.BOOKS_CATEGORY:
                    temp = booksCat;
                    break;
                case Constants.PROGRAMMING_PRINCIPLES_CATEGORY:
                    temp = programmingPrinciplesCat;
                    break;
                default:
                    temp.add(category);
                    break;
            }
        }

        String word = "";
        word = chooseWord(inputCategory, footballTeamsCat, booksCat, programmingPrinciplesCat);
        return word;
    }

    /*
        Chooses a word
        from the selected category of the dictionary.
     */
    public String chooseWord(String inputCategory,
                             List<String> footballTeamsCat,
                             List<String> booksCat,
                             List<String> programmingPrinciplesCat) {

        Random random = new Random();
        int n = 0;

        switch (inputCategory) {
            case Constants.FOOTBALL_TEAMS_CAT_STRING:
                n = random.nextInt(footballTeamsCat.size());
                return splitWordBySpace(footballTeamsCat.get(n).toCharArray());

            case Constants.BOOKS_CAT_STRING:
                n = random.nextInt(booksCat.size());
                return splitWordBySpace(booksCat.get(n).toCharArray());

            case Constants.PROGRAMMING_PRINCIPLES_CAT_STRING:
                n = random.nextInt(programmingPrinciplesCat.size());
                return splitWordBySpace(programmingPrinciplesCat.get(n).toCharArray());
        }

        throw new NullPointerException(Constants.INCORRECT_CATEGORY_EXCEPTION_MESSAGE);
    }

    /*
        Replaces all characters
        in the words/phrases with an underscore.
     */
    public String hideWord(char[] word) {
        String hiddenWord = "";

        for (char c : word) {
            hiddenWord += c;
        }

        String chars = "\\w";

        hiddenWord = hiddenWord.replaceAll(chars, "_");
        return hiddenWord.trim();
    }

    /*
        Divides the given word/phrase by whitespace.
     */
    public String splitWordBySpace(char[] word) {
        String dividedWord = "";

        for (char ch : word) {
            if (ch != ' ') {
                dividedWord += ch + " ";
            } else {
                dividedWord += " ";
            }
        }

        return dividedWord.trim();
    }
}

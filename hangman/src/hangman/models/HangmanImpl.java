package hangman.models;

import hangman.interfaces.Hangman;
import hangman.util.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HangmanImpl implements Hangman {
    private int attempts;

    private int points;

    private List<String> footballTeamsCat;

    private List<String> booksCat;

    private List<String> programmingPrinciplesCat;

    private Map<String, List<String>> categoriesMap;

    public HangmanImpl() {
        this.attempts = 10;
        this.points = 0;
        this.footballTeamsCat = new ArrayList<>();
        this.booksCat = new ArrayList<>();
        this.programmingPrinciplesCat = new ArrayList<>();
        this.categoriesMap = new LinkedHashMap<>();
    }

    @Override
    public int getAttempts() {
        return this.attempts;
    }

    @Override
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public void decreaseAttempts() {
        this.attempts--;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public void increasePoints() {
        this.points++;
    }

    @Override
    public boolean guessedWord(StringBuilder wordBeforeHiding, StringBuilder hiddenWord) {
        return wordBeforeHiding.toString().equals(hiddenWord.toString());
    }

    /*
        Chooses a category
        from the dictionary.
    */
    @Override
    public String chooseCategory(String inputCategory) throws IOException {
        File currentDir = new File("");
        String path = currentDir.getAbsolutePath()
                + Constants.CATEGORIES_FILE_LOCAL_PATH;

        Path categoriesPath = Paths.get(path);
        List<String> categories = Files.readAllLines(categoriesPath);

        List<String> temp = new ArrayList<>();

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
    @Override
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
    @Override
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
    @Override
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

package hangman.engines;

import hangman.io.ConsoleInputReader;
import hangman.io.ConsoleOutputWriter;
import hangman.models.Hangman;
import hangman.util.Constants;

import java.io.IOException;

public class Engine {
    private ConsoleInputReader reader;

    private ConsoleOutputWriter writer;

    private Hangman hangman;

    public Engine(Hangman hangman, ConsoleInputReader reader, ConsoleOutputWriter writer) {
        this.hangman = hangman;
        this.reader = reader;
        this.writer = writer;
    }

    public void run() throws IOException {
        while (true) {
            this.writer.writeLine(Constants.CHOOSE_CATEGORY_MESSAGE);
            this.writer.writeLine(Constants.FOOTBALL_TEAMS_CHOICE_MESSAGE);
            this.writer.writeLine(Constants.BOOKS_CHOICE_MESSAGE);
            this.writer.writeLine(Constants.PROGRAMMING_PRINCIPLES_CHOICE_MESSAGE);

            this.writer.write(">");
            String inputCategory = this.reader.readLine().toLowerCase();

            StringBuilder wordBeforeHiding = new StringBuilder();
            StringBuilder hiddenWord = new StringBuilder();

            try {
                wordBeforeHiding.append(this.hangman.chooseCategory(inputCategory));
                hiddenWord.append(this.hangman.hideWord(wordBeforeHiding.toString().toCharArray()));

            } catch (NullPointerException npe) {
                this.writer.writeLine(npe.getMessage());
                continue;
            }

            while (true) {
                this.writer.writeLine(String.format(Constants.LEFT_ATTEMPTS_MESSAGE, this.hangman.getAttempts()));
                this.writer.writeLine(String.format(Constants.CURRENT_PHRASE_MESSAGE, hiddenWord));

                this.writer.writeLine(Constants.ENTER_LETTER_MESSAGE);
                this.writer.write(">");

                String letter = this.reader.readLine();
                char inputLetter = letter.charAt(0);
                boolean foundLetter = false;

                /*
                    Reveals the characters when the player guessed them.
                 */
                for (int i = 0; i < wordBeforeHiding.length(); i++) {
                    if (Character.toLowerCase(inputLetter)
                            == Character.toLowerCase(wordBeforeHiding.charAt(i))) {

                        foundLetter = true;
                        hiddenWord.setCharAt(i, wordBeforeHiding.charAt(i));
                    }
                }

                if (hangman.guessedWord(wordBeforeHiding, hiddenWord)) {
                    this.hangman.increasePoints();
					this.hangman.setAttempts(10);
                    this.writer.writeLine(Constants.REVEALED_WORD_MESSAGE);
                    this.writer.writeLine(String.format(Constants.REVEALED_WORD, hiddenWord.toString()));
                    this.writer.writeLine(String.format(Constants.CURRENT_SCORE, hangman.getPoints()));
                    break;
                }

                if (!foundLetter) {
                    hangman.decreaseAttempts();
                    this.writer.writeLine(Constants.NON_CONTAINING_LETTER_MESSAGE);

                    if (hangman.getAttempts() == 0) {
                        this.writer.writeLine(String.format(Constants.GAME_OVER, wordBeforeHiding.toString()));
                        this.writer.writeLine(String.format(Constants.FINAL_POINTS, this.hangman.getPoints()));
                        return;
                    }
                }
            }
        }
    }
}

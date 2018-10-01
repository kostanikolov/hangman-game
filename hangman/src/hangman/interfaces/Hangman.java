package hangman.interfaces;

import java.io.IOException;
import java.util.List;

public interface Hangman {

    void decreaseAttempts();

    void increasePoints();

    boolean guessedWord(StringBuilder wordBeforeHiding, StringBuilder hiddenWord);

    String chooseCategory(String inputCategory) throws IOException;

    String chooseWord(String inputCategory,
                      List<String> footballTeamsCat,
                      List<String> booksCat,
                      List<String> programmingPrinciplesCat);

    String hideWord(char[] word);

    String splitWordBySpace(char[] word);

    int getAttempts();

    void setAttempts(int attempts);

    int getPoints();
}

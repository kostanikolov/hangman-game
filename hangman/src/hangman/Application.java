package hangman;

import hangman.engines.Engine;
import hangman.io.ConsoleInputReader;
import hangman.io.ConsoleOutputWriter;
import hangman.models.Hangman;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        ConsoleInputReader reader = new ConsoleInputReader();
        ConsoleOutputWriter writer = new ConsoleOutputWriter();
        Hangman hangman = new Hangman();
        Engine engine = new Engine(hangman, reader, writer);

        engine.run();
    }
}

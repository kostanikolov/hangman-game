package hangman;

import hangman.engines.Engine;
import hangman.interfaces.Hangman;
import hangman.interfaces.InputReader;
import hangman.interfaces.OutputWriter;
import hangman.io.ConsoleInputReader;
import hangman.io.ConsoleOutputWriter;
import hangman.models.HangmanImpl;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        InputReader reader = new ConsoleInputReader();
        OutputWriter writer = new ConsoleOutputWriter();
        Hangman hangman = new HangmanImpl();
        Engine engine = new Engine(hangman, reader, writer);

        engine.run();
    }
}

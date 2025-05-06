import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TypingTest {

    private static String lastInput = "";
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Boolean> results = new ArrayList<>();

    public static class InputRunnable implements Runnable {

        //TODO: Implement a thread to get user input without blocking the main thread
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    lastInput = scanner.nextLine();
                } catch (Exception e) {
                    break;
                }
            }
        }
    }


    public static void testWord(String wordToTest, Thread thread) {
        try {
            System.out.println();
            System.out.println(wordToTest);
            lastInput = "";
            int time = wordToTest.length() * 500;

            // TODO
            thread.join(time);

            System.out.println();
            System.out.println("You typed: " + lastInput);
            if (lastInput.equals(wordToTest)) {
                System.out.println("Correct");
                results.add(true);
            } else {
                System.out.println("Incorrect");
                results.add(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void typingTest(List<String> inputList) throws InterruptedException {
        Thread thread = new Thread(new InputRunnable());
        thread.start();
        for (int i = 0; i < inputList.size(); i++) {
            String wordToTest = inputList.get(i);
            testWord(wordToTest, thread);
            Thread.sleep(2000); // Pause briefly before showing the next word
        }
        thread.interrupt();

        // TODO: Display a summary of test results
        System.out.println("Typing test finished!");
        System.out.println("Correct answers: " + Collections.frequency(results, true) + "/" + results.size());
    }

    public static ArrayList loadingWords(String path) {
        ArrayList words = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void main(String[] args) throws InterruptedException {
        // TODO: Replace the hardcoded word list with words read from the given file in the resources folder (Words.txt)
        ArrayList<String> words = loadingWords("C:\\Users\\Sepehr\\exercise_5\\Fifth-Assignment-Multithreading-Basics\\src\\main\\resources\\Words.txt"); //probablly have a problem
        typingTest(words);

        System.out.println("Press enter to exit.");
        scanner.nextLine();
    }
}
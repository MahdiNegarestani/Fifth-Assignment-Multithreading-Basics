import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class TypingTest {

    private static String lastInput = "";
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Boolean> results = new ArrayList<>();
    private static boolean inputReceived = false;
    static long totalTime = 0;

    public static class InputRunnable implements Runnable {

        //TODO: Implement a thread to get user input without blocking the main thread
        @Override
        public void run() {
           lastInput = scanner.nextLine();
           inputReceived = true;
        }
    }


    public static void testWord(String wordToTest) {
        try {
            System.out.println();
            System.out.println(wordToTest);
            lastInput = "";
            Thread thread = new Thread(new InputRunnable());
            thread.start();

            // TODO
            int timeOut = wordToTest.length() * 1000;
            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime < timeOut) && !inputReceived) {
                Thread.sleep(100);
            }
            if (!inputReceived) {
                System.out.println("\nFinish,  press Enter to show the next word");
                thread.join();
                lastInput = "";
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            totalTime += elapsedTime;

            System.out.println();
            System.out.println("You typed: " + lastInput);
            if (lastInput.equals(wordToTest)) {
                System.out.println("Correct");
                results.add(true);
            } else {
                System.out.println("Incorrect");
                results.add(false);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void typingTest(List<String> inputList) throws InterruptedException {
        Collections.shuffle(inputList);
        for (int i = 0; i < inputList.size(); i++) {
            String wordToTest = inputList.get(i);
            testWord(wordToTest);
            Thread.sleep(2000); // Pause briefly before showing the next word
            inputReceived = false;
        }

        // TODO: Display a summary of test results
        System.out.println("Typing test finished!");
        System.out.println("Total time taken: " + totalTime + " ms");
        System.out.println("Correct answers: " + Collections.frequency(results, true) + "/" + results.size());
        System.out.println("Average time per word: " + (totalTime / inputList.size()) + " ms");
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
import java.io.*;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class WordleClone {

    public static void main(String args[]) {

        // Initializes variable guess
        String guess = "";

        // Calls function getAnswer to get random word from text file
        String answer = getAnswer();
        // Assigns int guesses to 0
        int guesses = 0;

        // Declares variable input to take in user input
        Scanner input = new Scanner(System.in);

        // Loop that will iterate until user makes 6 guesses, or if user guesses answer correctly
        do {

            // Asks and takes input so long as input is 5 characters long
            do {
                System.out.print("Guess: ");
                guess = input.nextLine();
                if(guess.length() != 5){
                    System.out.println("Guess must be 5 characters");
                }
                checkGuess(guess);
            } while(guess.length() != 5);

            // For loop that goes through each character in guess to compare to characters in answer
            for(int i = 0; i < guess.length(); i++) {

                // Compares ith character of guess to ith character in answer, and will print a 1 if it is a match, else it will print a 0 if there is no match
                if(guess.charAt(i) == answer.charAt(i)) {
                    System.out.print("1");
                }
                else if(answer.indexOf(guess.charAt(i)) != -1) {
                    System.out.print("2");
                }
                else {
                    System.out.print("0");
                }
            }

            // Prints line after showing how many characters user guessed correctly
            System.out.println();

            // Iterates guesses by 1
            guesses += 1;

            // Displays correct answers when user runs out of guesses or user guesses the correct answer, and prompts users if they want to retry
            if(guesses == 6 && guess.compareTo(answer) != 0 || guess.compareTo(answer) ==0){
                System.out.println("Correct answer is " + answer);
                if(guess.compareTo(answer) == 0) {
                    System.out.println("CONGRATS!!!");
                }
                System.out.print("Want to try again: ");
                String retry = input.nextLine();
                retry = retry.toLowerCase();
                if(retry.equals("y") || retry.equals("yes")) {
                    answer = getAnswer();
                    guesses = 0;
                }
            }


        } while(guesses < 6 && guess.compareTo(answer) != 0);


    }


    private static String getAnswer() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("E:/IntelliJ/WordleClone/src/dictionary.txt"));

            Random rand = new Random();

            int line = rand.nextInt(5757);
            int index = 1;

            while ((file.readLine()) != null) {
                if (index == line) {
                    String answer = (file.readLine());
                    return answer;

                }
                index++;
            }
        }
        catch(Exception noFile){
            System.out.println("That file cannot be found");
        }
        return("Answer cannot be found");
    }

    private static String checkGuess(String guess) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("E:/IntelliJ/WordleClone/src/dictionary.txt"));

            int index = 1;

            while ((file.readLine()) != null) {
                if (guess.equals(index)) {

                    return guess;
                }
                index++;
            }
        }
        catch(Exception noFile){
            System.out.println("That file cannot be found");
        }
        return("Guessed word is not found in dictionary");
    }
}

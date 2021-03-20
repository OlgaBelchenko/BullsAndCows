import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {

        private final char[] initialArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        private char[] resultArray;
        private String secretCode;
        private int numSymbols;
        private int length;


        public BullsAndCows(int length, int numSymbols) {

            this.length = length;
            this.numSymbols = numSymbols;
            this.resultArray = new char[numSymbols];
            generateArray();
            generateSecretCode();
            printStart();

        }

        // Grader function
        private String grader(String guess) {

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < this.secretCode.length(); i++) {
                if (guess.charAt(i) == this.secretCode.charAt(i)) {
                    bulls++;
                } else if (this.secretCode.indexOf(guess.charAt(i)) != -1) {
                    cows++;
                }
            }

            String bullResult;
            String cowResult;
            StringBuilder result = new StringBuilder("Grade: ");
            bullResult = bulls > 1 ? bulls + " bulls" : bulls + " bull";
            cowResult = cows > 1 ? cows + " cows" : cows + " cow";
            if (bulls != 0 && cows != 0) {
                result.append(bullResult).append(" and ").append(cowResult);
            } else {
                if (bulls != 0) {
                    result.append(bullResult);
                } else if (cows != 0) {
                    result.append(cowResult);
                } else {
                    result.append("None");
                }
            }

            return result.toString();

        }

        public boolean checkGuessLength(String guess) {
            if (guess.length() != secretCode.length()) {
                System.out.println("The length of your guess isn't correct! Try again!");
                return false;
            }
            return true;
        }

        public boolean checkCorrectSymbols(String s) {
            String strResultArray = String.valueOf(this.resultArray);
            for (int i = 0; i < s.length(); i++) {
                if (!strResultArray.contains(s.charAt(i) + "")) {
                    System.out.println("Your input contains invalid characters! Try again!");
                    return false;
                }
            }
            return true;
        }

        // Helper functions
        private void generateSecretCode() {

            StringBuilder sb = new StringBuilder();

            shuffleArray();
            for (int i = 0; i < this.length; i++) {
                sb.append(this.resultArray[i]);
            }

            // debugging
            this.secretCode = sb.toString();
            System.out.println(secretCode);

        }

        private void printStart() {
            String numbers;
            String letters;
            numbers = numSymbols >= 10 ? "(0-9" : "(0-" + this.initialArray[this.numSymbols - 1];
            if (this.numSymbols > 11) {
                letters = ", " + this.initialArray[10] + "-" + this.initialArray[this.numSymbols - 1] + ").";
            } else if (this.numSymbols == 11) {
                letters = ", " + this.initialArray[10] + ").";
            } else {
                letters = ")";
            }
            System.out.printf("The secret is prepared: %s %s%s%n", "*".repeat(this.length), numbers, letters);
        }

        private void generateArray() {

            if (this.numSymbols >= 0) {
                System.arraycopy(this.initialArray, 0, this.resultArray, 0, this.numSymbols);
            }

        }

        private void shuffleArray() {

            Random rand = new Random();
            for (int i = 0; i < this.resultArray.length; i++) {
                int index = rand.nextInt(i + 1);
                char a = this.resultArray[i];
                this.resultArray[i] = this.resultArray[index];
                this.resultArray[index] = a;

            }

        }


        public static void main(String[] args) {

            Scanner scan = new Scanner(System.in);

            System.out.println("Input the length of the secret code:");
            String strCodeLength = scan.nextLine();
            int codeLength = 0;
            try {
                codeLength = Integer.parseInt(strCodeLength);
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + strCodeLength + "\" isn't a valid number.");
                System.exit(0);
            }
            if (codeLength > 36) {
                System.out.println("Error: can't generate a secret number with " +
                        "a length of " + codeLength + " because there aren't enough unique symbols.");
                System.exit(0);
            }
            if (codeLength < 1) {
                System.out.println("Error: can't generate a secret number with " + codeLength + "symbols.");
                System.exit(0);
            }

            System.out.println("Input the number of possible symbols in the code:");
            int symbols = scan.nextInt();
            if (symbols < codeLength) {
                System.out.println("Error: it's not possible to generate a code with a length of " +
                        codeLength + " with " + symbols + " unique symbols.");
                System.exit(0);
            }
            if (symbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            }
            scan.nextLine();


            BullsAndCows game = new BullsAndCows(codeLength, symbols);
            int counter = 1;
            while (true) {
                System.out.println("Turn " + counter + ":");
                String guess = scan.nextLine();
                if (!game.checkGuessLength(guess) || !game.checkCorrectSymbols(guess)) {
                    continue;
                }
                System.out.println(game.grader(guess));
                if (guess.equals(game.secretCode)) {
                    System.out.println("Congratulations! You guessed the secret code.");
                    break;
                }
                counter++;
            }

        }


    }


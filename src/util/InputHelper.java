package util;

import java.util.Scanner;

/**
 * Safe console input helper.
 * Handles invalid input gracefully so the program doesn't crash.
 */
public class InputHelper {

    private Scanner scanner;

    public InputHelper() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Read a non-empty string from the user.
     */
    public String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Read an integer from the user with validation.
     */
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    /**
     * Read an integer within a range [min, max].
     */
    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    /**
     * Read a double from the user with validation.
     */
    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Read a string, allowing empty input (returns "" if user presses Enter).
     * Unlike readString(), this does not reject blank input.
     */
    public String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Display a prompt and wait for the user to press Enter.
     */
    public void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Close the scanner when the program exits.
     */
    public void close() {
        scanner.close();
    }
}

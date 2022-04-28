package view;

import java.util.List;
import java.util.Scanner;

public class InputManager {
    private static final InputManager INSTANCE = new InputManager();

    private InputManager() {
    }

    private final Scanner scanner = new Scanner(System.in);

    // Get number from user using console input.
    // min and max are included in possible choices.
    public int getNumberFromUserBetweenMinAndMax(int min, int max) {
        int result = min - 1;
        do {
            try{
                result = scanner.nextInt();
            } catch (Exception exc) {
                scanner.next();
            }
        } while(result < min || result > max);
        return result;
    }

    public int getNumberFromSpecifiedRange(int min, List<Integer> ints) {
        int result = min - 1;
        do {
            try {
                result = scanner.nextInt();
            } catch (Exception exc) {
                scanner.next();
            }
        } while(result < min || !ints.contains(result));
        return result;
    }

    // true for Yes
    // false for No

    public boolean getYesOrNo() {
        String input;
        for( ; ; ) {
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                return true;
            }
            else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                return false;
            }
        }
    }

    public String inputWithoutSpaces () {
        String input;
        do {
            input = scanner.nextLine();
        } while (input.equals("") || input.contains(" "));
        return input;
    }

    public String inputText() {
        String str;
        do {
            str = scanner.nextLine();
        } while(str.equals("") || str.equals("\n"));


        return str;

    }

    public static InputManager getInstance() {
        return INSTANCE;
    }
}

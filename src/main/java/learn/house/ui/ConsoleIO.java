package learn.house.ui;

import learn.house.models.States;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleIO {

    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_STRING
            = "[INVALID] Value cannot contain punctuation or numbers.";
    private static final String INVALID_STATE
            = "[INVALID] State must be two letters.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a date in MM/dd/yyyy format.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public String readPhrase(String prompt) {
        boolean isWord = false;
        while (true) {
            String result = readRequiredString(prompt);
            isWord = result.matches("[a-zA-Z ]+");
            if (isWord) {
                return result;
            }
            println(INVALID_STRING);
        }
    }

    public String readWord(String prompt) {
        boolean isWord = false;
        while (true) {
            String result = readRequiredString(prompt);
            isWord = result.matches("[a-zA-Z]+");
            if (isWord) {
                return result;
            }
            println(INVALID_STRING);
        }
    }

    public States readState(String prompt) {
        boolean isState = false;
        while (true) {
            try {
                String result = readString(prompt);
                return States.valueOf(result);
            } catch (IllegalArgumentException ex) {
                println("That's not a US state.");
            }
            println(INVALID_STRING);
        }
    }
    public States readStateOptional(String prompt) {
        boolean isState = false;
        while (true) {
            try {
                String result = readString(prompt);
                if (result.equals("")) return null;
                return States.valueOf(result);
            } catch (IllegalArgumentException ex) {
                println("That's not a US state.");
            }
            println(INVALID_STRING);
        }
    }

    public String readEmail(String prompt) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        while (true) {
            String email = readString(prompt);
            if (email.matches(regex)) {
                return email;
            }
            println("Invalid email format");
        }
    }
    public String readEmailOptional(String prompt) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        while (true) {
            String email = readString(prompt);
            if (email.equals("")) return null;
            if (email.matches(regex)) {
                return email;
            }
            println("Invalid email format");
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public double readDouble(String prompt, double min, double max) {
        while (true) {
            double result = readDouble(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }
    public BigDecimal readBigDecimalOptional(String prompt) {
        while (true) {
            String input = readString(prompt);
            if (input.equals("")) return null;
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            BigDecimal result = readBigDecimal(prompt);
            if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }
}
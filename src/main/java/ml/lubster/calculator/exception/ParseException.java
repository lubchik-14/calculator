package ml.lubster.calculator.exception;

public class ParseException extends Exception {
    private String message;

    public ParseException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

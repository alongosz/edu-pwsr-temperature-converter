package net.longosz.TemperatureConverter.errors;

/**
 * Exception class to be thrown for validation errors.
 */
public class UIValidationException extends Exception {
    /**
     * @param message User-friendly validation message
     */
    public UIValidationException(String message) {
        super(message);
    }
}

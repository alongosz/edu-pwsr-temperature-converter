package net.longosz.TemperatureConverter;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.longosz.TemperatureConverter.converters.CelsiusConverter;
import net.longosz.TemperatureConverter.converters.FahrenheitConverter;
import net.longosz.TemperatureConverter.converters.IConverter;
import net.longosz.TemperatureConverter.converters.KelvinConverter;
import net.longosz.TemperatureConverter.errors.UIValidationException;

public class Controller {

    @FXML
    private RadioButton fromCelsius;

    @FXML
    private ToggleGroup inputScale;

    @FXML
    private RadioButton fromFahrenheit;

    @FXML
    private RadioButton fromKelvin;

    @FXML
    private TextField inputField;

    @FXML
    private TextField outputField;

    @FXML
    private RadioButton toCelsius;

    @FXML
    private ToggleGroup outputScale;

    @FXML
    private RadioButton toFahrenheit;

    @FXML
    private RadioButton toKelvin;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        final ChangeListener<Toggle> toggleChanged = (observableValue, oldValue, newValue) -> {
            resetOutput();
            if (isReady()) {
                recalculate();
            }
        };

        inputScale.selectedToggleProperty().addListener(toggleChanged);
        outputScale.selectedToggleProperty().addListener(toggleChanged);

        // listen for dynamic changes of input text field
        inputField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            resetOutput();
            // immediately display user an error if he entered sth different than number
            if (isInputValueValid() && isReady()) {
                recalculate();
            }
        });
    }

    @FXML
    void inputChanged(ActionEvent event) {
        resetOutput();
        recalculate();
    }

    private void resetOutput() {
        outputField.setText("");
        setStatus("");
    }

    private void recalculate() {
        try {
            float inputValue = getInputNumber();
            double result = convert(inputValue);
            outputField.setText(String.format("%.4f", result));
        } catch (UIValidationException e) {
            setStatus(e.getLocalizedMessage());
            showErrorDialog(e.getLocalizedMessage());
        }
    }

    private double convert(float inputValue) throws UIValidationException {
        IConverter converter = getConverter();

        if (toCelsius.isSelected()) {
            return converter.toCelsius(inputValue);
        } else if (toFahrenheit.isSelected()) {
            return converter.toFahrenheit(inputValue);
        } else if (toKelvin.isSelected()) {
            return converter.toKelvin(inputValue);
        }

        throw new UIValidationException("Select an output scale");
    }

    private IConverter getConverter() throws UIValidationException {
        if (fromCelsius.isSelected()) {
            return new CelsiusConverter();
        } else if (fromFahrenheit.isSelected()) {
            return new FahrenheitConverter();
        } else if (fromKelvin.isSelected()) {
            return new KelvinConverter();
        }

        throw new UIValidationException("Select an input scale");
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    /**
     * Check if all the input data are ready for calculation of converted temperature w/o throwing exception
     *
     * @return bool
     */
    private boolean isReady() {
        return !inputField.getText().trim().isEmpty() &&
                // both scales have to be selected for calculations to make sense
                inputScale.getSelectedToggle() != null &&
                outputScale.getSelectedToggle() != null;
    }

    /**
     * @return float
     */
    private float getInputNumber() throws UIValidationException {
        try {
            return Float.parseFloat(inputField.getText().trim());
        } catch (NumberFormatException e) {
            inputField.requestFocus();
            throw new UIValidationException("Provided input value should be a decimal number");
        }
    }

    /**
     * Only check if it is possible to convert input to float.
     */
    private boolean isInputValueValid() {
        if (inputField.getText().isEmpty()) {
            // empty value is considered valid at this point due to usability, {@see isReady} method
            return true;
        }
        try {
            getInputNumber();
            return true;
        } catch (UIValidationException e) {
            setStatus(e.getLocalizedMessage());
            return false;
        }
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
    }
}

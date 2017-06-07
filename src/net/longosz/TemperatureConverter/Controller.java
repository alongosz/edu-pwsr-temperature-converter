package net.longosz.TemperatureConverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import net.longosz.TemperatureConverter.converters.CelsiusConverter;
import net.longosz.TemperatureConverter.converters.FahrenheitConverter;
import net.longosz.TemperatureConverter.converters.IConverter;
import net.longosz.TemperatureConverter.converters.KelvinConverter;
import net.longosz.TemperatureConverter.errors.UIValidationException;

public class Controller {
    @FXML
    public void initialize() {
        // Nothing to do
    }

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
    void inputChanged(ActionEvent event) {
        recalculate();
    }

    private void recalculate() {

        if (inputField.getText().trim().isEmpty()) {
            outputField.setText("");
            return;
        }

        try {
            float inputValue = Float.parseFloat(inputField.getText().trim());
            double result = convert(inputValue);
            outputField.setText(Double.toString(result));
        } catch (NumberFormatException e) {
            inputField.requestFocus();
            showErrorDialog("Provided input value should be a decimal number");
        } catch (UIValidationException e) {
            System.err.println(e.getMessage());
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
        } else  if (fromFahrenheit.isSelected()) {
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
}

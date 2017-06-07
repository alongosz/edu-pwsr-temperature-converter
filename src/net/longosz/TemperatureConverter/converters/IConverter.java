package net.longosz.TemperatureConverter.converters;

public interface IConverter {
    double toCelsius(float temperature);

    double toKelvin(float temperature);

    double toFahrenheit(float temperature);
}

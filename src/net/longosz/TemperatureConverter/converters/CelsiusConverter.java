package net.longosz.TemperatureConverter.converters;

public class CelsiusConverter implements IConverter {
    @Override
    public double toCelsius(float temperature) {
        return temperature;
    }

    @Override
    public double toKelvin(float temperature) {
        return temperature + 273.15;
    }

    @Override
    public double toFahrenheit(float temperature) {
        return temperature * 1.8 + 32;
    }
}

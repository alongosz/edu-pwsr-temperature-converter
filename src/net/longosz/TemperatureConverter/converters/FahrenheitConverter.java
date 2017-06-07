package net.longosz.TemperatureConverter.converters;

public class FahrenheitConverter implements IConverter {
    @Override
    public double toCelsius(float temperature) {
        return  5.0/9 * (temperature - 32);
    }

    @Override
    public double toKelvin(float temperature) {
        return (temperature + 459.67) * (5.0/9);
    }

    @Override
    public double toFahrenheit(float temperature) {
        return temperature;
    }
}

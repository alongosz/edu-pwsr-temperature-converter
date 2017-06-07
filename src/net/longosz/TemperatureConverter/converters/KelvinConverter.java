package net.longosz.TemperatureConverter.converters;

public class KelvinConverter implements IConverter {
    @Override
    public double toCelsius(float temperature) {
        return temperature - 273.15;
    }

    @Override
    public double toKelvin(float temperature) {
        return temperature;
    }

    @Override
    public double toFahrenheit(float temperature) {
        return  1.8 * (temperature - 273.15) + 32;
    }
}

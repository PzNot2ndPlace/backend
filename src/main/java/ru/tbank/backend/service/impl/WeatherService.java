package ru.tbank.backend.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tbank.backend.dto.WeatherResponse;


@Service
public class WeatherService {

    @Value("${weatherApi.token}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse fetchWeather(String city) {
        try {
            String url = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;
            String json = restTemplate.getForObject(url, String.class);
            JSONObject obj = new JSONObject(json);
            JSONObject current = obj.getJSONObject("current");

            double temp = current.getDouble("temp_c");

            String rawCondition = current.getJSONObject("condition").getString("text").toLowerCase();
            String condition;

            if (rawCondition.contains("rain")) {
                condition = "rainy";
            } else if (rawCondition.contains("clear")) {
                condition = "clear";
            } else {
                condition = "other";
            }

            return new WeatherResponse(temp, condition);
        } catch (RuntimeException e) {
            return new WeatherResponse(15D, "clear");
        }
    }

    public String getWeatherString() {
        var message = new StringBuilder();
        WeatherResponse weather = fetchWeather("tomsk");

        message.append("🌤️ Немного о погоде:\n");

        if (weather.getTemperature() > 30) {
            message.append("☀️ Сегодня жарко (")
                    .append(weather.getTemperature()).append("°C). Не забудь воду и головной убор!\n");
        } else if (weather.getTemperature() < 5) {
            message.append("❄️ Сегодня холодно (")
                    .append(weather.getTemperature()).append("°C). Одевайся теплее!\n");
        } else {
            message.append("🌡️ Температура комфортная: ").append(weather.getTemperature()).append("°C.\n");
        }

        String condition = weather.getCondition().toLowerCase();
        if (condition.contains("rain") || condition.contains("дождь")) {
            message.append("🌧️ Возьми зонтик, возможен дождь!\n");
        } else if (condition.contains("snow") || condition.contains("снег")) {
            message.append("❄️ На улице снег! Осторожнее на дорогах.\n");
        } else if (condition.contains("clear") || condition.contains("ясно")) {
            message.append("☀️ Ясная погода — отличный день для прогулки!\n");
        } else if (condition.contains("cloud") || condition.contains("облачно")) {
            message.append("☁️ Облачно, но без осадков.\n");
        }
        return message.toString();
    }
}
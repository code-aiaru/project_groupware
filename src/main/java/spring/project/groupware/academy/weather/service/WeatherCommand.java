package spring.project.groupware.academy.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.project.groupware.academy.chatbot.config.KeywordHandler;
import spring.project.groupware.academy.chatbot.config.ServiceCommand;

@Component
@KeywordHandler("날씨")
public class WeatherCommand implements ServiceCommand {

    private final WeatherChatbotService WeatherChatbotService;

    @Autowired
    public WeatherCommand(WeatherChatbotService WeatherChatbotService) {
        this.WeatherChatbotService = WeatherChatbotService;
    }

    @Override
    public String execute(String message) {
        return WeatherChatbotService.getWeatherForCity(message);
    }
}

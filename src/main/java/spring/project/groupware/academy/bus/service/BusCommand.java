package spring.project.groupware.academy.bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.project.groupware.academy.chatbot.config.KeywordHandler;
import spring.project.groupware.academy.chatbot.config.ServiceCommand;
import spring.project.groupware.academy.chatbot.service.BusChatbotService;

@Component
@KeywordHandler("버스")
public class BusCommand implements ServiceCommand {

//    private final BusChatService BusChatService;
    private final BusChatbotService BusChatBotService;

    @Autowired
    public BusCommand(BusChatbotService BusChatbotService) {
        this.BusChatBotService = BusChatbotService;
    }

    @Override
    public String execute(String message) {
        return BusChatBotService.BusSearch(message);
    }
}

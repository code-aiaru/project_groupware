package spring.project.groupware.academy.bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.project.groupware.academy.chatbot.config.KeywordHandler;
import spring.project.groupware.academy.chatbot.config.ServiceCommand;

@Component
@KeywordHandler("버스")
public class BusCommand implements ServiceCommand {

    private final BusChatService BusChatService;

    @Autowired
    public BusCommand(BusChatService BusChatService) {
        this.BusChatService = BusChatService;
    }

    @Override
    public String execute(String message) {
        return BusChatService.BusChat(message);
    }
}

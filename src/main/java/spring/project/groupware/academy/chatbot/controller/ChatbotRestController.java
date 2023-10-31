package spring.project.groupware.academy.chatbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.chatbot.service.ChatbotService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot2")
public class ChatbotRestController {


    private final ChatbotService chatbotService;

    @GetMapping("/chat")
    public String getChatResponse(@RequestParam String message) {

        log.info("챗봇 메세지 : {}", message);

        String botResponse = chatbotService.getResponse(message);

        return botResponse;
    }
}

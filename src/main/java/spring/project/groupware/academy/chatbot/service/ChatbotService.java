package spring.project.groupware.academy.chatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.config.HandlerMapping;
import spring.project.groupware.academy.chatbot.config.ServiceCommand;
import spring.project.groupware.academy.chatbot.dto.AnswerDTO;
import spring.project.groupware.academy.chatbot.dto.MessageDTO;

import java.util.HashSet;

import java.util.Set;

@Slf4j
@Service
public class ChatbotService {

    private final HandlerMapping handlerMapping;
    private final AnswerService answerService;
    private final Komoran komoran;

    @Autowired
    public ChatbotService(HandlerMapping handlerMapping, AnswerService answerService, Komoran komoran) {
        this.handlerMapping = handlerMapping;
        this.answerService = answerService;
        this.komoran = komoran;
    }

    public String getResponse(String message) {
        MessageDTO messageDTO = nlpAnalyze(message);
        return messageDTO.getAnswer().getResponse();
    }

    public MessageDTO nlpAnalyze(String message) {
        KomoranResult result = komoran.analyze(message);
        log.info("message: {}", message);

        Set<String> nouns = new HashSet<>(result.getNouns());
        log.info("KOMORAN 분석 결과: {}", result.getList());
        nouns.forEach(noun -> log.info("추출된 명사: {}", noun));

        String response = generateResponse(nouns, message);
        AnswerDTO answer = AnswerDTO.builder().response(response).build();
        return MessageDTO.builder().answer(answer).build();
    }

    private String generateResponse(Set<String> nouns, String message) {
        for (String noun : nouns) {
            ServiceCommand command = handlerMapping.getCommand(noun);
            if (command != null) {
                return command.execute(message);
            }
        }
        // 특정 커맨드를 찾지 못했을 때, 기본 응답 서비스를 사용
        log.info("기본 응답 서비스로 전환: {}", message);
        return answerService.getResponseByTriggerKeyword(message);
    }

}

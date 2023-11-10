package spring.project.groupware.academy.chatbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.config.KeywordHandler;
import spring.project.groupware.academy.chatbot.config.ServiceCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class HandlerMapping {
    private final Map<String, ServiceCommand> commandMap = new HashMap<>();

    public HandlerMapping(List<ServiceCommand> serviceCommands) {
        for (ServiceCommand serviceCommand : serviceCommands) {
            KeywordHandler annotation = AnnotatedElementUtils.findMergedAnnotation(
                    serviceCommand.getClass(), KeywordHandler.class
            );
            if (annotation != null) {
                commandMap.put(annotation.value(), serviceCommand);
            }
        }
    }

    public ServiceCommand getCommand(String keyword) {
        log.info("getCommand가 수신한 키워드 : {}", keyword);
        return commandMap.get(keyword);
    }

}

package spring.project.groupware.academy.chatbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.chatbot.dto.ScenarioDTO;
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.Selection;
import spring.project.groupware.academy.chatbot.service.ChatbotService;
import spring.project.groupware.academy.chatbot.service.ScenarioService;
import spring.project.groupware.academy.chatbot.service.SelectionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot2")
public class ChatbotRestController {


    private final ChatbotService chatbotService;
    private final ScenarioService scenarioService;
    private final SelectionService selectionService;

    @GetMapping("/chat")
    public String getChatResponse(@RequestParam String message) {

        log.info("챗봇 메세지 : {}", message);

        String botResponse = chatbotService.getResponse(message);

        return botResponse;

    }

    @GetMapping("/scenario")
    public ResponseEntity<?> getScenario(
            @RequestParam(name = "id", required = false) Optional<Integer> selectionIdOpt) {

        // selectionId가 없거나 null이면, 기본적으로 id가 1인 시나리오를 반환
        Integer selectionId = selectionIdOpt.orElse(1);

        // selectionId가 0이면, id가 1인 시나리오를 반환
        if (selectionId == 0) {
            selectionId = 1;
        }

        Scenario scenario = scenarioService.getScenarioBySelectionId(selectionId);
        List<Selection> selections = selectionService.getSelectionsByScenarioId(scenario.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("scenario", scenario);
        response.put("selections", selections);

        return ResponseEntity.ok(response);
    }

}

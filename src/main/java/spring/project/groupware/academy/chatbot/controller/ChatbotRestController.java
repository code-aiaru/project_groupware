package spring.project.groupware.academy.chatbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.groupware.academy.chatbot.dto.ScenarioDTO;
import spring.project.groupware.academy.chatbot.dto.SelectionDTO;
import spring.project.groupware.academy.chatbot.service.ChatbotService;
import spring.project.groupware.academy.chatbot.service.ScenarioService;
import spring.project.groupware.academy.chatbot.service.SelectionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 챗봇에서의 시나리오 조회
    @GetMapping("/scenario")
    public ResponseEntity<?> getScenario(@RequestParam(name = "id", required = false) Integer previousSelectionId) {

        ScenarioDTO scenarioDTO = scenarioService.getScenarioBySelectionId(previousSelectionId);
        List<SelectionDTO> selectionDTOs = selectionService.getSelectionsByScenarioId(scenarioDTO.getId());

        log.info("scenarioDTO : {}", scenarioDTO);
        log.info("selectionDTOs : {}", selectionDTOs);

        Map<String, Object> response = new HashMap<>();
        response.put("scenario", scenarioDTO);
        response.put("selections", selectionDTOs);

        return ResponseEntity.ok(response);
    }

}

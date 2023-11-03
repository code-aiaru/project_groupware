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
import spring.project.groupware.academy.chatbot.entity.Scenario;
import spring.project.groupware.academy.chatbot.entity.Selection;
import spring.project.groupware.academy.chatbot.service.ChatbotService;
import spring.project.groupware.academy.chatbot.service.ScenarioService;
import spring.project.groupware.academy.chatbot.service.SelectionService;
import spring.project.groupware.academy.weather.WeatherEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    @GetMapping("/scenario")
//    public ResponseEntity<?> getScenario(@RequestParam(name = "id", required = false) Integer previousSelectionId) {
//        ScenarioDTO scenarioDTO = scenarioService.getScenarioDTOBySelectionId(previousSelectionId);
//        List<SelectionDTO> selectionDTOs = selectionService.getSelectionDTOsByScenarioId(scenarioDTO.getId());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("scenario", scenarioDTO);
//        response.put("selections", selectionDTOs);
//
//        return ResponseEntity.ok(response);
//    }
}

package spring.project.groupware.academy.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.project.groupware.academy.chatbot.service.KomoranService;

@Controller
@RequestMapping("/chatbot")
public class ChatBotController {

	@Autowired
	private KomoranService komoranService;

	@GetMapping("/bot")
	public String chatbot(){
		return "/chatbot/bot";
	}

	@PostMapping("/botController")
	public String message(String message,Model model) throws Exception {
		
		model.addAttribute("msg", komoranService.nlpAnalyze(message));
		
		return "/chatbot/bot-message";
	}
}

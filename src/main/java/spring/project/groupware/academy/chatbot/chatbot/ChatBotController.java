package spring.project.groupware.academy.chatbot.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import spring.project.groupware.academy.chatbot.service.KomoranService;

@Controller
public class ChatBotController {

	@Autowired
	private KomoranService komoranService;
	
	@PostMapping("/botController")
	public String message(String message,Model model) throws Exception {
		
		model.addAttribute("msg", komoranService.nlpAnalyze(message));
		
		return "chatbot/bot-message";
	}
}

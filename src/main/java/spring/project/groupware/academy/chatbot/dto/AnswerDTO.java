package spring.project.groupware.academy.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDTO {
	
	private Integer id;

	private String response;

	private String triggerKeyword;

}

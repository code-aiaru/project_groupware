package spring.project.groupware.academy.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageDTO {

	private AnswerDTO answer;

	public MessageDTO answer(AnswerDTO answer) {
		this.answer = answer;
		return this;
	}

	public void setAnswer(AnswerDTO answer) {
		this.answer = answer;
	}
}

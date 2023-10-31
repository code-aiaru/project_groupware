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

	private String today;

	private String time;

	private AnswerDTO answer;

	public MessageDTO today(String today) {
		this.today = today;
		return this;
	}

	public MessageDTO time(String time) {
		this.time = time;
		return this;
	}

	public MessageDTO answer(AnswerDTO answer) {
		this.answer = answer;
		return this;
	}

	public void setAnswer(AnswerDTO answer) {
		this.answer = answer;
	}
}

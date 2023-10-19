package spring.project.groupware.academy.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.project.groupware.academy.chatbot.dto.AnswerDTO;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer")
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;

	private String content;
	
	private String keyword;    //name
	
	public Answer keyword(String keyword) {
		this.keyword=keyword;
		return this;
	}
	
	public AnswerDTO toAnswerDTO() {
		return AnswerDTO.builder()
				.no(no).content(content).keyword(keyword)
				.build();
	}

}

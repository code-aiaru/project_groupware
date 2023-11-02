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
	private long id;

	private String responseText;
	
	private String keyword;




//	public Answer keyword(String keyword) {
//		this.keyword=keyword;
//		return this;
//	}
//
//	public spring.project.groupware.academy.chatbot.dto.AnswerDTO toAnswerDTO() {
//		return AnswerDTO.builder()
//				.id(id).content(content).keyword(keyword)
//				.build();
//	}

}

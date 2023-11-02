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
	
	private long no;

	private String responseText;

	private String keyword;






//
//	private PhoneInfo phone;
//
//	private List<PhoneInfo> phoneInfoList;
//
//	public AnswerDTO phone(PhoneInfo phone){
//		this.phone=phone;
//		return this;
//	}



}

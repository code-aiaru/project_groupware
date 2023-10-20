package spring.project.groupware.academy.chatbot.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PhoneInfo {

	private String deptName;
	private String memberName;
	private String phone;
}

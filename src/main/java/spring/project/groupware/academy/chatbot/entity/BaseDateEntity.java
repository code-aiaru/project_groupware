package spring.project.groupware.academy.chatbot.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseDateEntity {
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
	
}

package spring.project.groupware.academy.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "dept")
@Entity
public class DepartmentEntity {//부서카테고리
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dno;
	private String dname;
	private int depth;//0,1,2
	
	@JoinColumn
	@ManyToOne//parent_dno
	private DepartmentEntity parent; 
	
	

}

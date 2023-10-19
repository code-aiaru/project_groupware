package spring.project.groupware.academy.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "member")
@Entity
public class MemberEntity extends BaseDateEntity{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long mno;//사원번호
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String id;//이메일x ->수정 id로 변경 (domain은 모두 같은 부분을 사용하기 때문에)
	
	@Column(nullable = false, unique = true)
	private String email;//id@greengames.shop
	
	@Column(nullable = false)
	private String pass;//비밀번호
	
	@Column(nullable = false)
	private String phone;//번호

	@Column(nullable = true)
	private LocalDate hireDate;
	
	@JoinColumn
	@ManyToOne
	private DepartmentEntity dept;
}

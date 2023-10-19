package spring.project.groupware.academy.chatbot.chatbot;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.project.groupware.academy.chatbot.repository.DepartmentEntityRepository;
import spring.project.groupware.academy.chatbot.repository.MemberEntityRepository;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

//  사용자 사전
//  구문 내에서 사용자 사전에 포함된 단어가 출현하면 사용자 사전에 정의된 품사를 우선 적용
//  주로 사람이름, 영화제목, 부서 등과 같이 고유명사를 인식하는데 활용

//  //KOMORAN에서 기본으로 제공되는 LIGHT 모델 사용
//  Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);

////사용자 사전 적용. 원하는 위치에 사용자 사전 파일을 생성한 후 경로만 지정해주면 됩니다.
//komoran.setUserDic("user_data/dic.user")


@Configuration
public class KomoranConfig {
	//.dic : 각종 사전(Dictionary) 파일
	//private String DEPT_DIC="dept.dic";

	//private String DIC_DIR="static/files/";
	private String USER_DIC="user.dic";

	@Autowired
	DepartmentEntityRepository dept;//부서이름
	
	@Autowired
	MemberEntityRepository member;//사원이름

	@Bean
	Komoran komoran() {
		userDic();
		
		Komoran komoran=new Komoran(DEFAULT_MODEL.LIGHT);
		komoran.setUserDic(USER_DIC);
		
		return komoran;
	}

	//부서테이블(부서명), 멤버테이블(이름) 
	private void userDic() {

		Set<String> keys = new HashSet<>();
		
		//기존에 수동으로 등록된 파일에서 고유명사만 추출
		try {
			File file=new File(USER_DIC);
				if(file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String data = null;
				while ((data = br.readLine()) != null) {
					if (data.startsWith("#"))//주석이 있으면 주석 제거
						continue;
					String[] str = data.split("\\t"); // 탭영역 만큼 간격 설정 이이름	NNP  영업부	NNP
					keys.add(str[0]); // set에 저장
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		////////////////////////////////////////////////////
		//부서명을 set에 저장
		dept.findAll().forEach(e -> {
			keys.add(e.getDname());
		});
		//사원명을 set에 저장
		member.findAll().forEach(e -> {
			keys.add(e.getName());
		});
		
		// Set에 저장된 명단을 고유명사로 파일에 등록
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DIC));
			keys.forEach(key -> {
				try {
					bw.write(key + "\tNNP\n");
					//  이이름	NNP
					//  영업부	NNP
					System.out.println(key);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});

			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
}

package spring.project.groupware.academy.chatbot2.service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.chatbot.dto.AnswerDTO;
import spring.project.groupware.academy.chatbot.dto.MessageDTO;
import spring.project.groupware.academy.chatbot.entity.Answer;
import spring.project.groupware.academy.chatbot.entity.ChatBotIntention;
import spring.project.groupware.academy.chatbot.repository.ChatBotIntentionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatbotService {
    private Komoran komoran;

    public ChatbotService() {
        this.komoran = new Komoran(DEFAULT_MODEL.FULL);
    }

    public String getResponse(String message) {
        // 사용자 메시지 분석
        MessageDTO messageDTO = nlpAnalyze(message);
        // 적절한 응답 생성 및 반환
        // 예시 코드
//        String analyzedText = this.komoran.analyze(message).getPlainText();
//        return "분석된 텍스트: " + analyzedText;
        return "분석된 텍스트: " + messageDTO.getAnswer().getContent();
    }

    public MessageDTO nlpAnalyze(String message) {

        KomoranResult result = komoran.analyze(message);

        //문자에서 명사들만 추출한 목록 중복제거해서 set
        Set<String> nouns = result.getNouns().stream()
                .collect(Collectors.toSet());
        nouns.forEach((noun) -> {
            System.out.println(">>>:" + noun);
        });
        ;//메세지에서 명사추출

        return analyzeToken(nouns);
    }

    //입력된 목적어를 하나씩 파악하여 DB에서 검색하기위해 decisionTree()메서드로 전달
    private MessageDTO analyzeToken(Set<String> nouns) {

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MessageDTO messageDTO = MessageDTO.builder()
                .time(today.format(timeFormatter))//시간세팅
                .build();

        for (String token : nouns) {

            //1차의도가 존재하는지 파악
            Optional<ChatBotIntention> result = decisionTree(token, null);

            if (result.isEmpty()) continue;//존재하지 않으면 다음토큰 검색

            //1차 토근확인시 실행
            System.out.println(">>>>1차:" + token);
            //1차목록 복사
            Set<String> next = nouns.stream().collect(Collectors.toSet());
            //목록에서 1차토큰 제거
            next.remove(token);

            //2차분석 메서드
            AnswerDTO answer = analyzeToken(next, result).toAnswerDTO();

//            //전화인경우 전화,전화번호 번호탐색
//            if (token.contains("전화")) {
//                PhoneInfo phone = analyzeTokenIsPhone(next);
//                answer.phone(phone);//전화인경우에만 전화 데이터
//            } else if (token.contains("안녕")) {
//                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
//                messageDTO.today(today.format(dateFormatter));//처음 접속할때만 날짜표기
//            } else if (token.contains("부서")) {
//                PhoneInfo dept = analyzeTokenIsDept(next);
//                System.out.println(dept.getDeptName()+" << 부서 이름");
//                System.out.println(dept.getMemberName()+" <<  이름");
//                answer.phone(dept);// 부서 -> 이름
//            }

            messageDTO.answer(answer);//토근에대한 응답정보

            return messageDTO;
        }
        //분석 명사들이 등록한 의도와 일치하는게 존재하지 않을경우 null
        AnswerDTO answer = decisionTree("기타", null).get().getAnswer().toAnswerDTO();
        messageDTO.answer(answer);//토근에대한 응답정보
        return messageDTO;
    }

    //1차의도가 존재하면
    //하위의도가 존재하는지 파악
    private Answer analyzeToken(Set<String> next, Optional<ChatBotIntention> upper) {

        for (String token : next) {
            // 1차의도를 부모로하는 토큰이 존재하는지 파악
            Optional<ChatBotIntention> result = decisionTree(token, upper.get());
            if (result.isEmpty()) continue;

            System.out.println(">>>>2차:" + token);
            return result.get().getAnswer();//1차-2차 존재하는경우 답변
        }
        return upper.get().getAnswer();//1차만 존재하는 답변
    }

    @Autowired
    private ChatBotIntentionRepository intention;

    //의도가 존재하는지 DB에서 파악
    // 안녕 -> 등록
    private Optional<ChatBotIntention> decisionTree(String token, ChatBotIntention upper) {
        return intention.findByNameAndUpper(token, upper);
    }

}

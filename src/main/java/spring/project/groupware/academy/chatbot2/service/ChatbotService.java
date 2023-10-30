package spring.project.groupware.academy.chatbot2.service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {
    private Komoran komoran;

    public ChatbotService() {
        this.komoran = new Komoran(DEFAULT_MODEL.FULL);
    }

    public String getResponse(String message) {
        // 사용자 메시지 분석
        // 적절한 응답 생성 및 반환
        // 예시 코드
        String analyzedText = this.komoran.analyze(message).getPlainText();
        return "분석된 텍스트: " + analyzedText;
    }
}

package spring.project.groupware.academy.post.service;

import org.springframework.stereotype.Component;

@Component
public class RandomNameGenerator {
    private static final String[] randomNames = {"게으른", "활기찬", "하극상", "재밌는", "잘사주는","답없는","이쁜"
            ,"깔끔한","뛰어난","가냘픈","뽀얀","거친","성가신","괜찮은","아름다운","아쉬운","날카로운","엄청난","점잖은"
            ,"주제넘은","지혜로운","멋진","밝은","욕심많은","노잼","잘생긴","못생긴","건방진","히스테리","든든한","문제아","폐급","국밥"
            ,"유쾌한","귀여운","똑똑한","일잘하는"};

    public String getRandomName() {
        int randomIndex = (int) (Math.random() * randomNames.length);
        return randomNames[randomIndex]+"직원";
    }


}

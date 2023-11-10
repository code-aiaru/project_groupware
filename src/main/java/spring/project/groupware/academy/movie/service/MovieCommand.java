package spring.project.groupware.academy.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.project.groupware.academy.chatbot.config.KeywordHandler;
import spring.project.groupware.academy.chatbot.config.ServiceCommand;

@Component
@KeywordHandler("영화")
public class MovieCommand implements ServiceCommand {

    private final MovieService movieService;

    @Autowired
    public MovieCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public String execute(String message) {
        return movieService.validMethod(message);
    }
}

package spring.project.groupware.academy.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.groupware.academy.chatbot.entity.ChatBotIntention;

import java.util.Optional;

public interface ChatBotIntentionRepository extends JpaRepository<ChatBotIntention, Long>{

	Optional<ChatBotIntention> findByName(String token);


	Optional<ChatBotIntention> findByNameAndUpper(String token, ChatBotIntention upper);
}

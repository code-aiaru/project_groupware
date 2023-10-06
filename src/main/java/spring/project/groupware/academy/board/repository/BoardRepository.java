package spring.project.groupware.academy.board.repository;

import org.springframework.data.repository.Repository;
import spring.project.groupware.academy.board.entity.BoardEntity;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends Repository<BoardEntity,Long> {


    BoardEntity save(BoardEntity board);

    Optional<BoardEntity> findById(Long id);

    List<BoardEntity> findAll();

    void deleteById(Long id);
}

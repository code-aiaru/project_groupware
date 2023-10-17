package spring.project.groupware.academy.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.board.entity.BoardEntity;

import java.util.List;
import java.util.Optional;
@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {


    BoardEntity save(BoardEntity board);

    Optional<BoardEntity> findById(Long id);

    Page<BoardEntity> findAll(Pageable pageable);

    void deleteById(Long id);
}

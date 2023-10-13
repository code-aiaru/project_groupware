package spring.project.groupware.academy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.groupware.academy.board.entity.NoticeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity,Long > {
    NoticeEntity save(NoticeEntity board);

    Optional<NoticeEntity> findById(Long id);

    List<NoticeEntity> findAll();

    void deleteById(Long id);
}

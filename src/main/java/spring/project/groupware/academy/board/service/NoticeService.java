package spring.project.groupware.academy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.board.dto.NoticeDto;
import spring.project.groupware.academy.board.entity.NoticeEntity;
import spring.project.groupware.academy.board.repository.NoticeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService{

    private final NoticeRepository noticeRepository;

    public NoticeDto createdNotice(NoticeDto noticeDto) {
        NoticeEntity createdNotice = new NoticeEntity(noticeDto.getTitle(), noticeDto.getContent(),noticeDto.getWriter());
        NoticeEntity notice = noticeRepository.save(createdNotice);

        return new NoticeDto(notice.getId(),notice.getTitle(),notice.getContent(),notice.getWriter());
    }

    public NoticeDto getNoticeById(Long id) {
        try {
            Optional<NoticeEntity> optionalNotice = noticeRepository.findById(id);
            if (optionalNotice.isPresent()) {
                NoticeEntity notice = optionalNotice.get();
                return new NoticeDto(notice.getId(), notice.getTitle(), notice.getContent(), notice.getWriter());
            } else {
                return null;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<NoticeDto> getAllNotices() {
        try {
            List<NoticeEntity> notices = noticeRepository.findAll();
            return notices.stream()
                    .map(notice -> new NoticeDto(notice.getId(),notice.getTitle(),notice.getContent(),notice.getWriter()))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean updateNotice(Long id, NoticeDto noticeDTO) {
        try {
            Optional<NoticeEntity> optionalNotice = noticeRepository.findById(id);
            if (optionalNotice.isPresent()) {
                NoticeEntity existingNotice = optionalNotice.get();
                existingNotice.setTitle(noticeDTO.getTitle());
                existingNotice.setContent(noticeDTO.getContent());
                NoticeEntity updatedNotice = noticeRepository.save(existingNotice);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteNotice(Long id) {
        try {
            Optional<NoticeEntity> optionalNotice = noticeRepository.findById(id);
            if (optionalNotice.isPresent()) {
                noticeRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }


}

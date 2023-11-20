package spring.project.groupware.academy.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.project.groupware.academy.post.dto.NoticeRequestDTO;
import spring.project.groupware.academy.post.dto.NoticeResponseDTO;
import spring.project.groupware.academy.post.entity.Notice;
import spring.project.groupware.academy.post.repository.NoticeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 게시글 저장
    @Transactional
    public Long saveNotice(final NoticeRequestDTO params) {
        Notice Notice = noticeRepository.save(params.toEntity());
        return Notice.getId();
    }

    // 게시글 상세정보 조회
    @Transactional(readOnly = true)
    public NoticeResponseDTO findNoticeById(final Long id) {
        Notice Notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Notice not found : " + id));
        return new NoticeResponseDTO(Notice);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<NoticeResponseDTO> findAllNotice() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(NoticeResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public boolean deleteNotice(Long id) {
        try {
            Optional<Notice> optionalNotice = noticeRepository.findById(id);
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
//    @Transactional
//    public boolean updateNotice(Long id) {
//        Optional<Notice> optionalNotice = noticeRepository.findById(id);
//        NoticeResponseDTO NoticeResponseDTO = new NoticeResponseDTO();
//
//        if (optionalNotice.isPresent()) {
//            Notice notice = optionalNotice.get();
//            notice.setTitle(NoticeResponseDTO.getTitle());
//            notice.setContent(NoticeResponseDTO.getContent());
//
//            Notice updateNotice = noticeRepository.save(notice);
//            return true;
//        } else {
//            return false;
//        }
//
//
//    }
//
//
//    public Page<Notice> getLastFiveArticlesFromNotice() {
//        return noticeRepository.findAllByOrderByIdDesc(PageRequest.of(0, 20));
//
//    }


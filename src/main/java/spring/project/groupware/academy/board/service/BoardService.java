package spring.project.groupware.academy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.entity.BoardEntity;
import spring.project.groupware.academy.board.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDTO) {
        BoardEntity board = new BoardEntity(boardDTO.getTitle(), boardDTO.getContent());
        BoardEntity createdBoard = boardRepository.save(board);
        return new BoardDto(createdBoard.getId(), createdBoard.getTitle(), createdBoard.getContent());
    }

    public BoardDto getBoardById(Long id) {
        try {
            Optional<BoardEntity> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                BoardEntity board = optionalBoard.get();
                return new BoardDto(board.getId(), board.getTitle(), board.getContent());
            } else {
                return null;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<BoardDto> getAllBoards() {
        try {
            List<BoardEntity> boards = boardRepository.findAll();
            return boards.stream()
                    .map(board -> new BoardDto(board.getId(), board.getTitle(), board.getContent()))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    public BoardDto updateBoard(Long id, BoardDto boardDTO) {
        try {
            Optional<BoardEntity> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                BoardEntity existingBoard = optionalBoard.get();
                // 업데이트할 게시판이 존재하는 경우
                // 엔티티 업데이트 로직 (예: 엔티티 필드 업데이트)

                // 엔티티를 저장하고 저장된 엔티티를 반환
                BoardEntity updatedBoard = boardRepository.save(new BoardEntity( boardDTO.getTitle(), boardDTO.getContent()));
                return new BoardDto(updatedBoard.getTitle(), updatedBoard.getContent());
            } else {
                return null; // 업데이트할 게시판이 존재하지 않는 경우
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null; // 데이터베이스 연산 실패 시
        }
    }

    public boolean deleteBoard(Long id) {
        try {
            Optional<BoardEntity> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                boardRepository.deleteById(id);
                return true; // 게시판 삭제 성공
            } else {
                return false; // 삭제할 게시판이 존재하지 않는 경우
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false; // 데이터베이스 연산 실패 시
        }
    }
}
package spring.project.groupware.academy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.entity.BoardEntity;
import spring.project.groupware.academy.board.repository.BoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private <T> T executeService(ServiceFunction<T> function) {
        try {
            return function.apply();
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private interface ServiceFunction<T> {
        T apply();
    }
    public Optional<BoardDto> createBoard(BoardDto boardDTO) {
        return executeService(() -> {
            BoardEntity board = new BoardEntity(boardDTO.getTitle(), boardDTO.getContent());
            BoardEntity createdBoard = boardRepository.save(board);
            return Optional.of(new BoardDto(createdBoard.getBoardId(), createdBoard.getTitle(), createdBoard.getContent()));
        });
    }
    public Optional<BoardDto> getBoardById(Long id) {
        return executeService(() -> boardRepository.findById(id)
                .map(board -> new BoardDto(board.getBoardId(), board.getTitle(), board.getContent())));
    }
    public List<BoardDto> getAllBoards() {
        return executeService(() -> {
            List<BoardEntity> boards = boardRepository.findAll();
            return boards.stream()
                    .map(board -> new BoardDto(board.getBoardId(), board.getTitle(), board.getContent()))
                    .collect(Collectors.toList());
        });
    }
    @Transactional(readOnly = false)
    public Optional<BoardDto> updateBoard(Long id, BoardDto boardDto) {
        return executeService(() -> boardRepository.findById(id)
                .map(existingBoard -> {
                    existingBoard.setTitle(boardDto.getTitle());
                    existingBoard.setContent(boardDto.getContent());
                    BoardEntity updatedBoard = boardRepository.save(existingBoard);
                    return new BoardDto(updatedBoard.getBoardId(), updatedBoard.getTitle(), updatedBoard.getContent());
                }));
    }
    @Transactional(readOnly = false)
    public boolean deleteBoard(Long id) {
        return executeService(() -> boardRepository.findById(id)
                .map(board -> {
                    boardRepository.deleteById(id);
                    return true;
                })
                .orElse(false));
    }
}

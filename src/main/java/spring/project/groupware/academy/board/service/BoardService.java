package spring.project.groupware.academy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.entity.BoardEntity;
import spring.project.groupware.academy.board.repository.BoardRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDto) {
        BoardEntity createdBoard = new BoardEntity(boardDto.getTitle(), boardDto.getContent(),boardDto.getWriter(),boardDto.getBoardPw());
        BoardEntity board = boardRepository.save(createdBoard);

    return new BoardDto(board.getId(),board.getTitle(),board.getContent(),board.getWriter(), board.getBoardPw());
    }

    public BoardDto getBoardById(Long id) {
        try {
            Optional<BoardEntity> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                BoardEntity board = optionalBoard.get();
                return new BoardDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter(), board.getBoardPw());
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
                    .map(board -> new BoardDto(board.getId(),board.getTitle(), board.getWriter(), board.getBoardPw(),board.getContent()))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean updateBoard(Long id, BoardDto boardDTO) {
        try {
            Optional<BoardEntity> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                BoardEntity existingBoard = optionalBoard.get();
                existingBoard.setContent(boardDTO.getContent());

                BoardEntity updatedBoard = boardRepository.save(existingBoard);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteBoard(Long id) {
        try {
            Optional<BoardEntity> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                boardRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean validatePassword(Long id,String clientPassword) {
        Optional<BoardEntity> optionalBoard = boardRepository.findById(id);

        String pw = optionalBoard.get().getBoardPw();
        if (pw.equals(clientPassword)) {
            return true;
        }
        return false;
    }
    }

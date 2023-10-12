package spring.project.groupware.academy.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.service.BoardService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public List<BoardDto> getAllBoards() {
        return boardService.getAllBoards();
    }


    @PostMapping("/create")
    public BoardDto createBoard(@RequestBody BoardDto boardDTO) {
        return boardService.createBoard(boardDTO);
    }


    @GetMapping("/{id}")
    public BoardDto detail(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDTO ,HttpSession session) {

        boolean updated = boardService.updateBoard(id, boardDTO);
        if (updated) {
            session.removeAttribute("editPageId");
            session.removeAttribute("clientPassword");
            return new ResponseEntity<>("게시물이 성공적으로 수정되었습니다.", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("게시물 수정에 실패했습니다. 비밀번호가 일치하지 않거나 게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public String deleteBoard(@PathVariable Long id,
                              @RequestParam String clientPassword) {
        boolean isPasswordValid = boardService.validatePassword(id, clientPassword);

        if (isPasswordValid) {
            boolean deleted = boardService.deleteBoard(id);
            if (deleted) {
                return "게시판 삭제 성공";
            }
        }
        return "게시판 삭제 실패";
    }


}
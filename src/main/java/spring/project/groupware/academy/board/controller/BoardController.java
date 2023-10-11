package spring.project.groupware.academy.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.service.BoardService;

import java.util.List;
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

    @PutMapping("/{id}/edit")
    public BoardDto updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDTO) {
        return boardService.updateBoard(id, boardDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }
}
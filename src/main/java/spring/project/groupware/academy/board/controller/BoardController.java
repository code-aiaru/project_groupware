package spring.project.groupware.academy.board.controller;

import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.service.BoardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;


    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @PostMapping
    public Optional<BoardDto> createBoard(@RequestBody BoardDto boardDTO) {
        return boardService.createBoard(boardDTO);
    }

    @GetMapping("/list") // 새로운 GET 맵핑을 추가
    public List<BoardDto> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public Optional<BoardDto> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @PutMapping("/{id}")
    public Optional<BoardDto> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        return boardService.updateBoard(id, boardDto);
    }
    @DeleteMapping("/{id}")
    public boolean deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }
}

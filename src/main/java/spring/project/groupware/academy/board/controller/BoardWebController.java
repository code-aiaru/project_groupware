package spring.project.groupware.academy.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.service.BoardService;

import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardWebController {

    private final BoardService boardService;

    public BoardWebController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String showBoardList(Model model) {
        List<BoardDto> boards = boardService.getAllBoards();
        model.addAttribute("boardList", boards);
        return "boards/list"; }

    @GetMapping("/create")
    public String showCreateBoardForm() {
        return "boards/create"; }

    @GetMapping("/detail/{id}")
    public String showBoardDetail(@PathVariable Long id, Model model) {
        BoardDto board = boardService.getBoardById(id);
        if (board != null) {
            model.addAttribute("board", board);
            return "boards/detail"; // "board-detail"은 게시물 상세 정보를 표시하는 템플릿 파일 이름입니다.
        } else {
            // 게시물이 없을 경우에 대한 처리
            return "redirect:/boards/list"; // 목록 페이지로 리다이렉트합니다.
        }
    }
}
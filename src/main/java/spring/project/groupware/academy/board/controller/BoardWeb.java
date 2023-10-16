package spring.project.groupware.academy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.groupware.academy.board.RandomNameGenerator;
import spring.project.groupware.academy.board.dto.BoardDto;
import spring.project.groupware.academy.board.service.BoardService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardWeb {

    private final BoardService boardService;
    private final RandomNameGenerator randomNameGenerator;

    public BoardWeb(BoardService boardService ,RandomNameGenerator randomNameGenerator) {
        this.randomNameGenerator = randomNameGenerator;
        this.boardService = boardService;
    }

    @GetMapping("/create")
    public String showCreateBoardForm(Model model) {

        String randomName = randomNameGenerator.getRandomName();

        model.addAttribute("randomName", randomName);

        return "boards/create";
    }

    @GetMapping("/list")
    public String showBoardList(Model model) {
        List<BoardDto> boards = boardService.getAllBoards();
        model.addAttribute("boardList", boards);
        return "boards/list";
    }



    @GetMapping("/detail/{id}")
    public String showBoardDetail(@PathVariable Long id, Model model) {
        BoardDto board = boardService.getBoardById(id);
        if (board != null) {
            model.addAttribute("board", board);
            model.addAttribute("id", id);
            return "boards/detail"; // "board-detail"은 게시물 상세 정보를 표시하는 템플릿 파일 이름입니다.
        } else {
            // 게시물이 없을 경우에 대한 처리
            return "redirect:/boards/list"; // 목록 페이지로 리다이렉트합니다.
        }
    }

    @PostMapping("/verifyPassword/{id}")
    public String verifyPassword(@PathVariable Long id, HttpSession session, @RequestParam String clientPassword) {
        boolean isPasswordValid = boardService.validatePassword(id, clientPassword);
        if (isPasswordValid) {

            session.setAttribute("editPageId",id);// 세션에 id 저장
            session.setAttribute("clientPassword", clientPassword); // 세션에 clientPassword 저장
            return "redirect:/boards/edit/" + id;
        } else {
            return "error"; // 비밀번호가 일치하지 않는 경우
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {
        Long editPageId = (Long) session.getAttribute("editPageId");
        String clientPassword = (String) session.getAttribute("clientPassword");

        BoardDto boardDto = boardService.getBoardById(id);
        if (boardDto != null) {
            if (editPageId.equals(boardDto.getId()))
                if (clientPassword.equals(boardDto.getBoardPw()))
                    // 세션에 저장된 id와 clientPassword를 사용하여 비밀번호를 다시 확인하고, 일치하는 경우에만 실행
                    model.addAttribute("board", boardDto);
            return "boards/edit"; // 수정 페이지의 템플릿 이름
        }
        return "redirect:/boards/list";
        }
        // 비밀번호가 유효하지 않거나 게시물이 없으면 목록 페이지로 리다이렉트
    }


//package spring.project.groupware.academy.board.controller;
//
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import spring.project.groupware.academy.board.RandomNameGenerator;
//import spring.project.groupware.academy.board.dto.BoardDto;
//import spring.project.groupware.academy.board.dto.NoticeDto;
//import spring.project.groupware.academy.board.service.BoardService;
//import spring.project.groupware.academy.post.service.NoticeService;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/boards")
//public class Board {
//
//    private final BoardService boardService;
//    private final NoticeService noticeService;
//
//
//    private final RandomNameGenerator randomNameGenerator;
//
//
//    @GetMapping("/getRandomWriter")
//    public String getRandomName() {
//        String randomName = randomNameGenerator.getRandomName();
//
//        return randomName;
//    }
//
//
//    public Board(BoardService boardService, NoticeService noticeService ,RandomNameGenerator randomNameGenerator) {
//        this.boardService = boardService;
//        this.noticeService = noticeService;
//        this.randomNameGenerator = randomNameGenerator;
//    }
//
//    @GetMapping("/{page}")
//    public ResponseEntity<Page<BoardDto>> getBoardsByPage(@PathVariable int page) {
//        int pageSize = 10; // 페이지당 보여줄 게시물 수
//        Page<BoardDto> boardPage = boardService.getBoardsByPage(page, pageSize);
//        return ResponseEntity.ok(boardPage);
//    }
//    @GetMapping("/{id}")
//    public BoardDto detail(@PathVariable Long id) {
//        return boardService.getBoardById(id);
//    }
//
//    @PostMapping
//    public BoardDto createBoard(@RequestBody BoardDto boardDTO) {
//        return boardService.createBoard(boardDTO);
//    }
//
//    @PostMapping("/verify/{id}")
//    public String verifyPassword(@PathVariable Long id, HttpSession session, @RequestParam String clientPassword) {
//        boolean isPasswordValid = boardService.validatePassword(id, clientPassword);
//        if (isPasswordValid) {
//
//            session.setAttribute("editPageId",id);// 세션에 id 저장
//            session.setAttribute("clientPassword", clientPassword); // 세션에 clientPassword 저장
//            return "success";
//        } else {
//            return "error"; // 비밀번호가 일치하지 않는 경우
//        }
//    }
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDTO ,HttpSession session) {
//
//        boolean updated = boardService.updateBoard(id, boardDTO);
//        if (updated) {
//            session.removeAttribute("editPageId");
//            session.removeAttribute("clientPassword");
//            return new ResponseEntity<>("게시물이 성공적으로 수정되었습니다.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("게시물 수정에 실패했습니다. 비밀번호가 일치하지 않거나 게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//        }
//    }
//    @DeleteMapping("{id}")
//    public String deleteBoard(@PathVariable Long id,
//                              @RequestParam String clientPassword) {
//        boolean isPasswordValid = boardService.validatePassword(id, clientPassword);
//
//        if (isPasswordValid) {
//            boolean deleted = boardService.deleteBoard(id);
//            if (deleted) {
//                return "게시판 삭제 성공";
//            }
//        }
//        return "게시판 삭제 실패";
//    }
//
//    /**공지사항 페이지 컨트롤러 **/
//
//    @GetMapping("/notice")
//    public List<NoticeDto> getAllNotice() {
//        return noticeService.getAllNotices();
//    }
//
//    @PostMapping("/notice")
//    public NoticeDto createNotice(@RequestBody NoticeDto noticeDTO) {
//        return noticeService.createdNotice(noticeDTO);
//    }
//
//    @GetMapping("/notice/{id}")
//    public NoticeDto NoticeDetail(@PathVariable Long id) {
//        return noticeService.getNoticeById(id);
//    }
//
//    @PutMapping("/notice/{id}")
//    public ResponseEntity<String> updateNotice(@PathVariable Long id, @RequestBody NoticeDto noticeDTO) {
//
//        boolean updated = noticeService.updateNotice(id, noticeDTO);
//        if (updated) {
//            return new ResponseEntity<>("게시물이 성공적으로 수정되었습니다.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("게시물 수정에 실패했습니다. 비밀번호가 일치하지 않거나 게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/notice/{id}")
//    public String deleteNotice(@PathVariable Long id) {
//        boolean deleted = noticeService.deleteNotice(id);
//        if (deleted) {
//            return "게시판 삭제 성공";
//        }
//        return "게시판 삭제 실패";
//    }
//}
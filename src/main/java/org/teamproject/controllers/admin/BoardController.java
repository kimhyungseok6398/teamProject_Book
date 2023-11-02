package org.teamproject.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.teamproject.commons.CommonException;
import org.teamproject.commons.Menu;
import org.teamproject.commons.MenuDetail;
import org.teamproject.entities.Board;
import org.teamproject.models.board.config.BoardConfigInfoService;
import org.teamproject.models.board.config.BoardConfigListService;
import org.teamproject.models.board.config.BoardConfigSaveService;

import java.util.List;

@Controller("AdminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {
    // @Controller : 1.view를 반환하기위해 사용하는 어노테이션

    // @RequestMapping : 요청방식에 관계없이 URL이 일치하는 요청을 가능

    // @RequiredArgsConstructor : private이 붙은 필드의 관련 생성자를 자동으로 생성해주는 어노테이션
    // 아니면 주로 Autowired를 넣어 생성자를 주입 해야하므로 코드가 길어질 우려

    // 의존성 추가
    private final HttpServletRequest request;
    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService boardconfigInfoService;
    private final BoardConfigListService boardConfigListService;

    /**
     * 4.게시판 목록 부분
     * @return
     */
    @GetMapping
    public String index(@ModelAttribute BoardSearch boardSearch, Model model) {
        commonProcess(model, "게시판 목록");

        Page<Board> data = boardConfigListService.gets(boardSearch);
        model.addAttribute("items", data.getContent());

        return  "admin/board/index";
    }

    /**
     * 1.게시판 등록부분
     */
    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm, Model model) {
        commonProcess(model, "게시판 등록");
    // 커멘드 객체에 기본값 완성
        return "admin/board/config";
    }
    @GetMapping("/posts")
    public String newOrder(Model model) {
        commonProcess(model, "게시판 관리");
        return "admin/board/posts";
    }

    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId, Model model) {
    // @PathVariable의 이름과 url의 괄호 안의 이름이 같은 경우 data에 해당 값을 저장한다

        commonProcess(model, "게시판 수정");


        Board board = boardconfigInfoService.get(bId, true);
        BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
        boardForm.setMode("update");
        boardForm.setListAccessRole(board.getListAccessRole().toString());
        boardForm.setViewAccessRole(board.getViewAccessRole().toString());
        boardForm.setWriteAccessRole(board.getWriteAccessRole().toString());
        boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
        boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

        model.addAttribute("boardForm", boardForm);

        return "admin/board/config";
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors, Model model) {
        String mode = boardForm.getMode();
        commonProcess(model, mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try {
            configSaveService.save(boardForm, errors);
        } catch (CommonException e) {
            errors.reject("BoardConfigError", e.getMessage());
        }

        if (errors.hasErrors()) {
            return "admin/board/config";
        }

        return "redirect:/admin/board"; // 게시판 목록으로 이동
    }

    private void commonProcess(Model model, String title) {

        String URI = request.getRequestURI();

        // 서브 메뉴처리
        String subMenuCode = Menu.getSubMenuCode(request);
        subMenuCode = title.equals("게시판 수정") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuDetail> submenus = Menu.gets("board");
        model.addAttribute("submenus", submenus);
        // 5. 서브메뉴 설명

        /**
         *   config.html에 Title과
         *  탭에 노출되는 Titlet 설정부분
         *
         */
        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);
    }
}

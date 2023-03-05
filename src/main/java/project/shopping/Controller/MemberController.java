package project.shopping.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shopping.Util;
import project.shopping.domain.Member;
import project.shopping.domain.MemberStatus;
import project.shopping.dto.MemberModifyForm;
import project.shopping.dto.MemberSaveForm;
import project.shopping.service.MemberService;
import project.shopping.web.login.Login;
import project.shopping.web.page.Page;

import java.util.List;

import static project.shopping.web.page.PageConst.MAX_PAGE_NUM;
import static project.shopping.web.page.PageConst.PAGE_LIST_NUM;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;


    //회원가입 폼
    @GetMapping("/form")
    public String memberForm(@ModelAttribute MemberSaveForm memberSaveForm) {
        return "member/memberForm";
    }


    //회원가입
    @PostMapping("/form")
    public String memberSave(@Validated @ModelAttribute MemberSaveForm memberSaveForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("memberSave bindingResult error: {}", bindingResult);
            return "member/memberForm";
        }

        Member member = Member.createMember(memberSaveForm.getUserId(), memberSaveForm.getPassword(), memberSaveForm.getName(),
                memberSaveForm.getAddress(), memberSaveForm.getPhone(), MemberStatus.COMMON);

        //중복회원 검증
        try {
            memberService.join(member);
        } catch (IllegalStateException illegalStateException) {
            bindingResult.reject("memberSaveFile");
            return "member/memberForm";
        }

        return "redirect:/";
    }


    //회원정보 조회
    @GetMapping("/info")
    public String memberInfo(@Login Member member, Model model) {
        model.addAttribute("member", memberService.findByOneUserId(member.getUserId()));
        return "member/memberInfo";
    }


    //회원정보 수정 폼
    @GetMapping("/{userId}/modify")
    public String memberModifyForm(@PathVariable String userId, @ModelAttribute MemberModifyForm memberModifyForm) {
        Member member = memberService.findByOneUserId(userId);
        memberModifyForm.memberModifyInit(member.getUserId(), member.getPassword(),
                member.getName(), member.getAddress(), member.getPhone());
        return "member/memberModify";
    }

    //회원정보 수정
    @PostMapping("/{userId}/modify")
    public String memberModifySave(@Login Member loginMember, @Validated @ModelAttribute MemberModifyForm memberModifyForm,
                                   BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("memberModifySave bindingResult error: {}", bindingResult);
            return "member/memberModify";
        }

        //비정상 접근 처리
        if (!loginMember.getUserId().equals(memberModifyForm.getUserId())) {
            //세션, 쿠키 초기화
            Util.sessionDestruct(request, response);
            return "mainPage";
        }

        //업데이트
        memberService.updateMember(memberModifyForm.getUserId(), memberModifyForm.getPassword(), memberModifyForm.getName(),
                memberModifyForm.getAddress(), memberModifyForm.getPhone());

        return "redirect:/member/info";
    }


    /**
     * 페이징
     * project.shopping.web.page.PageConst 에서
     * PAGE_LIST_NUM 출력할 객체 수
     * MAX_PAGE_NUM 출력할 페이지 개수 로
     * 수정 가능
     */
    @GetMapping("/list/{nowPage}")
    public String memberList(@PathVariable int nowPage, Model model) {

        log.info("nowPage: {} ", nowPage);

        //전체 페이지 수
        int lastPage = memberService.getLastPage(PAGE_LIST_NUM);

        //페이지 이외의 경우
        if (nowPage < 1) {
            return "redirect:/member/list/1"; //1이하 1로고정
        } else if (nowPage > lastPage) {
            return "redirect:/member/list/"+lastPage; //마지막 이상 마지막 고정
        }

        //남은 페이지 구하는 부분
        int leftPage = lastPage % MAX_PAGE_NUM; //남은페이지
        int fullPage = lastPage - leftPage; //나누어 나머지가 0인 전부 출력가능한 페이지

        //출력할 페이지 부분
        int endPrintPage = (((nowPage-1)/MAX_PAGE_NUM)+1)*MAX_PAGE_NUM; //출력할 마지막 페이지 숫자
        int startPrintPage = endPrintPage-MAX_PAGE_NUM+1; //출력할 시작 페이지

        //출력시킬 회원 id 시작 과 끝
        int maxResult = nowPage*PAGE_LIST_NUM; //게시판에 출력시킬 회원 마지막
        int firstResult = maxResult-PAGE_LIST_NUM; //게시판에 출력시킬 회원 시작

        //이전, 다음 페이지
        int previous;
        int next;


        //출력할 페이지
        Page pageObject = new Page();
        if (nowPage <= fullPage) {
            for (int i = startPrintPage; i <= endPrintPage; i++) {
                pageObject.getPages().add(i);
            }
            previous = startPrintPage-1;
            next = endPrintPage+1;
        } else {
            for (int i = fullPage+1; i <= lastPage; i++) {
                pageObject.getPages().add(i);
            }
            previous = fullPage;
            next = lastPage+1;
        }

        pageObject.pageInit(previous, next, lastPage, nowPage);
        model.addAttribute("pageObject", pageObject);

        //출력 리스트 (시작, 얼마나 불러올지)
        List<Member> members = memberService.getPageMember(firstResult, PAGE_LIST_NUM);
        model.addAttribute("members", members);

        return "member/memberList";
    }

    //회원 리스트 수정 폼
    @GetMapping("/{id}/listModify")
    public String memberListModify(@PathVariable Long id, Model model) {

        Member member = memberService.findOne(id);

        MemberModifyForm memberModifyForm = MemberModifyForm.createMemberModify(member.getUserId(), member.getPassword(),
                member.getName(), member.getAddress(), member.getPhone());

        model.addAttribute("member", memberModifyForm);

        return "member/memberListModify";
    }

    //회원 리스트 수정
    @PostMapping("/{id}/listModify")
    public String memberListModifySave(@Login Member loginMember, @Validated @ModelAttribute MemberModifyForm memberModifyForm,
                                       BindingResult bindingResult, @RequestParam int nowPage) {

        if (bindingResult.hasErrors()) {
            log.info("memberModifySave bindingResult error: {}", bindingResult);
            return "member/memberModify";
        }

        //업데이트
        memberService.updateMember(memberModifyForm.getUserId(), memberModifyForm.getPassword(), memberModifyForm.getName(),
                memberModifyForm.getAddress(), memberModifyForm.getPhone());

        return "redirect:/member/list/"+nowPage;
    }

    //회원 삭제
    @GetMapping("/{id}/delete")
    public String memberDelete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/list";
    }

}

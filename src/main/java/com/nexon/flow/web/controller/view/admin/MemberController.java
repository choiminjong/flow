package com.nexon.flow.web.controller.view.admin;

import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.service.admin.MemberService;
import com.nexon.flow.service.admin.RolerService;
import com.nexon.flow.web.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MemberController {

    final private MemberService memberService;
    final private RolerService rolerService;

    @GetMapping(value = "/member")
    public String members(Model model,
                          @PageableDefault(size=10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                          @RequestParam(required = false, defaultValue = "") String searchText ) {

        Page<Member> members = memberService.getMemberPage(searchText, pageable);
        Page<MemberResponse.MembertoDtoList> memberDtoList = new MemberResponse().toDtoList(members);

        int startPage = Math.max(1,memberDtoList.getPageable().getPageNumber() - 8);
        int endPage = Math.min(memberDtoList.getTotalPages(), memberDtoList.getPageable().getPageNumber() + 8);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("members", memberDtoList);

        return "member/member";
    }

    @GetMapping(value = "/member/detail")
    public String viewMember(@RequestParam(value = "id") Long id, Model model) {

        MemberResponse memberResponse = memberService.getById(id);
        List<Role> roleList =  rolerService.getRoles();
        model.addAttribute("member", memberResponse);
        model.addAttribute("roleList", roleList);

        return "member/memberdetail";
    }
}

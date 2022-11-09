package com.nexon.flow.web.controller.view.admin;

import com.nexon.flow.domain.dto.admin.RoleDto;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.service.admin.RolerService;
import com.nexon.flow.web.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RoleController {

    final private RolerService rolerService;

    @GetMapping(value = "/role")
    public String roles(Model model,
                        @PageableDefault(size = 10) Pageable pageable,
                        @RequestParam(required = false, defaultValue = "") String searchText ) {

        Page<Role> roles = rolerService.getRolePage(searchText, pageable);

        int startPage = Math.max(1,roles.getPageable().getPageNumber() - 8);
        int endPage = Math.min(roles.getTotalPages(), roles.getPageable().getPageNumber() + 8);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("roles", roles);

        return "role/role";
    }

//    @GetMapping(value = "/role/detail")
//    public String viewRole(@RequestParam(value = "id") Long id, Model model) {
//
//        RoleDto roleDto = rolerService.getById(id);
//        ModelMapper modelMapper = new ModelMapper();
//        RoleResponse roleResponse = modelMapper.map(roleDto, RoleResponse.class);
//
//        model.addAttribute("role", roleResponse);
//        model.addAttribute("roleMemberSize", roleResponse.getMemberList().size());
//
//        return "role/roledetail";
//    }

    @GetMapping(value = "/role/detail")
    public String viewRole(@RequestParam(value = "id") Long id, Model model) {

        Role role = rolerService.getById(id);
        model.addAttribute("role", role);

        return "role/roledetail";
    }

}

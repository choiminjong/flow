package com.nexon.flow.web.controller.view.admin;

import com.nexon.flow.domain.dto.admin.ResourcesDto;
import com.nexon.flow.domain.entity.Resources;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.service.admin.ResourcesService;
import com.nexon.flow.service.admin.RolerService;
import com.nexon.flow.web.response.ResourcesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ResourcesController {

    final private ResourcesService resourcesService;
    final private RolerService rolerService;

    @GetMapping(value = "/resources")
    public String resources(Model model,
                            @PageableDefault(size=20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText ) {

        Page<Resources> resources = resourcesService.getResourcesPage(searchText, pageable);
        Page<ResourcesResponse.ResourcestoDtoList> resourcesDtoList = new ResourcesResponse().toDtoList(resources);

        int startPage = Math.max(1,resourcesDtoList.getPageable().getPageNumber() - 8);
        int endPage = Math.min(resourcesDtoList.getTotalPages(), resourcesDtoList.getPageable().getPageNumber() + 8);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("resources", resourcesDtoList);

        return "resources/resources";
    }

    @GetMapping(value = "/resources/detail")
    public String viewResources(@RequestParam(value = "id") Long id, Model model) {

        ResourcesDto resourcesDto= resourcesService.getById(id);
        //List<Role> roleList =  rolerService.getRoles();

        model.addAttribute("resources", resourcesDto);
        //model.addAttribute("roleList", roleList);
        //model.addAttribute("resourcesRoleSize", resourcesDto.getRoles().size());

        return "resources/resourcesdetail";
    }

}

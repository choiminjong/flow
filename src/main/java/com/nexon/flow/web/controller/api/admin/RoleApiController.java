package com.nexon.flow.web.controller.api.admin;

import com.nexon.flow.core.except.ResponseHandler;
import com.nexon.flow.domain.dto.admin.RoleDto;
import com.nexon.flow.domain.dto.admin.RoleMemberInterface;
import com.nexon.flow.domain.entity.Role;
import com.nexon.flow.service.admin.RolerService;
import com.nexon.flow.web.request.RoleRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(tags = "권한 관리")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class RoleApiController {

    final private RolerService rolerService;

    /*
     * 권한 조회
     * @param Pageable
     * @param searchText : username 검색
     */
    @GetMapping(value = "/roles")
    @ApiOperation(value = "권한 조회" , notes = " 전체목록 조회 또는 rolename 컬럼 like 조회 가능합니다. ")
    public ResponseEntity<Object> role(@ApiParam(value = "searchText" , type= "string" , example = "rolename")
                                       @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                       @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Role> roles = rolerService.getRolePage(searchText, pageable);
        return ResponseHandler.generateResponse("권한조회.", HttpStatus.OK, roles);
    }


    /*
     * 권한 개별 조회
     * @param id
     * @param Pageable
     */
    @GetMapping(value = "/role/{roleName}/member")
    @ApiOperation(value = "권한 그룹개별 조회" , notes = " 권한 그룹개별 조회. ")
    public ResponseEntity<Object> findByRoleByMember( @ApiParam(value = "roleName" , type= "string" , example = "1")
                                                      @PageableDefault(size = 20) Pageable pageable,
                                                      @PathVariable String roleName){

        Page<RoleMemberInterface> byRoleByMember = rolerService.findByRoleByMember(roleName, pageable);
        return ResponseHandler.generateResponse("단건 권한조회.", HttpStatus.OK, byRoleByMember);
    }


    /*
     * 권한 개별 조회
     * @param id
     * @param Pageable
     */
    @GetMapping(value = "/role/{id}")
    @ApiOperation(value = "권한 개별 조회" , notes = " 권한 No 조회. ")
    public ResponseEntity<Object> roleDetail( @ApiParam(value = "id" , type= "string" , example = "1")
                                              @PathVariable Long id){

        Role role = rolerService.getById(id);
        return ResponseHandler.generateResponse("단건 권한조회.", HttpStatus.OK, role);
    }

    /*
     * 권한 생성
     * @param roleName
     * @param roleDesc
     */
    @PostMapping("/role")
    @ApiOperation(value = "권한 생성" , notes = " RoleRequest.Create Dto 데이터. ")
    public ResponseEntity<Object> createRole( @ApiParam(value = "권한 데이터", required = true)
                                              @RequestBody RoleRequest.Create create) {
        ModelMapper modelMapper = new ModelMapper();
        Role roleData = modelMapper.map(create, Role.class);
        rolerService.save(roleData);

        return ResponseHandler.generateResponse("권한을 생성했습니다.", HttpStatus.OK, null);
    }

    /*
     * 권한 수정
     * @param id
     * @param roleName
     * @param roleDesc
     */
    @PutMapping("/role/{id}")
    @ApiOperation(value = "권한 수정" , notes = " RoleRequest.ModifiyRole Dto 데이터. ")
    public ResponseEntity<Object> modifyRole( @ApiParam(value = "id" , type= "string" ,example = "1")
                                              @PathVariable Long id,
                                              @ApiParam(value = "권한 수정", required = true)
                                              @RequestBody RoleRequest.ModifiyRole modifiy) {

        ModelMapper modelMapper = new ModelMapper();
        Role roleData = modelMapper.map(modifiy, Role.class);
        rolerService.modifyRole(id,roleData);

        return ResponseHandler.generateResponse("권한을 수정했습니다.", HttpStatus.OK, null);
    }

    /*
     * 권한 삭제
     * @param id
     */
    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "권한 삭제" , notes = " role id ")
    public ResponseEntity<Object> deleteRole( @ApiParam(value = "id" , type= "string" ,example = "1")
                                              @PathVariable Long id) {
        rolerService.deleteById(id);
        return ResponseHandler.generateResponse("권한을 삭제했습니다.", HttpStatus.OK, null);
    }

}

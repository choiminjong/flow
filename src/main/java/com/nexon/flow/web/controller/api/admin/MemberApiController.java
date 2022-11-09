package com.nexon.flow.web.controller.api.admin;

import com.nexon.flow.core.except.ResponseHandler;
import com.nexon.flow.domain.dto.admin.MemberDto;
import com.nexon.flow.domain.entity.Member;
import com.nexon.flow.service.admin.MemberService;
import com.nexon.flow.web.request.MemberRequest;
import com.nexon.flow.web.response.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;

@Api(tags = "사용자 관리")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MemberApiController {

    final private MemberService memberService;

    /*
     * 사용자 개별 조회
     * @param id
     * @param Pageable
     */
    @GetMapping(value = "/member/{id}")
    @ApiOperation(value = "사용자 개별 조회" , notes = " 사용자 No 조회. ")
    public ResponseEntity<Object> memberDetail( @ApiParam(value = "id" , type= "string",example = "1")
                                                @PathVariable Long id) {
        MemberResponse memberResponse = memberService.getById(id);
        return ResponseHandler.generateResponse("단건 회원조회.", HttpStatus.OK, memberResponse);
    }

    /*
     * 사용자 조회
     * @param Pageable
     * @param searchText : username 검색
     */
    @GetMapping(value = "/members")
    @ApiOperation(value = "사용자 조회" , notes = " 전체목록 조회 또는 username 컬럼 like 조회 가능합니다. ")
    public ResponseEntity<Object> member(@ApiParam(value = "searchText" , type= "string" , example = "admin")
                                         @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                         @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Member> members = memberService.getMemberPage(searchText, pageable);
        Page<MemberResponse.MembertoDtoList> membertoDtoLists = new MemberResponse().toDtoList(members);

        return ResponseHandler.generateResponse("회원조회.", HttpStatus.OK, membertoDtoLists);
    }


    /*
     * 회원가입
     * @param email
     * @param username
     * @param displayname
     * @param password
     * @param directory
     */
    @PostMapping("/member")
    @ApiOperation(value = "회원가입" , notes = " MemberRequest.Join Dto 데이터 ")
    public ResponseEntity<Object> join( @ApiParam(value = "회원가입 데이터", required = true)
                                        @RequestBody MemberRequest.Join join) {

        ModelMapper modelMapper = new ModelMapper();
        Member joinData = modelMapper.map(join, Member.class);
        memberService.save(joinData);

        return ResponseHandler.generateResponse("회원 가입을 성공했습니다.", HttpStatus.OK, null);
    }

    /*
     * 회원정보 수정
     * @param id
     * @param email
     * @param username
     * @param password
     * @param displayname
     * @param directory
     * @param displayname
     * @param Status status
     * @param List<String> roles
     */
    @PutMapping("/member")
    @ApiOperation(value = "회원정보 수정" , notes = " MemberRequest.Modifiy Dto 데이터 ")
    public ResponseEntity<Object> modifyMember( @ApiParam(value = "회원정보 수정", required = true)
                                                @RequestBody MemberRequest.ModifiyMember modifiy) {

        ModelMapper modelMapper = new ModelMapper();
        MemberDto modifiyData = modelMapper.map(modifiy, MemberDto.class);
        memberService.modifyMember(modifiyData);

        return ResponseHandler.generateResponse("회원 정보를 수정했습니다.", HttpStatus.OK, null);
    }

    /*
     * 회원 패스워드 수정
     * @param id
     * @param password
     */
    @PutMapping("/member/{id}/password")
    @ApiOperation(value = "회원 패스워드 수정" , notes = " MemberRequest.ModifiyPassword Dto 데이터 ")
    public ResponseEntity<Object> modifyPsswordMember(@ApiParam(value = "id" , type= "string" ,example = "1")
                                                      @PathVariable Long id,
                                                      @ApiParam(value = "회원 패스워드 수정", required = true)
                                                      @RequestBody MemberRequest.ModifiyPassword modifiy) {

        String chagePassword = modifiy.getPassword();
        memberService.ModifyMemberPassword(id, chagePassword);

        return ResponseHandler.generateResponse("패스워드를 수정했습니다.", HttpStatus.OK, null);
    }

}

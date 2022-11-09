package com.nexon.flow.web.controller.api.admin;

import com.nexon.flow.domain.dto.admin.ResourceByRoleInterface;
import com.nexon.flow.security.metadatasource.UrlSecurityMetadataSource;
import com.nexon.flow.core.except.ResponseHandler;
import com.nexon.flow.domain.dto.admin.ResourcesDto;
import com.nexon.flow.domain.entity.Resources;
import com.nexon.flow.service.admin.ResourcesService;
import com.nexon.flow.web.request.ResourcesRequest;
import com.nexon.flow.web.response.ResourcesResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "리소스 관리")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ResourcesApiController {

    final private ResourcesService resourcesService;
    final private UrlSecurityMetadataSource urlSecurityMetadataSource;

    /*
     * 리소스 조회
     * @param Pageable
     * @param searchText : username 검색
     */
    @GetMapping(value = "/resources")
    @ApiOperation(value = "리소스 조회" , notes = " 전체목록 조회 또는 resourceName 컬럼 like 조회 가능합니다. ")
    public ResponseEntity<Object> resources(@ApiParam(value = "searchText" , type= "string" ,example = "resourceName")
                                            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                            @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Resources> resources = resourcesService.getResourcesPage(searchText, pageable);
        Page<ResourcesResponse.ResourcestoDtoList> resourcesDtoList = new ResourcesResponse().toDtoList(resources);

        return ResponseHandler.generateResponse("리소스 조회.", HttpStatus.OK, resourcesDtoList);
    }


    /*
     * 리소스 개별 조회
     * @param resourceId
     * @param Pageable
     */
    @GetMapping(value = "/resource/{id}/role")
    @ApiOperation(value = "리소스 개별 조회" , notes = " 리소스 No 조회. ")
    public ResponseEntity<Object> findResourceByRole(@ApiParam(value = "resourceId" , type= "string" , example = "1")
                                                     @PageableDefault(size = 20) Pageable pageable,
                                                     @PathVariable Long id) {

        Page<ResourceByRoleInterface> resourceByRole = resourcesService.findResourceByRole(id, pageable);
        return ResponseHandler.generateResponse("단건 리소스 조회.", HttpStatus.OK, resourceByRole);
    }


    /*
     * 리소스 개별 조회
     * @param id
     * @param Pageable
     */
    @GetMapping(value = "/resource/{id}")
    @ApiOperation(value = "리소스 개별 조회" , notes = " 리소스 No 조회. ")
    public ResponseEntity<Object> resourcesDetail(@PathVariable Long id) {

        ResourcesDto resourcesDto= resourcesService.getById(id);
        ModelMapper modelMapper = new ModelMapper();
        ResourcesResponse resourcesResponse = modelMapper.map(resourcesDto, ResourcesResponse.class);

        return ResponseHandler.generateResponse("단건 리소스 조회.", HttpStatus.OK, resourcesResponse);
    }

    /*
     * 리스소 생성
     * @param resourceName
     * @param httpMethod
     * @param resourceType
     */
    @PostMapping("/resource")
    @ApiOperation(value = "리소스 생성" , notes = " ResourcesRequest 데이터. ")
    public ResponseEntity<Object> createResources( @ApiParam(value = "리스소 데이터", required = true)
                                                   @RequestBody ResourcesRequest resourcesRequest) {

        ModelMapper modelMapper = new ModelMapper();
        Resources resourcesData = modelMapper.map(resourcesRequest, Resources.class);
        resourcesService.save(resourcesData);

        try {
            urlSecurityMetadataSource.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseHandler.generateResponse("리소스 등록을 성공했습니다.", HttpStatus.OK, null);
    }

    /*
     * 리소스 수정
     * @param id
     * @param resourceName
     * @param resourceType
     * @param List<String> roles
     */
    @PutMapping("/resource/{id}")
    @ApiOperation(value = "리소스 수정" , notes = " ResourcesRequest.ModifiyResources Dto 데이터 ")
    public ResponseEntity<Object> modifyResources(  @ApiParam(value = "id" ,type= "string" , example = "1")
                                                    @PathVariable Long id,
                                                    @ApiParam(value = "리소스 수정", required = true)
                                                    @RequestBody ResourcesRequest.ModifiyResources modifiy) {

        ModelMapper modelMapper = new ModelMapper();
        ResourcesDto modifiyData = modelMapper.map(modifiy, ResourcesDto.class);
        resourcesService.modifyResources(id,modifiyData);

        try {
            urlSecurityMetadataSource.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseHandler.generateResponse("리소스 정보를 수정했습니다.", HttpStatus.OK, null);
    }

    /*
     * 리소스 삭제
     * @param id
     */
    @DeleteMapping("/resource/{id}")
    @ApiOperation(value = "리소스 삭제" , notes = " Resources id ")
    public ResponseEntity<Object> deleteResource( @ApiParam(value = "id" , type= "string" , example = "1")
                                                  @PathVariable Long id) {
        resourcesService.deleteById(id);
        return ResponseHandler.generateResponse("리소스를 삭제했습니다.", HttpStatus.OK, null);
    }

}

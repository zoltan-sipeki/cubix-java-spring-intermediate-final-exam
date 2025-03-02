package hu.cubix.zoltan_sipeki.catalog_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.zoltan_sipeki.catalog_service.api.CategoryRestControllerApi;
import hu.cubix.zoltan_sipeki.catalog_service.dto.CategoryRequestDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.CategoryResponseDto;
import hu.cubix.zoltan_sipeki.catalog_service.mapper.CategoryMapper;
import hu.cubix.zoltan_sipeki.catalog_service.service.CategoryService;
import hu.cubix.zoltan_sipeki.catalog_service.validator.Validators;
import hu.cubix.zoltan_sipeki.common_lib.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CategoryController implements CategoryRestControllerApi {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<CategoryResponseDto> createCategory(CategoryRequestDto categoryRequestDto) {
        var errors = Validators.validate(categoryRequestDto);
        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
        
        var category = categoryMapper.mapToModel(categoryRequestDto);
        category = categoryService.createCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.mapToDto(category));
    }

}

package hu.cubix.zoltan_sipeki.catalog_service.mapper;

import org.mapstruct.Mapper;

import hu.cubix.zoltan_sipeki.catalog_service.dto.CategoryRequestDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.CategoryResponseDto;
import hu.cubix.zoltan_sipeki.catalog_service.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    public CategoryResponseDto mapToDto(Category category);

    public Category mapToModel(CategoryRequestDto category);
}

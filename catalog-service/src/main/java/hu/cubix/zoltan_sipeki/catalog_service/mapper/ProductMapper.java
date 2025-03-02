package hu.cubix.zoltan_sipeki.catalog_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductRequestDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductResponseDto;
import hu.cubix.zoltan_sipeki.catalog_service.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "category.name")
    public ProductResponseDto mapToDto(Product product);

    @Mapping(target = "category.name", source = "category")
    public Product mapToModel(ProductRequestDto product);

    public List<ProductResponseDto> mapToDtoList(List<Product> products);
}

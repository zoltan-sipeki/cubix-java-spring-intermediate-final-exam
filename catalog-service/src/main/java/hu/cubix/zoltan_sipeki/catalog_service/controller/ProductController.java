package hu.cubix.zoltan_sipeki.catalog_service.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.zoltan_sipeki.catalog_service.api.ProductRestControllerApi;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductFilterDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductPriceHistoryDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductRequestDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductResponseDto;
import hu.cubix.zoltan_sipeki.catalog_service.mapper.ProductMapper;
import hu.cubix.zoltan_sipeki.catalog_service.service.ProductService;
import hu.cubix.zoltan_sipeki.catalog_service.validator.Validators;
import hu.cubix.zoltan_sipeki.common_lib.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController implements ProductRestControllerApi {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<ProductResponseDto> createProduct(ProductRequestDto productRequestDto) {
        var errors = Validators.validate(productRequestDto);
        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }

        var product = productMapper.mapToModel(productRequestDto);
        product = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToDto(product));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequestDto) {
        var errors = Validators.validate(productRequestDto);
        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }

        var product = productMapper.mapToModel(productRequestDto);
        product = productService.updateProduct(id, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToDto(product));
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> filterProducts(ProductFilterDto productFilterDto,
            @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productMapper.mapToDtoList(productService.filterProducts(productFilterDto, pageable)));
    }

    @Override
    public ResponseEntity<List<ProductPriceHistoryDto>> getPriceHistory(Long id) {
        return ResponseEntity.ok(productService.getPriceHistory(id));
    }

}

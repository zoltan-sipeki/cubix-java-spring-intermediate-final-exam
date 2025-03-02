package hu.cubix.zoltan_sipeki.catalog_service.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import hu.cubix.zoltan_sipeki.catalog_service.dto.CategoryRequestDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductFilterDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductRequestDto;

public class Validators {

    public static Map<String, String> validate(CategoryRequestDto dto) {
        var errors = new HashMap<String, String>();
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.put("name", "must not be empty");
        }

        return errors;
    }

    public static Map<String, String> validate(ProductRequestDto dto) {
        var errors = new HashMap<String, String>();
        if (!StringUtils.hasText(dto.getName())) {
            errors.put("name", "must not be empty");
        }

        if (!StringUtils.hasText(dto.getCategory())) {
            errors.put("category", "must not be empty");
        }

        var price = dto.getPrice();
        if (price == null) {
            errors.put("price", "must not be null");
        }

        if (price < 0) {
            errors.put("price", "must not be negative");
        }

        return errors;
    }

    public static Map<String, String> validate(ProductFilterDto dto) {
        var errors = new HashMap<String, String>();
        var price =  dto.getPrice();
        if (price == null) {
            return errors;
        }

        if (price.size() > 2) {
            errors.put("price", "must have less than 3 elements");
        }
        
        for (int i = 0; i < price.size(); i++) {
            if (price.get(i) < 0) {
                errors.put("price " + (i + 1), "must not be negative");
            }
        }

        return errors;
    }
}

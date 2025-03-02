package hu.cubix.zoltan_sipeki.catalog_service.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.catalog_service.model.Category;
import hu.cubix.zoltan_sipeki.catalog_service.repository.CategoryRepository;
import hu.cubix.zoltan_sipeki.common_lib.exception.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(Category category) {
        try {
            return categoryRepository.saveAndFlush(category);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistsException("CATEGORY", "name", category.getName());
        }
    }
}

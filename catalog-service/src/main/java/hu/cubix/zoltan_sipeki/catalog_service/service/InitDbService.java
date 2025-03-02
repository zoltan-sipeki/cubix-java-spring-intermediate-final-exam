package hu.cubix.zoltan_sipeki.catalog_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.catalog_service.model.Category;
import hu.cubix.zoltan_sipeki.catalog_service.model.Product;
import hu.cubix.zoltan_sipeki.catalog_service.repository.CategoryRepository;
import hu.cubix.zoltan_sipeki.catalog_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public void init() {
        var category1 = new Category(0, "Books");
        var category2 = new Category(0, "Movies");
        var category3 = new Category(0, "Music");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        productRepository.save(new Product(0, "The Lord of the Rings", 19.99, category1));
        productRepository.save(new Product(0, "The Hobbit", 15.99, category1));
        productRepository.save(new Product(0, "The Fellowship of the Ring", 12.99, category2));
        productRepository.save(new Product(0, "The Two Towers", 12.99, category2));
        productRepository.save(new Product(0, "The Return of the King", 12.99, category2));
        productRepository.save(new Product(0, "The Fellowship of the Ring Soundtrack", 15.99, category3));
        productRepository.save(new Product(0, "The Two Towers Soundtrack", 15.99, category3));
        productRepository.save(new Product(0, "The Return of the King Soundtrack", 15.99, category3));

    }
}

package hu.cubix.zoltan_sipeki.catalog_service.service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductFilterDto;
import hu.cubix.zoltan_sipeki.catalog_service.dto.ProductPriceHistoryDto;
import hu.cubix.zoltan_sipeki.catalog_service.model.Product;
import hu.cubix.zoltan_sipeki.catalog_service.model.QProduct;
import hu.cubix.zoltan_sipeki.catalog_service.repository.CategoryRepository;
import hu.cubix.zoltan_sipeki.catalog_service.repository.ProductRepository;
import hu.cubix.zoltan_sipeki.common_lib.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public Product createProduct(Product product) {
        var category = categoryRepository.findByName(product.getCategory().getName())
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY", "name", product.getCategory().getName()));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(long id, Product product) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT", "id", Long.toString(id)));

        var category = categoryRepository.findByName(product.getCategory().getName())
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY", "name", product.getCategory().getName()));

        product.setId(id);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public List<Product> filterProducts(ProductFilterDto dto, Pageable pageable) {
        var product = QProduct.product;
        var predicates = new ArrayList<Predicate>();

        if (dto.getName() != null) {
            predicates.add(product.name.containsIgnoreCase(dto.getName()));
        }

        if (dto.getCategory() != null) {
            predicates.add(product.category.name.startsWithIgnoreCase(dto.getCategory()));
        }

        var price = dto.getPrice();
        if (price != null && !price.isEmpty()) {
            if (price.size() == 2) {
                // swap the prices around if the first is bigger than the second
                var first = price.get(0);
                var second = price.get(1);
                var diff = first - second;
                if (diff > 0) {
                    first -= diff;
                    second += diff;
                }

                predicates.add(product.price.between(first, second));
            } else if (price.size() == 1) {
                predicates.add(product.price.eq(price.get(0)));
            }
        }

        return predicates.isEmpty() ? new ArrayList<Product>()
                : productRepository.findAll(ExpressionUtils.allOf(predicates), pageable).getContent();
    }

    @SuppressWarnings("unchecked")
    @Cacheable("productPriceHistoryCache")
    @Transactional
    public List<ProductPriceHistoryDto> getPriceHistory(long id) {
        var revisions = AuditReaderFactory.get(em).createQuery().forRevisionsOfEntity(Product.class, false, false)
                .add(AuditEntity.property("id").eq(id))
                .add(AuditEntity.property("price").hasChanged())
                .addOrder(AuditEntity.revisionNumber().asc())
                .getResultList();

        return revisions.stream().map(r -> {
            var arr = (Object[]) r;
            var product = (Product) arr[0];
            var revEntity = (DefaultRevisionEntity) arr[1];

            var history = new ProductPriceHistoryDto();
            history.setEffectiveFrom(
                    OffsetDateTime.ofInstant(Instant.ofEpochMilli(revEntity.getTimestamp()), ZoneId.systemDefault()));
            history.setPrice(product.getPrice());
            return history;
        }).toList();
    }
}

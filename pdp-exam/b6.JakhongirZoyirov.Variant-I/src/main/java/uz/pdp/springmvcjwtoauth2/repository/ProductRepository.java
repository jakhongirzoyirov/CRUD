package uz.pdp.springmvcjwtoauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.springmvcjwtoauth2.entity.ProductEntity;

@RepositoryRestResource(path = "product", collectionResourceRel = "products")
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}

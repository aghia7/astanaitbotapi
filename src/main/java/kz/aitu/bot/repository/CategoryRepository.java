package kz.aitu.bot.repository;

import kz.aitu.bot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentId(Long id);

    List<Category> findByParentCategoryNameRusContaining(String categoryName);

    List<Category> findByParentCategoryNameKazContaining(String categoryName);

    List<Category> findByParentCategoryNameEngContaining(String categoryName);

    void deleteById(Long id);
}

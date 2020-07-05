package kz.aitu.bot.service;

import kz.aitu.bot.dtos.CategoryInsertUpdateDTO;
import kz.aitu.bot.model.Category;
import kz.aitu.bot.repository.CategoryRepository;
import kz.aitu.bot.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoryByParentId(Long id) {
        return categoryRepository.findByParentId(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Category> getCategoryByNameRus(String categoryName) {
        return categoryRepository.findByParentCategoryNameRusContaining(categoryName);
    }

    @Override
    public List<Category> getCategoryByNameKaz(String categoryName) {
        return categoryRepository.findByParentCategoryNameKazContaining(categoryName);
    }

    @Override
    public List<Category> getCategoryByNameEng(String categoryName) {
        return categoryRepository.findByParentCategoryNameEngContaining(categoryName);
    }

    @Override
    public void addCategory(CategoryInsertUpdateDTO categoryInsertDTO) {
        Category category = new Category(categoryInsertDTO);
        categoryRepository.save(category);
    }

    @Override
    public void removeCategoryById(Long parentId) {
        categoryRepository.deleteById(parentId);
    }

    @Override
    public void updateCategory(CategoryInsertUpdateDTO categoryUpdateDTO) throws Exception {
        if (!categoryRepository.existsById(categoryUpdateDTO.getId()))
            throw new Exception("Category does not exist!");
        Category category = new Category(categoryUpdateDTO);
        categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}

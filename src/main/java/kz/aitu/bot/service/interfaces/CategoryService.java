package kz.aitu.bot.service.interfaces;

import kz.aitu.bot.dtos.CategoryInsertUpdateDTO;
import kz.aitu.bot.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryByParentId(Long id);

    List<Category> getAllCategories();

    List<Category> getCategoryByNameRus(String categoryName);

    List<Category> getCategoryByNameKaz(String categoryName);

    List<Category> getCategoryByNameEng(String categoryName);

    void addCategory(CategoryInsertUpdateDTO categoryInsertDTO);

    void removeCategoryById(Long parentId);

    void updateCategory(CategoryInsertUpdateDTO categoryUpdateDTO) throws Exception;

    Category getCategoryById(Long id);
}

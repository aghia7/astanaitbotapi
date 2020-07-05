package kz.aitu.bot.rest;

import kz.aitu.bot.dtos.CategoryDTO;
import kz.aitu.bot.dtos.CategoryInsertUpdateDTO;
import kz.aitu.bot.dtos.LogDTO;
import kz.aitu.bot.model.Category;
import kz.aitu.bot.model.Language;
import kz.aitu.bot.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryById(@PathVariable("id")Long id) {
        Category category = categoryService.getCategoryById(id);

        if(category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(category, HttpStatus.OK);

    }

    @RequestMapping(value = "parent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getCategoryByParentId(@PathVariable("id")Long parentId) {
        List<Category> categories = categoryService.getCategoryByParentId(parentId);

        if(categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @RequestMapping(value = "{lang}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> getCategoryByParentIdLang(@PathVariable("lang")String lang, @PathVariable("id")Long parentId) {
        if(parentId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Category> categories = categoryService.getCategoryByParentId(parentId);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        if(categories.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try {
            lang = lang.toLowerCase();
            lang = lang.replaceAll("\\s", "");
            for (Category category : categories) {
                categoryDTOS.add(new CategoryDTO(category, Language.convert(lang)));
            }

            return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(categoryDTOS, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{lang}/cat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> getCategoriesByParentCategoryNameRusLang(@PathVariable("lang")String lang, @RequestParam("category")String categoryName) {
        lang = lang.toLowerCase();
        lang = lang.replaceAll("\\s", "");

        return getCategoryByName(categoryName, Language.convert(lang));
    }


    private ResponseEntity<List<CategoryDTO>> getCategoryByName(String categoryName, Language lang) {
        if(categoryName.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Category> categories;

        switch (lang) {
            case KAZ: categories = categoryService.getCategoryByNameKaz(categoryName); break;
            case ENG: categories = categoryService.getCategoryByNameEng(categoryName); break;
            default: categories = categoryService.getCategoryByNameRus(categoryName);
        }

        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        if(categories.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try {
            for (Category category : categories) {
                categoryDTOS.add(new CategoryDTO(category, lang));
            }

            return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = this.categoryService.getAllCategories();

        if(categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @RequestMapping(value = "admin/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategory(@RequestBody CategoryInsertUpdateDTO categoryInsertDTO) {
        try {
            categoryService.addCategory(categoryInsertDTO);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parent category does not exist");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(new LogDTO("A new category was created successfully!"));
    }

    @RequestMapping(value = "admin/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(@RequestBody CategoryInsertUpdateDTO categoryUpdateDTO) {
        try {
            categoryService.updateCategory(categoryUpdateDTO);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parent category does not exist");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(new LogDTO("Selected category was updated successfully!"));
    }

    @RequestMapping(value = "admin/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCategory(@PathVariable("id")Long parentId) {
        try {
            categoryService.removeCategoryById(parentId);
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category does not exist!");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category has its sub-categories");
        }

        return ResponseEntity.ok(new LogDTO("Selected category was removed successfully!"));
    }


}

package kz.aitu.bot.dtos;

import kz.aitu.bot.model.Category;
import kz.aitu.bot.model.Language;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private Long parentId;

    public CategoryDTO(Category category, Language lang) {
        this.setId(category.getId());
        if (lang == Language.ENG) {
            this.setCategoryName(category.getNameEng());
        } else if (lang == Language.KAZ) {
            this.setCategoryName(category.getNameKaz());
        } else {
            this.setCategoryName(category.getNameRus());
        }
    }
}

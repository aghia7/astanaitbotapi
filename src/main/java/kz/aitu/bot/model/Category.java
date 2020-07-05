package kz.aitu.bot.model;

import kz.aitu.bot.dtos.CategoryInsertUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name_rus")
    private String nameRus;

    @Column(name = "category_name_kaz")
    private String nameKaz;

    @Column(name = "category_name_eng")
    private String nameEng;

    @Column(name = "parent_category_id")
    private Long parentId;

    @Formula("(select cc.category_name_rus from categories cc where cc.id = parent_category_id)")
    public String parentCategoryNameRus;

    @Formula("(select cc.category_name_eng from categories cc where cc.id = parent_category_id)")
    public String parentCategoryNameEng;

    @Formula("(select cc.category_name_kaz from categories cc where cc.id = parent_category_id)")
    public String parentCategoryNameKaz;

    public Category(CategoryInsertUpdateDTO dto) {
        setId(dto.getId());
        setNameKaz(dto.getNameKaz());
        setNameEng(dto.getNameEng());
        setNameRus(dto.getNameRus());
        setParentId(dto.getParentId());
    }
}

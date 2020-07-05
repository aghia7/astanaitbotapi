package kz.aitu.bot.dtos;

import lombok.Data;

@Data
public class CategoryInsertUpdateDTO {
    private Long id;
    private String nameRus;
    private String nameKaz;
    private String nameEng;
    private Long parentId;
}

package kz.aitu.bot.dtos;

import lombok.Data;

@Data
public class QuestionInsertUpdateDTO {
    private Long id;
    private String questionRus;
    private String questionKaz;
    private String questionEng;
    private String answerRus;
    private String answerKaz;
    private String answerEng;
    private long categoryId;
}

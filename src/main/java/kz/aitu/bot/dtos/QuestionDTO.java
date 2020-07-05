package kz.aitu.bot.dtos;

import kz.aitu.bot.model.Language;
import kz.aitu.bot.model.Question;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String question;
    private String answer;
    private Long categoryId;

    public QuestionDTO(Question question, Language lang) {
        this.setId(question.getId());
        if (lang == Language.KAZ) {
            this.setQuestion(question.getQuestionKaz());
            this.setAnswer(question.getAnswerKaz());
            this.setCategoryId(question.getCategoryId());
        } else if (lang == Language.ENG) {
            this.setQuestion(question.getQuestionEng());
            this.setAnswer(question.getAnswerEng());
            this.setCategoryId(question.getCategoryId());
        } else {
            this.setQuestion(question.getQuestionRus());
            this.setAnswer(question.getAnswerRus());
            this.setCategoryId(question.getCategoryId());
        }
    }

}

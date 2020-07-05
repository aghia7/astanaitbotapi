package kz.aitu.bot.model;

import kz.aitu.bot.dtos.QuestionInsertUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Data
@Entity
@Table(name = "faq")
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_rus")
    private String questionRus;

    @Column(name = "question_kaz")
    private String questionKaz;

    @Column(name = "question_eng")
    private String questionEng;

    @Column(name = "answer_rus")
    private String answerRus;

    @Column(name = "answer_kaz")
    private String answerKaz;

    @Column(name = "answer_eng")
    private String answerEng;

//    @Column(name = "question_counter")
//    private int questionCounter;

    @Column(name = "category_id")
    private long categoryId;

    @Formula("(select cc.category_name_rus from categories cc where cc.id = category_id)")
    public String categoryNameRus;

    @Formula("(select cc.category_name_kaz from categories cc where cc.id = category_id)")
    public String categoryNameKaz;

    @Formula("(select cc.category_name_eng from categories cc where cc.id = category_id)")
    public String categoryNameEng;

    public Question(QuestionInsertUpdateDTO dto) {
        setId(dto.getId());
        setQuestionEng(dto.getQuestionEng());
        setQuestionKaz(dto.getQuestionKaz());
        setQuestionRus(dto.getQuestionRus());
        setAnswerEng(dto.getAnswerEng());
        setAnswerKaz(dto.getAnswerKaz());
        setAnswerRus(dto.getAnswerRus());
        setCategoryId(dto.getCategoryId());
    }
}

package kz.aitu.bot.service.interfaces;

import kz.aitu.bot.dtos.QuestionInsertUpdateDTO;
import kz.aitu.bot.model.Question;

import java.util.List;

public interface QuestionService {

    Question getById(Long id);

    Question getAnswerByQuestionRus(String question);
    Question getAnswerByQuestionKaz(String question);
    Question getAnswerByQuestionEng(String question);

    List<Question> getByCategoryId(Long id);

    List<Question> findAll();

    List<Question> getByCategoryNameKaz(String catname);

    List<Question> getByCategoryNameRus(String catname);

    List<Question> getByCategoryNameEng(String catname);

    void addQuestion(QuestionInsertUpdateDTO questionInsertDTO);

    void removeQuestionById(Long parentId);

    void updateQuestion(QuestionInsertUpdateDTO questionUpdateDTO) throws Exception;


//    void  addQuestion(Question question);
//
//    void editQuestion(Question question);
//
//    void deleteQuestion(Long id);


}

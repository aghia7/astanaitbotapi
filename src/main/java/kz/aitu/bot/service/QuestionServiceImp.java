package kz.aitu.bot.service;

import kz.aitu.bot.dtos.QuestionInsertUpdateDTO;
import kz.aitu.bot.model.Question;
import kz.aitu.bot.repository.QuestionRepository;
import kz.aitu.bot.service.interfaces.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public Question getById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public Question getAnswerByQuestionRus(String question) {
        return questionRepository.findByQuestionRusContaining(question);
    }

    @Override
    public Question getAnswerByQuestionKaz(String question) {
        return questionRepository.findByQuestionKazContaining(question);
    }

    @Override
    public Question getAnswerByQuestionEng(String question) {
        return questionRepository.findByQuestionEngContaining(question);
    }

    @Override
    public List<Question> getByCategoryId(Long id) {
        return questionRepository.findByCategoryId(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getByCategoryNameKaz(String catname) {
        return questionRepository.findByCategoryNameKazContaining(catname);
    }

    @Override
    public List<Question> getByCategoryNameRus(String catname) {
        return questionRepository.findByCategoryNameRusContaining(catname);
    }

    @Override
    public List<Question> getByCategoryNameEng(String catname) {
        return questionRepository.findByCategoryNameEngContaining(catname);
    }

    @Override
    public void addQuestion(QuestionInsertUpdateDTO questionInsertDTO) {
        Question category = new Question(questionInsertDTO);
        questionRepository.save(category);
    }

    @Override
    public void removeQuestionById(Long parentId) {
        questionRepository.deleteById(parentId);
    }

    @Override
    public void updateQuestion(QuestionInsertUpdateDTO questionUpdateDTO) throws Exception {
        if (!questionRepository.existsById(questionUpdateDTO.getId()))
            throw new Exception("Category does not exist!");
        Question category = new Question(questionUpdateDTO);
        questionRepository.save(category);
    }

}

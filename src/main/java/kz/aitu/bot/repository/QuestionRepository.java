package kz.aitu.bot.repository;

import kz.aitu.bot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategoryId(Long id);
    List<Question> findByCategoryNameRusContaining(String categoryName);
    List<Question> findByCategoryNameKazContaining(String categoryName);
    List<Question> findByCategoryNameEngContaining(String categoryName);
    Question findByQuestionRusContaining(String question);
    Question findByQuestionKazContaining(String question);
    Question findByQuestionEngContaining(String question);
    void deleteById(Long id);
}

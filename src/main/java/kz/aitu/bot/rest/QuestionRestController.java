package kz.aitu.bot.rest;

import kz.aitu.bot.dtos.LogDTO;
import kz.aitu.bot.dtos.QuestionDTO;
import kz.aitu.bot.dtos.QuestionInsertUpdateDTO;
import kz.aitu.bot.model.Language;
import kz.aitu.bot.model.Question;
import kz.aitu.bot.service.interfaces.QuestionService;
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
@RequestMapping("/api/v1/questions/")
public class QuestionRestController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "{lang}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDTO> getQuestionLang(@PathVariable("lang") String lang, @PathVariable("id") Long questionId) {
        if (questionId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question question = this.questionService.getById(questionId);

        if (question == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            lang = lang.toLowerCase();
            lang = lang.replaceAll("\\s", "");
            return new ResponseEntity<>(new QuestionDTO(question, Language.convert(lang)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new QuestionDTO(question, Language.RUS), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "lang/{lang}/question", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDTO> getAnswerByQuestionLang(@PathVariable("lang") String lang, @RequestParam("question") String questionAsked) {
        if (questionAsked == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            lang = lang.toLowerCase(); //KAZ RUS ENG
            lang = lang.replaceAll("\\s", "");
            Language language = Language.convert(lang);
            Question question;
            if (language == Language.KAZ) {
                question = this.questionService.getAnswerByQuestionKaz(questionAsked);
            } else if (language == Language.RUS) {
                question = this.questionService.getAnswerByQuestionRus(questionAsked);
            } else {
                question = this.questionService.getAnswerByQuestionEng(questionAsked);
            }
            if (question == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(new QuestionDTO(question, language), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable("id") Long questionId) {
        if (questionId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question question = this.questionService.getById(questionId);

        if (question == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new QuestionDTO(question, Language.RUS), HttpStatus.OK);
    }

    @RequestMapping(value = "full/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> getFullQuestion(@PathVariable("id") Long questionId) {
        if (questionId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Question question = this.questionService.getById(questionId);

        if (question == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = this.questionService.findAll();

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @RequestMapping(value = "lang/{lang}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsLang(@PathVariable("lang") String lang) {
        List<Question> questions = this.questionService.findAll();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        if (questions.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try {
            lang = lang.toLowerCase();
            lang = lang.replaceAll("\\s", "");
            for (Question question : questions) {
                questionDTOS.add(new QuestionDTO(question, Language.convert(lang)));
            }

            return new ResponseEntity<>(questionDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(questionDTOS, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "lang/{lang}/cat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCategoryIdAndLang(@PathVariable("lang") String lang, @PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Question> questions = questionService.getByCategoryId(id);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            lang = lang.toLowerCase();
            lang = lang.replaceAll("\\s", "");
            for (Question question : questions) {
                questionDTOS.add(new QuestionDTO(question, Language.convert(lang)));
            }

            return new ResponseEntity<>(questionDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(questionDTOS, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/lang/{lang}/catname", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCategoryNameAndLang(@PathVariable("lang") String lang, @RequestParam("category") String catname) {
        if (catname == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        try {
            lang = lang.toLowerCase();
            lang = lang.replaceAll("\\s", "");
            Language language = Language.convert(lang);
            List<Question> questions;
            switch (language) {
                case KAZ: questions = questionService.getByCategoryNameKaz(catname); break;
//                case ENG -> questions = questionService.getByCategoryNameEng(catname);
                default: questions = questionService.getByCategoryNameRus(catname); break;
            }


            if (questions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            for (Question question : questions) {
                questionDTOS.add(new QuestionDTO(question, language));
            }

            return new ResponseEntity<>(questionDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(questionDTOS, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "admin/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createQuestion(@RequestBody QuestionInsertUpdateDTO questionInsertDTO) {
        try {
            questionService.addQuestion(questionInsertDTO);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parent category does not exist");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(new LogDTO("A new question was created successfully!"));
    }

    @RequestMapping(value = "admin/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(@RequestBody QuestionInsertUpdateDTO questionUpdateDTO) {
        try {
            questionService.updateQuestion(questionUpdateDTO);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parent category does not exist");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(new LogDTO("Selected question was updated successfully!"));
    }

    @RequestMapping(value = "admin/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long parentId) {
        try {
            questionService.removeQuestionById(parentId);
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category does not exist!");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category has its sub-categories");
        }

        return ResponseEntity.ok(new LogDTO("Selected question was removed successfully!"));
    }

}

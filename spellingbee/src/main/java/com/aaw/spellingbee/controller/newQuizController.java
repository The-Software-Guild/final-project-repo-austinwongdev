/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
import com.aaw.spellingbee.service.SpellingBeeService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Austin Wong
 */

@Controller
public class NewQuizController {
    
    private final SpellingBeeService service;

    @Autowired
    public NewQuizController(SpellingBeeService service) {
        this.service = service;
    }
    
    @GetMapping("newQuiz")
    public String newQuiz(){
        return "redirect:/takeQuiz?quizId=0";
    }
    
    @GetMapping("takeQuiz")
    public String takeQuiz(int quizId, Model model){
        
        if (quizId == 0){
            Quiz newQuiz = service.generateQuiz();
            Attempt newAttempt = service.createAttempt(newQuiz.getQuizId());
            return "redirect:/takeQuiz?quizId="
                    +newQuiz.getQuizId();
        }
        
        Quiz quiz = service.getQuiz(quizId);
        Word word = quiz.getNextWord();
        if (word == null){
            // Handle error
        }
        String pronunciationURL = word.getFirstPronunciationURL();
        String wordExampleHidingWord = service.hideWord(word.getExampleUsage(), word.getHeadword());
        String wordDefinitionHidingWord = service.hideWord(word.getDefinition(), word.getHeadword());
        
        model.addAttribute("word", word);
        model.addAttribute("wordExampleHidingWord", wordExampleHidingWord);
        model.addAttribute("wordDefinitionHidingWord", wordDefinitionHidingWord);
        model.addAttribute("wordNumber", quiz.getNextWordNumber());
        model.addAttribute("pronunciationURL", pronunciationURL);
        model.addAttribute("quizId", quizId);
        model.addAttribute("attemptId", quiz.getMostRecentAttempt().getAttemptId());
        
        return "takeQuiz";
    }
    
    @PostMapping("submitGuess")
    public String submitGuess(Guess guess, HttpServletRequest request){
        
        int attemptId = Integer.parseInt(request.getParameter("attemptId"));
        int quizId = Integer.parseInt(request.getParameter("quizId"));
                
        // Store guess
        guess.setGuess(request.getParameter("guessInput"));
        guess.setWordId(request.getParameter("wordId"));
        guess.setCorrectSpelling(request.getParameter("headword"));
        guess.setAttemptId(attemptId);
        guess.setIsCorrect(service.isGuessCorrect(guess));
        service.addGuess(guess);
        
        Quiz quiz = service.getQuiz(quizId);
        Word word = quiz.getNextWord();
        if (word == null){
            return "redirect:/quizResult?attemptId="+attemptId;
        }
            
        return "redirect:/takeQuiz?quizId="+quizId;
    }
}

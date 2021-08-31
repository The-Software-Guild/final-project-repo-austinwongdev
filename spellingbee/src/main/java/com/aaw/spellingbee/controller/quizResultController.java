/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.service.SpellingBeeService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Austin Wong
 */

@Controller
public class QuizResultController {

    private final SpellingBeeService service;

    @Autowired
    public QuizResultController(SpellingBeeService service) {
        this.service = service;
    }
    
    @GetMapping("quizResult")
    public String displayQuizResult(int id, Model model){
        
        // *** Fake code to get webpage temporarily running
        Attempt attempt = new Attempt();
        attempt.setAttemptDate(LocalDate.now());
        attempt.setAttemptId(id);
        attempt.setQuizId(id);
        attempt.setPercentScore(40);
        model.addAttribute("attempt", attempt);
        model.addAttribute("numCorrect", 2);
        model.addAttribute("numIncorrect", 3);
        // *** End fake code
        
        return "quizResult";
    }
    
    @GetMapping("deleteQuizResult")
    public String deleteQuizResult(int id){
        return "history";
    }
    
}

/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.service.SpellingBeeService;
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
    public String displayQuizResult(int attemptId, Model model){
        
        Attempt attempt = service.getAttempt(attemptId);
        model.addAttribute("attempt", attempt);
        model.addAttribute("guesses", attempt.getGuesses());
        
        return "quizResult";
    }
    
    @GetMapping("deleteQuizResult")
    public String deleteQuizResult(int attemptId){
        
        service.deleteAttempt(attemptId);
        
        return "redirect:/history";
    }
    
}

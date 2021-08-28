/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.Guess;
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
    
    @GetMapping("newQuiz")
    public String createNewQuiz(){
        
        // This is where we would be making 3rd party API calls to get random words to make the quiz
        
        // return "redirect:/takeQuiz?quizId="+quizId;
        
        // *** Fake code for testing purposes
        return "redirect:/takeQuiz?quizId=1";
        // *** End fake code
    }
    
    @GetMapping("takeQuiz")
    public String takeQuiz(int quizId, Model model){
        
        // *** Fake code to get webpage temporarily running
        model.addAttribute("quizId", quizId);
        
        // This is where we would be making 3rd party API calls to get each attribute
        
        model.addAttribute("wordDefinition", "A learned fool");
        model.addAttribute("wordExample", "You are too _____ to handle.");
        model.addAttribute("wordPronunciationURL", "https://media.merriam-webster.com/audio/prons/en/us/mp3/w/wonder03.mp3");
        // *** End fake code
        
        return "takeQuiz";
    }
    
    @PostMapping("submitGuess")
    public String submitGuess(Guess guess, Model model){
        
        // You can perform logic here and then pass in the next attributes through the model"
        model.addAttribute("quizId", "this is a test");
        
        // If from this logic you find out that all words have been guessed, then add
        // attempt to DB and return to quizResult screen instead of takeQuiz screen
        // (use if statement)
        
        return "takeQuiz";
    }
    
}

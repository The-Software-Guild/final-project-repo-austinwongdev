/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.DictionaryEntry;
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.service.SpellingBeeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    //private List<DictionaryEntry> entries;
    private int entryIndex;
    private DictionaryEntry entry;
    private Quiz newQuiz;

    @Autowired
    public NewQuizController(SpellingBeeService service) {
        this.service = service;
    }
    
    @GetMapping("newQuiz")
    public String createNewQuiz(Model model){
        List<DictionaryEntry> entries = service.generateQuiz();
        entry = entries.get(0);
        entryIndex = 0;
        newQuiz = new Quiz();
        model.addAttribute("entries", entries);
        model.addAttribute("entryIndex", entryIndex);
        model.addAttribute("entry", entry); // entry is coming back null, so the problem may be from generating quiz?
        return "redirect:/takeQuiz";
    }
    
    @GetMapping("takeQuiz")
    public String takeQuiz(Model model){
        
        model.addAttribute("entry", entry); // this works so you have to add it here, but you're still having problems with getting a pronunciation, example, and definition
        // Biggest problem appears to be finding ones with example usages
        model.addAttribute("wordNumber", entryIndex+1);
        //model.addAttribute("entry", entries.get(entryIndex));
        
        return "takeQuiz";
    }
    
    @PostMapping("submitGuess")
    public String submitGuess(Guess guess, Model model){
        
        // Store guess
        // ***AAW
        
        entryIndex += 1;
        
        if (entryIndex < service.getNumQuizWords()){
            model.addAttribute("wordNumber", entryIndex+1);
            //model.addAttribute("entry", entries.get(entryIndex));
        
            return "takeQuiz";
        }
        else{
            // Add quiz to DB and get attemptId
            // ***AAW to do
            int attemptId = 1;
            
            return "redirect:/quizResult?attemptId="+attemptId;
        }
    }
    
}

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
    private List<DictionaryEntry> entries;
    private Quiz newQuiz;

    @Autowired
    public NewQuizController(SpellingBeeService service) {
        this.service = service;
    }
    
    @GetMapping("newQuiz")
    public String createNewQuiz(){
        entries = service.generateQuiz();
        entryIndex = 0;
        newQuiz = new Quiz();
        return "redirect:/takeQuiz";
    }
    
    @GetMapping("takeQuiz")
    public String takeQuiz(Model model){
        
        entry = entries.get(entryIndex);
        model.addAttribute("entry", entry);
        model.addAttribute("wordNumber", entryIndex+1);
        model.addAttribute("pronunciationURL", entry.getPronunciationURLs().get(0));
        
        return "takeQuiz";
    }
    
    @PostMapping("submitGuess")
    public String submitGuess(Guess guess, Model model){
        
        // Store guess
        // ***AAW
        
        entryIndex += 1;
        
        if (entryIndex < service.getNumQuizWords()){
            entry = entries.get(entryIndex);
            model.addAttribute("entry", entries.get(entryIndex));
            model.addAttribute("wordNumber", entryIndex+1);
            model.addAttribute("pronunciationURL", entry.getPronunciationURLs().get(0));
        
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

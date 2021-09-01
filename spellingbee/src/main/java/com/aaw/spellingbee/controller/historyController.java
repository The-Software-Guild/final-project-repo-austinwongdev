/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.service.SpellingBeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Austin Wong
 */

@Controller
public class HistoryController {
    
    private final SpellingBeeService service;

    @Autowired
    public HistoryController(SpellingBeeService service) {
        this.service = service;
    }

    @GetMapping("history")
    public String displayHistory(Model model){
        List<Attempt> attempts = service.getAllPartialOrCompleteAttempts();
        model.addAttribute("attempts", attempts);
        return "history";
    }
    
}

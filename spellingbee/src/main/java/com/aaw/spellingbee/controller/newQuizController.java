/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Austin Wong
 */

@Controller
public class newQuizController {
    
    @GetMapping("newQuiz")
    public String displayNewQuiz(){
        return "newQuiz";
    }
    
}

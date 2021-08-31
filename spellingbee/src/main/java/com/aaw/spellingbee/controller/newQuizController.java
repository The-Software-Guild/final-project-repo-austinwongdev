/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.controller;

import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.service.SpellingBeeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

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
    public String createNewQuiz(){
        // This is where we would be making 3rd party API calls to get random words to make the quiz

        
        // return "redirect:/takeQuiz?quizId="+quizId;

        // *** Fake code for testing purposes
        testGetJsonEntityAsString();
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
    
    /**
     * Gets 5 random words from a 3rd party random word API
     * @return - A List of 5 words as Strings
     */
    private List<String> getWordsForQuiz(){
        
        final String RANDOM_WORD_API_GET_WORD_REQUEST = "https://random-word-api.herokuapp.com/word?number=5";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(RANDOM_WORD_API_GET_WORD_REQUEST, String[].class);
        String[] wordsArr = responseEntity.getBody();
        List<String> words = new ArrayList<>(Arrays.asList(wordsArr));
        
        return words;
    }
    
    private void testGetJsonEntityAsString(){
        
        final String GET_JSON_FOR_BATTLE = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/battle?key=49b65708-71c6-4de2-a46e-af5b83260027";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(GET_JSON_FOR_BATTLE, String.class);
        String jsonStr = response.getBody();
        JSONArray json = new JSONArray(jsonStr);
        if (json.iterator().hasNext()){
            JSONObject firstentry = json.getJSONObject(1);
            JSONObject meta = firstentry.getJSONObject("meta");
            JSONObject hwi = firstentry.getJSONObject("hwi");
            String id = meta.getString("id");
            String headword = hwi.getString("hw").replace("*", "");
        }
    }
    
}

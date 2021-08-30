/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
import com.aaw.spellingbee.model.WordVariant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class GuessDaoDBTest {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    WordVariantDao wordVariantDao;
    
    @Autowired
    WordDao wordDao;
    
    @Autowired
    GuessDao guessDao;
    
    @Autowired
    QuizDao quizDao;
    
    @Autowired
    AttemptDao attemptDao;
    
    private Word word1;
    private Word word2;
    private Word word3;
    private WordVariant word1Variant1;
    private WordVariant word2Variant1;
    private WordVariant word2Variant2;
    private Quiz quiz1;
    private Attempt attempt1;
    private Guess guess1;
    private Guess guess2;
    
    public GuessDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        // Clear out guess table in DB
        List<Guess> guesses = guessDao.getAllGuesses();
        for (Guess guess : guesses){
            guessDao.deleteGuess(guess.getGuessId());
        }
        
        // Clear out attempt table in DB
        List<Attempt> attempts = attemptDao.getAllAttempts();
        for (Attempt attempt : attempts){
            attemptDao.deleteAttempt(attempt.getAttemptId());
        }
        
        // Clear out quiz table in DB
        List<Quiz> quizzes = quizDao.getAllQuizzes();
        for (Quiz quiz : quizzes){
            quizDao.deleteQuiz(quiz.getQuizId());
        }
        
        // Clear out wordvariant table in DB
        List<WordVariant> wordVariants = wordVariantDao.getAllWordVariants();
        for (WordVariant wordVariant : wordVariants){
            wordVariantDao.deleteWordVariant(wordVariant.getWordVariantId());
        }
        
        // Clear out word table in DB
        List<Word> words = wordDao.getAllWords();
        for (Word word : words){
            wordDao.deleteWord(word.getWordId());
        }
        
        // Create words
        word1 = new Word();
        word1.setHeadword("acknowledgment");
        word1.setWordId("acknowledgment");
        
        word2 = new Word();
        word2.setHeadword("kabbalah");
        word2.setWordId("kabbalah");
        
        word3 = new Word();
        word3.setHeadword("fantastic");
        word3.setWordId("fantastic:1");
        
        // Create wordvariants and add to Word objects
        // acknowledgment : acknowledgement
        word1Variant1 = new WordVariant();
        word1Variant1.setWordId(word1.getWordId());
        word1Variant1.setWordVariant("acknowledgement");
        
        word1.setWordVariants(new ArrayList<>(Arrays.asList(word1Variant1)));
        
        // kabbalah: kabbala or kabala
        word2Variant1 = new WordVariant();
        word2Variant1.setWordId(word2.getWordId());
        word2Variant1.setWordVariant("kabbala");
        
        word2Variant2 = new WordVariant();
        word2Variant2.setWordId(word2.getWordId());
        word2Variant2.setWordVariant("kabala");
        
        word2.setWordVariants(new ArrayList<>(Arrays.asList(word2Variant1, word2Variant2)));
        
        // Create quiz
        quiz1 = new Quiz();
        quiz1.setQuizId(1);
        quiz1.setWords(new ArrayList<>(Arrays.asList(word1, word2)));
        
        // Create attempt
        attempt1 = new Attempt();
        attempt1.setAttemptId(1);
        attempt1.setAttemptDate(LocalDate.now());
        attempt1.setQuizId(1);
        attempt1.setPercentScore(40f);
        
        // Create guesses
        guess1 = new Guess();
        guess1.setAttemptId(1);
        guess1.setGuess("acknowledgment");
        guess1.setGuessId(1);
        guess1.setIsCorrect(true);
        guess1.setWordId(word1.getWordId());
        
        guess2 = new Guess();
        guess2.setAttemptId(1);
        guess2.setGuess("cobbola");
        guess2.setGuessId(2);
        guess2.setIsCorrect(false);
        guess2.setWordId(word2.getWordId());
        
        // Link guesses to attempts and attempts to quizzes
        attempt1.setGuesses(new ArrayList<>(Arrays.asList(guess1, guess2)));
        quiz1.setAttempts(new ArrayList<>(Arrays.asList(attempt1)));
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getGuessesForAttemptId method, of class GuessDaoDB.
     */
    @Test
    public void testGetGuessesForAttemptId() {
    }

    /**
     * Test of getAllGuesses method, of class GuessDaoDB.
     */
    @Test
    public void testGetAllGuesses() {
    }

    /**
     * Test of deleteGuess method, of class GuessDaoDB.
     */
    @Test
    public void testDeleteGuess() {
    }

    /**
     * Test of addGuess method, of class GuessDaoDB.
     */
    @Test
    public void testAddGuess() {
    }

    /**
     * Test of getGuessByGuessId method, of class GuessDaoDB.
     */
    @Test
    public void testGetGuessByGuessId() {
    }
    
}

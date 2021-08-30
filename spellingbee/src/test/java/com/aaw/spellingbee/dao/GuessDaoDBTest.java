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
import static org.junit.jupiter.api.Assertions.*;
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
    private Quiz quiz2;
    private Attempt attempt1;
    private Attempt attempt2;
    private Guess guess1;
    private Guess guess2;
    private Guess guess3;
    private Guess guess4;
    private Guess guess5;
    
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
        
        // kabbalah: kabbala or kabala
        word2Variant1 = new WordVariant();
        word2Variant1.setWordId(word2.getWordId());
        word2Variant1.setWordVariant("kabbala");
        
        word2Variant2 = new WordVariant();
        word2Variant2.setWordId(word2.getWordId());
        word2Variant2.setWordVariant("kabala");
        
        // Link variants to words
        word1.setWordVariants(new ArrayList<>(Arrays.asList(word1Variant1)));
        word2.setWordVariants(new ArrayList<>(Arrays.asList(word2Variant1, word2Variant2)));
        word3.setWordVariants(new ArrayList<>());
        
        // Add all words
        wordDao.addWord(word1);
        wordDao.addWord(word2);
        wordDao.addWord(word3);
        
        // Create quizzes without linking to other tables
        quiz1 = new Quiz();
        quiz1.setQuizId(1);
        quiz2 = new Quiz();
        quiz2.setQuizId(2);
        final String INSERT_QUIZ = "INSERT INTO quiz (quizId) VALUES(?)";
        jdbc.update(INSERT_QUIZ, quiz1.getQuizId());
        jdbc.update(INSERT_QUIZ, quiz2.getQuizId());
        
        // Create attempts and add quiz IDs
        attempt1 = new Attempt();
        attempt1.setAttemptId(1);
        attempt1.setAttemptDate(LocalDate.now());
        attempt1.setPercentScore(40f);
        attempt1.setQuizId(quiz1.getQuizId());
        
        attempt2 = new Attempt();
        attempt2.setAttemptId(2);
        attempt2.setAttemptDate(LocalDate.now());
        attempt2.setPercentScore(100f);
        attempt2.setQuizId(2);
        
        // Insert attempts without guesses
        final String INSERT_ATTEMPT = "INSERT INTO attempt (attemptId,"
                + "attemptDate, quizId) VALUES (?, ?, ?)";
        jdbc.update(INSERT_ATTEMPT, attempt1.getAttemptId(), attempt1.getAttemptDate(),
                attempt1.getQuizId());
        jdbc.update(INSERT_ATTEMPT, attempt2.getAttemptId(), attempt2.getAttemptDate(),
                attempt2.getQuizId());
        
        // Create guesses for attempts
        guess1 = new Guess();
        guess1.setGuess("acknowledgment");
        guess1.setIsCorrect(true);
        guess1.setWordId(word1.getWordId());
        guess1.setAttemptId(attempt1.getAttemptId());
        
        guess2 = new Guess();
        guess2.setGuess("cobbola");
        guess2.setIsCorrect(false);
        guess2.setWordId(word2.getWordId());
        guess2.setAttemptId(attempt1.getAttemptId());
        
        guess3 = new Guess();
        guess4 = new Guess();
        guess5 = new Guess();
        guess3.setIsCorrect(true);
        guess4.setIsCorrect(true);
        guess5.setIsCorrect(true);
        guess3.setWordId(word1.getWordId());
        guess4.setWordId(word2.getWordId());
        guess5.setWordId(word3.getWordId());
        guess3.setGuess(word1.getHeadword());
        guess4.setGuess(word2.getHeadword());
        guess5.setGuess(word3.getHeadword());
        guess3.setAttemptId(attempt2.getAttemptId());
        guess4.setAttemptId(attempt2.getAttemptId());
        guess5.setAttemptId(attempt2.getAttemptId());
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getGuessesForAttemptId method, of class GuessDaoDB.
     */
    @Test
    public void testGetGuessesForAttemptId() {
        
        guess1 = guessDao.addGuess(guess1);
        guess2 = guessDao.addGuess(guess2);
        guess3 = guessDao.addGuess(guess3);
        guess4 = guessDao.addGuess(guess4);
        guess5 = guessDao.addGuess(guess5);
        
        List<Guess> guessesForAttempt1 = guessDao.getGuessesForAttemptId(attempt1.getAttemptId());
        assertEquals(2, guessesForAttempt1.size());
        assertTrue(guessesForAttempt1.contains(guess1));
        assertTrue(guessesForAttempt1.contains(guess2));
        assertFalse(guessesForAttempt1.contains(guess3));
        assertFalse(guessesForAttempt1.contains(guess4));
        assertFalse(guessesForAttempt1.contains(guess5));
        
    }

    /**
     * Test of getAllGuesses method, of class GuessDaoDB.
     */
    @Test
    public void testGetAllGuesses() {
        
        guess1 = guessDao.addGuess(guess1);
        guess2 = guessDao.addGuess(guess2);
        
        List<Guess> guessesFromDao = guessDao.getAllGuesses();
        assertEquals(2, guessesFromDao.size());
        assertTrue(guessesFromDao.contains(guess1));
        assertTrue(guessesFromDao.contains(guess2));
        
    }

    /**
     * Test of deleteGuess method, of class GuessDaoDB.
     */
    @Test
    public void testDeleteGuess() {
        
        guess1 = guessDao.addGuess(guess1);
        guess2 = guessDao.addGuess(guess2);
        
        Guess guessFromDao = guessDao.getGuessByGuessId(guess1.getGuessId());
        assertNotNull(guessFromDao);
        assertEquals(guess1, guessFromDao);
        
        guessDao.deleteGuess(guess1.getGuessId());
        guessFromDao = guessDao.getGuessByGuessId(guess1.getGuessId());
        assertNull(guessFromDao);
        
    }

    /**
     * Test of addGuess and getGuessByGuessId method, of class GuessDaoDB.
     */
    @Test
    public void testAddAndGetGuess() {
        
        guess1 = guessDao.addGuess(guess1);
        Guess guessFromDao = guessDao.getGuessByGuessId(guess1.getGuessId());
        assertNotNull(guessFromDao);
        assertEquals(guess1, guessFromDao);
        
    }
}

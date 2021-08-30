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
public class QuizDaoDBTest {
    
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
    private Guess guess1;
    private Guess guess2;
    
    public QuizDaoDBTest() {
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
        
        // Create first quiz, don't add yet
        quiz1 = new Quiz();
        quiz1.setQuizId(1);
        quiz1.setWords(new ArrayList<>(Arrays.asList(word1, word2)));
        
        attempt1 = new Attempt();
        attempt1.setAttemptId(1);
        attempt1.setAttemptDate(LocalDate.now());
        attempt1.setPercentScore(40f);
        
        guess1 = new Guess();
        guess1.setGuess("acknowledgment");
        guess1.setGuessId(1);
        guess1.setIsCorrect(true);
        guess1.setWordId(word1.getWordId());
        
        guess2 = new Guess();
        guess2.setGuess("cobbola");
        guess2.setGuessId(2);
        guess2.setIsCorrect(false);
        guess2.setWordId(word2.getWordId());
        
        attempt1.setGuesses(new ArrayList<>(Arrays.asList(guess1, guess2)));
        quiz1.setAttempts(new ArrayList<>(Arrays.asList(attempt1)));
        
        // Create second Quiz, don't add yet
        Guess guess3 = new Guess();
        Guess guess4 = new Guess();
        Guess guess5 = new Guess();
        guess3.setIsCorrect(true);
        guess4.setIsCorrect(true);
        guess5.setIsCorrect(true);
        guess3.setWordId(word1.getWordId());
        guess4.setWordId(word2.getWordId());
        guess5.setWordId(word3.getWordId());
        guess3.setGuess(word1.getHeadword());
        guess4.setGuess(word2.getHeadword());
        guess5.setGuess(word3.getHeadword());
        
        Attempt attempt2 = new Attempt();
        attempt2.setAttemptDate(LocalDate.now());
        attempt2.setPercentScore(100f);
        attempt2.setGuesses(new ArrayList<>(Arrays.asList(guess3, guess4, guess5)));
        
        quiz2 = new Quiz();
        quiz2.setWords(new ArrayList<>(Arrays.asList(word1, word2, word3)));
        quiz2.setAttempts(new ArrayList<>(Arrays.asList(attempt2)));
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addQuiz and getQuizById methods, of class QuizDaoDB.
     */
    @Test
    public void testAddAndGetQuiz() {
        
        quiz1 = quizDao.addQuiz(quiz1);
        Quiz quizFromDao = quizDao.getQuizByQuizId(quiz1.getQuizId());
        assertNotNull(quizFromDao);
        assertEquals(quiz1.getQuizId(), quizFromDao.getQuizId());
        assertEquals(quiz1.getWords(), quizFromDao.getWords());
        assertEquals(quiz1.getAttempts(), quizFromDao.getAttempts());
        
    }

    /**
     * Test of getAllQuizzes method, of class QuizDaoDB.
     */
    @Test
    public void testGetAllQuizzes() {
        
        // Add first quiz
        quiz1 = quizDao.addQuiz(quiz1);
        
        // Add second quiz
        quiz2 = quizDao.addQuiz(quiz2);
        
        // Get all quizzes
        List<Quiz> quizzes = quizDao.getAllQuizzes();
        assertEquals(2, quizzes.size());
        assertTrue(quizzes.contains(quiz1));
        assertTrue(quizzes.contains(quiz2));
        
    }

    /**
     * Test of deleteQuiz method, of class QuizDaoDB.
     */
    @Test
    public void testDeleteQuiz() {
        
        // Add quizzes
        quiz1 = quizDao.addQuiz(quiz1);
        quiz2 = quizDao.addQuiz(quiz2);
        
        // Ensure quiz to delete (quiz1) is in DB
        Quiz quizFromDao = quizDao.getQuizByQuizId(quiz1.getQuizId());
        assertNotNull(quizFromDao);
        assertEquals(quiz1, quizFromDao);
        
        // Delete quiz1
        quizDao.deleteQuiz(quiz1.getQuizId());
        
        // Ensure quiz1 is deleted
        quizFromDao = quizDao.getQuizByQuizId(quiz1.getQuizId());
        assertNull(quizFromDao);
        
        // Ensure rows in other tables referencing quiz are deleted
        final String SELECT_QUIZWORDS_BY_QUIZ_ID = "SELECT COUNT(*) FROM quizword WHERE "
                + "quizId = ?";
        int numEntriesInQuizWordTable = jdbc.queryForObject(SELECT_QUIZWORDS_BY_QUIZ_ID, Integer.class, quiz1.getQuizId());
        assertEquals(0, numEntriesInQuizWordTable);
        
        List<Guess> allGuesses = guessDao.getAllGuesses();
        assertEquals(3, allGuesses.size());
        
        List<Attempt> allAttempts = attemptDao.getAllAttempts();
        assertEquals(1, allAttempts.size());
        
        // Ensure words are NOT deleted
        List<Word> allWords = wordDao.getAllWords();
        assertEquals(3, allWords.size());
        
    }
    
}

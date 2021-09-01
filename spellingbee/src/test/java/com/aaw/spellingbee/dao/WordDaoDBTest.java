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
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class WordDaoDBTest {
    
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
    
    public WordDaoDBTest() {
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
        word1.setDefinition("definition");
        word1.setExampleUsage("example");
        word1.setOffensive(true);
        word1.addPronunciationURL("URL");
        
        word2 = new Word();
        word2.setHeadword("kabbalah");
        word2.setWordId("kabbalah");
        word2.setDefinition("definition");
        word2.setExampleUsage("example");
        word2.setOffensive(true);
        word2.addPronunciationURL("URL");
        
        word3 = new Word();
        word3.setHeadword("fantastic");
        word3.setWordId("fantastic:1");
        word3.setDefinition("definition");
        word3.setExampleUsage("example");
        word3.setOffensive(true);
        word3.addPronunciationURL("URL");
        
        
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
     * Test of addWord and getWord methods, of class WordDaoDB.
     */
    @Test
    public void testAddAndGetWord() {
        
        // Ensure word is added to retrieved from db
        word2 = wordDao.addWord(word2);
        
        Word wordFromDao = wordDao.getWordByWordId(word2.getWordId());
        
        assertNotNull(wordFromDao);
        assertEquals(word2, wordFromDao);
        
        // Ensure wordvariants are added too
        List<WordVariant> wordVariants = wordVariantDao.getWordVariantsForWordId(word2.getWordId());
        assertEquals(2, wordVariants.size());
        
        // No longer applicable - handled at service layer not at dao layer
//        // Ensure word variants are added to and retrieved from db
//        List<WordVariant> wordVariantsFromDao = wordVariantDao.getWordVariantsForWordId(word2.getWordId());
//        assertEquals(wordVariantsFromDao.size(), word2.getWordVariants().size());
//        assertEquals(word2.getWordVariants(), wordVariantsFromDao);
        
    }
    
    @Test
    public void testAddExistingWord() {
        
        word1 = wordDao.addWord(word1);
        
        Word wordFromDao = wordDao.getWordByWordId(word1.getWordId());
        assertNotNull(wordFromDao);
        assertEquals(word1, wordFromDao);
        
        // Try adding same word
        wordFromDao = wordDao.addWord(word1);
        assertNotNull(wordFromDao);
        assertEquals(word1, wordFromDao);
        wordFromDao = wordDao.getWordByWordId(wordFromDao.getWordId());
        assertNotNull(wordFromDao);
        assertEquals(word1, wordFromDao);
    }

    /**
     * Test of getWordsForQuizId method, of class WordDaoDB.
     */
    @Test
    public void testGetWordsForQuizId() {
        
        // Add quiz to db (words in quiz object are added to db in addQuiz())
        quiz1 = quizDao.addQuiz(quiz1);
        
        List<Word> wordsForQuizFromDao = wordDao.getWordsForQuizId(quiz1.getQuizId());
        assertEquals(2, wordsForQuizFromDao.size());
        assertTrue(wordsForQuizFromDao.contains(word1));
        assertTrue(wordsForQuizFromDao.contains(word2));
        assertFalse(wordsForQuizFromDao.contains(word3));
        
    }

    /**
     * Test of deleteWord method, of class WordDaoDB.
     */
    @Test
    public void testDeleteWord() {
        
        // Add words and guess to db (which requires adding a quiz and attempt)
        // Attempts and guesses should be added in addQuiz()
        quiz1 = quizDao.addQuiz(quiz1);
        
        // Ensure word2 was added
        Word wordFromDao = wordDao.getWordByWordId(word2.getWordId());
        assertNotNull(wordFromDao);
        assertEquals(word2, wordFromDao);
        
        // Ensure word2 is deleted from word table
        wordDao.deleteWord(word2.getWordId());
        wordFromDao = wordDao.getWordByWordId(word2.getWordId());
        assertNull(wordFromDao);
        
        // Ensure word variants (for word2) are deleted from wordvariant table
        List<WordVariant> wordVariantsFromDao = wordVariantDao.getWordVariantsForWordId(word2.getWordId());
        assertEquals(0, wordVariantsFromDao.size());
        
        // Ensure guesses (for word2) are deleted from guess table
        List<Guess> guessesFromDao = guessDao.getGuessesForAttemptId(attempt1.getAttemptId());
        assertEquals(1, guessesFromDao.size());
        assertTrue(guessesFromDao.contains(guess1));
        assertFalse(guessesFromDao.contains(guess2));
        
        // Ensure entries in quizword table for word2 are deleted
        final String SELECT_QUIZWORDS_BY_WORD_ID = "SELECT COUNT(*) FROM quizword WHERE "
                + "wordId = ?";
        int numEntriesInQuizWordTable = jdbc.queryForObject(SELECT_QUIZWORDS_BY_WORD_ID, Integer.class, word2.getWordId());
        assertEquals(0, numEntriesInQuizWordTable);
        
    }

    /**
     * Test of getAllWords method, of class WordDaoDB.
     */
    @Test
    public void testGetAllWords() {
        
        word1 = wordDao.addWord(word1);
        word2 = wordDao.addWord(word2);
        
        List<Word> words = wordDao.getAllWords();
        
        assertEquals(2, words.size());
        assertTrue(words.contains(word1));
        assertTrue(words.contains(word2));
        
    }
    
}

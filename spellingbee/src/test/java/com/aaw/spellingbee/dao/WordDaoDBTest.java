/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Word;
import com.aaw.spellingbee.model.WordVariant;
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

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class WordDaoDBTest {
    
    @Autowired
    WordVariantDao wordVariantDao;
    
    @Autowired
    WordDao wordDao;
    
    @Autowired
    GuessDao guessDao;
    
    private Word word1;
    private Word word2;
    private Word word3;
    private WordVariant word1Variant1;
    private WordVariant word2Variant1;
    private WordVariant word2Variant2;
    
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
        
        // Clear out word table in DB
        List<Word> words = wordDao.getAllWords();
        for (Word word : words){
            wordDao.deleteWord(word.getWordId());
        }
        
        // Clear out wordvariant table in DB
        List<WordVariant> wordVariants = wordVariantDao.getAllWordVariants();
        for (WordVariant wordVariant : wordVariants){
            wordVariantDao.deleteWordVariant(wordVariant.getWordVariantId());
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
        
        // ***AAW to do
        // Create quiz
        // Create attempt
        // Create guess
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
        
        Word wordFromDao = wordDao.getWord(word2.getWordId());
        
        assertNotNull(wordFromDao);
        assertEquals(word2, wordFromDao);
        
        // Ensure word variants are added to and retrieved from db
        List<WordVariant> wordVariantsFromDao = wordVariantDao.getWordVariantsForWordId(word2.getWordId());
        assertEquals(wordVariantsFromDao.size(), word2.getWordVariants().size());
        assertEquals(word2.getWordVariants(), wordVariantsFromDao);
        
    }

    /**
     * Test of getWordsForQuizId method, of class WordDaoDB.
     */
    @Test
    public void testGetWordsForQuizId() {
        
        // ***AAW to do
        
    }

    /**
     * Test of deleteWord method, of class WordDaoDB.
     */
    @Test
    public void testDeleteWord() {
        
        // Ensure word is deleted from word table
        word2 = wordDao.addWord(word2);
        Word wordFromDao = wordDao.getWord(word2.getWordId());
        assertEquals(word2, wordFromDao);
        
        wordDao.deleteWord(word2.getWordId());
        wordFromDao = wordDao.getWord(word2.getWordId());
        assertNull(wordFromDao);
        
        // Ensure word variants are deleted from wordvariant table
        List<WordVariant> wordVariantsFromDao = wordVariantDao.getWordVariantsForWordId(word2.getWordId());
        assertEquals(0, wordVariantsFromDao.size());
        
        // Ensure guesses are deleted from guess table
        // ***AAW to do
        
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

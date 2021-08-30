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
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class WordVariantDaoDBTest {
    
    @Autowired
    WordVariantDao wordVariantDao;
    
    @Autowired
    WordDao wordDao;
    
    @Autowired
    GuessDao guessDao;
    
    private Word word1;
    private Word word2;
    private WordVariant word1Variant1;
    private WordVariant word2Variant1;
    private WordVariant word2Variant2;
    
    public WordVariantDaoDBTest() {
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
        
        // Create words that have variants
        word1 = new Word();
        word1.setHeadword("acknowledgment");
        word1.setWordId("acknowledgment");
        
        word2 = new Word();
        word2.setHeadword("kabbalah");
        word2.setWordId("kabbalah");
        
        // Create wordvariants
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
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addWordVariant and getWordVariant methods, of class WordVariantDaoDB.
     */
    @Test
    public void testAddAndGetWordVariant() {
        
        WordVariant fromDao = wordVariantDao.addWordVariant(word1Variant1);
        assertNotNull(fromDao);
        word1Variant1.setWordVariantId(fromDao.getWordVariantId());
        assertEquals(word1Variant1, fromDao);
        
        fromDao = wordVariantDao.getWordVariant(word1Variant1.getWordVariantId());
        assertNotNull(fromDao);
        assertEquals(word1Variant1, fromDao);
        
    }
    
    /**
     * Test of getAllWordVariants method, of class WordVariantDaoDB.
     */
    @Test
    public void testGetAllWordVariants() {
        
        word1Variant1 = wordVariantDao.addWordVariant(word1Variant1);
        word2Variant1 = wordVariantDao.addWordVariant(word2Variant1);
        word2Variant2 = wordVariantDao.addWordVariant(word2Variant2);
        
        List<WordVariant> fromDao = wordVariantDao.getAllWordVariants();
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(word1Variant1));
        assertTrue(fromDao.contains(word2Variant1));
        assertTrue(fromDao.contains(word2Variant2));
        
    }

    /**
     * Test of getWordVariantsForWordId method, of class WordVariantDaoDB.
     */
    @Test
    public void testGetWordVariantsForWordId() {
        
        // acknowledgment word variant
        word1Variant1 = wordVariantDao.addWordVariant(word1Variant1);
        
        // kabbalah word variants
        word2Variant1 = wordVariantDao.addWordVariant(word2Variant1);
        word2Variant2 = wordVariantDao.addWordVariant(word2Variant2);
        
        // Getting word variants for kabbalah
        List<WordVariant> fromDao = wordVariantDao.getWordVariantsForWordId(word2.getWordId());
        
        // Should contain word variants for kabbalah, but not acknowledgment
        assertEquals(2, fromDao.size());
        assertFalse(fromDao.contains(word1Variant1));
        assertTrue(fromDao.contains(word2Variant1));
        assertTrue(fromDao.contains(word2Variant2));
        
    }

    /**
     * Test of deleteWordVariant method, of class WordVariantDaoDB.
     */
    @Test
    public void testDeleteWordVariant() {
        
        // Add a word variant to the database
        word1Variant1 = wordVariantDao.addWordVariant(word1Variant1);
        WordVariant fromDao = wordVariantDao.getWordVariant(word1Variant1.getWordVariantId());
        assertNotNull(fromDao);
        assertEquals(word1Variant1, fromDao);
        
        // Delete word variant from database
        wordVariantDao.deleteWordVariant(word1Variant1.getWordVariantId());
        
        // Try to get word variant from database (should fail)
        fromDao = wordVariantDao.getWordVariant(word1Variant1.getWordVariantId());
        assertNull(fromDao);
        
    }
    
}

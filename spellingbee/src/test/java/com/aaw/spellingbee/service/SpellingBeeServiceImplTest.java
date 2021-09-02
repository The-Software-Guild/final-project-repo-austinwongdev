/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.spellingbee.service;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
import com.aaw.spellingbee.model.WordVariant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class SpellingBeeServiceImplTest {
    
    @Autowired
    SpellingBeeService service;
    
    Quiz quiz1;
    Quiz quiz2;
    Word word1;
    Word word2;
    Word word3;
    Attempt attempt1;
    Attempt attempt2;
    WordVariant word1Variant1;
    WordVariant word2Variant1;
    WordVariant word2Variant2;
    Guess guess1;
    Guess guess2;
    Guess guess3;
    Guess guess4;
    Guess guess5;
    
    public SpellingBeeServiceImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
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
     * Test of getNumQuizWords method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetNumQuizWords() {
        int expectedNum = 5;
        int fromDao = service.getNumQuizWords();
        assertEquals(expectedNum, fromDao);
    }

    /**
     * Test of addQuiz and getQuiz methods, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testAddGetQuiz() {
        
        quiz1 = service.addQuiz(quiz1);
        Quiz quizFromService = service.getQuiz(quiz1.getQuizId());
        assertNotNull(quizFromService);
        assertEquals(quiz1.getQuizId(), quizFromService.getQuizId());
        assertEquals(quiz1.getWords(), quizFromService.getWords());
        assertEquals(quiz1.getAttempts(), quizFromService.getAttempts());
        
    }

    /**
     * Test of getAllQuizzes method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetAllQuizzes() {
    }

    /**
     * Test of getAllPartialOrCompleteAttempts method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetAllPartialOrCompleteAttempts() {
    }

    /**
     * Test of getAttempt method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetAttempt() {
    }

    /**
     * Test of getAttemptsForQuiz method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetAttemptsForQuiz() {
    }

    /**
     * Test of getGuessesForAttempt method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetGuessesForAttempt() {
    }

    /**
     * Test of deleteAttempt method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testDeleteAttempt() {
    }

    /**
     * Test of getWordsForQuiz method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetWordsForQuiz() {
    }

    /**
     * Test of generateQuiz method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGenerateQuiz() {
    }

    /**
     * Test of createWordFromJSON method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testCreateWordFromJSON() {
    }

    /**
     * Test of hideWord method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testHideWord() {
    }

    /**
     * Test of createAttempt method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testCreateAttempt() {         
        String jsonStrFail1 = "[\"Esd\",\"lad\",\"lade\",\"lads\",\"lady\",\"ldg\",\"ldr\",\"leaf\",\"led\",\"lede\",\"lid\",\"lido\",\"lids\",\"lief\",\"lndg\",\"loaf\",\"lode\",\"ltd\",\"lude\",\"luff\"]";
        String jsonStrFail2 = "[{\"meta\":{\"id\":\"parasiticidal\",\"uuid\":\"9cff23bd-7000-4f7f-a930-b9d85e354ec5\",\"sort\":\"160064000\",\"src\":\"collegiate\",\"section\":\"alpha\",\"stems\":[\"parasiticidal\",\"parasiticide\",\"parasiticides\"],\"offensive\":false},\"hwi\":{\"hw\":\"par*a*sit*i*cid*al\",\"prs\":[{\"mw\":\"\\u02ccper-\\u0259-\\u02ccsi-t\\u0259-\\u02c8s\\u012b-d\\u1d4al\",\"sound\":{\"audio\":\"parasi05\",\"ref\":\"c\",\"stat\":\"1\"}},{\"mw\":\"\\u02ccpa-r\\u0259-\"}]},\"fl\":\"adjective\",\"def\":[{\"sseq\":[[[\"sense\",{\"dt\":[[\"text\",\"{bc}destructive to {a_link|parasites}\"]]}]]]}],\"uros\":[{\"ure\":\"par*a*sit*i*cide\",\"prs\":[{\"mw\":\"\\u02ccper-\\u0259-\\u02c8si-t\\u0259-\\u02ccs\\u012bd\",\"sound\":{\"audio\":\"parasi06\",\"ref\":\"c\",\"stat\":\"1\"}},{\"mw\":\"\\u02ccpa-r\\u0259-\"}],\"fl\":\"noun\"}],\"date\":\"1892\",\"shortdef\":[\"destructive to parasites\"]}]";
        String jsonStrFail3 = "[{\"meta\":{\"id\":\"axletree\",\"uuid\":\"8a35ae9d-a691-490c-a862-61236ea30c44\",\"sort\":\"015074000\",\"src\":\"collegiate\",\"section\":\"alpha\",\"stems\":[\"axletree\",\"axletrees\"],\"offensive\":false},\"hwi\":{\"hw\":\"ax*le*tree\",\"prs\":[{\"mw\":\"\\u02c8ak-s\\u0259l-(\\u02cc)tr\\u0113\",\"sound\":{\"audio\":\"axletr01\",\"ref\":\"c\",\"stat\":\"1\"}}]},\"fl\":\"noun\",\"def\":[{\"sseq\":[[[\"sense\",{\"dt\":[[\"text\",\"{bc}{sx|axle||1b(1)}\"]]}]]]}],\"et\":[[\"text\",\"Middle English {it}axeltre{\\/it}, from Old Norse {it}\\u01ebxultr\\u0113{\\/it}, from {it}\\u01ebxull{\\/it} axle + {it}tr\\u0113{\\/it} tree\"]],\"date\":\"14th century\",\"shortdef\":[\"axle\"]}]";
        String jsonStrPass1 = "[{\"meta\":{\"id\":\"tedious\",\"uuid\":\"88192c49-7c3e-4013-8ceb-92dd0b8c8cc5\",\"sort\":\"200076700\",\"src\":\"collegiate\",\"section\":\"alpha\",\"stems\":[\"tedious\",\"tediously\",\"tediousness\",\"tediousnesses\"],\"offensive\":false},\"hwi\":{\"hw\":\"te*dious\",\"prs\":[{\"mw\":\"\\u02c8t\\u0113-d\\u0113-\\u0259s\",\"sound\":{\"audio\":\"tediou01\",\"ref\":\"c\",\"stat\":\"1\"}},{\"mw\":\"\\u02c8t\\u0113-j\\u0259s\"}]},\"fl\":\"adjective\",\"def\":[{\"sseq\":[[[\"sense\",{\"dt\":[[\"text\",\"{bc}{d_link|tiresome|tiresome} because of length or dullness {bc}{sx|boring||} \"],[\"vis\",[{\"t\":\"a {wi}tedious{\\/wi} public ceremony\"}]]]}]]]}],\"uros\":[{\"ure\":\"te*dious*ly\",\"fl\":\"adverb\"},{\"ure\":\"te*dious*ness\",\"fl\":\"noun\"}],\"quotes\":[{\"t\":\"Another of their assignments was to slow-fly any plane that had a new engine to break it in; that meant flying the aircraft for a {qword}tedious{\\/qword} hour-and-a-half as slowly as it would possibly go without falling out of the sky.\",\"aq\":{\"auth\":\"Doris Weatherford\",\"source\":\"{it}American Women and World War II{\\/it}\",\"aqdate\":\"1990\"}},{\"t\":\"Writing a new spreadsheet or word-processing program these days is a {qword}tedious{\\/qword} process, like building a skyscraper out of toothpicks.\",\"aq\":{\"auth\":\"Jeff Goodell\",\"source\":\"{it}Rolling Stone{\\/it}\",\"aqdate\":\"16 June 1994\"}},{\"t\":\"From there, it became clear that the deposition was going to be neither as undramatic nor as quotidian, and even {qword}tedious{\\/qword}, as it at first appeared.\",\"aq\":{\"auth\":\"Renata Adler\",\"source\":\"{it}New Yorker{\\/it}\",\"aqdate\":\"June 23, 1986\"}}],\"et\":[[\"text\",\"Middle English, from Late Latin {it}taediosus{\\/it}, from Latin {it}taedium{\\/it} {dx_ety}see {dxt|tedium||}{\\/dx_ety}\"]],\"date\":\"15th century\",\"shortdef\":[\"tiresome because of length or dullness : boring\"]}]";
        String jsonStrPass2 = "[{\"meta\":{\"id\":\"battlefront\",\"uuid\":\"7b2cfdba-b430-4c32-9f13-cb216946a0bd\",\"sort\":\"020100800\",\"src\":\"collegiate\",\"section\":\"alpha\",\"stems\":[\"battlefront\",\"battlefronts\"],\"offensive\":false},\"hwi\":{\"hw\":\"bat*tle*front\",\"prs\":[{\"mw\":\"\\u02c8ba-t\\u1d4al-\\u02ccfr\\u0259nt\",\"sound\":{\"audio\":\"battle06\",\"ref\":\"c\",\"stat\":\"1\"}}]},\"fl\":\"noun\",\"def\":[{\"sseq\":[[[\"sense\",{\"sn\":\"1\",\"dt\":[[\"text\",\"{bc}the military sector in which actual combat takes place\"]]}]],[[\"sense\",{\"sn\":\"2\",\"dt\":[[\"text\",\"{bc}a place or context within which a struggle is fought \"],[\"vis\",[{\"t\":\"\\u2026 he had already served more than a decade as an attorney on the {wi}battlefronts{\\/wi} of the civil rights movement.\",\"aq\":{\"auth\":\"Vincent Harding\"}}]]]}]]]}],\"date\":\"1813{ds||1||}\",\"shortdef\":[\"the military sector in which actual combat takes place\",\"a place or context within which a struggle is fought\"]}]";
        String wordFail1 = "lsdf";
        String wordFail2 = "parasiticidal";
        String wordFail3 = "axletrees";
        String wordPass1 = "tedious";
        String wordPass2 = "battlefronts";
    }

    /**
     * Test of isGuessCorrect method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testIsGuessCorrect() {
    }

    /**
     * Test of addGuess method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testAddGuess() {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aaw.spellingbee.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Austin Wong
 */

@SpringBootTest
public class SpellingBeeServiceImplTest {
    
    @Autowired
    SpellingBeeService service;
    
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getNumQuizWords method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetNumQuizWords() {
    }

    /**
     * Test of getQuiz method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetQuiz() {
    }

    /**
     * Test of getAllQuizzes method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetAllQuizzes() {
    }

    /**
     * Test of addQuiz method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testAddQuiz() {
    }

    /**
     * Test of getAllAttempts method, of class SpellingBeeServiceImpl.
     */
    @Test
    public void testGetAllAttempts() {
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
        
//        // Pass
//        DictionaryEntry passEntry1 = service.getDictionaryEntry(wordPass1, jsonStrPass1);
//        assertNotNull(passEntry1);
//        
//        // Pass
//        DictionaryEntry passEntry2 = service.getDictionaryEntry(wordPass2, jsonStrPass2);
//        assertNotNull(passEntry2);
//        
//        // Fail
//        DictionaryEntry failEntry3 = service.getDictionaryEntry(wordFail3, jsonStrFail3);
//        assertNull(failEntry3);
//        
//        // Fail
//        DictionaryEntry failEntry2 = service.getDictionaryEntry(wordFail2, jsonStrFail2);
//        assertNull(failEntry2);
//        
//        // Fail
//        DictionaryEntry failEntry1 = service.getDictionaryEntry(wordFail1, jsonStrFail1);
//        assertNull(failEntry1);
        
        
    }
    
}

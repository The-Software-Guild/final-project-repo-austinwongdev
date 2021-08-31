/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.service;

import com.aaw.spellingbee.dao.AttemptDao;
import com.aaw.spellingbee.dao.GuessDao;
import com.aaw.spellingbee.dao.QuizDao;
import com.aaw.spellingbee.dao.WordDao;
import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.DictionaryEntry;
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Austin Wong
 */

@Component
public class SpellingBeeServiceImpl implements SpellingBeeService {
    
    private final AttemptDao attemptDao;
    private final GuessDao guessDao;
    private final QuizDao quizDao;
    private final WordDao wordDao;
    
    private final String apiKey = "49b65708-71c6-4de2-a46e-af5b83260027";
    private final int numQuizWords = 5;

    @Autowired
    public SpellingBeeServiceImpl(AttemptDao attemptDao, GuessDao guessDao, QuizDao quizDao, WordDao wordDao) {
        this.attemptDao = attemptDao;
        this.guessDao = guessDao;
        this.quizDao = quizDao;
        this.wordDao = wordDao;
    }
    
    @Override
    public int getNumQuizWords(){
        return numQuizWords;
    }

    @Override
    public Quiz getQuiz(int quizId) {
        return quizDao.getQuizByQuizId(quizId);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizDao.addQuiz(quiz);
    }

    @Override
    public List<Attempt> getAllAttempts() {
        List<Attempt> attempts = attemptDao.getAllAttempts();
        for (Attempt attempt : attempts){
            calculateAndSetPercentScore(attempt);
            getCorrectSpellingForGuesses(attempt);
        }
        return attempts;
    }

    @Override
    public Attempt getAttempt(int attemptId) {
        Attempt attempt = attemptDao.getAttemptByAttemptId(attemptId);
        calculateAndSetPercentScore(attempt);
        getCorrectSpellingForGuesses(attempt);
        return attempt;
    }

    @Override
    public List<Attempt> getAttemptsForQuiz(int quizId) {
        List<Attempt> attempts = attemptDao.getAttemptsForQuizId(quizId);
        for (Attempt attempt : attempts){
            calculateAndSetPercentScore(attempt);
            getCorrectSpellingForGuesses(attempt);
        }
        return attempts;
    }
    
    @Override
    public List<Guess> getGuessesForAttempt(int attemptId){
        return guessDao.getGuessesForAttemptId(attemptId);
    }
    
    @Override
    public void deleteAttempt(int attemptId){
        attemptDao.deleteAttempt(attemptId);
    }

    @Override
    public List<Word> getWordsForQuiz(int quizId) {
        return wordDao.getWordsForQuizId(quizId);
    }
    
    /**
     * Calculates percent of correct guesses for a given attempt
     * @param attempt - Attempt object
     */
    private void calculateAndSetPercentScore(Attempt attempt){
        int numCorrect = 0;
        int numIncorrect = 0;
        for (Guess guess : attempt.getGuesses()){
            if (guess.isIsCorrect()){
                numCorrect += 1;
            }
            else{
                numIncorrect += 1;
            }
        }
        
        float percentScore = 100*((float) numCorrect / (numCorrect + numIncorrect));
        attempt.setPercentScore(percentScore);
        attempt.setNumCorrect(numCorrect);
        attempt.setNumIncorrect(numIncorrect);
    }
    
    /**
     * Helper function that gets the correct spelling for each word guessed and
     * sets that in the corresponding Guess objects for the given attempt
     * @param attempt - Attempt object (represents a quiz result)
     */
    private void getCorrectSpellingForGuesses(Attempt attempt){
        for (Guess guess : attempt.getGuesses()){
            Word word = wordDao.getWordByWordId(guess.getWordId());
            String correctSpelling = word.getHeadword();
            guess.setCorrectSpelling(correctSpelling);
        }
    }
    
    /**
     * Uses Merriam-Webster's dictionary api and a random word api to generate
     * a spelling quiz.
     * @return - List (of size numWords) of DictionaryEntry objects
     */
    public List<DictionaryEntry> generateQuiz(){
        List<DictionaryEntry> entries = new ArrayList<>();
        Set<String> quizWords = new HashSet<>();
        
        List<String> initialWords = generateRandomWordsForQuiz(numQuizWords);
        for (String wordStr : initialWords){
            // Prevent duplicates
            if (!quizWords.contains(wordStr)){
                DictionaryEntry entry = getDictionaryEntry(wordStr);
                // Prevent words without enough data
                if (entry != null){ // && isDictionaryEntryValid(entry, entry.getHeadword())){
                    entries.add(entry);
                    quizWords.add(wordStr);
                }
            }
        }
        
        // Look up more words if not all words in our initial list were valid
        while (entries.size() < numQuizWords){
            List<String> extraWords = generateRandomWordsForQuiz(numQuizWords);
            for (String wordStr : extraWords){
                if (!quizWords.contains(wordStr)){
                    DictionaryEntry entry = getDictionaryEntry(wordStr);
                    if (entry != null){ // && isDictionaryEntryValid(entry, entry.getHeadword())){
                        entries.add(entry);
                        quizWords.add(wordStr);
                        // Break early if we get enough total words
                        if (entries.size() >= numQuizWords){
                            break;
                        }
                    }
                }
            }
        }
        
        return entries;
    }
    
    /**
     * Checks a DictionaryEntry object to see if the given word matches the entry's
     * headword or its variants, if the entry has a definition and example usage,
     * if the entry has a pronunciation audio file, and if the entry is not offensive.
     * @param entry - DictionaryEntry object
     * @param wordStr - String of word to check
     * @return - true if the entry is valid, false otherwise
     */
    private boolean isDictionaryEntryValid(DictionaryEntry entry, String wordStr){
        if (entry.getDefinition() == null || entry.getDefinition().isEmpty()){
            return false;
        }
        if (entry.getExampleUsage() == null || entry.getExampleUsage().isEmpty()){
            return false;
        }
        if (entry.getPronunciationURLs().isEmpty()){
            return false;
        }
        if (!entry.getHeadword().equals(wordStr) && !entry.getVariants().contains(wordStr)){
            return false;
        }
        if (entry.isOffensive()){
            return false;
        }
        
        return true;
    }
    
    /**
     * Gets random words from a 3rd party random word API
     * @param numWords - Integer representing number of words to get from API
     * @return - A List of words as Strings
     */
    private List<String> generateRandomWordsForQuiz(int numWords){
        
        final String RANDOM_WORD_API_GET_WORD_REQUEST = "https://random-word-api.herokuapp.com/word?number=" + numWords;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(RANDOM_WORD_API_GET_WORD_REQUEST, String[].class);
        String[] wordsArr = responseEntity.getBody();
        List<String> words = new ArrayList<>(Arrays.asList(wordsArr));
        
        return words;
    }
    
    private DictionaryEntry getDictionaryEntry(String wordStr){
        
        DictionaryEntry entry = new DictionaryEntry();
        
        // Construct URL for HTML GET call to Dictionary API
        final String GET_DICTIONARY_DATA_FOR_WORD = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"
                + wordStr
                + "?key="
                + apiKey;
        
        // Get JSON response from Dictionary API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(GET_DICTIONARY_DATA_FOR_WORD, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            return null;
        }
        String jsonStr = response.getBody();
        JSONArray json = new JSONArray(jsonStr);
        
        try{
            // Parse JSON response
            for (int i=0; i<json.length(); i++){

                JSONObject jsonEntry = json.getJSONObject(i);
                JSONObject metadata = jsonEntry.getJSONObject("meta");
                JSONObject headwordInfo = jsonEntry.getJSONObject("hwi");

                // Search for metadata
                entry.setId(metadata.getString("id"));
                entry.setHeadword(headwordInfo.getString("hw").replace("*", ""));
                entry.setOffensive(metadata.getBoolean("offensive"));
                
                if (!entry.getHeadword().equals(wordStr) && metadata.has("stems")){
                    List<Object> stems = metadata.getJSONArray("stems").toList();
                    if (!stems.contains(wordStr)){
                        return null;
                    }
                }

                // Search for pronunciations
                if (!headwordInfo.has("prs")){
                    continue;
                }
                JSONArray pronunciations = headwordInfo.getJSONArray("prs");
                addPronunciationsToEntry(entry, pronunciations);
                if (entry.getPronunciationURLs().isEmpty()){
                    continue;
                }

                // Search for variants
                if (jsonEntry.has("vrs")){
                    JSONArray variantsArr = jsonEntry.getJSONArray("vrs");
                    addVariantsToEntry(entry, variantsArr);
                }
                else{
                    entry.setVariants(new ArrayList<>());
                }

                // Search for definition and example usage
                JSONArray senseSequences = jsonEntry.getJSONArray("def").getJSONObject(0).getJSONArray("sseq");
                for (int j=0; j< senseSequences.length(); j++){
                    JSONArray senseSequence = senseSequences.getJSONArray(j);
                    if (senseSequenceHasDefinitionAndExampleUsage(entry, senseSequence)){
                        return entry;
                    }
                }
            }
        } catch(JSONException ex){
            return null;
        }
        
        return entry;
    }
    
    /**
     * Helper function to parse senseSequence ("sseq") and determine if a sense
     * exists in the senseSequence that contains both a definition ("text" in "dt")
     * and an example usage (AKA verbal illustration - "vis" in "dt"). Also sets
     * definition and example usage in the DictionaryEntry object only if both are found.
     * @param entry - DictionaryEntry object used to hold definition and example usage
     * @param senseSequence - JSONArray object for senseSequence
     * @return - true if senseSequence contains at least one sense with both a 
     * definition and verbal illustration, false otherwise
     */
    private boolean senseSequenceHasDefinitionAndExampleUsage(DictionaryEntry entry, JSONArray senseSequence){
        for (int k=0; k <senseSequence.length(); k++){
            JSONObject sense = senseSequence.getJSONArray(k).getJSONObject(1);
            JSONArray definingText = sense.getJSONArray("dt");
            if (definingText.length() < 2){
                    continue;
            }
            for (int l=0; l < definingText.length(); l++){
                JSONArray definingTextEntry = definingText.getJSONArray(l);
                if (definingTextEntry.getString(0).equals("text")){
                    entry.setDefinition(definingTextEntry.getString(1));
                }
                else if (definingTextEntry.getString(0).equals("vis")){
                    entry.setExampleUsage(definingTextEntry.getString(1));
                }
            }
            // Found both a sense with both definition and example usage, so break early
            if (entry.getDefinition() != null && entry.getExampleUsage() != null){
                return true;
            }
            // Otherwise, reset fields and keep searching
            else{
                entry.setDefinition(null);
                entry.setExampleUsage(null);
            }
        }
        return false;
    }
    
    /**
     * Helper function that parses JSONArray of pronunciations ("prs"), producing a List
     * of Strings representing URLs to a word's pronunciations, then adds that list
     * to the provided DictionaryEntry
     * @param entry - DictionaryEntry object
     * @param pronunciations - JSONArray of pronunciation data
     * @return - true if contains URLs were added, false otherwise
     */
    private boolean addPronunciationsToEntry(DictionaryEntry entry, JSONArray pronunciations){
        List<String> pronunciationURLs = new ArrayList<>();
        entry.setPronunciationURLs(pronunciationURLs);
        for (int j=0; j<pronunciations.length(); j++){
            if (!pronunciations.getJSONObject(j).has("sound")){
                return false;
            }
            String baseFilename = pronunciations.getJSONObject(j).getJSONObject("sound").getString("audio");
            String subdirectory;
            if (baseFilename.startsWith("bix")){
                subdirectory = "bix";
            }
            else if (baseFilename.startsWith("gg")){
                subdirectory = "gg";
            }
            else if (baseFilename.substring(0, 1).matches("^[0-9[:punct:]]{1}$")){
                subdirectory = "number";
            }
            else{
                subdirectory = baseFilename.substring(0, 1);
            }

            String fileType = "mp3";
            String pronunciationURL = "https://media.merriam-webster.com/audio/prons/en/us/"
                    + fileType
                    + "/"
                    + subdirectory
                    + "/wonder03"
                    + "."
                    + fileType;
            pronunciationURLs.add(pronunciationURL);
        }
        entry.setPronunciationURLs(pronunciationURLs);
        return true;
    }
    
    /**
     * Helper function that parses the variants ("vrs") array, adding any found
     * variants to the given DictionaryEntry as a List of Strings
     * @param entry - DictionaryEntry object
     * @param variantsArr - JSONArray for the "vrs" field from Merriam-Webster's
     * Collegiate Dictionary with Audio API
     */
    private void addVariantsToEntry(DictionaryEntry entry, JSONArray variantsArr){
        List<String> variants = new ArrayList<>();
        for (int j=0; j<variantsArr.length(); j++){
            JSONObject variantEntry = variantsArr.getJSONObject(j);
            String variant = variantEntry.getString("va").replace("*", "");
            variants.add(variant);
        }
        entry.setVariants(variants);
    }
    
}

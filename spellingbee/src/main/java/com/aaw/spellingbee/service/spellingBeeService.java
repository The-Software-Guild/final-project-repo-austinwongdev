/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.service;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.DictionaryEntry;
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface SpellingBeeService {
    
    int getNumQuizWords();
    Quiz getQuiz(int quizId);
    List<Quiz> getAllQuizzes();
    Quiz addQuiz(Quiz quiz);
    List<Attempt> getAllAttempts();
    Attempt getAttempt(int attemptId);
    List<Attempt> getAttemptsForQuiz(int quizId);
    List<Guess> getGuessesForAttempt(int attemptId);
    void deleteAttempt(int attemptId);
    List<Word> getWordsForQuiz(int quizId);
    
    List<DictionaryEntry> generateQuiz();
    
}

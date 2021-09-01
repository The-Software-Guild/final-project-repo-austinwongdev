/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.service;

import com.aaw.spellingbee.model.Attempt;
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
    
    Attempt createAttempt(int quizId);
    Attempt getAttempt(int attemptId);
    void deleteAttempt(int attemptId);
    List<Attempt> getAllPartialOrCompleteAttempts();
    List<Attempt> getAttemptsForQuiz(int quizId);
    
    List<Guess> getGuessesForAttempt(int attemptId);
    boolean isGuessCorrect(Guess guess);
    Guess addGuess(Guess guess);
    
    List<Word> getWordsForQuiz(int quizId);
    String hideWord(String phrase, String word);
    
    Quiz generateQuiz();
    
    Word createWordFromJSON(String wordStr);
    
}

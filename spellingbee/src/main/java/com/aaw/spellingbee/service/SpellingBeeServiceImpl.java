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
import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    public SpellingBeeServiceImpl(AttemptDao attemptDao, GuessDao guessDao, QuizDao quizDao, WordDao wordDao) {
        this.attemptDao = attemptDao;
        this.guessDao = guessDao;
        this.quizDao = quizDao;
        this.wordDao = wordDao;
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
        }
        return attempts;
    }

    @Override
    public Attempt getAttempt(int attemptId) {
        Attempt attempt = attemptDao.getAttemptByAttemptId(attemptId);
        calculateAndSetPercentScore(attempt);
        return attempt;
    }

    @Override
    public List<Attempt> getAttemptsForQuiz(int quizId) {
        List<Attempt> attempts = attemptDao.getAttemptsForQuizId(quizId);
        for (Attempt attempt : attempts){
            calculateAndSetPercentScore(attempt);
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
        
        float percentScore = ((float) numCorrect / (numCorrect + numIncorrect));
        attempt.setPercentScore(percentScore);
    }
    
}

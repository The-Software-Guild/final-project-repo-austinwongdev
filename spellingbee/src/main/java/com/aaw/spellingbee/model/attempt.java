/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class Attempt {

    private int attemptId;
    private LocalDate attemptDate;
    private int quizId;
    private List<Guess> guesses = new ArrayList<>();
    private float percentScore;
    private int numCorrect;
    private int numIncorrect;

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getNumIncorrect() {
        return numIncorrect;
    }

    public void setNumIncorrect(int numIncorrect) {
        this.numIncorrect = numIncorrect;
    }

    public float getPercentScore() {
        return percentScore;
    }

    public void setPercentScore(float percentScore) {
        this.percentScore = percentScore;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public LocalDate getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(LocalDate attemptDate) {
        this.attemptDate = attemptDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<Guess> guesses) {
        this.guesses = guesses;
    }
    
    public void addGuess(Guess guess){
        guesses.add(guess);
    }
    
    public List<String> getGuessedWordsIds(){
        List<String> guessedWordsIds = new ArrayList<>();
        for (Guess guess : guesses){
            guessedWordsIds.add(guess.getWordId());
        }
        return guessedWordsIds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.attemptId;
        hash = 97 * hash + Objects.hashCode(this.attemptDate);
        hash = 97 * hash + this.quizId;
        hash = 97 * hash + Objects.hashCode(this.guesses);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attempt other = (Attempt) obj;
        if (this.attemptId != other.attemptId) {
            return false;
        }
        if (this.quizId != other.quizId) {
            return false;
        }
        if (!Objects.equals(this.attemptDate, other.attemptDate)) {
            return false;
        }
        if (!Objects.equals(this.guesses, other.guesses)) {
            return false;
        }
        return true;
    }
    
}

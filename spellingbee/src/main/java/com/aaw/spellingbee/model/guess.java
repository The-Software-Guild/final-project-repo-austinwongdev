/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class Guess {

    private int guessId;
    private String guess;
    private int attemptId;
    private String wordId;
    private boolean isCorrect;
    private String correctSpelling;

    public int getGuessId() {
        return guessId;
    }

    public void setGuessId(int guessId) {
        this.guessId = guessId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
    
    public String getCorrectSpelling() {
        return correctSpelling;
    }

    public void setCorrectSpelling(String correctSpelling) {
        this.correctSpelling = correctSpelling;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.guessId;
        hash = 97 * hash + Objects.hashCode(this.guess);
        hash = 97 * hash + this.attemptId;
        hash = 97 * hash + Objects.hashCode(this.wordId);
        hash = 97 * hash + (this.isCorrect ? 1 : 0);
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
        final Guess other = (Guess) obj;
        if (this.guessId != other.guessId) {
            return false;
        }
        if (this.attemptId != other.attemptId) {
            return false;
        }
        if (this.isCorrect != other.isCorrect) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.wordId, other.wordId)) {
            return false;
        }
        return true;
    }

    
    
}

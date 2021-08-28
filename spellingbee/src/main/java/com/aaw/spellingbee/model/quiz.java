/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class Quiz {

    private int quizId;
    private List<Attempt> attempts;
    private List<Word> words;

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.quizId;
        hash = 79 * hash + Objects.hashCode(this.attempts);
        hash = 79 * hash + Objects.hashCode(this.words);
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
        final Quiz other = (Quiz) obj;
        if (this.quizId != other.quizId) {
            return false;
        }
        if (!Objects.equals(this.attempts, other.attempts)) {
            return false;
        }
        if (!Objects.equals(this.words, other.words)) {
            return false;
        }
        return true;
    }

        
    
}

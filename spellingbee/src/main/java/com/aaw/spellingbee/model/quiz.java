/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Austin Wong
 */
public class Quiz {

    private int quizId;
    private List<Attempt> attempts = new ArrayList<>();
    private List<Word> words = new ArrayList<>();

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
    
    public void addAttempt(Attempt attempt){
        attempts.add(attempt);
    }
    
    public void addWord(Word word){
        words.add(word);
    }
    
    public Attempt getMostRecentAttempt(){
        Comparator<Attempt> compareById = 
                (Attempt a1, Attempt a2) -> Integer.compare(a1.getAttemptId(), a2.getAttemptId());
        Collections.sort(attempts, compareById.reversed());
        return attempts.get(0);
    }
    
    public Word getNextWord(){
        Attempt attempt = getMostRecentAttempt();
        List<String> guessedWordIds = attempt.getGuessedWordsIds();
        List<String> allWordIds = getWords().stream()
                .map(w -> w.getWordId()).collect(Collectors.toList());
        allWordIds.removeAll(guessedWordIds);
        if (allWordIds.size() > 0){
            Word nextWord = getWord(allWordIds.get(0));
            return nextWord;
        }
        return null;
    }
    
    public int getNextWordNumber(){
        Attempt attempt = getMostRecentAttempt();
        return attempt.getGuesses().size() + 1;
    }
    
    public Word getWord(String wordId){
        for (int i=0; i < words.size(); i++){
            Word word = words.get(i);
            if (word.getWordId().equals(wordId)){
                return word;
            }
        }
        return null;
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

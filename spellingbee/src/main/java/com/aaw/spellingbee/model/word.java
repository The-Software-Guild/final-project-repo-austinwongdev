/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class Word {

    private String wordId;
    private String headword;
    private List<WordVariant> wordVariants = new ArrayList<>();
    private boolean offensive;
    private List<String> pronunciationURLs = new ArrayList<>();
    private String definition;
    private String exampleUsage;

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getHeadword() {
        return headword;
    }

    public void setHeadword(String headword) {
        this.headword = headword;
    }

    public List<WordVariant> getWordVariants() {
        return wordVariants;
    }

    public void setWordVariants(List<WordVariant> wordVariants) {
        this.wordVariants = wordVariants;
    }

    public boolean isOffensive() {
        return offensive;
    }

    public void setOffensive(boolean offensive) {
        this.offensive = offensive;
    }

    public List<String> getPronunciationURLs() {
        return pronunciationURLs;
    }

    public void setPronunciationURLs(List<String> pronunciationURLs) {
        this.pronunciationURLs = pronunciationURLs;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExampleUsage() {
        return exampleUsage;
    }

    public void setExampleUsage(String exampleUsage) {
        this.exampleUsage = exampleUsage;
    }

    public void addWordVariant(WordVariant wordVariant){
        wordVariants.add(wordVariant);
    }
    
    public void addPronunciationURL(String pronunciationURL){
        pronunciationURLs.add(pronunciationURL);
    }
    
    public String getFirstPronunciationURL(){
        if (pronunciationURLs.isEmpty()){
            return null;
        }
        return pronunciationURLs.get(0);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.wordId);
        hash = 79 * hash + Objects.hashCode(this.headword);
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
        final Word other = (Word) obj;
        if (!Objects.equals(this.wordId, other.wordId)) {
            return false;
        }
        if (!Objects.equals(this.headword, other.headword)) {
            return false;
        }
        return true;
    }
    
    
    
}

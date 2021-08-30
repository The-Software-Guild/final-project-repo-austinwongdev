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
public class Word {

    private String wordId;
    private String headword;
    private List<WordVariant> wordVariants;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.wordId);
        hash = 67 * hash + Objects.hashCode(this.headword);
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

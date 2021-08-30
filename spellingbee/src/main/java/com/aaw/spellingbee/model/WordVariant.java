/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.util.Objects;

/**
 *
 * @author Austin Wong
 */
public class WordVariant {

    private int wordVariantId;
    private String wordId;
    private String wordVariant;

    public int getWordVariantId() {
        return wordVariantId;
    }

    public void setWordVariantId(int wordVariantId) {
        this.wordVariantId = wordVariantId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWordVariant() {
        return wordVariant;
    }

    public void setWordVariant(String wordVariant) {
        this.wordVariant = wordVariant;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.wordVariantId;
        hash = 89 * hash + Objects.hashCode(this.wordId);
        hash = 89 * hash + Objects.hashCode(this.wordVariant);
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
        final WordVariant other = (WordVariant) obj;
        if (this.wordVariantId != other.wordVariantId) {
            return false;
        }
        if (!Objects.equals(this.wordId, other.wordId)) {
            return false;
        }
        if (!Objects.equals(this.wordVariant, other.wordVariant)) {
            return false;
        }
        return true;
    }
    
    
    
}

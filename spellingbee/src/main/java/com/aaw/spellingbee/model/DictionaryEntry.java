/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.model;

import java.util.List;

/**
 *
 * @author Austin Wong
 */
public class DictionaryEntry {

    private String headword;
    private String id;
    private boolean offensive;
    private List<String> pronunciationURLs;
    private List<String> variants;
    private String definition;
    private String exampleUsage;

    public String getHeadword() {
        return headword;
    }

    public void setHeadword(String headword) {
        this.headword = headword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
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
    
}

/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.WordVariant;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface WordVariantDao {

    WordVariant getWordVariantByWordVariantId(int wordVariantId);
    List<WordVariant> getWordVariantsForWordId(String wordId);
    List<WordVariant> getAllWordVariants();
    WordVariant addWordVariant(WordVariant wordVariant);
    void deleteWordVariant(int wordVariantId);
    
}

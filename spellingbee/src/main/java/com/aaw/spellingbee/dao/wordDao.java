/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Word;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface WordDao {

    Word getWord(String wordId);
    List<Word> getWordsForQuizId(int quizId);
    List<Word> getAllWords();
    Word addWord(Word word);
    void deleteWord(String wordId);
    
}

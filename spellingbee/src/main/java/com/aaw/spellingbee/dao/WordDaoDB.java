/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Word;
import com.aaw.spellingbee.model.WordVariant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Austin Wong
 */

@Repository
public class WordDaoDB implements WordDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    WordVariantDao wordVariantDao;
    
    @Override
    public Word getWordByWordId(String wordId) {
        try{
            final String SELECT_WORD_BY_WORD_ID = "SELECT * FROM word "
                + "WHERE wordId = ?";
            Word word = jdbc.queryForObject(SELECT_WORD_BY_WORD_ID,
                    new WordMapper(),
                    wordId);
            
            // Set variants
            setVariantsForWord(word);
            
            return word;
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Word> getWordsForQuizId(int quizId) {
        final String SELECT_WORDS_FOR_QUIZ_ID = "SELECT w.* FROM word w " +
            "INNER JOIN quizword qw ON w.wordId = qw.wordId " +
            "WHERE qw.quizId = ? "
                + "ORDER BY questionNumber";
        List<Word> words = jdbc.query(SELECT_WORDS_FOR_QUIZ_ID, new WordMapper(), quizId);
        
        // Set variants
        for (Word word : words){
            setVariantsForWord(word);
        }
        
        return words;
    }

    @Override
    @Transactional
    public Word addWord(Word word) {
        final String INSERT_WORD = "INSERT INTO word "
                + "(wordId, headword) "
                + "VALUES (?, ?)";
        
        try{
            jdbc.update(INSERT_WORD,
                    word.getWordId(),
                    word.getHeadword());

            // Add word variants
            for (WordVariant wordVariant : word.getWordVariants()){
                wordVariantDao.addWordVariant(wordVariant);
            }
        } catch(DuplicateKeyException ex){
            word = getWordByWordId(word.getWordId());
        }
        
        return word;
    }

    @Override
    @Transactional
    public void deleteWord(String wordId) {
        
        final String DELETE_VARIANTS_FOR_WORD_ID = "DELETE FROM wordvariant WHERE wordId = ?";
        jdbc.update(DELETE_VARIANTS_FOR_WORD_ID, wordId);
        
        final String DELETE_GUESSES_FOR_WORD_ID = "DELETE g FROM guess g " +
            "INNER JOIN word w ON w.wordId = g.wordId " +
            "WHERE w.wordId = ?";
        jdbc.update(DELETE_GUESSES_FOR_WORD_ID, wordId);
        
        final String DELETE_QUIZWORD_FOR_WORD_ID = "DELETE qw FROM quizword qw " +
            "INNER JOIN word w ON w.wordId = qw.wordId " +
            "WHERE w.wordId = ?";
        jdbc.update(DELETE_QUIZWORD_FOR_WORD_ID, wordId);
        
        final String DELETE_WORD = "DELETE FROM word WHERE wordId = ?";
        jdbc.update(DELETE_WORD, wordId);
    }

    @Override
    public List<Word> getAllWords() {
        final String SELECT_ALL_WORDS = "SELECT * FROM word ORDER BY wordId";
        List<Word> words = jdbc.query(SELECT_ALL_WORDS, new WordMapper());
        
        // Set variants
        for (Word word: words){
            setVariantsForWord(word);
        }
        
        return words;
    }
    
    /**
     * Helper function that gets a list of word variants for a given word and
     * sets the field in the Word object with that list result
     * @param word - Word object
     */
    private void setVariantsForWord(Word word){
        List<WordVariant> wordVariants = wordVariantDao.getWordVariantsForWordId(word.getWordId());
        word.setWordVariants(wordVariants);
    }

    public static final class WordMapper implements RowMapper<Word> {

        @Override
        public Word mapRow(ResultSet rs, int index) throws SQLException {
            Word word = new Word();
            
            word.setHeadword(rs.getString("headword"));
            word.setWordId(rs.getString("wordId"));
            
            return word;
        }
    }
}

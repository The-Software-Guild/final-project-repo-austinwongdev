/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Word;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
    public Word getWord(String wordId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Word> getWordsForQuizId(int quizId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Word addWord(Word word) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteWord(String wordId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Word> getAllWords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.WordVariant;
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
public class WordVariantDaoDB implements WordVariantDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public WordVariant getWordVariant(int wordVariantId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WordVariant> getWordVariantsForWordId(String wordId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WordVariant addWordVariant(WordVariant wordVariant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteWordVariant(int wordVariantId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WordVariant> getAllWordVariants() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static final class WordVariantMapper implements RowMapper<WordVariant> {

        @Override
        public WordVariant mapRow(ResultSet rs, int index) throws SQLException {
            WordVariant wordVariant = new WordVariant();
            
            wordVariant.setWordId(rs.getString("wordId"));
            wordVariant.setWordVariant(rs.getString("wordVariant"));
            wordVariant.setWordVariantId(rs.getInt("wordVariantId"));
            
            return wordVariant;
        }
    }
    
}

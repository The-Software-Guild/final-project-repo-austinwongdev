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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        try{
            final String SELECT_WORD_VARIANT_BY_WORD_VARIANT_ID = "SELECT * FROM wordvariant "
                + "WHERE wordVariantId = ?";
            return jdbc.queryForObject(SELECT_WORD_VARIANT_BY_WORD_VARIANT_ID,
                    new WordVariantMapper(),
                    wordVariantId);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<WordVariant> getWordVariantsForWordId(String wordId) {
        final String SELECT_WORD_VARIANTS_FOR_WORD_ID = "SELECT *FROM wordvariant "
                + "WHERE wordId = ?";
        return jdbc.query(SELECT_WORD_VARIANTS_FOR_WORD_ID, new WordVariantMapper(), wordId);
    }

    @Override
    @Transactional
    public WordVariant addWordVariant(WordVariant wordVariant) {
        final String INSERT_WORD_VARIANT = "INSERT INTO wordvariant "
                + "(wordId, wordvariant) "
                + "VALUES (?, ?)";
        jdbc.update(INSERT_WORD_VARIANT,
                wordVariant.getWordId(),
                wordVariant.getWordVariant());
        int wordVariantId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        wordVariant.setWordVariantId(wordVariantId);
        return wordVariant;
    }

    @Override
    public void deleteWordVariant(int wordVariantId) {
        final String DELETE_WORD_VARIANT = "DELETE FROM wordvariant WHERE wordVariantId = ?";
        jdbc.update(DELETE_WORD_VARIANT, wordVariantId);
    }

    @Override
    public List<WordVariant> getAllWordVariants() {
        final String SELECT_ALL_WORD_VARIANTS = "SELECT * FROM wordvariant";
        List<WordVariant> wordVariants = jdbc.query(SELECT_ALL_WORD_VARIANTS, new WordVariantMapper());
        return wordVariants;
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

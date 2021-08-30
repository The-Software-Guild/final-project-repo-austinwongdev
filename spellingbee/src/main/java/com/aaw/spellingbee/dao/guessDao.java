/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Guess;
import com.aaw.spellingbee.model.WordVariant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Austin Wong
 */
public interface GuessDao {

    List<Guess> getAllGuesses();
    void deleteGuess(int guessId);
    
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

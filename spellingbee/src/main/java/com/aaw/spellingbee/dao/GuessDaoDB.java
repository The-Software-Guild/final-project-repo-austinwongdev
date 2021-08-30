/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Guess;
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
public class GuessDaoDB implements GuessDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Guess> getGuessesForAttemptId(int attemptId) {
        final String SELECT_GUESSES_FOR_ATTEMPT = "SELECT * FROM guess "
                + "WHERE attemptId = ? ORDER BY guessId";
        List<Guess> guesses = jdbc.query(SELECT_GUESSES_FOR_ATTEMPT, new GuessMapper(), attemptId);
        return guesses;
    }
    
    @Override
    public List<Guess> getAllGuesses() {
        final String SELECT_ALL_GUESSES = "SELECT * FROM guess ORDER BY guessId";
        List<Guess> guesses = jdbc.query(SELECT_ALL_GUESSES, new GuessMapper());
        return guesses;
    }

    @Override
    public void deleteGuess(int guessId) {
        final String DELETE_GUESS = "DELETE FROM guess WHERE guessId = ?";
        jdbc.update(DELETE_GUESS, guessId);
    }

    @Override
    @Transactional
    public Guess addGuess(Guess guess) {
        final String INSERT_GUESS = "INSERT INTO guess "
                + "(guess, attemptId, wordId, isCorrect) "
                + "VALUES (?, ?, ?, ?)";
        jdbc.update(INSERT_GUESS,
                guess.getGuess(),
                guess.getAttemptId(),
                guess.getWordId(),
                guess.isIsCorrect());
        int guessId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        guess.setGuessId(guessId);
        return guess;
    }

    @Override
    public Guess getGuessByGuessId(int guessId) {
        try{
            final String GET_GUESSES_BY_GUESS_ID = "SELECT * FROM guess WHERE guessId = ?";
            Guess guess = jdbc.queryForObject(GET_GUESSES_BY_GUESS_ID, new GuessMapper(), guessId);
            return guess;
            
        } catch (DataAccessException ex){
            return null;
        }
    }
    
    public static final class GuessMapper implements RowMapper<Guess> {

        @Override
        public Guess mapRow(ResultSet rs, int index) throws SQLException {
            Guess guess = new Guess();
            
            guess.setAttemptId(rs.getInt("attemptId"));
            guess.setGuess(rs.getString("guess"));
            guess.setGuessId(rs.getInt("guessId"));
            guess.setIsCorrect(rs.getBoolean("isCorrect"));
            guess.setWordId(rs.getString("wordId"));
            
            return guess;
        }
    }

}

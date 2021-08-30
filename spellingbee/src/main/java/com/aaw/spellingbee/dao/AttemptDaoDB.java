/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.Guess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Austin Wong
 */

@Repository
public class AttemptDaoDB implements AttemptDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    GuessDao guessDao;
    
    @Override
    @Transactional
    public Attempt addAttempt(Attempt attempt) {
        final String INSERT_ATTEMPT = "INSERT INTO attempt "
                + "(quizId, attemptDate) "
                + "VALUES (?, ?)";
        jdbc.update(INSERT_ATTEMPT,
                attempt.getQuizId(),
                attempt.getAttemptDate());
        int attemptId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        attempt.setAttemptId(attemptId);
        
        // Add guesses
        for (Guess guess : attempt.getGuesses()){
            guess.setAttemptId(attemptId);
            guessDao.addGuess(guess);
        }
        
        return attempt;
    }

    @Override
    public List<Attempt> getAllAttempts() {
        final String SELECT_ALL_ATTEMPTS = "SELECT * FROM attempt";
        List<Attempt> attempts = jdbc.query(SELECT_ALL_ATTEMPTS, new AttemptMapper());
        
        // Set guesses
        for (Attempt attempt: attempts){
            setGuessesForAttempt(attempt);
        }
        
        return attempts;
    }
    
    @Override
    public List<Attempt> getAttemptsForQuizId(int quizId){
        final String SELECT_ATTEMPTS_FOR_QUIZ = "SELECT * FROM attempt "
                + "WHERE quizId = ?";
        List<Attempt> attempts = jdbc.query(SELECT_ATTEMPTS_FOR_QUIZ, new AttemptMapper(), quizId);
        
        // Set guesses
        for (Attempt attempt : attempts){
            setGuessesForAttempt(attempt);
        }
        
        return attempts;        
    }
    
    /**
     * Helper function that queries the guess table to set guesses for an attempt
     * @param attempt - Attempt object
     */
    private void setGuessesForAttempt(Attempt attempt){
        List<Guess> guesses = guessDao.getGuessesForAttemptId(attempt.getAttemptId());
        attempt.setGuesses(guesses);
    }

    @Override
    @Transactional
    public void deleteAttempt(int attemptId) {
        final String DELETE_GUESSES_FOR_ATTEMPT_ID = "DELETE FROM guess WHERE attemptId = ?";
        jdbc.update(DELETE_GUESSES_FOR_ATTEMPT_ID, attemptId);
        
        final String DELETE_ATTEMPT = "DELETE FROM attempt WHERE attemptId = ?";
        jdbc.update(DELETE_ATTEMPT, attemptId);
    }

    public static final class AttemptMapper implements RowMapper<Attempt> {

        @Override
        public Attempt mapRow(ResultSet rs, int index) throws SQLException {
            Attempt attempt = new Attempt();
            
            attempt.setAttemptDate(rs.getDate("attemptDate").toLocalDate());
            attempt.setAttemptId(rs.getInt("attemptId"));
            // Not currently supported - Will be calculated in service layer
            // attempt.setPercentScore(rs.getFloat("percentScore"));
            attempt.setQuizId(rs.getInt("quizId"));
            
            return attempt;
        }
    }
    
}

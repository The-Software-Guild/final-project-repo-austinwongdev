/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 30, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Attempt;
import com.aaw.spellingbee.model.Quiz;
import com.aaw.spellingbee.model.Word;
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
public class QuizDaoDB implements QuizDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    AttemptDao attemptDao;
    
    @Autowired
    WordDao wordDao;
    
    @Override
    @Transactional
    public Quiz addQuiz(Quiz quiz) {
        
        final String INSERT_QUIZ = "INSERT INTO quiz "
                + "() "
                + "VALUES ()";
        jdbc.update(INSERT_QUIZ);
        int quizId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        quiz.setQuizId(quizId);
        
        // Add words
        for (Word word : quiz.getWords()){
            wordDao.addWord(word);
            
            // Add quiz and words to quizword
            final String INSERT_QUIZWORDS = "INSERT INTO quizword "
                + " (quizId, wordId) "
                + "VALUES (?, ?)";
            jdbc.update(INSERT_QUIZWORDS, quizId, word.getWordId());
        }
        
        // Add attempts
        for (Attempt attempt : quiz.getAttempts()){
            attempt.setQuizId(quizId);
            attemptDao.addAttempt(attempt);
        }
        
        return quiz;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        final String SELECT_ALL_QUIZZES = "SELECT * FROM quiz";
        List<Quiz> quizzes = jdbc.query(SELECT_ALL_QUIZZES, new QuizMapper());
        
        for (Quiz quiz : quizzes){
            setAttemptsAndWordsForQuiz(quiz);
        }
        
        return quizzes;
    }
    
    /**
     * Helper function that gets all attempts and all words for a quiz from the
     * database and sets the Quiz object's attempts and words fields with those
     * results
     * @param quiz - Quiz object
     */
    private void setAttemptsAndWordsForQuiz(Quiz quiz){
        // Set attempts
        List<Attempt> attempts = attemptDao.getAttemptsForQuizId(quiz.getQuizId());
        quiz.setAttempts(attempts);

        // Set words
        List<Word> words = wordDao.getWordsForQuizId(quiz.getQuizId());
        quiz.setWords(words);
    }

    @Override
    @Transactional
    public void deleteQuiz(int quizId) {
        
        final String DELETE_GUESSES_FOR_QUIZ_ID = "DELETE g FROM guess g " +
            "INNER JOIN attempt a ON a.attemptId = g.attemptId " +
            "INNER JOIN quiz q ON q.quizId = a.quizId " +
            "WHERE q.quizId = ?";
        jdbc.update(DELETE_GUESSES_FOR_QUIZ_ID, quizId);
        
        final String DELETE_ATTEMPT_FOR_QUIZ_ID = "DELETE a FROM attempt a " +
            "INNER JOIN quiz q ON q.quizId = a.quizId " +
            "WHERE q.quizId = ?;";
        jdbc.update(DELETE_ATTEMPT_FOR_QUIZ_ID, quizId);
        
        final String DELETE_QUIZWORD_FOR_QUIZ_ID = "DELETE qw FROM quizword qw " +
            "INNER JOIN quiz q ON q.quizId = qw.quizId " +
            "WHERE q.quizId = ?";
        jdbc.update(DELETE_QUIZWORD_FOR_QUIZ_ID, quizId);
        
        final String DELETE_QUIZ_FOR_QUIZ_ID = "DELETE FROM quiz WHERE quizId = ?";
        jdbc.update(DELETE_QUIZ_FOR_QUIZ_ID, quizId);
    }
    
    public static final class QuizMapper implements RowMapper<Quiz> {

        @Override
        public Quiz mapRow(ResultSet rs, int index) throws SQLException {
            Quiz quiz = new Quiz();
            
            quiz.setQuizId(rs.getInt("quizId"));
            
            return quiz;
        }
    }

}

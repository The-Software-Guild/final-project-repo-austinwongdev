/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Quiz;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface QuizDao {

    Quiz addQuiz(Quiz quiz);
    List<Quiz> getAllQuizzes();
    void deleteQuiz(int quizId);
    
}

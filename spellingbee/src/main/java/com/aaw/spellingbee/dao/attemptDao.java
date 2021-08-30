/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Attempt;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface AttemptDao {

    Attempt addAttempt(Attempt attempt);
    Attempt getAttemptByAttemptId(int attemptId);
    List<Attempt> getAttemptsForQuizId(int quizId);
    List<Attempt> getAllAttempts();
    void deleteAttempt(int attemptId);
    
}

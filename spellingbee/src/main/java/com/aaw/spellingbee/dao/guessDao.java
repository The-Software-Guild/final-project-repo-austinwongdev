/*
 * @author Austin Wong
 * email: austinwongdev@gmail.com
 * date: Aug 27, 2021
 * purpose: 
 */

package com.aaw.spellingbee.dao;

import com.aaw.spellingbee.model.Guess;
import java.util.List;

/**
 *
 * @author Austin Wong
 */
public interface GuessDao {

    Guess addGuess(Guess guess);
    Guess getGuessByGuessId(int guessId);
    List<Guess> getGuessesForAttemptId(int attemptId);
    List<Guess> getAllGuesses();
    void deleteGuess(int guessId);

}

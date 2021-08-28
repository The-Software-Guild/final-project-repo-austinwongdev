USE spellingbeedbtest;

INSERT INTO quiz (quizId) VALUES (1), (2), (3);
INSERT INTO word (wordId, word) VALUES (1, 'apple'), (2, 'orange'), (3, 'pear'), (4, 'boring'), (5, 'arachnid'), (6, 'lunge'), (7, 'fantastic'), (8, 'acetaminophen'), (9, 'pneumonia'), (10, 'quiescent');
INSERT INTO quizword (quizId, wordId) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7);
INSERT INTO attempt (attemptId, attemptDate, quizId) VALUES (1, '2015-01-01', 1), (2, '2021-08-23', 2), (3, '2021-08-23', 3), (4, '2021-08-25', 2);
INSERT INTO guess (guessId, guess, attemptId, wordId, isCorrect) VALUES (1, 'apple', 1, 1, 1), (2, 'oringe', 1, 2, 0), (3, 'pair', 1, 3, 0), (4, 'boring', 1, 4, 1), (5, 'araknid', 1, 5, 0),
																		(6, 'lunge', 2, 6, 1), (7, 'fantastic', 2, 7, 1), (8, 'acetaminophen', 2, 8, 1), (9, 'pneumonia', 2, 9, 1), (10, 'quiescent', 2, 10, 1),
                                                                        (11, 'pare', 3, 3, 0), (12, 'boaring', 3, 4, 0), (13, 'iraqnid', 3, 5, 0), (14, 'lunj', 3, 6, 0), (15, 'fantastick', 3, 7, 0),
                                                                        (16, 'apple', 4, 1, 1), (17, 'orange', 4, 2, 1), (18, 'pear', 4, 3, 1), (19, 'boring', 4, 4, 1), (20, 'araknid', 4, 5, 0);
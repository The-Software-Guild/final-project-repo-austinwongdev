USE spellingbeedbtest;

INSERT INTO quiz (quizId) VALUES (1), (2), (3);
INSERT INTO word (wordId, headword) VALUES ('orange:1', 'orange'), ('boring', 'boring'), ('lunge:1', 'lunge'), ('fantastic:1', 'fantastic'), ('quiescent', 'quiescent'), ('paleontology', 'paleontology'), ('acknowledgment', 'acknowledgment');
INSERT INTO wordvariant (wordVariantId, wordVariant, wordId) VALUES (1, 'acknowledgement', 'acknowledgment');
INSERT INTO quizword (quizId, wordId) VALUES (1, 'orange:1'), (1, 'boring'), (1, 'lunge:1'), (1, 'fantastic:1'), (1, 'quiescent'), 
											 (2, 'paleontology'), (2, 'acknowledgment'), (2, 'orange:1'), (2, 'boring'), (2, 'lunge:1');
INSERT INTO attempt (attemptId, attemptDate, quizId) VALUES (1, '2015-01-01', 1), 
															(2, '2021-08-23', 2),
                                                            (3, '2021-08-23', 2);
INSERT INTO guess (guessId, guess, attemptId, wordId, isCorrect) VALUES (1, 'orange', 1, 'orange:1', 1), (2, 'boring', 1, 'boring', 1), (3, 'lunge', 1, 'lunge:1', 1), (4, 'fantastic', 1, 'fantastic:1', 1), (5, 'quiescent', 1, 'quiescent', 1),
																		(6, 'paliontology', 2, 'paleontology', 0), (7, 'agknowledgemint', 2, 'acknowledgment', 0), (8, 'oringe', 2, 'orange:1', 0), (9, 'boaring', 2, 'boring', 0), (10, 'lunj', 2, 'lunge:1', 0),
                                                                        (11, 'paleontology', 3, 'paleontology', 1), (12, 'acknowledgement', 3, 'acknowledgment', 1), (13, 'orange', 3, 'orange:1', 1), (14, 'boaring', 3, 'boring', 0), (15, 'lunge', 3, 'lunge:1', 1);
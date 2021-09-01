DROP DATABASE IF EXISTS spellingbeedb;
CREATE DATABASE spellingbeedb;
USE spellingbeedb;

CREATE TABLE quiz (
	quizId INT NOT NULL AUTO_INCREMENT,
    CONSTRAINT pk_quiz PRIMARY KEY (quizId)
);

CREATE TABLE word (
	wordId VARCHAR(52) NOT NULL,
    headword VARCHAR(50) NOT NULL,
    pronunciationURL VARCHAR(100) NOT NULL,
    offensive BOOLEAN,
    definition VARCHAR(1000) NOT NULL, 
    exampleUsage VARCHAR(1000) NOT NULL,
    CONSTRAINT pk_word PRIMARY KEY (wordId)
);

CREATE TABLE wordvariant (
	wordVariantId INT NOT NULL AUTO_INCREMENT,
    wordId VARCHAR(52) NOT NULL,
    wordVariant VARCHAR(50) NOT NULL,
    CONSTRAINT pk_wordvariant PRIMARY KEY (wordVariantId),
    CONSTRAINT fk_wordvariant_word FOREIGN KEY (wordId)
		REFERENCES word (wordId)
);

CREATE TABLE quizword (
	quizId INT NOT NULL,
    wordId VARCHAR(52) NOT NULL,
    questionNumber INT NOT NULL,
    CONSTRAINT pk_quizword PRIMARY KEY (quizId, wordId),
    CONSTRAINT fk_quizword_quiz FOREIGN KEY (quizId)
		REFERENCES quiz (quizId),
	CONSTRAINT fk_quizword_word FOREIGN KEY (wordId)
		REFERENCES word (wordId)
);

CREATE TABLE attempt (
	attemptId INT NOT NULL AUTO_INCREMENT,
    quizId INT NOT NULL,
    attemptDate DATE NOT NULL,
    CONSTRAINT pk_attempt PRIMARY KEY (attemptId),
    CONSTRAINT fk_attempt_quiz FOREIGN KEY (quizId)
		REFERENCES quiz (quizId)
);

CREATE TABLE guess (
	guessId INT NOT NULL AUTO_INCREMENT,
    guess VARCHAR(45) NOT NULL,
    wordId VARCHAR(52) NOT NULL,
    attemptId INT NOT NULL,
    isCorrect BOOLEAN NOT NULL,
    CONSTRAINT pk_guess PRIMARY KEY (guessId),
    CONSTRAINT fk_guess_word FOREIGN KEY (wordId)
		REFERENCES word (wordId),
	CONSTRAINT fk_guess_attempt FOREIGN KEY (attemptId)
		REFERENCES attempt (attemptId)
);
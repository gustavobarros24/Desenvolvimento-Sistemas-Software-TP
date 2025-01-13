CREATE SCHEMA IF NOT EXISTS `horariogen`;
USE `horariogen`;

CREATE TABLE IF NOT EXISTS `horariogen`.`Aluno`(
	`email` VARCHAR(45) NOT NULL,
	`estatuto` VARCHAR(45),
    `matricula` INT NOT NULL,
    `nome` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`email`)
    );

CREATE TABLE IF NOT EXISTS `horariogen`.`Diretor`(
	`email` VARCHAR(45) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`email`)
    );

CREATE TABLE IF NOT EXISTS `horariogen`.`User`(
	`email` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `userType` VARCHAR(45) NOT NULL,
    `aluno_email` VARCHAR(45),
    `diretor_email` VARCHAR(45),
    PRIMARY KEY (`email`),
    FOREIGN KEY (aluno_email) REFERENCES Aluno(email),
    FOREIGN KEY (diretor_email) REFERENCES Diretor(email)
    );
    
CREATE TABLE IF NOT EXISTS `horariogen`.`Uc`(
	code VARCHAR(45) NOT NULL,
    year INT NOT NULL,
    semester INT,
    name VARCHAR(45) NOT NULL,
    criterio VARCHAR(45),
    PRIMARY KEY (`code`)
    );
    
    CREATE TABLE IF NOT EXISTS `horariogen`.`Turno`(
	`idturno` VARCHAR(45) NOT NULL,
    `sala` VARCHAR(45) NOT NULL,
    `lotacao` INT NOT NULL,
    `tipo` VARCHAR(45) NOT NULL,
    `horas` TIME NOT NULL,
    `dia` INT NOT NULL,
    `quantidadedeinscritos` INT NOT NULL,
    `uc_code` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idturno`),
    FOREIGN KEY (`uc_code`) REFERENCES UC(code)
    );
    
CREATE TABLE IF NOT EXISTS `horariogen`.`Horario`(
	aluno_email VARCHAR(45) NOT NULL,
    turno_idturno VARCHAR(45) NOT NULL,
    PRIMARY KEY (aluno_email, turno_idturno),
    FOREIGN KEY (aluno_email) REFERENCES aluno(email),
    FOREIGN KEY (turno_idturno) REFERENCES turno(idturno)
    );

CREATE TABLE IF NOT EXISTS `horariogen`.`Ucsfrequentadas`(
	aluno_email VARCHAR(45) NOT NULL,
    uc_code VARCHAR(45) NOT NULL,
    PRIMARY KEY (aluno_email, uc_code),
    FOREIGN KEY (aluno_email) REFERENCES aluno(email),
    FOREIGN KEY (uc_code) REFERENCES uc(code)
    );
	
    

    
    
INSERT INTO horariogen.Diretor (email, name) VALUES ('hrpa@uminho.pt', 'Hugo Almeida');


INSERT INTO horariogen.User (email, password, userType, aluno_email, diretor_email) VALUES
('hrpa@uminho.pt', '1234', 'DIRETOR',NULL,'hrpa@uminho.pt');


INSERT INTO horariogen.Uc (code, year, semester, name, criterio) VALUES
('J305N3',3,1,'Cálculo de Programas',NULL),
('J302N1',1,2,'Programação Imperativa',NULL),
('J304N3',2,2,'Programação Orientada a Objetos',NULL),
('J304N4',2,2,'Base de Dados',NULL),
('J301N1',1,1,'Programação Funcional',NULL),
('J305N2',3,1,'Desenvolvimento de Sistemas de Software',NULL);


INSERT INTO horariogen.Turno (idturno, sala, lotacao, tipo, horas, dia, quantidadeDeInscritos, uc_code) VALUES
('T1DSS','CP 1 - 2.18',100,'T','09:00:00',1,0,'J305N2'),
('TP1DSS','CP 1 - 2.09',30,'TP','11:00:00',1,0,'J305N2'),
('TP2DSS','CP 1 - 1.27',50,'TP','11:00:00',2,0,'J305N2'),
('T1CP','CP 7 - 0.11',10,'T','11:00:00',3,0,'J305N3'),
('T2CP','CP 1 - 2.11',10,'T','11:00:00',1,0,'J305N3'),
('TP1CP','CP 2 - 2.07',10,'TP','17:00:00',3,0,'J305N3'),
('TP2CP','CP 3 - 1.05',10,'TP','09:00:00',3,0,'J305N3'),
('T1BD','CP 2 - 2.09',20,'T','11:00:00',4,0,'J304N4'),
('TP1BD','CP 3 - 1.05',10,'TP','14:00:00',4,0,'J304N4'),
('TP2BD','CP 2 - 0.11',10,'TP','14:00:00',4,0,'J304N4'),
('T1PF','CP 2 - 1.11',16,'T','15:00:00',5,0,'J301N1'),
('T2PF','CP 2 - 2.11',16,'T','16:00:00',1,0,'J301N1'),
('TP1PF','CP 2 - 3.11',16,'TP','17:00:00',2,0,'J301N1'),
('TP2PF','CP 2 - 4.11',16,'TP','17:00:00',3,0,'J301N1'),
('T1POO','CP 2 - 5.11',80,'T','19:00:00',1,0,'J304N3'),
('TP1POO','CP 2 - 6.11',40,'TP','17:00:00',1,0,'J304N3'),
('TP2POO','CP 2 - 7.11',25,'TP','09:00:00',2,0,'J304N3'),
('T1PI','CP 2 - 8.11',100,'T','11:00:00',3,0,'J302N1'),
('TP1PI','CP 2 - 9.11',20,'TP','11:00:00',4,0,'J302N1'),
('TP2PI','CP 2 - 10.11',20,'TP','13:00:00',5,0,'J302N1');


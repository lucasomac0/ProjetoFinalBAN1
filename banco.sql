
CREATE TABLE Cliente (
    Matrícula INT PRIMARY KEY, 
    Nome VARCHAR(100) NOT NULL, 
    CPF VARCHAR(20) UNIQUE NOT NULL,  
    Telefone VARCHAR(20), 
    Email VARCHAR(100) 
);

CREATE TABLE Funcionario (
    CPF VARCHAR(20) PRIMARY KEY, 
    Nome VARCHAR(100) NOT NULL, 
    Telefone VARCHAR(20), 
    Salário NUMERIC, 
    IdUnidade INT 
);

CREATE TABLE Funcionario (
    CPF VARCHAR(20) PRIMARY KEY, 
    Nome VARCHAR(100) NOT NULL,
    Telefone VARCHAR(20),
    Salário NUMERIC, -- 
    IdUnidade INT
);

CREATE TABLE Funcionario (
    CPF VARCHAR(20) PRIMARY KEY, 
    Nome VARCHAR(100) NOT NULL, 
    Telefone VARCHAR(20), 
    Salário NUMERIC, -- 
    IdUnidade INT 
);

CREATE TABLE Participação (
    MatrículaCliente INT NOT NULL, 
    CódigoTurma INT NOT NULL, 
    NúmeroChamada INT, 
    
    PRIMARY KEY (MatrículaCliente, CódigoTurma),
    
    FOREIGN KEY (MatrículaCliente) REFERENCES Cliente(Matrícula),
    FOREIGN KEY (CódigoTurma) REFERENCES Turma(Código)
);

INSERT INTO Funcionario (CPF, Nome, Telefone, Salário, IdUnidade) VALUES
('111.111.111-11', 'Ana Silva', '987654321', 4500.00, 1), 
('222.222.222-22', 'Bruno Souza', '912345678', 3000.00, 2), 
('333.333.333-33', 'Carlos Lima', '998877665', 5000.00, 1); 

INSERT INTO Instrutor (CPF, CREF, Especialidade, Horários) VALUES
('111.111.111-11', '12345-G', 'Musculação', '08:00:00'),
('333.333.333-33', '67890-P', 'Yoga', '18:30:00');

INSERT INTO Cliente (Matrícula, Nome, CPF, Telefone, Email) VALUES
(1001, 'João Santos', '444.444.444-44', '99991111', 'joao@email.com'),
(1002, 'Mariana', '555.555.555-55', '99992222', 'mariana@email.com'),
(1003, 'Pedro Rocha', '666.666.666-66', '99993333', 'pedro@email.com');

INSERT INTO Turma (Código, QtdVagas, Modalidade, CPFInstrutor) VALUES
(1, 20, 'Musculação', '111.111.111-11'),
(2, 15, 'Yoga', '333.333.333-33'),
(3, 25, 'Musculação', '111.111.111-11');

INSERT INTO Participação (MatrículaCliente, CódigoTurma, NúmeroChamada) VALUES
(1001, 1, 1), 
(1001, 2, 1), 
(1002, 1, 2), 
(1003, 3, 1); 

CREATE TABLE pessoa(
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
ativo BOOLEAN NOT NULL,
logradouro VARCHAR(20),
numero VARCHAR(10),
complemento VARCHAR(20),
bairro VARCHAR(20),
cep VARCHAR(20),
cidade VARCHAR(20),
estado VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values 
	('Guilherme', true, 'Rua', '151', 'Casa','Centro','96178000','Arambaré','RS');
    INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values 
	('jesus', true, 'Rua', '222', 'Céu','Centro','696969','Céu','Space');
    INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values 
	('Carlos', true, 'Rua', '122', 'Casa','Centro','96180000','Camaquã','RS');
    INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values 
	('Susi', true, 'Rua', '151', 'Casa','Centro','96178000','Arambaré','RS');
    INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values 
	('Iur', true, 'Rua', '151', 'Casa','Centro','96178000','Arambaré','RS');
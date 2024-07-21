/* comentario */
/* executar -> ctrl + enter */

create database dbpets;

use dbpets;  #usando o banco de dados

create table clientes(
idcliente int primary key,
nome varchar(50) not null,
nomepet varchar(50) not null,
fone varchar(15),
login varchar(15) not null unique,
senha varchar(15) not null
);

describe clientes;  #visualizar de forma estruturada

#CRUD
insert into clientes(idcliente,nome,nomepet,fone,login,senha) values (1,'Andressa Luna','Petisco','85981544785','andressaluna','123456');  #CREATE
insert into clientes(idcliente, nome, nomepet, fone, login, senha) values
(2, 'Bruno Silva', 'Bolt', '85998765432', 'brunosilva', 'senha789'),
(3, 'Carla Ferreira', 'Mimi', '85991234567', 'carlaferreira', '123carla'),
(4, 'Diego Santos', 'Toby', '85992345678', 'diegosantos', 'diegosenha'),
(5, 'Elaine Costa', 'Belinha', '85993456789', 'elainecosta', 'costa456');

select * from clientes; #READ

update clientes set nomepet = 'Ajudante' where idcliente = 2;  #UPDATE

delete from clientes where idcliente = 5;  #DELETE

create table tbusuarios(
idusuario int primary key auto_increment,
nomeusuario varchar(50) not null,
nomeusuariopet varchar(50) not null,
foneusuario varchar(15) not null,
emailusuario varchar(50) not null unique
);

insert into tbusuarios(nomeusuario,nomeusuariopet,foneusuario,emailusuario) values ('Andressa Luan', 'Petisco', '981544785', 'andressaevelyn200214@gmail.com'),
('Bruno Silva', 'Bolt', '998765432', 'brunosilva@example.com'),
('Carla Ferreira', 'Mimi', '991234567', 'carlaferreira@example.com'),
('Diego Santos', 'Toby', '992345678', 'diegosantos@example.com'),
('Elaine Costa', 'Luna', '993456789', 'elainecosta@example.com');

select * from tbusuarios;

#Um usuario pode ter varios pets, então o relacionamento é 1 para muitos

create table tbos(
os int primary key auto_increment,   
data_os timestamp default current_timestamp,
tipo_alimento varchar(50) not null,
quantidade_pacotes int not null,
quantidade_dias int not null,
prescricao varchar(50),          
idusuario int not null,
foreign key(idusuario) references tbusuarios(idusuario)     #Tenho 1 usuario para várias OS. Assim, o usuário não precisa se cadastrar novamente toda vez que criar uma nova ordem de serviço
);

describe tbos;

drop table tbos;  #Apagar tabela

insert into tbos(tipo_alimento,quantidade_pacotes,quantidade_dias,prescricao,idusuario) values ('Bovino', 10, 7, 'Não', 1);

select * from tbos;

#Trazendo informações de duas tabelas com inner join
select 
O.os, tipo_alimento,quantidade_pacotes,quantidade_dias,prescricao,
U.nomeusuario, foneusuario
from tbos as O
inner join tbusuarios as U
on (O.idusuario = U.idusuario);



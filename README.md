# ianes-patrimonio
Ianes Patrimônio é um projeto MVC de gerenciamento de itens de patrimônios escolares, que visa sanar os problemas de desorganização encontrados em instituições.
Os itens possuem localização atual e histórico de movimentações, além da identificação dos responsáveis pela mesma.
Tal projeto é um Web Service, a versão web também se encontra em meu repositório.

## Funcionalidades
**Todas as funcionalidades exigem que o usuário esteja autenticado**

•	Cadastro de usuário (somente administrador);

•	Alteração de usuário (somente administrador);

•	Autenticação (login através de sessão);

•	Alteração de senha;

•	Excluir usuário (somente administrador);

•	Listar usuários (somente administradores);


•	Cadastro de patrimônio (somente administrador);
•	Listagem de patrimônios;
•	Alteração de patrimônio (somente administrador);
•	Excluir patrimônio (somente administrador);

•	Cadastrar ambiente (somente administrador);
•	Listar ambiente;
•	Alterar ambiente (somente administrador);
•	Exclusão ambiente (somente administrador);

•	Cadastro item de patrimônio;
•	Exclusão de item de patrimônio (somente administrador);
•	Listar itens de patrimônio (ao clicar em um patrimônio específico);
•	Especificação de onde está no momento;
•	Data da última atualização (último movimento); 
•	Realização de movimentação;
•	Visualização de movimentações de um determinado item;

### Utilização
Utilização do banco de dados MySQL.
Para editar as configurações do banco de dados MySQL, acesse a classe br.senai.sp.info.ianes.config.PersistenceConfig e configure de acordo com suas informações.

#### Observação: Como segundo a regra de negócios somente o administrador pode realizar cadastros, ao executar a aplicação o usuário admin email: "admin@email.com" senha: "admin" será criado automaticamente.

### Criar o banco de dados
CREATE SCHEMA ianespatrimonio;

### Colocá-lo em uso
USE ianespatrimonio;

### Criar a tabela de usuarios
CREATE TABLE usuario (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(20) NOT NULL,
sobrenome VARCHAR(40) NOT NULL,
email VARCHAR(120) NOT NULL UNIQUE,
senha VARCHAR(32) NOT NULL,
tipo INT(11) NOT NULL
);

### Criar a tabela de categorias de patrimônio
CREATE TABLE categoriapatrimonio (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(30) UNIQUE NOT NULL
);

### Criar a tabela de categoria de ambientes
CREATE TABLE ambiente (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(40) UNIQUE NOT NULL
);

### Criar a tabela de patrimonios
CREATE TABLE patrimonio (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
dataCadastro DATETIME NOT NULL,
nome VARCHAR(40) UNIQUE NOT NULL,
categoria BIGINT(20) NOT NULL,
usuario_cadastrou BIGINT(20) NOT NULL,

);
ALTER TABLE patrimonio ADD FOREIGN KEY(usuario_cadastrou) REFERENCES usuario(id);
ALTER TABLE patrimonio ADD FOREIGN KEY(categoria) REFERENCES categoriapatrimonio(id);

### Criar a tabela de item patrimônio
CREATE TABLE itempatrimonio (
id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
dataUltimaMovimentacao DATETIME DEFAULT NULL,
ambiente_id BIGINT(20) NOT NULL,
patrimonio_id BIGINT(20) NOT NULL,
usuario_cadastrou_id BIGINT(20)NOT NULL
);
ALTER TABLE movimentacao ADD FOREIGN KEY(usuario_cadastrou_id) REFERENCES usuario(id);
ALTER TABLE movimentacao ADD FOREIGN KEY(patrimonio_id) REFERENCES patrimonio(id);
ALTER TABLE movimentacao ADD FOREIGN KEY(ambiente_id) REFERENCES ambiente(id);

### Criar a tabela de movimentação
CREATE TABLE movimentacao (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
dataMovimentacao DATETIME NOT NULL,
ambiente_destino BIGINT(20) NOT NULL,
ambiente_origem BIGINT(20) NOT NULL,
item_movimentado BIGINT(20) NOT NULL,
usuario BIGINT(20)NOT NULL
);
ALTER TABLE movimentacao ADD FOREIGN KEY(usuario) REFERENCES usuario(id);
ALTER TABLE movimentacao ADD FOREIGN KEY(ambiente_destino) REFERENCES ambiente(id);
ALTER TABLE movimentacao ADD FOREIGN KEY(ambiente_origem) REFERENCES ambiente(id);
ALTER TABLE movimentacao ADD FOREIGN KEY(item_movimentado) REFERENCES itempatrimonio(id);

## Endpoints

### Usuário

| Método | Endpoint | Descrição |
| :---         |     :---:      |          ---: |
| POST | "/rest/auth/jwt" | Gera Token JWT |
| GET | "/rest/patrimonios" | Exibe todos os patrimônios cadastrados |
| GET |  "/rest/patrimonios/{id}" | Exibe um determinado patrimnio |
| GET | "/rest/itens" | Exibe todos os itens |
| GET | "/rest/itens/{id}" | Exibe um determinado item |
| POST | "/rest/itens/{id}/movimentacoes" | Realiza a movimentação de determinado item |
| GET | "/rest/itens/{id}/movimentacoes" | Exibe o histórico de movimentações de determinado item| 
| GET | "/rest/ambientes" | Exibe todos os ambiwentes cadastrados|

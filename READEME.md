# 🏦 Digital Bank API

<div align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.0+-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Java-17+-blue?style=for-the-badge&logo=java" alt="Java">
  <img src="https://img.shields.io/badge/MySQL-8.0+-orange?style=for-the-badge&logo=mysql" alt="MySQL">
  <img src="https://img.shields.io/badge/JUnit-5-green?style=for-the-badge&logo=junit5" alt="JUnit">
  <img src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger" alt="Swagger">
</div>

<div align="center">
  <h3>🚀 API REST completa para sistema bancário digital</h3>
  <p>Desenvolvida com Spring Boot, oferece funcionalidades completas de gestão bancária incluindo clientes, contas, transações e histórico detalhado.</p>
</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Instalação](#-instalação)
- [Configuração](#-configuração)
- [Como Usar](#-como-usar)
- [Endpoints](#-endpoints)
- [Testes](#-testes)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Contribuição](#-contribuição)
- [Autor](#-autor)
- [Licença](#-licença)

---

## 🎯 Sobre o Projeto

A **Digital Bank API** é uma aplicação backend robusta que simula as operações de um banco digital moderno. Construída seguindo as melhores práticas de desenvolvimento, oferece uma arquitetura limpa, testes abrangentes e documentação completa.

### ✨ Principais Características

- **Arquitetura REST** com Spring Boot
- **Validação robusta** de dados de entrada
- **Tratamento global** de exceções
- **Documentação automática** com OpenAPI/Swagger
- **Cobertura completa** de testes unitários
- **Segurança implementada** com Spring Security
- **Banco de dados relacional** com JPA/Hibernate

---

## 🚀 Funcionalidades

### 👥 Gestão de Clientes
- ✅ Cadastro de novos clientes
- ✅ Consulta por CPF
- ✅ Listagem de todos os clientes
- ✅ Atualização de dados
- ✅ Exclusão de clientes

### 🏧 Gestão de Contas
- ✅ Criação de contas (Corrente e Poupança)
- ✅ Consulta de contas por cliente
- ✅ Depósitos e saques
- ✅ Transferências entre contas
- ✅ Simulação de rendimento (Poupança)

### 📊 Histórico e Auditoria
- ✅ Histórico completo de transações
- ✅ Rastreamento de todas as operações
- ✅ Controle de data/hora das operações

---

## 🛠 Tecnologias

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **Spring Validation**

### Banco de Dados
- **MySQL 8.0+**
- **Hibernate/JPA**

### Testes
- **JUnit 5**
- **Mockito**
- **Spring Boot Test**
- **MockMvc**

### Documentação
- **OpenAPI 3**
- **Swagger UI**

### Ferramentas
- **Maven**
- **Docker** (em desenvolvimento)

---

## 📥 Instalação

### Pré-requisitos

- ☕ **Java 17** ou superior
- 🐬 **MySQL 8.0** ou superior
- 📦 **Maven 3.8** ou superior

### 1. Clone o repositório

```bash
git clone https://github.com/wallace-spring-boot/digital-bank-api.git
cd digital-bank-api
```

### 2. Configure o banco de dados

```sql
CREATE DATABASE digital_bank_api;
```

### 3. Configure as variáveis de ambiente

```bash
export DB_USERNAME=seu_usuario
export DB_PASSWORD=sua_senha
```

### 4. Execute a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## ⚙️ Configuração

### application.properties

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/digital_bank_api
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# Taxa de Rendimento da Poupança (0.5% ao mês)
rendimento.poupanca.taxa-mensal=0.5
```

---

## 🔧 Como Usar

### 📖 Documentação da API

Acesse a documentação interativa da API:

```
http://localhost:8080/swagger-ui/index.html
```

### 🔐 Autenticação

A API utiliza autenticação básica. Usuários padrão:

| Usuário | Senha | Role    |
|---------|-------|---------|
| admin   | 0987  | USER    |
| user    | 1234  | MANAGER |

---

## 📡 Endpoints

### 👥 Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/clientes` | Lista todos os clientes |
| `GET` | `/clientes/{cpf}` | Busca cliente por CPF |
| `POST` | `/clientes` | Cadastra novo cliente |
| `PUT` | `/clientes/{cpf}` | Atualiza dados do cliente |
| `DELETE` | `/clientes/{cpf}` | Remove cliente |

### 🏧 Contas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/contas/clientes/{cpf}/contas` | Lista contas do cliente |
| `POST` | `/contas` | Cria nova conta |
| `PUT` | `/contas/{id}/deposito` | Realiza depósito |
| `PUT` | `/contas/{id}/saque` | Realiza saque |
| `PUT` | `/contas/transferencias` | Transfere entre contas |
| `GET` | `/contas/{id}/simulacao-rendimento` | Simula rendimento |
| `GET` | `/contas/{id}/historico` | Histórico da conta |

### 📄 Exemplos de Uso

#### Cadastrar Cliente
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Wallace Silva",
    "cpf": "123.456.789-00"
  }'
```

#### Criar Conta Corrente
```bash
curl -X POST http://localhost:8080/contas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "tipoConta": "CC"
  }'
```

#### Realizar Depósito
```bash
curl -X PUT http://localhost:8080/contas/1/deposito \
  -H "Content-Type: application/json" \
  -d '{
    "valor": 1000.00,
    "contaId": 1
  }'
```

---

## 🧪 Testes

### Executar todos os testes

```bash
mvn test
```

### Executar testes com relatório de cobertura

```bash
mvn test jacoco:report
```

### Estrutura de Testes

```
src/test/java/
├── controllers/     # Testes dos Controllers
├── services/       # Testes dos Services  
└── integration/    # Testes de Integração (em desenvolvimento)
```

---

## 📁 Estrutura do Projeto

```
src/main/java/com/wallace/spring/boot/
├── controller/         # Controllers REST
├── dto/                # Data Transfer Objects
├── enums/              # Enumerações
├── exceptions/         # Tratamento de Exceções
├── model/
│   ├── entities/       # Entidades JPA
│   ├── repository/     # Repositórios
│   └── services/       # Lógica de Negócio
└── security/           # Configurações de Segurança
```

---

## 🔒 Tratamento de Erros

A API retorna respostas padronizadas para erros:

```json
{
  "timestamp": "2024-01-15T10:30:45",
  "message": "Cliente não encontrado",
  "details": "uri=/clientes/123.456.789-00"
}
```

### Códigos de Status

- `200` - Sucesso
- `201` - Criado com sucesso
- `204` - Sem conteúdo
- `400` - Dados inválidos
- `404` - Recurso não encontrado
- `409` - Conflito (ex: CPF duplicado)
- `422` - Erro de negócio (ex: saldo insuficiente)
- `500` - Erro interno do servidor

---

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Para contribuir:

1. Faça um **Fork** do projeto
2. Crie uma **Branch** para sua funcionalidade (`git checkout -b feature/MinhaFuncionalidade`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona MinhaFuncionalidade'`)
4. **Push** para a Branch (`git push origin feature/MinhaFuncionalidade`)
5. Abra um **Pull Request**

### 📝 Padrões de Commit

- `feat:` Nova funcionalidade
- `fix:` Correção de bug
- `docs:` Documentação
- `test:` Testes
- `refactor:` Refatoração de código

---

## 🚀 Roadmap

- [ ] **Docker/Docker-compose** para containerização
- [ ] **JWT Authentication** para segurança avançada
- [ ] **Paginação** nas consultas
- [ ] **Cache Redis** para performance
- [ ] **Testes de Integração** com TestContainers
- [ ] **CI/CD Pipeline** com GitHub Actions
- [ ] **Monitoring** com Actuator + Micrometer
- [ ] **API Versioning** para compatibilidade

---

## 👨‍💻 Autor

<div align="center">
  <img src="https://github.com/wallacemaia2007.png" width="100px" style="border-radius: 50%;" alt="Wallace Maia"/>
  
  **Wallace Maia**
  
  [![LinkedIn](https://img.shields.io/badge/-LinkedIn-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/wallacemaia-dev/)
  [![GitHub](https://img.shields.io/badge/-GitHub-black?style=flat&logo=github)](https://github.com/wallacemaia2007)
  [![Email](https://img.shields.io/badge/-Email-red?style=flat&logo=gmail)](mailto:wallacemaia2007@gmail.com)
</div>

> 💡 **Sobre mim:** Desenvolvedor Backend apaixonado por criar soluções robustas e escaláveis. Especialista em Java/Spring Boot, sempre buscando aplicar as melhores práticas de desenvolvimento e arquitetura de software.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">
  <h3>⭐ Se este projeto te ajudou, considere dar uma estrela!</h3>
  
  **Desenvolvido com ❤️ por [Wallace Maia](https://github.com/wallacemaia2007)**
</div>
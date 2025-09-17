# 🏦 Digital Bank API

<div align="center">
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.0+-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security">
  <img src="https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit">
  <img src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger">
</div>

<div align="center">
  <h3>🚀 API REST segura e completa para sistema bancário digital</h3>
  <p>Sistema bancário moderno com autenticação JWT, controle de acesso baseado em roles, transações seguras e histórico completo.</p>
</div>

---

## 📋 Sobre o Projeto

A **Digital Bank API** é uma aplicação backend robusta que simula operações bancárias com foco em **segurança**, **performance** e **escalabilidade**. Desenvolvida com Spring Boot e Spring Security, implementa autenticação JWT, controle de permissões granular e cobertura completa de testes unitários.

### ✨ Principais Características

- 🔐 **Autenticação JWT** com Spring Security
- 👥 **Sistema de Roles** (USER/ADMIN) com permissões granulares
- 🏧 **Operações bancárias** completas (depósitos, saques, transferências)
- 📊 **Histórico detalhado** de todas as transações
- 🧪 **Cobertura de testes** com JUnit 5 e Mockito
- 📖 **Documentação automática** com OpenAPI/Swagger
- ⚡ **Validação robusta** com Bean Validation
- 🎯 **Tratamento global** de exceções
- 📈 **Simulação de rendimentos** para conta poupança

---

## 🛠 Stack Tecnológica

### Core Framework
- **Java 17+** - Linguagem principal
- **Spring Boot 3.x** - Framework base
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Validation** - Validação de dados

### Segurança
- **JWT (JSON Web Tokens)** - Autenticação stateless
- **BCrypt** - Criptografia de senhas
- **Role-Based Access Control** - Controle de acesso

### Banco de Dados
- **MySQL 8.0+** - Banco principal
- **JPA/Hibernate** - ORM
- **HikariCP** - Pool de conexões

### Testes & Qualidade
- **JUnit 5** - Framework de testes
- **Mockito** - Mocking framework
- **MockMvc** - Testes de integração web
- **ArgumentCaptor** - Captura de argumentos em testes

### Documentação & APIs
- **OpenAPI 3** - Especificação da API
- **Swagger UI** - Interface de documentação
- **Bean Validation** - Validações declarativas

---

## 🔐 Sistema de Segurança

### Usuários Padrão
A aplicação inicializa com dois usuários pré-configurados:

| Email | Senha | Role | Permissões |
|-------|-------|------|------------|
| `admin@admin.com` | `admin123` | **ADMIN** | Todas as operações (CRUD completo) |
| `user@user.com` | `user123` | **USER** | Leitura e criação apenas |

### Controle de Acesso (RBAC)
- **USER**: `user:read`, `user:write`
- **ADMIN**: `admin:read`, `admin:write`, `admin:update`, `admin:delete` + todas de USER

### Endpoints Protegidos
```
GET    /clientes/**      → user:read ou admin:read
POST   /clientes         → user:write ou admin:write  
PUT    /clientes/**      → admin:update (somente admin)
DELETE /clientes/**      → admin:delete (somente admin)
```

---

## 🚀 Funcionalidades

### 👥 Gestão de Clientes
- ✅ Cadastro com validação de CPF
- ✅ Busca por CPF individual
- ✅ Listagem completa (paginada)
- ✅ Atualização de dados (admin only)
- ✅ Exclusão de clientes (admin only)

### 🏧 Sistema Bancário
- ✅ Criação de contas (Corrente/Poupança)
- ✅ Operações financeiras (depósito/saque/transferência)
- ✅ Validação de saldo e limites
- ✅ Simulação de rendimentos (poupança)
- ✅ Controle de tipos de conta por cliente

### 📊 Auditoria & Histórico
- ✅ Histórico completo de transações
- ✅ Rastreamento de origem/destino
- ✅ Timestamps detalhados
- ✅ Tipos de transação identificados

---

## 📥 Instalação & Configuração

### Pré-requisitos
- ☕ **Java 17+**
- 🐬 **MySQL 8.0+**
- 📦 **Maven 3.8+**

### 1. Clone & Configure
```bash
git clone https://github.com/seu-usuario/digital-bank-api.git
cd digital-bank-api

# Configure as variáveis de ambiente
export DB_USERNAME=seu_usuario
export DB_PASSWORD=sua_senha
export JWT_SECRET=chave_secreta_base64_aqui
```

### 2. Banco de Dados
```sql
CREATE DATABASE digital_bank_api CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Execute a Aplicação
```bash
mvn clean install
mvn spring-boot:run
```

**Aplicação disponível em:** `http://localhost:8080`  
**Documentação Swagger:** `http://localhost:8080/swagger-ui/index.html`

---

## 📡 Principais Endpoints

### 🔑 Autenticação
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| `POST` | `/api/v1/auth/register` | Registrar novo usuário | Não |
| `POST` | `/api/v1/auth/authenticate` | Login/obter token JWT | Não |

### 👥 Clientes
| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|-----------|
| `GET` | `/clientes` | Listar clientes | `user:read` |
| `GET` | `/clientes/{cpf}` | Buscar por CPF | `user:read` |
| `POST` | `/clientes` | Cadastrar cliente | `user:write` |
| `PUT` | `/clientes/{cpf}` | Alterar dados | `admin:update` |
| `DELETE` | `/clientes/{cpf}` | Remover cliente | `admin:delete` |

### 🏧 Contas & Operações
| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|-----------|
| `GET` | `/contas/clientes/{cpf}/contas` | Contas do cliente | `user:read` |
| `POST` | `/contas` | Criar conta | `user:write` |
| `PUT` | `/contas/{id}/deposito` | Depósito | `user:write` |
| `PUT` | `/contas/{id}/saque` | Saque | `user:write` |
| `PUT` | `/contas/transferencias` | Transferência | `user:write` |
| `GET` | `/contas/{id}/simulacao-rendimento` | Simular rendimento | `user:read` |
| `GET` | `/contas/{id}/historico` | Histórico | `user:read` |

---

## 🧪 Testes Unitários

### Cobertura Completa
- **Controllers** - Testes com MockMvc
- **Services** - Testes de lógica de negócio  
- **Repositories** - Testes de persistência
- **Integration** - Testes end-to-end

### Tecnologias de Teste
```java
@ExtendWith(MockitoExtension.class)  // JUnit 5 + Mockito
@Mock                                // Mocking de dependências
@InjectMocks                        // Injeção de mocks
MockMvc                             // Testes de API
ArgumentCaptor                      // Captura de argumentos
@DisplayName                        // Documentação de testes
```

### Executar Testes
```bash
# Todos os testes
mvn test

# Com relatório de cobertura
mvn test jacoco:report

# Testes específicos
mvn test -Dtest=ClienteControllerTest
mvn test -Dtest=ContaServiceTest
```

---

## 💡 Exemplos de Uso

### 1. Autenticação
```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@admin.com",
    "senha": "admin123"
  }'

# Response: {"token": "eyJhbGciOiJIUzI1NiJ9..."}
```

### 2. Operações Bancárias
```bash
# Criar cliente (com token)
curl -X POST http://localhost:8080/clientes \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Wallace Silva",
    "cpf": "123.456.789-00"
  }'

# Criar conta corrente
curl -X POST http://localhost:8080/contas \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "tipoConta": "CC"
  }'

# Realizar depósito
curl -X PUT http://localhost:8080/contas/1/deposito \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "valor": 1500.00,
    "contaId": 1
  }'
```

---

## 🏗 Arquitetura

### Estrutura do Projeto
```
src/main/java/com/wallace/spring/boot/
├── 📁 config/             # Configurações (Security, CORS, etc.)
├── 📁 controller/         # Controllers REST
├── 📁 dto/                # Data Transfer Objects
├── 📁 enums/              # Enumerações (Role, Permission, etc.)
├── 📁 exceptions/         # Exceções customizadas
├── 📁 model/
│   ├── entities/          # Entidades JPA
│   └── repository/        # Repositórios
└── 📁 services/           # Lógica de negócio
```

### Padrões Aplicados
- **DTO Pattern** - Transferência de dados
- **Repository Pattern** - Acesso a dados
- **Service Layer** - Lógica de negócio
- **Global Exception Handler** - Tratamento centralizado de erros
- **Builder Pattern** - Construção de objetos complexos

---

## 🔍 Validações & Tratamento de Erros

### Validações Automáticas
- **CPF** - Validação brasileira
- **Email** - Formato válido
- **Valores** - Positivos e não nulos
- **Tipos de conta** - CC/CP apenas

### Respostas de Erro Padronizadas
```json
{
  "timestamp": "2024-09-17T16:30:45",
  "message": "Cliente não encontrado",
  "details": "uri=/clientes/123.456.789-00"
}
```

### Códigos HTTP
- `200` ✅ Sucesso
- `201` ✅ Criado
- `204` ✅ Sem conteúdo  
- `400` ❌ Dados inválidos
- `401` ❌ Não autenticado
- `403` ❌ Sem permissão
- `404` ❌ Não encontrado
- `409` ❌ Conflito
- `422` ❌ Regra de negócio

---

## 👨‍💻 Autor

<div align="center">
  <img src="https://github.com/wallacemaia2007.png" width="100px" style="border-radius: 50%;" alt="Wallace Maia"/>
  
  **Wallace Maia**  
  *Desenvolvedor Backend Java/Spring*
  
  [![LinkedIn](https://img.shields.io/badge/-LinkedIn-0A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/wallacemaia-dev/)
  [![GitHub](https://img.shields.io/badge/-GitHub-181717?style=flat&logo=github&logoColor=white)](https://github.com/wallacemaia2007)
  [![Email](https://img.shields.io/badge/-Email-EA4335?style=flat&logo=gmail&logoColor=white)](mailto:wallacemaia2007@gmail.com)
</div>

---

<div align="center">
  <sub>Desenvolvido com ❤️ usando Spring Boot + Spring Security</sub>
</div>
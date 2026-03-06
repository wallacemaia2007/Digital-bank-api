# 🏦 Digital Bank API - Sistema Bancário Digital Completo

<div align="center">
  <img src="https://img.shields.io/badge/Full%20Stack-Complete-success?style=for-the-badge" alt="Full Stack">
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Angular-17.3+-DD0031?style=for-the-badge&logo=angular&logoColor=white" alt="Angular">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.0+-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/TypeScript-5.0+-3178C6?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript">
</div>

<div align="center">
  <h3>🚀 Plataforma bancária completa com interface moderna e backend robusto</h3>
  <p>Sistema full-stack para operações bancárias digitais com autenticação segura, dashboard intuitivo e gerenciamento completo de contas.</p>
</div>

---

## 📋 Visão Geral do Projeto

O **Digital Bank API** é uma aplicação full-stack que simula um banco digital completo, incluindo:

- ✅ **Interface moderna** com Angular 17 e Material Design
- ✅ **Backend robusto** com Spring Boot 3 e Spring Security
- ✅ **Autenticação segura** com JWT
- ✅ **Operações bancárias** (depósitos, saques, transferências)
- ✅ **Dashboard gerencial** com gráficos e estatísticas
- ✅ **Sistema de usuários/admin** com controle de acesso
- ✅ **Histórico de transações** completo
- ✅ **Responsivo** e otimizado para mobile

---

## 🎨 Frontend - Digital Bank Web

### 📱 Tecnologias do Frontend

```
┌─────────────────────────────────────────┐
│         FRONTEND (Angular 17)           │
├─────────────────────────────────────────┤
│ • Angular 17.3 (Framework principal)    │
│ • TypeScript 5+ (Linguagem)             │
│ • Angular Material (UI Components)      │
│ • PrimeNG (Componentes avançados)       │
│ • Tailwind CSS (Styling)                │
│ • SCSS (Pré-processador CSS)            │
│ • RxJS (Programação reativa)            │
│ • ApexCharts (Gráficos)                 │
│ • Ngx-Toastr (Notificações)             │
│ • Ngx-Mask (Máscaras de entrada)        │
│ • Angular CDK (Utilidades)              │
└─────────────────────────────────────────┘
```

### 📁 Estrutura do Frontend

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/                    # Serviços core (auth, interceptors)
│   │   │   ├── auth/                # Guarda de rotas e autenticação
│   │   │   ├── interceptors/        # Interceptadores HTTP
│   │   │   └── models/              # Modelos de dados
│   │   │
│   │   ├── modules/
│   │   │   ├── public/              # Rotas públicas (signin, signup, landing)
│   │   │   ├── home/                # Página inicial após login
│   │   │   ├── gerencial/           # Dashboard e gerenciamento
│   │   │   │   ├── pages/
│   │   │   │   │   ├── dashboard/   # Dashboard com gráficos
│   │   │   │   │   └── users/       # Gestão de usuários
│   │   │   │   └── gerencial.routes.ts
│   │   │   │
│   │   │   └── shared/              # Componentes reutilizáveis
│   │   │       ├── components/
│   │   │       │   ├── base-button/
│   │   │       │   ├── common-table/
│   │   │       │   ├── loading/
│   │   │       │   ├── page-header/
│   │   │       │   ├── sidenav/
│   │   │       │   └── user-card/
│   │   │       └── models/
│   │   │
│   │   ├── app.component.ts         # Componente raiz
│   │   ├── app.routes.ts            # Rotas principais
│   │   └── app.config.ts            # Configurações globais
│   │
│   ├── assets/                      # Imagens e recursos estáticos
│   ├── environments/                # Configurações por ambiente
│   ├── styles.scss                  # Estilos globais
│   └── main.ts                      # Entry point
│
├── angular.json                     # Configuração Angular
├── tailwind.config.js               # Configuração Tailwind
├── tsconfig.json                    # Configuração TypeScript
└── package.json                     # Dependências npm
```

### 🎯 Funcionalidades do Frontend

#### 📖 Páginas Públicas
- **Landing Page** - Página inicial atraente
- **Sign In** - Login com email e senha
- **Sign Up** - Cadastro de novos usuários
- **Recuperação de Senha** (preparado)

#### 🏠 Páginas Autenticadas
- **Home/Dashboard** - Overview da conta do usuário
- **Operações Bancárias**
  - 💰 Depósitos
  - 🏧 Saques
  - 💸 Transferências
  - 📊 Simulador de rendimentos
  
#### 👨‍💼 Painel Gerencial (Admin)
- **Dashboard** - Estatísticas e gráficos (ApexCharts)
- **Gestão de Usuários** - CRUD de usuários
- **Histórico de Transações** - Filtros e busca
- **Relatórios** - Visualizações gerenciais

#### 🎨 Componentes Reutilizáveis
- **BaseButton** - Botão personalizado
- **CommonTable** - Tabela com paginação
- **Loading** - Spinner de carregamento
- **PageHeader** - Cabeçalho padrão
- **SideNav** - Navegação lateral
- **UserCard** - Card com dados do usuário
- **LabelAndInfo** - Exibição de informações

### ⚙️ Configuração Angular

**Versão:** 17.3.2  
**TypeScript:** 5.0+  
**Node:** 18+  
**Package Manager:** npm/yarn

---

## ⚙️ Backend - Digital Bank API

### 🔧 Tecnologias do Backend

```
┌──────────────────────────────────────────┐
│      BACKEND (Spring Boot 3)             │
├──────────────────────────────────────────┤
│ • Java 17+ (Linguagem)                   │
│ • Spring Boot 3.x (Framework)            │
│ • Spring Security (Autenticação/Authz)   │
│ • Spring Data JPA (ORM/Persistência)     │
│ • Spring Validation (Validações)         │
│ • JWT (JSON Web Tokens)                  │
│ • BCrypt (Criptografia)                  │
│ • MySQL 8.0+ (Banco de dados)            │
│ • HikariCP (Pool de conexões)            │
│ • OpenAPI 3 / Swagger UI (Docs)          │
│ • JUnit 5 + Mockito (Testes)             │
└──────────────────────────────────────────┘
```

### 📁 Estrutura do Backend

```
backend/
├── src/main/java/com/wallace/spring/boot/
│   ├── ✅ application/            # Classe principal da aplicação
│   ├── 🔐 config/                 # Configurações
│   │   ├── SecurityConfig.java    # JWT e Spring Security
│   │   ├── CorsConfig.java        # CORS
│   │   └── OpenApiConfig.java     # Swagger/OpenAPI
│   │
│   ├── 🎮 controller/             # Controllers REST
│   │   ├── AuthController.java    # Login/Register
│   │   ├── ClienteController.java # Gestão de clientes
│   │   └── ContaController.java   # Contas e operações
│   │
│   ├── 📦 dto/                    # Data Transfer Objects
│   │   ├── AuthDto.java
│   │   ├── ClienteDto.java
│   │   └── ContaDto.java
│   │
│   ├── 🔑 enums/                  # Enumerações
│   │   ├── Role.java              # USER, ADMIN
│   │   ├── Permission.java        # Permissões granulares
│   │   └── TipoConta.java         # CC (Corrente), CP (Poupança)
│   │
│   ├── ⚠️  exceptions/             # Exceções customizadas
│   │   ├── ClienteNotFoundException.java
│   │   ├── SaldoInsuficienteException.java
│   │   └── GlobalExceptionHandler.java
│   │
│   ├── 💾 model/
│   │   ├── entities/              # Entidades JPA
│   │   │   ├── Cliente.java
│   │   │   ├── Conta.java
│   │   │   ├── Transacao.java
│   │   │   └── Usuario.java
│   │   │
│   │   └── repository/            # Repositórios Spring Data
│   │       ├── ClienteRepository.java
│   │       ├── ContaRepository.java
│   │       └── UsuarioRepository.java
│   │
│   └── 🔧 services/               # Lógica de negócio
│       ├── ClienteService.java
│       ├── ContaService.java
│       ├── TransacaoService.java
│       ├── AuthService.java
│       └── JwtTokenProvider.java
│
├── src/main/resources/
│   └── application.yml            # Configurações (DB, JWT, etc)
│
├── src/test/java/                 # Testes unitários
│   └── com/wallace/...
│
├── pom.xml                        # Dependências Maven
└── mvnw / mvnw.cmd               # Maven Wrapper
```

### 🔐 Sistema de Segurança

#### Usuários Padrão
```
Email             | Senha       | Role
--                | --          | --
admin@admin.com   | admin123    | ADMIN
user@user.com     | user123     | USER
```

#### Controle de Acesso (RBAC)
```
Permissões USER:
├── user:read    (Leitura de dados)
└── user:write   (Criação de dados)

Permissões ADMIN:
├── admin:read   (Leitura total)
├── admin:write  (Criação total)
├── admin:update (Atualização)
└── admin:delete (Exclusão)
```

### 📡 Principais Endpoints

#### 🔑 Autenticação
```
POST   /api/v1/auth/register       - Registrar novo usuário
POST   /api/v1/auth/authenticate   - Login (retorna JWT)
```

#### 👥 Clientes
```
GET    /clientes                   - Listar clientes (paginado)
GET    /clientes/{cpf}             - Buscar cliente por CPF
POST   /clientes                   - Cadastrar cliente (user:write)
PUT    /clientes/{cpf}             - Atualizar cliente (admin:update)
DELETE /clientes/{cpf}             - Deletar cliente (admin:delete)
```

#### 🏧 Contas & Operações
```
GET    /contas/clientes/{cpf}/contas         - Contas do cliente
POST   /contas                                - Criar conta
PUT    /contas/{id}/deposito                 - Realizar depósito
PUT    /contas/{id}/saque                    - Realizar saque
PUT    /contas/transferencias                - Transferência entre contas
GET    /contas/{id}/simulacao-rendimento    - Simular rendimento (poupança)
GET    /contas/{id}/historico               - Histórico de transações
```

### 📊 Funcionalidades Bancárias

- ✅ Criação de contas (Corrente/Poupança)
- ✅ Depósitos com validação
- ✅ Saques com verificação de saldo
- ✅ Transferências entre contas
- ✅ Simulação de rendimentos
- ✅ Histórico completo de transações
- ✅ Validação de CPF
- ✅ Controle de permissões por operação

---

## 🏗 Arquitetura Geral

```
┌──────────────────────────────────────────────────────────────┐
│                     WEB BROWSER                              │
│              (Angular 17 - TypeScript/SCSS)                  │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ • Dashboard (Gráficos)                                │  │
│  │ • Operações Bancárias                                 │  │
│  │ • Gestão de Usuários                                  │  │
│  │ • Autenticação (JWT stored in localStorage)           │  │
│  └────────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────────┘
                        ↓↑ HTTP/HTTPS
                   REST API Calls
┌────────────────────────────────────────────────────────────────┐
│              SPRING BOOT API SERVER (Backend)                  │
│              (Java 17 - Port 8080)                             │
│  ┌────────────────────────────────────────────────────────┐   │
│  │ JWT Authentication & Authorization                     │   │
│  │  ├── Spring Security                                   │   │
│  │  └── Token Validation                                  │   │
│  ├────────────────────────────────────────────────────────┤   │
│  │ REST Controllers                                       │   │
│  │  ├── AuthController                                    │   │
│  │  ├── ClienteController                                 │   │
│  │  └── ContaController                                   │   │
│  ├────────────────────────────────────────────────────────┤   │
│  │ Business Logic (Services)                              │   │
│  │  ├── ClienteService                                    │   │
│  │  ├── ContaService                                      │   │
│  │  └── TransacaoService                                  │   │
│  ├────────────────────────────────────────────────────────┤   │
│  │ Data Access Layer (JPA/Hibernate)                      │   │
│  │  ├── ClienteRepository                                 │   │
│  │  ├── ContaRepository                                   │   │
│  │  └── TransacaoRepository                               │   │
│  └────────────────────────────────────────────────────────┘   │
└────────────────────────────────────────────────────────────────┘
                        ↓↑ JDBC/SQL
                    Database Driver
┌────────────────────────────────────────────────────────────────┐
│                   MYSQL DATABASE (8.0+)                        │
│  ├── digital_bank_api (Database)                              │
│  │   ├── clientes (Tabela)                                    │
│  │   ├── contas (Tabela)                                      │
│  │   ├── transacoes (Tabela)                                  │
│  │   └── usuarios (Tabela)                                    │
└────────────────────────────────────────────────────────────────┘
```

---

## 🚀 Como Rodar o Projeto

### ✅ Pré-requisitos

- ☕ **Java 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- 🐬 **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- 📦 **Node.js 18+** - [Download](https://nodejs.org/)
- 🔧 **Maven 3.8+** - Incluído no projeto (mvnw)
- 📝 **Git** - [Download](https://git-scm.com/)

### 1️⃣ Clone o Repositório

```bash
git clone https://github.com/wallacemaia2007/digital-bank-api.git
cd digital-bank-api
```

### 2️⃣ Configurar Banco de Dados

```bash
# Conectar no MySQL
mysql -u root -p

# Criar database (no MySQL CLI)
CREATE DATABASE digital_bank_api CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

### 3️⃣ Configurar Variáveis de Ambiente

**Arquivo:** `backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/digital_bank_api
    username: root
    password: sua_senha_aqui
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

security:
  jwt:
    secret: sua_chave_secreta_base64_muito_longa_aqui
    expiration: 86400000  # 24 horas em ms
```

### 4️⃣ Executar Backend

```bash
cd backend

# Com Maven Wrapper (recomendado)
./mvnw clean install
./mvnw spring-boot:run

# Ou com Maven instalado local
mvn clean install
mvn spring-boot:run

# Backend rodando em: http://localhost:8080
# Swagger disponível em: http://localhost:8080/swagger-ui/index.html
```

### 5️⃣ Executar Frontend

```bash
cd frontend

# Instalar dependências
npm install
# ou
yarn install

# Iniciar servidor Angular em modo desenvolvimento
npm start
# ou
yarn start

# Frontend rodando em: http://localhost:4200
```

### 📋 Acessar a Aplicação

- **Frontend:** `http://localhost:4200`
- **Backend API:** `http://localhost:8080`
- **Swagger API Docs:** `http://localhost:8080/swagger-ui/index.html`

**Credenciais Padrão:**
- Admin: `admin@admin.com` / `admin123`
- Usuário: `user@user.com` / `user123`

---

## 🧪 Executar Testes

### Backend (JUnit 5 + Mockito)

```bash
cd backend

# Todos os testes
mvn test

# Com cobertura
mvn test jacoco:report

# Testes específicos
mvn test -Dtest=ClienteControllerTest
mvn test -Dtest=ContaServiceTest
```

### Frontend (Karma + Jasmine)

```bash
cd frontend

# Executar testes
npm run test

# Com coverage
ng test --code-coverage
```

---

## 📦 Dependências Principais

### Backend
```xml
<!-- Spring Boot -->
<spring-boot-starter-web>
<spring-boot-starter-data-jpa>
<spring-boot-starter-security>

<!-- JWT -->
<jjwt-api>
<jjwt-impl>
<jjwt-jackson>

<!-- MySQL -->
<mysql-connector-java>

<!-- Validation -->
<spring-boot-starter-validation>

<!-- OpenAPI/Swagger -->
<springdoc-openapi-starter-webmvc-ui>
```

### Frontend
```json
{
  "@angular/core": "^17.3.0",
  "@angular/material": "^17.3.10",
  "primeng": "^17.18.9",
  "tailwindcss": "^3.x",
  "rxjs": "^7.8.0",
  "ng-apexcharts": "^1.8.0",
  "ngx-toastr": "^19.0.0"
}
```

---

## 🎯 Fluxo de Autenticação

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │
       │ 1. POST /api/v1/auth/authenticate
       │    { email, senha }
       ↓
┌──────────────────────────────┐
│   Spring Boot API Server     │
│   (AuthController)           │
│                              │
│  1. Valida credenciais       │
│  2. Compara com BD           │
│  3. Gera JWT Token           │
└──────┬───────────────────────┘
       │
       │ 2. Response: { token: "eyJ..." }
       ↓
┌──────────────────┐
│   LocalStorage   │ ← Token armazenado
└──────┬───────────┘
       │
       │ 3. Requisições subsequentes incluem header:
       │    Authorization: Bearer eyJ...
       ↓
┌──────────────────────────────┐
│   Spring Boot API Server     │
│   (JwtAuthenticationFilter)  │
│                              │
│  1. Extrai token do header   │
│  2. Valida token             │
│  3. Cria Authentication      │
│  4. Passa para SecurityContext
└──────────────────────────────┘
```

---

## 📚 Documentação da API

Uma vez que ambos os servidores estiverem rodando, acesse:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

Aqui você pode testar todos os endpoints diretamente.

---

## 💻 Desenvolvimento Local

### Scripts Úteis

**Backend:**
```bash
cd backend
./mvnw clean install      # Compilar
./mvnw spring-boot:run    # Rodar
mvn test                  # Testes
mvn clean package         # Build para produção
```

**Frontend:**
```bash
cd frontend
npm install               # Instalar deps
npm start                 # Rodar dev server
ng build                  # Build produção
npm run lint              # ESLint
npm run test              # Testes
```

### Variáveis de Ambiente (Frontend)

**Arquivo:** `frontend/src/environments/environment.ts`

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

---

## 🏅 Padrões e Boas Práticas

### Backend
- ✅ **MVC/Layered Architecture** - Controllers → Services → Repositories
- ✅ **DTO Pattern** - Separa interno do externo
- ✅ **Repository Pattern** - Acesso a dados
- ✅ **Global Exception Handler** - Erros centralizados
- ✅ **JWT Authentication** - Stateless
- ✅ **RBAC** - Role-Based Access Control
- ✅ **Unit Tests** - JUnit 5 + Mockito

### Frontend
- ✅ **Smart/Dumb Components** - Container/Presentational
- ✅ **Services** - Lógica compartilhada
- ✅ **Guards** - Proteção de rotas
- ✅ **Interceptors** - HTTP global
- ✅ **RxJS** - Programação reativa
- ✅ **Typed** - TypeScript strict

---

## 🐛 Troubleshooting

### Backend não conecta no MySQL
```bash
# Verifique se MySQL está rodando
mysql -u root -p

# Verifique application.yml
# Url, usuário e senha devem estar corretos
```

### CORS error no Frontend
```
Verifique backend/src/main/java/.../config/CorsConfig.java
Certifique-se que http://localhost:4200 está na whitelist
```

### JWT Token inválido
```
Verifique se a chave secreta em application.yml 
está igual em ambas as requisições (encode/decode)
```

### Frontend não conecta na API
```
Verifique environment.ts
apiUrl deve apontar para http://localhost:8080
```

---

## 👨‍💻 Autor

<div align="center">
  <img src="https://github.com/wallacemaia2007.png" width="100px" style="border-radius: 50%;" alt="Wallace Maia"/>
  
  **Wallace Maia**  
  *Desenvolvedor Full Stack - Java/Spring + Angular*
  
  [![LinkedIn](https://img.shields.io/badge/-LinkedIn-0A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/wallacemaia-dev/)
  [![GitHub](https://img.shields.io/badge/-GitHub-181717?style=flat&logo=github&logoColor=white)](https://github.com/wallacemaia2007)
  [![Email](https://img.shields.io/badge/-Email-EA4335?style=flat&logo=gmail&logoColor=white)](mailto:wallacemaia2007@gmail.com)
</div>

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo LICENSE para mais detalhes.

---

## 🌟 Agradecimentos

Desenvolvido com ❤️ usando as melhores tecnologias do mercado.

**Stack escolhido:**
- Backend: Spring Boot 3 + Spring Security + JWT
- Frontend: Angular 17 + Material + Tailwind
- Database: MySQL 8
- Testes: JUnit 5 + Mockito + Karma/Jasmine

---

**Última atualização:** Março 2026  
**Status:** ✅ Em desenvolvimento contínuo


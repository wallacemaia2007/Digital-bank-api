# 🏦 Digital Bank Web - Frontend

<div align="center">
  <img src="https://img.shields.io/badge/Angular-17.3+-DD0031?style=for-the-badge&logo=angular&logoColor=white" alt="Angular">
  <img src="https://img.shields.io/badge/TypeScript-5.0+-3178C6?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript">
  <img src="https://img.shields.io/badge/Tailwind-CSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white" alt="Tailwind">
  <img src="https://img.shields.io/badge/Angular%20Material-17.3+-3F51B5?style=for-the-badge&logo=material-design&logoColor=white" alt="Material">
</div>

<div align="center">
  <h3>Interface moderna para sistema bancário digital</h3>
  <p>Frontend responsivo e otimizado com Angular 17, Material Design e Tailwind CSS</p>
</div>

---

## 📋 Sobre

Frontend da aplicação **Digital Bank** - uma plataforma bancária completa com:

- ✅ Autenticação e autorização (JWT)
- ✅ Dashboard com gráficos interativos
- ✅ Operações bancárias (depósito, saque, transferência)
- ✅ Gestão de usuários (admin)
- ✅ Interface responsiva para mobile/desktop
- ✅ Componentes reutilizáveis

---

## 🛠 Tecnologias

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Angular** | 17.3+ | Framework principal |
| **TypeScript** | 5.0+ | Linguagem |
| **Tailwind CSS** | 3.x | Styling |
| **SCSS** | - | Pré-processador CSS |
| **Angular Material** | 17.3+ | Componentes UI |
| **PrimeNG** | 17.18+ | Componentes avançados |
| **RxJS** | 7.8+ | Programação reativa |
| **ApexCharts** | 1.8+ | Gráficos |
| **ngx-toastr** | 19.0+ | Notificações |
| **ngx-mask** | 18.0+ | Máscaras de entrada |

---

## 📁 Estrutura do Projeto

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/                    # Serviços e configurações core
│   │   │   ├── auth/                # Autenticação e guards
│   │   │   ├── interceptors/        # Interceptadores HTTP
│   │   │   └── models/              # Interfaces de dados
│   │   │
│   │   ├── modules/
│   │   │   ├── public/              # Rotas públicas (login, signup)
│   │   │   ├── home/                # Página inicial
│   │   │   ├── gerencial/           # Dashboard admin
│   │   │   └── shared/              # Componentes compartilhados
│   │   │
│   │   ├── app.component.ts
│   │   ├── app.routes.ts
│   │   └── app.config.ts
│   │
│   ├── assets/                      # Imagens e recursos
│   ├── environments/                # Configurações por ambiente
│   ├── styles.scss                  # Estilos globais
│   └── main.ts
│
├── angular.json
├── tailwind.config.js
├── tsconfig.json
├── package.json
└── README.md
```

---

## 🚀 Como Rodar

### Pré-requisitos
- **Node.js** 18+ ([Download](https://nodejs.org/))
- **npm** ou **yarn**

### Instalação

```bash
# Clonar repositório
git clone https://github.com/wallacemaia2007/digital-bank-api.git
cd digital-bank-api/frontend

# Instalar dependências
npm install
```

### Desenvolvimento

```bash
# Rodar servidor local
npm start

# Ou com ng
ng serve

# Acessar: http://localhost:4200
```

### Build Produção

```bash
# Build otimizado
npm run build

# Arquivos em: dist/projeto-web/
```

---

## 📦 Scripts Disponíveis

```bash
npm start           # Inicia servidor dev (:4200)
npm run build       # Build produção
npm run watch       # Watch mode
npm run lint        # ESLint
npm test            # Testes (Karma)
ng serve            # Alternativa para npm start
```

---

## 🎨 Componentes Principais

### Páginas Públicas
- **Landing Page** - Página inicial atraente
- **Sign In** - Login com email/senha
- **Sign Up** - Cadastro de novo usuário

### Páginas Autenticadas
- **Home** - Dashboard do usuário
- **Operações** - Depósitos, saques, transferências
- **Histórico** - Transações

### Painel Admin
- **Dashboard** - Gráficos e estatísticas
- **Usuários** - CRUD de usuários
- **Transações** - Histórico completo

### Componentes Compartilhados
```
shared/components/
├── base-button/         - Botão padrão
├── common-table/        - Tabela com paginação
├── loading/             - Spinner
├── page-header/         - Cabeçalho padrão
├── sidenav/             - Menu lateral
└── user-card/           - Card do usuário
```

---

## 🔐 Autenticação

### Fluxo de Login
1. Usuário entra com email e senha
2. Frontend envia para `POST /api/v1/auth/authenticate`
3. Backend retorna JWT token
4. Token armazenado em `localStorage`
5. Requests incluem header: `Authorization: Bearer {token}`

### Configuração (environment.ts)

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### Interceptor Global
Adiciona token automaticamente a todas as requisições:
```typescript
// core/interceptors/global-error/global-error.interceptor.ts
```

---

## 🛡️ Guards de Rota

```typescript
// Protege rotas autenticadas
import { AuthGuard } from '@app/core/auth/auth.guard';

// app.routes.ts
{
  path: 'home',
  canActivate: [AuthGuard],
  component: HomeComponent
}
```

---

## 🎯 Modelos de Dados

### User
```typescript
interface User {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  role: 'USER' | 'ADMIN';
  dataCriacao: Date;
}
```

### Conta
```typescript
interface Conta {
  id: number;
  clienteId: number;
  tipoConta: 'CC' | 'CP';
  saldo: number;
  dataCriacao: Date;
}
```

### Transacao
```typescript
interface Transacao {
  id: number;
  contaId: number;
  tipo: 'DEPOSITO' | 'SAQUE' | 'TRANSFERENCIA';
  valor: number;
  dataTransacao: Date;
}
```

---

## 🧪 Testes

```bash
# Rodar testes
npm run test

# Com coverage
ng test --code-coverage

# Apenas um arquivo
ng test --include='**/seu-component.spec.ts'
```

---

## 🎨 Estilos

### Tailwind CSS
```scss
// Configurado em tailwind.config.js
// Classes utilitárias disponíveis em todos os templates
<div class="flex justify-center items-center bg-blue-500">
```

### SCSS Global
```scss
// src/styles.scss
// Variáveis, mixins e estilos globais
```

### Angular Material
```typescript
// Componentes Material disponíveis
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
```

---

## 📡 Comunicação com Backend

### HTTPClient Service
```typescript
// Serviços usam HttpClient para requisições
constructor(private http: HttpClient) {}

// GET
this.http.get('/api/resource')

// POST
this.http.post('/api/resource', data)

// Interceptor trata erros globais
```

---

## 🌐 CORS

Backend deve estar configurado com CORS:
```
Origem permitida: http://localhost:4200
```

Se receber erro CORS:
1. Verifique CORS no backend
2. Certifique que `apiUrl` em environment.ts está correto
3. Verifique se backend está rodando

---

## 📱 Responsividade

Projeto totalmente responsivo com:
- Mobile first approach
- Breakpoints Tailwind
- Menu móvel em sidenav

```scss
// Tailwind Breakpoints
sm: 640px
md: 768px
lg: 1024px
xl: 1280px
2xl: 1536px
```

---

## 🐛 Troubleshooting

### Porta 4200 já em uso
```bash
ng serve --port 4300
```

### Limpeza de cache
```bash
rm -rf node_modules package-lock.json
npm install
```

### Build erro
```bash
npm run lint --fix
npm run build
```

### Token expirado
Faça login novamente - novo token será gerado.

---

## 📚 Recursos

- [Angular Docs](https://angular.io)
- [Angular Material](https://material.angular.io)
- [Tailwind CSS](https://tailwindcss.com)
- [TypeScript](https://www.typescriptlang.org)
- [RxJS](https://rxjs.dev)

---

## 👨‍💻 Autor

**Wallace Maia**  
Frontend Developer  
[GitHub](https://github.com/wallacemaia2007) | [LinkedIn](https://www.linkedin.com/in/wallacemaia-dev/)

---

**Status:** ✅ Em desenvolvimento  
**Última atualização:** Março 2026

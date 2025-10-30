# 🏛️ Smart Legal API

API REST para gerenciamento completo de escritório de advocacia com integração de IA para geração de petições.

## 📋 Funcionalidades

- ✅ **Gestão de Advogados** - CRUD completo com autenticação
- ✅ **Gestão de Clientes** - Cadastro e gerenciamento de clientes
- ✅ **Gestão de Processos** - Controle completo de processos judiciais
- ✅ **Andamentos Processuais** - Histórico de movimentações
- ✅ **Documentos** - Upload e gerenciamento de arquivos
- 🤖 **Geração de Petições com IA** - Utilizando Google Gemini AI
- 🔐 **Autenticação JWT** - Sistema de login seguro
- 📊 **Sistema de Auditoria** - Log de todas as ações
- 📈 **Monitoramento** - Métricas e health checks

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.5.6**
- **MySQL** - Banco de dados relacional
- **Flyway** - Migrações de banco de dados
- **Spring Security + JWT** - Autenticação e autorização
- **Swagger/OpenAPI** - Documentação interativa da API
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento de objetos
- **Apache POI** - Geração de documentos Word
- **Logback + AOP** - Sistema de logs avançado
- **Caffeine** - Cache em memória
- **Actuator + Prometheus** - Monitoramento

## 📁 Estrutura do Projeto (DDD)

```
src/main/java/smartLegalApi/
├── domain/              # Camada de Domínio (Regras de Negócio)
├── application/         # Camada de Aplicação (Casos de Uso)
├── infrastructure/      # Camada de Infraestrutura (Persistência, Serviços Externos)
└── presentation/        # Camada de Apresentação (Controllers, DTOs)
```

## 🚀 Como Rodar

### Pré-requisitos

- Java 17+
- Maven 3.9+
- MySQL 8.0+
- (Opcional) Docker

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/smart-legal-api.git
cd smart-legal-api
```

### 2. Configurar Banco de Dados

Certifique-se de que o MySQL está rodando e crie o banco:

```sql
CREATE DATABASE AdvocaciaDB;
```

### 3. Configurar Variáveis de Ambiente

Copie o arquivo de exemplo e preencha com suas credenciais:

```bash
cp .env.example .env
```

Edite o `.env` com seus valores reais.

### 4. Instalar Dependências

```bash
mvn clean install
```

### 5. Rodar a Aplicação

```bash
# Desenvolvimento
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Ou via IDE
# Execute a classe SmartlegalBootApplication.java
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

Após iniciar a aplicação, acesse:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🧪 Testando a API

### Opção 1: Swagger UI (Recomendado)

1. Acesse `http://localhost:8080/swagger-ui.html`
2. Você verá todos os endpoints documentados
3. Clique em um endpoint → "Try it out" → Preencha os campos → "Execute"

### Opção 2: Postman

1. Importe a collection do Swagger
2. Configure o ambiente com `BASE_URL=http://localhost:8080`
3. Faça requisições aos endpoints

### Opção 3: cURL

```bash
# Criar advogado
curl -X POST http://localhost:8080/api/advogados \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "oab": "SP123456",
    "cpf": "12345678901",
    "email": "joao@example.com",
    "telefone": "11988888888",
    "senha": "senha123"
  }'

# Listar advogados
curl http://localhost:8080/api/advogados
```

## 🗂️ Estrutura do Banco de Dados

O Flyway cria automaticamente as seguintes tabelas:

- `advogado` - Dados dos advogados
- `clientes` - Dados dos clientes
- `processos` - Processos judiciais
- `andamentos` - Andamentos processuais
- `documentos` - Documentos anexados
- `peticoes` - Petições geradas
- `audit_log` - Log de auditoria

## 🔐 Autenticação

A API usa JWT para autenticação. Após fazer login, você receberá um token que deve ser incluído no header de todas as requisições protegidas:

```
Authorization: Bearer seu-token-jwt-aqui
```

## 📊 Monitoramento

Endpoints do Actuator disponíveis:

- **Health:** [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- **Metrics:** [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
- **Prometheus:** [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

## 🐳 Docker (Em breve)

```bash
# Build
docker build -t smart-legal-api .

# Run
docker-compose up
```

## 📝 Próximos Passos (Sprints)

- [x] **Sprint 1:** Fundação e Infraestrutura ✅
- [ ] **Sprint 2:** Módulo de Advogados
- [ ] **Sprint 3:** Módulo de Clientes
- [ ] **Sprint 4:** Módulo de Processos
- [ ] **Sprint 5:** Segurança e Autenticação
- [ ] **Sprint 6:** Módulo de Petições + IA
- [ ] **Sprint 7:** Sistema de Logs e Auditoria
- [ ] **Sprint 8:** Cache e Performance
- [ ] **Sprint 9:** Validações e Regras de Negócio
- [ ] **Sprint 10:** Finalização e Documentação

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença Apache 2.0. Veja o arquivo `LICENSE` para mais detalhes.

## 👥 Autores

- **Time Smart Legal** - [contato@smartlegal.com.br](mailto:contato@smartlegal.com.br)

---

⚖️ **Smart Legal** - Tecnologia a serviço da advocacia

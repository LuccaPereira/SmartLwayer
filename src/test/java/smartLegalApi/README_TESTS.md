# 🧪 Estrutura de Testes - Smart Legal API

## 📂 Organização dos Diretórios

A estrutura de testes espelha a arquitetura da aplicação, seguindo os princípios do DDD (Domain-Driven Design):

```
src/test/java/smartLegalApi/
│
├── domain/                      # Testes de Entidades de Domínio
│   ├── advogado/
│   │   └── entity/             # Testes de Advogado.java
│   ├── cliente/
│   │   └── entity/             # Testes de Cliente.java
│   ├── processo/
│   │   └── entity/             # Testes de Processo, Andamento, Documento
│   ├── peticao/
│   │   └── entity/             # Testes de Peticao.java
│   └── audit/
│       └── entity/             # Testes de AuditLog.java
│
├── application/                 # Testes de Use Cases (Regras de Negócio)
│   ├── advogado/
│   │   └── usecase/            # Testes dos 7 use cases de advogado
│   ├── cliente/
│   │   └── usecase/            # Testes dos 7 use cases de cliente
│   ├── processo/
│   │   └── usecase/            # Testes dos 15 use cases de processo
│   ├── peticao/
│   │   └── usecase/            # Testes dos 9 use cases de petição
│   └── auth/
│       └── usecase/            # Testes dos 3 use cases de autenticação
│
├── infrastructure/              # Testes de Infraestrutura
│   ├── security/
│   │   └── service/            # Testes de JwtService, PasswordEncoder, etc.
│   ├── ai/
│   │   └── service/            # Testes de GeminiService, PeticaoPromptService
│   └── persistence/
│       └── adapter/            # Testes de Adapters (integração com JPA)
│
└── presentation/                # Testes de Controllers (REST API)
    └── controller/              # Testes dos endpoints REST
```

## 🎯 Tipos de Testes

### 1. **Testes de Entidades de Domínio** (`domain/`)

- **Objetivo**: Validar regras de negócio puras
- **Características**:
  - Sem dependências externas
  - Testes rápidos
  - Focam em lógica de negócio
- **Exemplo**: Validações de CPF, OAB, regras de negócio

### 2. **Testes de Use Cases** (`application/`)

- **Objetivo**: Testar fluxos completos de negócio
- **Características**:
  - Usam mocks de repositórios
  - Validam orquestração de operações
  - Testam exceções de negócio
- **Exemplo**: Criar advogado, gerar petição com IA

### 3. **Testes de Infraestrutura** (`infrastructure/`)

- **Objetivo**: Validar integrações externas
- **Características**:
  - Podem usar @DataJpaTest
  - Testam mapeamentos e queries
  - Validam integrações (API, BD)
- **Exemplo**: Persistência JPA, chamadas à API Gemini

### 4. **Testes de Controllers** (`presentation/`)

- **Objetivo**: Testar endpoints REST
- **Características**:
  - Usam @WebMvcTest
  - Testam serialização JSON
  - Validam status HTTP e respostas
- **Exemplo**: POST /api/advogados, GET /api/processos/{id}

## 🛠️ Tecnologias de Teste

- **JUnit 5**: Framework de testes
- **Mockito**: Mocks e stubs
- **AssertJ**: Assertions fluentes
- **Spring Boot Test**: Integração com Spring
- **Testcontainers**: Containers Docker para testes de integração
- **MockMvc**: Testes de controllers REST

## 📝 Convenções de Nomenclatura

```java
// Padrão: [NomeDaClasse]Test.java

// Exemplos:
AdvogadoTest.java                    // Testa entidade Advogado
CriarAdvogadoUseCaseTest.java        // Testa use case
AdvogadoControllerTest.java          // Testa controller
JwtServiceTest.java                  // Testa serviço
```

## 🧪 Exemplo de Estrutura de Teste

```java
@ExtendWith(MockitoExtension.class)
class CriarAdvogadoUseCaseTest {

    @Mock
    private AdvogadoRepository advogadoRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @InjectMocks
    private CriarAdvogadoUseCase useCase;

    @Test
    void deveCriarAdvogadoComSucesso() {
        // Given (Arrange)
        // ...

        // When (Act)
        // ...

        // Then (Assert)
        // ...
    }

    @Test
    void deveLancarExcecaoQuandoOABJaExiste() {
        // ...
    }
}
```

## ✅ Checklist de Testes

### Entidades de Domínio

- [ ] Validações de Value Objects (CPF, OAB, Email)
- [ ] Regras de negócio nas entidades
- [ ] Métodos de criação e atualização

### Use Cases

- [ ] Fluxos de sucesso
- [ ] Tratamento de exceções
- [ ] Validações de regras de negócio
- [ ] Integrações entre camadas

### Controllers

- [ ] Status HTTP corretos (200, 201, 404, 400, etc.)
- [ ] Validação de DTOs
- [ ] Serialização/deserialização JSON
- [ ] Autenticação e autorização

### Integração

- [ ] Persistência no banco de dados
- [ ] Transações
- [ ] Consultas customizadas (JPA)

## 🚀 Comandos para Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar testes de uma classe específica
mvn test -Dtest=CriarAdvogadoUseCaseTest

# Executar testes de um pacote
mvn test -Dtest="smartLegalApi.application.advogado.**"

# Executar com cobertura de código
mvn test jacoco:report

# Pular testes
mvn install -DskipTests
```

## 📊 Cobertura de Código

Meta: **80% de cobertura** em todas as camadas

- **Domínio**: 90%+ (crítico)
- **Application**: 85%+ (importante)
- **Infrastructure**: 70%+
- **Presentation**: 75%+

## 📚 Recursos Adicionais

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [AssertJ](https://assertj.github.io/doc/)

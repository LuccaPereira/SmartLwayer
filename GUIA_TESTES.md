# 🧪 Guia de Testes - Smart Legal API

Este guia ensina como testar a API durante o desenvolvimento.

## 📋 Índice

1. [Preparação do Ambiente](#preparação-do-ambiente)
2. [Testando com Swagger](#testando-com-swagger)
3. [Testando com Postman](#testando-com-postman)
4. [Testando com cURL](#testando-com-curl)
5. [Testes Automatizados](#testes-automatizados)

---

## 🔧 Preparação do Ambiente

### 1. Certifique-se de que o MySQL está rodando

```bash
# Linux/Mac
sudo systemctl status mysql

# Windows (como Admin)
net start MySQL80
```

### 2. Inicie a aplicação

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Aguarde até ver:

```
Started SmartlegalBootApplication in X.XXX seconds
```

### 3. Verifique se a API está UP

Abra o navegador em:

```
http://localhost:8080/actuator/health
```

Deve retornar:

```json
{
  "status": "UP"
}
```

---

## 🎨 Testando com Swagger (RECOMENDADO)

### Por que usar Swagger?

- ✅ Interface visual intuitiva
- ✅ Documentação automática
- ✅ Testa diretamente no navegador
- ✅ Vê os schemas dos objetos
- ✅ Não precisa instalar nada

### Como usar:

1. **Abra o Swagger UI:**

   ```
   http://localhost:8080/swagger-ui.html
   ```

2. **Você verá grupos de endpoints:**

   - 📋 **Advogado Controller** - Gestão de advogados
   - 👥 **Cliente Controller** - Gestão de clientes
   - 📄 **Processo Controller** - Gestão de processos
   - ... e outros

3. **Para testar um endpoint:**

   **Exemplo: Criar um Advogado**

   a) Clique em **"Advogado Controller"** para expandir

   b) Clique em **POST /api/advogados**

   c) Clique no botão **"Try it out"**

   d) Preencha o JSON no corpo da requisição:

   ```json
   {
     "nome": "Dr. João Silva",
     "oab": "SP123456",
     "cpf": "12345678901",
     "email": "joao.silva@example.com",
     "telefone": "11988888888",
     "senha": "senha123"
   }
   ```

   e) Clique em **"Execute"**

   f) Veja a resposta abaixo:

   ```json
   {
     "id": 1,
     "nome": "Dr. João Silva",
     "oab": "SP123456",
     "email": "joao.silva@example.com",
     "dataCadastro": "2024-01-15T10:30:00"
   }
   ```

4. **Testando endpoints protegidos (com JWT):**

   a) Primeiro, faça login:

   - Vá em **POST /api/auth/login**
   - Try it out
   - Envie: `{"oab": "SP123456", "senha": "senha123"}`
   - Copie o token da resposta

   b) Clique no botão **"Authorize"** (cadeado no topo)

   c) Cole o token: `Bearer seu-token-aqui`

   d) Clique em **"Authorize"** e depois **"Close"**

   e) Agora você pode testar endpoints protegidos!

---

## 📮 Testando com Postman

### Instalação

[Download Postman](https://www.postman.com/downloads/)

### Configuração Inicial

1. **Criar uma Collection:**

   - Clique em "New" → "Collection"
   - Nome: "Smart Legal API"

2. **Configurar Variáveis:**
   - Clique na collection → "Variables"
   - Adicione:
     - `base_url` = `http://localhost:8080`
     - `token` = (vazio por enquanto)

### Exemplos de Requisições

#### 1. Criar Advogado

```
POST {{base_url}}/api/advogados
Content-Type: application/json

{
  "nome": "Dr. João Silva",
  "oab": "SP123456",
  "cpf": "12345678901",
  "email": "joao@example.com",
  "telefone": "11988888888",
  "senha": "senha123"
}
```

#### 2. Login

```
POST {{base_url}}/api/auth/login
Content-Type: application/json

{
  "oab": "SP123456",
  "senha": "senha123"
}
```

Depois do login:

- Copie o token da resposta
- Vá em "Variables" da collection
- Cole no valor de `token`

#### 3. Listar Advogados (Protegido)

```
GET {{base_url}}/api/advogados
Authorization: Bearer {{token}}
```

#### 4. Criar Cliente

```
POST {{base_url}}/api/clientes
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "nomeCompleto": "Maria Santos",
  "cpfCnpj": "98765432100",
  "email": "maria@example.com",
  "telefone": "11977777777",
  "endereco": "Rua das Flores, 123 - São Paulo/SP"
}
```

### Dica: Importar do Swagger

1. No Postman, vá em "Import"
2. Cole a URL: `http://localhost:8080/v3/api-docs`
3. Clique em "Import"
4. Todos os endpoints serão importados automaticamente! 🎉

---

## 💻 Testando com cURL (Terminal)

### Criar Advogado

```bash
curl -X POST http://localhost:8080/api/advogados \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Dr. João Silva",
    "oab": "SP123456",
    "cpf": "12345678901",
    "email": "joao@example.com",
    "telefone": "11988888888",
    "senha": "senha123"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "oab": "SP123456",
    "senha": "senha123"
  }'
```

Salve o token retornado:

```bash
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Listar Advogados (com token)

```bash
curl -X GET http://localhost:8080/api/advogados \
  -H "Authorization: Bearer $TOKEN"
```

### Buscar Advogado por ID

```bash
curl -X GET http://localhost:8080/api/advogados/1 \
  -H "Authorization: Bearer $TOKEN"
```

### Criar Cliente

```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nomeCompleto": "Maria Santos",
    "cpfCnpj": "98765432100",
    "email": "maria@example.com",
    "telefone": "11977777777",
    "endereco": "Rua das Flores, 123"
  }'
```

---

## 🧪 Testes Automatizados

### Rodar Todos os Testes

```bash
mvn test
```

### Rodar Testes de uma Classe Específica

```bash
mvn test -Dtest=AdvogadoControllerTest
```

### Ver Cobertura de Testes (JaCoCo)

```bash
mvn clean test jacoco:report
```

Depois abra: `target/site/jacoco/index.html`

### Exemplo de Teste

```java
@SpringBootTest
@AutoConfigureMockMvc
class AdvogadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCriarAdvogado() throws Exception {
        String advogadoJson = """
            {
                "nome": "João Silva",
                "oab": "SP123456",
                "cpf": "12345678901",
                "email": "joao@email.com",
                "telefone": "11988888888",
                "senha": "senha123"
            }
            """;

        mockMvc.perform(post("/api/advogados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(advogadoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }
}
```

---

## 🎯 Fluxo Completo de Teste

### 1. Cadastrar Advogado

```bash
POST /api/advogados
```

### 2. Fazer Login

```bash
POST /api/auth/login
# Guarde o token!
```

### 3. Criar Cliente

```bash
POST /api/clientes
Authorization: Bearer {token}
```

### 4. Criar Processo

```bash
POST /api/processos
Authorization: Bearer {token}

{
  "numeroProcesso": "1234567-89.2024.8.26.0100",
  "titulo": "Ação de Cobrança",
  "descricao": "Cobrança de dívida",
  "idAdvogado": 1,
  "idCliente": 1,
  "dataAbertura": "2024-01-15"
}
```

### 5. Gerar Petição com IA

```bash
POST /api/peticoes/gerar
Authorization: Bearer {token}

{
  "tipoAcao": "COBRANCA",
  "nomeAutor": "Maria Santos",
  "nomeReu": "João Devedor",
  "motivoAcao": "Não pagamento de dívida"
}
```

---

## 🐛 Troubleshooting

### Erro: "Connection refused"

✅ Certifique-se de que a aplicação está rodando:

```bash
netstat -an | grep 8080
```

### Erro: "401 Unauthorized"

✅ Seu token expirou ou é inválido. Faça login novamente.

### Erro: "404 Not Found"

✅ Verifique se o endpoint está correto. Veja no Swagger.

### Erro: "500 Internal Server Error"

✅ Veja os logs da aplicação:

```bash
tail -f logs/smartlegal-dev.log
```

---

## 📚 Recursos Adicionais

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/v3/api-docs
- **Health Check:** http://localhost:8080/actuator/health
- **Metrics:** http://localhost:8080/actuator/metrics

---

✅ **Pronto! Agora você sabe como testar a API de todas as formas!**

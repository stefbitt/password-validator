# 🔐 Password Validator API

API REST para validação de senhas com múltiplas regras de segurança. Desenvolvida com Spring Boot, valida se uma senha atende critérios como complexidade, comprimento, ausência de espaços e caracteres repetidos.

---

## 📋 Regras de Validação

Uma senha válida precisa atender **todas** as seguintes regras:

- ✅ Conter pelo menos **1 letra maiúscula**
- ✅ Conter pelo menos **1 letra minúscula**
- ✅ Conter pelo menos **1 número**
- ✅ Conter pelo menos **1 caractere especial** (ex: `!@#$%^&*()-+`)
- ✅ Ter **no mínimo 9 caracteres**
- ✅ **Não conter espaços**
- ✅ **Não conter caracteres repetidos**

---

## 📦 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.itau.password_validator
│   │       ├── config         # Configurações da aplicação (ex: beans, cors, etc.)
│   │       ├── controller     # Camada de entrada da API (REST controllers)
│   │       ├── exception      # Exceções personalizadas e handlers globais
│   │       ├── model
│   │       │   ├── request    # Modelos de entrada (DTOs da API)
│   │       │   └── response   # Modelos de saída (DTOs da API)
│   │       ├── service        # Lógica de negócio principal (PasswordValidatorService)
│   │       └── validator      # Regras individuais de validação de senha (PasswordRule e implementações)
│   └── resources
│       └── application.properties   # Configurações da aplicação

src
└── test
    ├── java
    │   └── com.itau.password_validator
    │       ├── unit
    │       │   ├── controller   # Testes unitários dos controllers
    │       │   ├── exception    # Testes unitários de handlers e exceções
    │       │   ├── service      # Testes unitários de serviços
    │       │   └── validator    # Testes unitários das regras
    │       └── integration
    │           ├── runner       # Runner do Cucumber com integração JUnit
    │           └── steps        # Step Definitions dos testes BDD
    └── resources
        └── features
            ├── cucumber.properties     # Configuração do Cucumber
            └── password_validation.feature # Cenários BDD (Gherkin)─ Integration
```

---

## 🚀 Como executar

git clone https://github.com/stefbitt/password_validator

cd password_validator

mvn clean install

mvn spring-boot:run<br>

A aplicação será iniciada na porta 80.

### 1. Requisitos

- Java 21+
- docker
- Maven 3.8+
- IDE (IntelliJ, Eclipse ou VS Code)

### 2. Build

```bash
mvn clean install
```

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

Ou execute a classe `PasswordValidatorApplication` via sua IDE.

## Local
POST http://localhost:80/api/v1/password/validate

## Cloud Aws
POST https://o5u5gjbv2j.execute-api.us-east-1.amazonaws.com/dev/api/v1/password/validate

# Docker
para subir a aplicação através do docker basta executar os comandos abaixo

POST http://localhost:80/api/v1/password/validate

```bash
docker build -t validator-password:latest .
docker run validator-password:latest 80:80
```

---

## 📮 Endpoint

### POST `/api/v1/password/validate`

#### Request

```json
{
  "password": "Abc123!@#"
}
```

#### Response

```json
{
  "isValid": true,
  "errors": []
}
```

Ou em caso de erro:

```json
{
  "isValid": false,
  "errors": [
    "Password must contain at least one uppercase letter",
    "Password must not contain repeated characters"
  ]
}
```

---

## ✅ Testes

### 1. Testes unitários

Os testes unitários cobrem todos os validadores e serviços da aplicação.

```bash
mvn test
```

### 2. Testes BDD com Cucumber

#### Executar os testes BDD:

```bash
mvn verify
```

Exemplo de cenário:

```gherkin
Scenario Outline: Validate password and check response
  Given the password "<password>"
  When the password is sent to the API
  Then the response status should be <status>
  And the field "isValid" should be <isValid>
  And the errors list size should be <errorsSize>

  Examples:
    | password       | status | isValid | errorsSize |
    | Abcdef1!@      | 200    | true    | 0          |
    | abc abc        | 200    | false   | 3          |
```

---

## 📚 Tecnologias

- Java 21
- Spring Boot
- Maven
- JUnit 5
- Cucumber
- Mockito
- Lombok

---

## 👨‍💻 Autor

Desenvolvido por Stefany – Projeto técnico de validação de senhas.

---

## 🛡️ Licença

Este projeto é livre para uso educacional.

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
│   └── java
│       └── com.itau.password_validator
│           ├── controller
│           ├── model
│           ├── service
│           ├── validator
│           └── exception
└── test
    └── java
        └── com.itau.password_validator
            ├── service
            ├── validator
            └── bdd
```

---

## 🚀 Como executar

### 1. Requisitos

- Java 17+
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

---

## 📮 Endpoint

### POST `/api/password/validate`

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

#### Feature usada: `password_validation.feature`

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

- Java 17
- Spring Boot
- Maven
- JUnit 5
- Cucumber
- Mockito
- Lombok

---

## 👨‍💻 Autor

Desenvolvido por [Seu Nome Aqui] – Projeto técnico de validação de senhas.

---

## 🛡️ Licença

Este projeto é livre para uso educacional e demonstração.

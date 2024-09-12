# Microserviço responsável pela parte de login do sistema.


## Descrição

O projeto **msLogin** é um microserviço de autenticação e autorização desenvolvido em Java utilizando Spring Boot e Spring Security. Ele implementa a autenticação via JWT (JSON Web Token), permitindo que usuários façam login, e garantindo o controle de acesso aos recursos de forma segura.

## Funcionalidades

- **Autenticação JWT:** Geração de tokens JWT após login bem-sucedido.
- **Autorização:** Verificação de permissões de acesso baseadas em roles.
- **Registro de Usuários:** Registro de novos usuários com criptografia de senhas utilizando BCrypt.
- **Validação de Login:** Lógica de validação de usuários e senhas na camada de serviço.
- **Gestão de Exceções:** Tratamento centralizado de exceções utilizando `ControllerAdvice`.

## Requisitos

- Java 17+
- Spring Boot 3.x
- Maven 3.x

## Estrutura do Projeto

- **Controller:** Camada responsável por gerenciar as requisições HTTP.
- **Service:** Camada onde está a lógica de negócio, incluindo validação de login e registro de usuários.
- **Security:** Configurações de segurança utilizando Spring Security, incluindo filtros para validação de JWT.
- **Repository:** Acesso a dados de usuários utilizando Spring Data JPA.
- **Exception Handling:** Implementação de tratamento global de exceções para garantir respostas consistentes em caso de erros.

## Configuração

1. Clone o repositório:

   ```bash
   git clone https://github.com/henriquegardini/msLogin.git
    ```

2. Accesse o diretório do projeto:

   ```bash
   cd msLogin
   ```

3. Instale as dependências do Maven:

   ```bash
   mvn clean install
   ```

4. Configure o banco de dados no arquivo application.properties ou application.yml.

5. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

## Uso

### Endpoints disponíveis:
- **POST /login:** Autenticação de usuários. Retorna um token JWT válido por 1 hora.
- **POST /register:** Registro de novos usuários.
- **GET /users:** Listagem de todos os usuários (apenas para usuários com role ADMIN).

### Exemplo de Requisição:
**Login**:
```bash
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'
```

**Registro**:
```bash
curl -X POST http://localhost:8080/register -H "Content-Type: application/json" -d '{"username": "user", "password": "user", "role": "ADMIN"}'
```

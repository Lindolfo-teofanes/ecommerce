# Documentação do Projeto Ecommerce

## Visão Geral

Este projeto é uma aplicação de ecommerce baseado em um teste tecnico. 

---

## Tecnologias Utilizadas

- **Java 21**: Linguagem principal para o backend.
- **Spring Boot**: Framework utilizado para agilidade no desenvolvimento.
- **PostgreSQL**: Banco de dados relacional.
- **RabbitMQ**: Mensageria assíncrona para comunicação entre serviços.
- **Docker e Docker Compose**: Para contêinerização e orquestração dos serviços.
- **Gradle**: Sistema de build utilizado no projeto.

---

## Pré-requisitos

- **Docker** e **Docker Compose** instalados
- **Java 21** instalado
- **Intellij** instalado
- **Insomnia** ou outro cliente HTTP para realizar testes (opcional)

---

## Passo a Passo para Execução

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/Lindolfo-teofanes/ecommerce.git
   cd ecommerce
   ```

2. **Configure as variáveis de ambiente no arquivo `docker-compose.yml` (se necessário):**

   ```yaml
   SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/ecommerce
   SPRING_DATASOURCE_USERNAME: lindolfo
   SPRING_DATASOURCE_PASSWORD: az
   SPRING_RABBITMQ_HOST: rabbitmq
   SPRING_RABBITMQ_PORT: 5672
   ```

3. **Suba os containers Docker:**

   No diretório raiz do projeto, execute:
   ```bash
   docker-compose up --build
   ```

4. **Acesse a aplicação:**

   - O backend estará disponível em: [http://localhost:8080](http://localhost:8080)
   - O frontend estará disponível em: [http://localhost:5173](http://localhost:5173)

---

## Endpoints da API

### **Pedidos**
#### 1. Criar Pedido
- **URL**: `/pedidos`
- **Método**: `POST`
- **Descrição**: Cria um novo pedido no sistema.
- **Exemplo de Requisição**:
  ```json
  {
    "nomeCliente": "Lindolfo",
    "endereco": "Rua Cesar Ramos dos Santos, Parque Residencial Rita Vieira, Campo Grande-MS, 79052564",
    "produtos": {"7e2c7c15-ffe6-487c-8b85-f8e31440c433":{"nome":"Pneu","preco":10.99}}
  }
  ```


#### 2. Listar Pedidos
- **URL**: `/pedidos`
- **Método**: `GET`
- **Descrição**: Retorna todos os pedidos cadastrados.
- **Exemplo de Resposta**:
  ```json
  [
    {
        "id": "a0d9409f-6950-4c86-965e-2d7276c2adc1",
        "nomeCliente": "Lindolfo",
        "produtos": "{\"7e2c7c15-ffe6-487c-8b85-f8e31440c433\":{\"nome\":\"Pneu\",\"preco\":10.99}}",
        "status": "Criado",
        "endereco": "Rua Cesar Ramos dos Santos, Parque Residencial Rita Vieira, Campo Grande-MS, 79052564",
        "dataCriacao": "2025-01-13T09:08:31.236552",
        "dataAtualizacao": "2025-01-13T09:08:31.23657"
    }
  ]
  ```

#### 3. Atualizar Status do Pedido
- **URL**: `/pedidos/${id}?status=${status}`
- **Método**: `PATCH`
- **Descrição**: Atualiza o status de um pedido.

#### 4. Listar Produtos
- **URL**: `/produtos`
- **Método**: `GET`
- **Descrição**: Retorna todos os produtos.
- **Exemplo de Resposta**:
  ```json
  [
    {
        "id": "7e2c7c15-ffe6-487c-8b85-f8e31440c433",
        "nome": "Pneu",
        "preco": 10.99,
        "dataCriacao": "2025-01-13T08:04:29.44371",
        "dataAtualizacao": "2025-01-13T08:04:29.44371"
    },
    {
        "id": "f4893207-49e8-4c11-94e0-64f3d908ebeb",
        "nome": "Livro",
        "preco": 20.49,
        "dataCriacao": "2025-01-13T08:04:29.44371",
        "dataAtualizacao": "2025-01-13T08:04:29.44371"
    }
  ]
  ```

#### 5. Busca CEP
- **URL**: `/api/cep/{cep}`
- **Método**: `GET`
- **Descrição**: Retorna o endereço.
- **Exemplo de Resposta**:
  ```json
  "Rua Cesar Ramos dos Santos, Parque Residencial Rita Vieira, Campo Grande-MS, 79052564"
  ```
---

## Melhorias Futuras

- Implementar um front responsivo.
- Desenvolver um sistema de CI/CD para automatizar o deploy.

---


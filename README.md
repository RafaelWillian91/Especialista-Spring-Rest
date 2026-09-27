## Sobre o projeto

Este repositório reúne a implementação desenvolvida durante o curso
Especialista Spring REST, da AlgaWorks.

O projeto tem foco em desenvolvimento Back-End com Java e Spring Boot,
explorando desde fundamentos de APIs REST até tópicos mais avançados
de persistência, validação, tratamento de erros, testes e cache HTTP.

## Principais conceitos aplicados

- Spring Boot
- Spring MVC
- REST APIs
- JPA / Hibernate
- Spring Data JPA
- MySQL
- Flyway
- Bean Validation
- Tratamento global de exceções
- Testes de integração
- DTOs
- Upload e download de arquivos
- Domain Events
- Cache HTTP e ETag

- ## Arquitetura

Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL

## Exemplos de endpoints

GET /restaurantes
GET /pedidos/{codigo}
POST /pedidos
PUT /restaurantes/{id}
DELETE /formas-pagamento/{id}

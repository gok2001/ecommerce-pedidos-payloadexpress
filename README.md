# Sistema de Gestão de Pedidos — E-commerce

> Atividade desafiadora da Unidade Curricular **Desenvolvimento Back-end**
> Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas — Turma CSTADS601

## Equipe / Squad

| Nome | Papel na Aula 01 |
|---|---|
| _Guilherme Orige Kernbichler_ | Responsável do dia |
| _Davi Livino Mazoti_ | |
| _João Paulo Arábia da Silva_ | |

## Descrição do desafio

_A equipe de desenvolvimento Payload Express, recebeu a demanda de construir um sistema de gestão 
de pedidos para um e-commerce de produtos eletrônicos, contemplando cadastro de produtos,
clientes, pedidos e processamento de pagamentos._

## Funcionalidades previstas

- [ ] Cadastro e gerenciamento de produtos - Davi
- [ ] Cadastro e gerenciamento de clientes - João
- [ ] Criação e gerenciamento de pedidos - Guilherme
- [ ] Processamento de pagamentos (cartão, boleto, Pix) - João
- [ ] Testes automatizados (unitários e de integração) - Guilherme
- [ ] Pipeline de CI/CD - Davi
- [ ] API REST para consumo por um front-end - João, Davi, Guilherme

## Tecnologias

- Java
- Maven
- Git / GitHub
- _(demais tecnologias serão adicionadas ao longo do semestre: JUnit, Spring Boot, banco de
  dados, GitHub Actions...)_

## Estrutura de pastas

```
ecommerce-pedidos-payloadexpress/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/senai/ecommerce/
│   │           ├── modelo/
│   │           ├── servico/
│   │           ├── repositorio/
│   │           └── util/
│   └── test/
│       └── java/
│           └── com/senai/ecommerce/
├── pom.xml
├── README.md
└── .gitignore
```

## Como rodar o projeto

_(Preencher a partir das próximas aulas, conforme o projeto evoluir.)_

## Roadmap do projeto (por aula)

| Aula | Entrega |
|---|---|
| 01 | Repositório criado, estruturado, com README e commit inicial |
| 02 | Fluxo de branches e primeiro Pull Request revisado |
| 03 | Classe utilitária (Utils) do domínio |
| 04 | Classes de domínio inicial (Produto, Cliente, Pedido, ItemPedido) |
| 05 | Encapsulamento e abstração aplicados |
| 06 | Hierarquia de formas de pagamento (herança) |
| 07 | Relacionamentos entre classes do domínio |
| 08 | Módulo de pagamento polimórfico |
| 09 | Tratamento de exceções |
| 10 | Suíte de testes unitários |
| 11 | Suíte de testes de integração + relatório de cobertura |
| 12 | Persistência: conexão, Create e Read |
| 13 | Persistência: Update, Delete e padrão DAO/Repository |
| 14 | Migração para Spring Boot |
| 15 | API REST + pipeline CI/CD |
| 16 | Entrega final, documentação e apresentação |

## Combinado da equipe (ética e convivência)

1. _Respeito mútuo_
2. _Colaboração entre os integrantes_
3. _Divisão justa das tarefas_

## Licença

Projeto acadêmico — Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe".
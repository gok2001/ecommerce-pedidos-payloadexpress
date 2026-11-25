# Aula 09 — Critério e mapa de exceções

## Critério adotado

As exceções de negócio recuperáveis são **checked**. O código que chama a operação precisa capturá-las e decidir como continuar, por exemplo oferecendo outro produto ou forma de pagamento. Erros causados por dados inválidos ou por uma chamada incompatível com o estado do objeto são **unchecked**, pois devem ser corrigidos na entrada ou no código chamador.

A raiz `ECommerceException` estende `Exception`. As exceções de estoque, pagamento recusado, pedido inválido e cliente não encontrado herdam dessa raiz. Validações de argumentos usam `IllegalArgumentException`, e operações inválidas no estado atual usam `IllegalStateException`.

## Classificação das situações do roteiro

| Situação | Classificação | Exceção aplicada ou esperada | Justificativa |
|---|---|---|---|
| Estoque insuficiente para o item pedido | Checked | `EstoqueInsuficienteException` | Quem monta o pedido pode reduzir a quantidade ou escolher outro produto. |
| Preço negativo no cadastro de produto | Unchecked | `IllegalArgumentException` | O valor deve ser corrigido antes de cadastrar o produto. |
| Pagamento recusado pelo cartão | Checked | `PagamentoRecusadoException` | O cliente pode tentar outra forma de pagamento. |
| Pedido pago pela segunda vez | Checked | `PedidoInvalidoException` | O fluxo chamador pode informar que o pedido já foi pago e encerrar a tentativa. |
| Cliente não encontrado pelo identificador | Checked | `ClienteNaoEncontradoException` | O fluxo chamador pode pedir outro identificador ou interromper o cadastro do pedido. |
| E-mail em formato inválido | Unchecked | `IllegalArgumentException` | O dado de entrada precisa ser corrigido antes de criar ou atualizar o cliente. |

## Mapa de cenários e registro de execução

O código de demonstração cobre estoque insuficiente, pagamento pendente com boleto e tentativa de pagar um pedido já pago. O teste automatizado existente cobre estoque insuficiente. Os demais casos abaixo precisam ser executados e registrados pela equipe antes da entrega.

| Cenário | Camada de validação/tratamento | Resultado esperado | Execução |
|---|---|---|---|
| Preço negativo no cadastro | Construtor de `Produto` | Rejeitar com mensagem que indique que o preço deve ser maior que zero | Pendente |
| E-mail sem formato válido | Construtor/setter de `Cliente` | Rejeitar e pedir um endereço válido | Pendente |
| Cliente ausente na busca | Busca de cliente | Informar que o identificador não foi encontrado e permitir informar outro | Pendente |
| Adicionar quantidade acima do estoque | Domínio de produto/pedido | Informar estoque disponível e quantidade solicitada, sem alterar o estoque | Demonstrado no `App` e coberto em teste |
| Pagar pedido sem itens | Domínio de pedido | Rejeitar com `PedidoInvalidoException` e informar que é necessário adicionar itens | Pendente |
| Cartão recusado por limite insuficiente | Domínio de pagamento/pedido | Informar a recusa e permitir tentar outra forma de pagamento | Pendente |
| Pagar pedido já pago | Domínio de pedido | Rejeitar com `PedidoInvalidoException`, preservando o estado pago | Demonstrado no `App` |
| Compra válida | Fluxo completo | Finalizar o pagamento e avançar o pedido para `PROCESSANDO` | Demonstrado no `App` com Pix |

## Itens a conferir na implementação

- A data do pedido é armazenada como texto. Validar que ela representa uma data válida ou trocar o tipo para `LocalDate`.
- A mensagem de e-mail inválido deve orientar o cliente a informar um endereço válido.
- A mensagem de cliente não encontrado deve orientar a conferência do identificador.
- Conferir o namespace solicitado no enunciado (`com.senai.ecommerce.excecao`) com o padrão já adotado pelo projeto (`com.payloadexpress_ecommerce.excecao`).
- Após executar os cenários pendentes, atualizar a coluna Execução com o resultado real e a referência ao teste ou evidência.

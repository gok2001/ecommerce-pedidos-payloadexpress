# Template de Pull Request

> **Como usar:** salve este conteúdo no repositório da equipe como
> `.github/pull_request_template.md` e faça o commit na `main` (via Pull Request 🙂).
> A partir daí, todo PR aberto no GitHub já nasce com este roteiro preenchido.

---

## O que foi feito

_(Descreva em uma ou duas frases a mudança deste PR. Uma funcionalidade por PR.)_

## Por que foi feito

_(Qual módulo da Atividade Desafiadora este PR atende? O que ele destrava para a equipe?)_

## Como testar

1. `git switch <nome-da-branch>`
2. _(passo a passo para o revisor conferir que a mudança funciona)_
3. _(o que se espera ver como resultado)_

## Checklist do autor

- [ ] Parti de uma `main` atualizada (`git switch main && git pull`)
- [ ] A branch segue a convenção da equipe (`tipo/nome-curto-com-hifen`)
- [ ] Os commits são pequenos e descritivos (`tipo: descrição no imperativo`)
- [ ] Não deixei código comentado, arquivo temporário ou teste manual esquecido
- [ ] Indiquei um revisor em **Reviewers**
- [ ] O PR não tem conflitos pendentes com a `main`

## Checklist do revisor

- [ ] Li a descrição e depois o código na aba **Files changed**
- [ ] O código faz o que o PR diz que faz
- [ ] Os nomes de classes, métodos e variáveis estão claros
- [ ] Deixei ao menos um comentário de melhoria, na linha específica do código
- [ ] Deixei ao menos um comentário reconhecendo um ponto positivo
- [ ] Comentei o código, nunca a pessoa

## Observações

_(Dúvidas, decisões que a equipe precisa validar, pontos que ficaram para depois.)_

---

<sub>Atividade Desafiadora — Sistema de Gestão de Pedidos · UC Desenvolvimento Back-end ·
Turma CSTADS601 · Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe"</sub>
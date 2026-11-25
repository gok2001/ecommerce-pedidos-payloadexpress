# Payload Express — Front-end

Interface React + Vite para gestão de e-commerce. Esta pasta pode substituir o conteúdo atual de `front/`; o backend Java e `mock-api/` permanecem fora dela.

## Stack
- React + Vite
- React Router DOM (SPA)
- Lucide React
- Recharts
- CSS próprio com identidade preta e amarela e cards vitrificados

## Instalação
No terminal, dentro desta pasta:

```bash
npm install
npm run dev
```

O Vite inicia normalmente em `http://localhost:5173`.

## JSON Server
O front-end está preparado para usar `http://localhost:3001` quando conectarmos os serviços às coleções. O dashboard e as páginas usam dados demonstrativos nesta primeira versão.

Na pasta `mock-api`, execute o comando correspondente à versão instalada do JSON Server. Para JSON Server v0.x, por exemplo:

```bash
npx json-server --watch db.json --port 3001
```

Se a versão instalada for v1, confirme a sintaxe no `package.json` e na documentação dessa versão.

## Rotas
- `/` — Dashboard
- `/produtos` — Produtos
- `/clientes` — Clientes
- `/pedidos` — Pedidos
- `/pagamentos` — Pagamentos

## Observações
- O cadastro demonstrativo de produtos atualiza o estado em memória; a conexão com o `db.json` será feita na próxima etapa.
- Botões de ações ainda exibem avisos de demonstração.
- Os dados do dashboard são fictícios.

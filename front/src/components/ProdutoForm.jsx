import { useState } from 'react';
import './ProdutoForm.css';

function ProdutoForm({
    onProdutoCadastrado,
    exibirProdutos,
    onAlternarProdutos
}) {

    const [produto, setProduto] = useState({
        codigo: '',
        nome: '',
        descricao: '',
        preco: '',
        quantidadeEmEstoque: ''
    });

    const handleChange = (event) => {
        const { name, value } = event.target;

        setProduto({
            ...produto,
            [name]: value
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const novoProduto = {
            codigo: produto.codigo,
            nome: produto.nome,
            descricao: produto.descricao,
            preco: Number(produto.preco),
            quantidadeEmEstoque: Number(produto.quantidadeEmEstoque),
            ativo: true
        };

        try {
            const response = await fetch('http://localhost:3000/produtos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(novoProduto)
            });

            if (!response.ok) {
                throw new Error('Erro ao cadastrar produto');
            }

            const produtoCadastrado = await response.json();

            if (onProdutoCadastrado) {
                onProdutoCadastrado(produtoCadastrado);
            }

            setProduto({
                codigo: '',
                nome: '',
                descricao: '',
                preco: '',
                quantidadeEmEstoque: ''
            });

        } catch (error) {
            console.error('Erro ao cadastrar produto:', error);
        }
    };

    return (
        <form className="produto-form" onSubmit={handleSubmit}>

            <div className="row g-4">

                <div className="col-12 col-md-6">
                    <div className="campo">
                        <label htmlFor="codigo">Código</label>

                        <input
                            id="codigo"
                            type="text"
                            name="codigo"
                            value={produto.codigo}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>

                <div className="col-12 col-md-6">
                    <div className="campo">
                        <label htmlFor="nome">Nome</label>

                        <input
                            id="nome"
                            type="text"
                            name="nome"
                            value={produto.nome}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>

                <div className="col-12">
                    <div className="campo">
                        <label htmlFor="descricao">Descrição</label>

                        <textarea
                            id="descricao"
                            name="descricao"
                            value={produto.descricao}
                            onChange={handleChange}
                            rows="4"
                        />
                    </div>
                </div>

                <div className="col-12 col-md-6">
                    <div className="campo">
                        <label htmlFor="preco">Preço</label>

                        <input
                            id="preco"
                            type="number"
                            name="preco"
                            value={produto.preco}
                            onChange={handleChange}
                            step="0.01"
                            min="0"
                            required
                        />
                    </div>
                </div>

                <div className="col-12 col-md-6">
                    <div className="campo">
                        <label htmlFor="quantidadeEmEstoque">
                            Quantidade em estoque
                        </label>

                        <input
                            id="quantidadeEmEstoque"
                            type="number"
                            name="quantidadeEmEstoque"
                            value={produto.quantidadeEmEstoque}
                            onChange={handleChange}
                            min="0"
                            required
                        />
                    </div>
                </div>

                <div className="col-12">
                    <div className="botoes-produto">

                        <button type="submit">
                            Cadastrar produto
                        </button>

                        <button
                            type="button"
                            className="botao-exibir"
                            onClick={onAlternarProdutos}
                        >
                            {exibirProdutos
                                ? 'Ocultar Produtos Cadastrados'
                                : 'Exibir Produtos Cadastrados'
                            }
                        </button>

                    </div>
                </div>

            </div>

        </form>
    );
}

export default ProdutoForm;
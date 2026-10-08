import { useEffect, useState } from 'react';
import './ProdutoList.css';

function ProdutoList() {

    const [produtos, setProdutos] = useState([]);
    const [carregando, setCarregando] = useState(true);

    useEffect(() => {
        carregarProdutos();
    }, []);

    const carregarProdutos = async () => {

        try {
            const response = await fetch('http://localhost:3000/produtos');

            if (!response.ok) {
                throw new Error('Erro ao buscar produtos');
            }

            const dados = await response.json();

            setProdutos(dados);

        } catch (error) {
            console.error('Erro ao carregar produtos:', error);

        } finally {
            setCarregando(false);
        }
    };

    const excluirProduto = async (id) => {

        const confirmar = window.confirm(
            'Deseja realmente excluir este produto?'
        );

        if (!confirmar) {
            return;
        }

        try {

            const response = await fetch(
                `http://localhost:3000/produtos/${id}`,
                {
                    method: 'DELETE'
                }
            );

            if (!response.ok) {
                throw new Error('Erro ao excluir produto');
            }

            setProdutos(
                produtos.filter((produto) => produto.id !== id)
            );

        } catch (error) {
            console.error('Erro ao excluir produto:', error);
        }
    };

    if (carregando) {
        return (
            <section className="produto-list">
                <p className="mensagem">
                    Carregando produtos...
                </p>
            </section>
        );
    }

    return (
        <section className="produto-list">

            <h2>Produtos cadastrados</h2>

            {produtos.length === 0 ? (

                <p className="mensagem">
                    Nenhum produto cadastrado.
                </p>

            ) : (

                <div className="tabela-container">
                    <div className="tabela-reponsive">
                        <table className='table table-hover align-middle'>

                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Nome</th>
                                    <th>Descrição</th>
                                    <th>Preço</th>
                                    <th>Estoque</th>
                                    <th>Status</th>
                                    <th>Ação</th>
                                </tr>
                            </thead>

                            <tbody>

                                {produtos.map((produto) => (

                                    <tr key={produto.id}>

                                        <td>{produto.codigo}</td>

                                        <td>{produto.nome}</td>

                                        <td>{produto.descricao}</td>

                                        <td>
                                            R$ {Number(produto.preco).toFixed(2)}
                                        </td>

                                        <td>
                                            {produto.quantidadeEmEstoque}
                                        </td>

                                        <td>
                                            {produto.ativo
                                                ? 'Ativo'
                                                : 'Inativo'
                                            }
                                        </td>

                                        <td>
                                            <button
                                                className="botao-excluir"
                                                onClick={() =>
                                                    excluirProduto(produto.id)
                                                }
                                            >
                                                Excluir
                                            </button>
                                        </td>

                                    </tr>

                                ))}

                            </tbody>

                        </table>

                    </div>

                </div>

            )}

        </section>
    );
}

export default ProdutoList;
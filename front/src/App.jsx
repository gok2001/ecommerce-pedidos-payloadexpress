import { useState } from 'react';

import NavBar from './components/NavBar';
import ProdutoForm from './components/ProdutoForm';
import ProdutoList from './components/ProdutoList';

function App() {

    const [exibirProdutos, setExibirProdutos] = useState(false);

    const alternarProdutos = () => {
        setExibirProdutos(!exibirProdutos);
    };

    return (
        <>
            <NavBar />

            <main>
                <h1>Cadastro de Produtos</h1>

                <ProdutoForm
                    exibirProdutos={exibirProdutos}
                    onAlternarProdutos={alternarProdutos}
                />

                {exibirProdutos && (
                    <ProdutoList />
                )}

            </main>
        </>
    );
}

export default App;
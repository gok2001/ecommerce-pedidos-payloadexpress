import { useState } from 'react';
import './NavBar.css';

function NavBar() {
    const [menuAberto, setMenuAberto] = useState(false);

    const alternarMenu = () => {
        setMenuAberto(!menuAberto);
    };

    return (
        <header className="navbar">

            <div className="navbar-logo">
                <span>LOGO</span>
            </div>

            <button
                className={`menu-button ${menuAberto ? 'ativo' : ''}`}
                onClick={alternarMenu}
                aria-label="Abrir menu"
            >
                <span></span>
                <span></span>
                <span></span>
            </button>

            {menuAberto && (
                <nav className="menu">
                    <a href="#">Início</a>
                    <a href="#">Produtos</a>
                    <a href="#">Pedidos</a>
                    <a href="#">Clientes</a>

                    <div className="menu-divisor"></div>

                    <a href="#">Sair</a>
                </nav>
            )}

        </header>
    );
}

export default NavBar;
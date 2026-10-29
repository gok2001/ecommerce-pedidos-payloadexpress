import { useState } from 'react'
import { Navigate, Route, Routes } from 'react-router-dom'
import MainLayout from './layouts/MainLayout.jsx'
import Dashboard from './pages/Dashboard/Dashboard.jsx'
import Produtos from './pages/Produtos/Produtos.jsx'
import Clientes from './pages/Clientes/Clientes.jsx'
import Pedidos from './pages/Pedidos/Pedidos.jsx'
import Pagamentos from './pages/Pagamentos/Pagamentos.jsx'
import './styles/app.css'

export default function App() {
  const [searchTerm, setSearchTerm] = useState('')
  return (
    <MainLayout searchTerm={searchTerm} onSearchChange={setSearchTerm}>
      <Routes>
        <Route path="/" element={<Dashboard searchTerm={searchTerm} />} />
        <Route path="/produtos" element={<Produtos searchTerm={searchTerm} />} />
        <Route path="/clientes" element={<Clientes searchTerm={searchTerm} />} />
        <Route path="/pedidos" element={<Pedidos searchTerm={searchTerm} />} />
        <Route path="/pagamentos" element={<Pagamentos searchTerm={searchTerm} />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </MainLayout>
  )
}

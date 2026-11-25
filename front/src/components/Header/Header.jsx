import { Bell, Command, Moon, Search, Sun } from 'lucide-react'
import { useState } from 'react'
export default function Header({ searchTerm, onSearchChange }) {
  const [dark, setDark] = useState(true)
  const [showNotifications, setShowNotifications] = useState(false)
  const [showSearch, setShowSearch] = useState(false)
  const date = new Intl.DateTimeFormat('pt-BR', { day:'2-digit', month:'short', year:'numeric' }).format(new Date())
  return (
    <header className="topbar">
      <div className="topbar-search">
        <Search size={19} className="search-icon"/>
        <input aria-label="Buscar" value={searchTerm} onChange={e => onSearchChange(e.target.value)} placeholder="Buscar por produtos, pedidos, clientes..." />
        <kbd><Command size={11}/> K</kbd>
      </div>
      <div className="topbar-actions">
        <div className="notification-wrap">
          <button className={`icon-button ${showNotifications ? 'is-on' : ''}`} aria-label="Notificações" onClick={() => setShowNotifications(v => !v)}><Bell size={20}/><i/></button>
          {showNotifications && <div className="notification-popover"><strong>Notificações</strong><p><span className="notification-dot"/> Você está visualizando dados demonstrativos.</p><p><span className="notification-dot"/> Integração com a API será configurada depois.</p></div>}
        </div>
        <button className="icon-button theme-button" aria-label="Alternar tema de destaque" onClick={() => setDark(v => !v)}>{dark ? <Moon size={20}/> : <Sun size={20}/>}</button>
        <div className="topbar-divider"/>
        <span className="topbar-date">{date}</span>
        <span className="topbar-time">{new Date().toLocaleTimeString('pt-BR',{hour:'2-digit',minute:'2-digit'})}</span>
      </div>
      <button className="mobile-search-button" aria-label="Mostrar pesquisa" onClick={() => setShowSearch(v => !v)}><Search size={20}/></button>
      {showSearch && <div className="mobile-search"><Search size={18}/><input value={searchTerm} onChange={e => onSearchChange(e.target.value)} placeholder="Buscar..." autoFocus/></div>}
    </header>
  )
}

import { NavLink } from 'react-router-dom'
import { BarChart3, Box, ChevronLeft, ChevronRight, CreditCard, LayoutDashboard, LogOut, Package, Settings, Users, ShoppingCart } from 'lucide-react'
import Logo from '../Logo/Logo.jsx'

const links = [
  { to: '/', label: 'Dashboard', icon: LayoutDashboard, end: true },
  { to: '/produtos', label: 'Produtos', icon: Box },
  { to: '/clientes', label: 'Clientes', icon: Users },
  { to: '/pedidos', label: 'Pedidos', icon: Package },
  { to: '/pagamentos', label: 'Pagamentos', icon: CreditCard },
]
export default function Sidebar({ collapsed, onToggle }) {
  return (
    <aside className={`sidebar ${collapsed ? 'sidebar-collapsed' : ''}`}>
      <button className="sidebar-toggle" onClick={onToggle} aria-label={collapsed ? 'Expandir menu' : 'Recolher menu'}>
        {collapsed ? <ChevronRight size={18}/> : <ChevronLeft size={18}/>}
      </button>
      <div className="sidebar-brand"><Logo compact={collapsed}/></div>
      <nav className="sidebar-nav" aria-label="Navegação principal">
        <span className="nav-section-label">{!collapsed && 'MENU PRINCIPAL'}</span>
        {links.map(({to,label,icon:Icon,end}) => (
          <NavLink key={to} to={to} end={end} title={collapsed ? label : undefined} className={({isActive}) => `nav-link ${isActive ? 'active' : ''}`}>
            <Icon size={20} strokeWidth={1.8}/><span>{label}</span>{!collapsed && label !== 'Dashboard' && <ChevronRight className="nav-chevron" size={14}/>}
          </NavLink>
        ))}
        {!collapsed && <span className="nav-section-label nav-section-secondary">SISTEMA</span>}
        <button className="nav-link" title={collapsed ? 'Relatórios' : undefined} onClick={() => window.dispatchEvent(new CustomEvent('payload:notice',{detail:'Relatórios estará disponível em uma próxima etapa.'}))}>
          <BarChart3 size={20} strokeWidth={1.8}/><span>Relatórios</span>{!collapsed && <ChevronRight className="nav-chevron" size={14}/>}
        </button>
        <button className="nav-link" title={collapsed ? 'Configurações' : undefined} onClick={() => window.dispatchEvent(new CustomEvent('payload:notice',{detail:'Configurações estará disponível em uma próxima etapa.'}))}>
          <Settings size={20} strokeWidth={1.8}/><span>Configurações</span>{!collapsed && <ChevronRight className="nav-chevron" size={14}/>}
        </button>
      </nav>
      <div className="sidebar-bottom">
        <div className="profile-avatar">A</div>
        {!collapsed && <div className="profile-copy"><strong>Admin</strong><span>Administrador</span></div>}
        <button className="logout-button" title="Sair" onClick={() => window.dispatchEvent(new CustomEvent('payload:notice',{detail:'A autenticação será implementada em uma próxima etapa.'}))}><LogOut size={18}/>{!collapsed && <span>Sair</span>}</button>
      </div>
    </aside>
  )
}

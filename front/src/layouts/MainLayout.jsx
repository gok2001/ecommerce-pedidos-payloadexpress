import { useEffect, useState } from 'react'
import Sidebar from '../components/Sidebar/Sidebar.jsx'
import Header from '../components/Header/Header.jsx'
import './MainLayout.css'

export default function MainLayout({ children, searchTerm, onSearchChange }) {
  const [collapsed, setCollapsed] = useState(false)
  const [notice, setNotice] = useState('')
  useEffect(() => {
    const handler = event => { setNotice(event.detail); window.setTimeout(() => setNotice(''), 3200) }
    window.addEventListener('payload:notice', handler)
    return () => window.removeEventListener('payload:notice', handler)
  }, [])
  return (
    <div className={`app-shell ${collapsed ? 'is-collapsed' : ''}`}>
      <Sidebar collapsed={collapsed} onToggle={() => setCollapsed(v => !v)} />
      <div className="main-area">
        <Header searchTerm={searchTerm} onSearchChange={onSearchChange}/>
        <main className="page-content">{children}</main>
      </div>
      {notice && <div className="toast-notice">{notice}</div>}
    </div>
  )
}

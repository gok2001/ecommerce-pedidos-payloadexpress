import { useMemo } from 'react'
import { ArrowRight, CreditCard, Eye, FileText, Package, ShoppingCart, Wallet } from 'lucide-react'
import { CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis, Bar, ComposedChart } from 'recharts'
import { Link } from 'react-router-dom'
import StatCard from '../../components/StatCard/StatCard.jsx'
import './Dashboard.css'

const sales = [
  { month:'Jan', total:3200, orders:2800 }, { month:'Fev', total:5100, orders:4300 },
  { month:'Mar', total:4800, orders:4100 }, { month:'Abr', total:8300, orders:7000 },
  { month:'Mai', total:7600, orders:6500 }, { month:'Jun', total:11200, orders:9500 }
]
const recentOrders = [
  { id:'#000124', customer:'Lucas Almeida', date:'26/10/2026', value:'R$ 356,90', status:'Em processamento', tone:'info' },
  { id:'#000123', customer:'Mariana Silva', date:'26/10/2026', value:'R$ 219,90', status:'Pago', tone:'success' },
  { id:'#000122', customer:'Carlos Oliveira', date:'25/10/2026', value:'R$ 129,50', status:'Pendente', tone:'warning' },
  { id:'#000121', customer:'Fernanda Costa', date:'24/10/2026', value:'R$ 489,00', status:'Entregue', tone:'success' },
  { id:'#000120', customer:'Rafael Lima', date:'24/10/2026', value:'R$ 162,10', status:'Cancelado', tone:'danger' }
]
const lowStock = [
  { name:'Notebook Ultra 15', category:'Informática', stock:3 },
  { name:'Headset Gamer Pro', category:'Acessórios', stock:5 },
  { name:'Mouse sem fio', category:'Acessórios', stock:7 },
  { name:'Monitor 24" Full HD', category:'Informática', stock:6 },
  { name:'Teclado mecânico', category:'Acessórios', stock:4 }
]
function PanelHeading({ title, action }) { return <div className="panel-heading"><h2>{title}</h2>{action}</div> }
export default function Dashboard({ searchTerm = '' }) {
  const orders = useMemo(() => recentOrders.filter(o => `${o.id} ${o.customer} ${o.status}`.toLowerCase().includes(searchTerm.toLowerCase())), [searchTerm])
  const products = useMemo(() => lowStock.filter(p => `${p.name} ${p.category}`.toLowerCase().includes(searchTerm.toLowerCase())), [searchTerm])
  return (
    <div className="dashboard-page">
      <section className="welcome-banner">
        <div className="welcome-copy"><span className="eyebrow">PAINEL DE CONTROLE</span><h1>Olá, Admin <span>👋</span></h1><p>Acompanhe o desempenho do seu e-commerce em um só lugar.</p></div>
        <div className="welcome-art" aria-hidden="true"><div className="art-circuit circuit-one"/><div className="art-box"><Package size={48}/></div><div className="art-caption">PEDIDOS<br/>EM MOVIMENTO</div></div>
      </section>
      <section className="stats-grid" aria-label="Indicadores principais">
        <StatCard title="Vendas totais" value="R$ 12.450,00" change="12%" icon={ShoppingCart} sparkline={[10,15,13,22,18,24,19,26,22,35]}/>
        <StatCard title="Pedidos realizados" value="148" change="8%" icon={FileText} sparkline={[8,10,18,15,20,16,22,19,30,34]}/>
        <StatCard title="Ticket médio" value="R$ 84,12" change="6%" icon={Wallet} sparkline={[10,14,21,17,20,16,23,19,26,31]}/>
        <StatCard title="Produtos cadastrados" value="320" change="5%" icon={Package} sparkline={[7,12,18,14,16,13,19,17,24,32]}/>
      </section>
      <section className="analytics-grid">
        <article className="glass-panel chart-panel sales-panel">
          <PanelHeading title="Evolução das vendas" action={<select className="period-select" aria-label="Período"><option>Últimos 6 meses</option><option>Últimos 30 dias</option><option>Últimos 12 meses</option></select>}/>
          <div className="sales-chart"><ResponsiveContainer width="100%" height="100%"><ComposedChart data={sales} margin={{top:10,right:12,bottom:0,left:-14}}><defs><linearGradient id="salesBar" x1="0" y1="0" x2="0" y2="1"><stop offset="0%" stopColor="#f5c518" stopOpacity=".72"/><stop offset="100%" stopColor="#f5c518" stopOpacity=".12"/></linearGradient></defs><CartesianGrid stroke="rgba(255,255,255,.075)" vertical/><XAxis dataKey="month" tick={{fill:'#b5bec7',fontSize:11}} axisLine={{stroke:'rgba(255,255,255,.12)'}} tickLine={false}/><YAxis tick={{fill:'#aab4bd',fontSize:10}} axisLine={false} tickLine={false}/><Tooltip contentStyle={{background:'#171b1e',border:'1px solid #5b4b15',borderRadius:10,color:'#fff'}} formatter={v => [`R$ ${Number(v).toLocaleString('pt-BR')}`, 'Vendas']}/><Bar dataKey="orders" fill="url(#salesBar)" radius={[3,3,0,0]} barSize={24}/><Line type="monotone" dataKey="total" stroke="#ffd52e" strokeWidth={2.5} dot={{r:3,fill:'#ffd52e',stroke:'#171b1e',strokeWidth:1}} activeDot={{r:5}}/></ComposedChart></ResponsiveContainer></div>
          <div className="chart-legend"><span><i className="legend-line"/>Vendas</span><span><i className="legend-bar"/>Pedidos</span></div>
        </article>
        <article className="glass-panel status-panel">
          <PanelHeading title="Pedidos por situação"/>
          <div className="status-content"><div className="donut-chart"><div><strong>148</strong><span>Pedidos</span></div></div><ul className="legend-list"><li><i className="yellow-dot"/>Entregue <b>58%</b></li><li><i className="green-dot"/>Em processamento <b>18%</b></li><li><i className="orange-dot"/>Pendente <b>12%</b></li><li><i className="red-dot"/>Cancelado <b>8%</b></li><li><i className="gray-dot"/>Aguardando pagamento <b>4%</b></li></ul></div>
        </article>
        <article className="glass-panel payment-panel">
          <PanelHeading title="Métodos de pagamento"/>
          <div className="payment-methods">{[['Cartão de crédito',42,CreditCard],['Pix',28,Wallet],['Boleto',18,FileText],['Transferência',8,ArrowRight],['Outros',4,Package]].map(([name,percent,Icon])=><div className="payment-row" key={name}><Icon size={17}/><span>{name}</span><div className="payment-track"><i style={{width:`${percent}%`}}/></div><b>{percent}%</b></div>)}</div>
        </article>
      </section>
      <section className="bottom-grid">
        <article className="glass-panel table-panel recent-panel">
          <PanelHeading title="Pedidos recentes" action={<Link className="button button-outline" to="/pedidos">Ver todos <ArrowRight size={14}/></Link>}/>
          <div className="table-wrap"><table className="data-table"><thead><tr><th>#</th><th>Cliente</th><th>Data</th><th>Valor</th><th>Situação</th><th>Ações</th></tr></thead><tbody>{orders.map(o=><tr key={o.id}><td className="id-cell">{o.id}</td><td>{o.customer}</td><td>{o.date}</td><td>{o.value}</td><td><span className={`status-pill status-${o.tone}`}>{o.status}</span></td><td><button className="table-action" aria-label={`Visualizar pedido ${o.id}`} onClick={() => window.dispatchEvent(new CustomEvent('payload:notice',{detail:`Pedido ${o.id}: detalhes demonstrativos.`}))}><Eye size={17}/></button></td></tr>)}</tbody></table>{orders.length===0&&<div className="empty-state">Nenhum pedido encontrado.</div>}</div>
        </article>
        <article className="glass-panel table-panel stock-panel">
          <PanelHeading title="Produtos com estoque baixo" action={<Link className="button button-outline" to="/produtos">Ver todos <ArrowRight size={14}/></Link>}/>
          <div className="table-wrap"><table className="data-table"><thead><tr><th>Produto</th><th>Categoria</th><th>Estoque</th><th>Ações</th></tr></thead><tbody>{products.map((p,i)=><tr key={p.name}><td><span className={`product-mini mini-${i%4}`}><Package size={16}/></span>{p.name}</td><td>{p.category}</td><td><b className={p.stock<=5?'stock-critical':'stock-warning'}>{p.stock}</b></td><td><button className="table-action" aria-label={`Visualizar ${p.name}`} onClick={() => window.dispatchEvent(new CustomEvent('payload:notice',{detail:`${p.name}: estoque demonstrativo.`}))}><Eye size={17}/></button></td></tr>)}</tbody></table>{products.length===0&&<div className="empty-state">Nenhum produto encontrado.</div>}</div>
        </article>
      </section>
      <p className="demo-note">Os indicadores e registros desta tela são demonstrativos e serão conectados ao JSON Server na próxima etapa.</p>
    </div>
  )
}

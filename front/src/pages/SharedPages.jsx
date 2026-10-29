import { useMemo, useState } from 'react'
import { Plus, Search, Eye, Pencil, Trash2, Package, Users, ShoppingCart, CreditCard } from 'lucide-react'
import './SharedPages.css'

const initialProducts = [
  { id:'PRD-001', name:'Notebook Ultra 15', category:'Informática', price:4299.90, stock:3 },
  { id:'PRD-002', name:'Headset Gamer Pro', category:'Acessórios', price:349.90, stock:5 },
  { id:'PRD-003', name:'Mouse sem fio', category:'Acessórios', price:129.90, stock:7 },
  { id:'PRD-004', name:'Monitor 24" Full HD', category:'Informática', price:899.90, stock:6 },
  { id:'PRD-005', name:'Teclado mecânico', category:'Acessórios', price:279.90, stock:4 },
  { id:'PRD-006', name:'Webcam Full HD', category:'Periféricos', price:219.90, stock:18 }
]
const initialCustomers = [
  {id:'CLI-001',name:'Lucas Almeida',email:'lucas.almeida@email.com',orders:8,spent:1840.50},
  {id:'CLI-002',name:'Mariana Silva',email:'mariana.silva@email.com',orders:5,spent:920.00},
  {id:'CLI-003',name:'Carlos Oliveira',email:'carlos.oliveira@email.com',orders:3,spent:568.40},
  {id:'CLI-004',name:'Fernanda Costa',email:'fernanda.costa@email.com',orders:11,spent:3240.90},
  {id:'CLI-005',name:'Rafael Lima',email:'rafael.lima@email.com',orders:2,spent:289.00}
]
const initialOrders = [
  {id:'#000124',customer:'Lucas Almeida',date:'26/10/2026',value:356.90,status:'Em processamento'},
  {id:'#000123',customer:'Mariana Silva',date:'26/10/2026',value:219.90,status:'Pago'},
  {id:'#000122',customer:'Carlos Oliveira',date:'25/10/2026',value:129.50,status:'Pendente'},
  {id:'#000121',customer:'Fernanda Costa',date:'24/10/2026',value:489.00,status:'Entregue'},
  {id:'#000120',customer:'Rafael Lima',date:'24/10/2026',value:162.10,status:'Cancelado'}
]
const initialPayments = [
  {id:'PAY-0091',order:'#000124',method:'Cartão de crédito',date:'26/10/2026',value:356.90,status:'Aprovado'},
  {id:'PAY-0090',order:'#000123',method:'Pix',date:'26/10/2026',value:219.90,status:'Aprovado'},
  {id:'PAY-0089',order:'#000122',method:'Boleto',date:'25/10/2026',value:129.50,status:'Pendente'},
  {id:'PAY-0088',order:'#000121',method:'Pix',date:'24/10/2026',value:489.00,status:'Aprovado'},
  {id:'PAY-0087',order:'#000120',method:'Cartão de crédito',date:'24/10/2026',value:162.10,status:'Recusado'}
]
const money = value => Number(value).toLocaleString('pt-BR',{style:'currency',currency:'BRL'})
const tone = status => /aprovado|pago|entregue|ativo/i.test(status) ? 'success' : /pendente|processamento/i.test(status) ? 'warning' : /cancelado|recusado/i.test(status) ? 'danger' : 'info'

function PageShell({title,description,icon:Icon,children,action}) {
 return <div className="resource-page"><div className="page-heading"><div><div className="resource-title-line"><span className="resource-title-icon"><Icon size={22}/></span><h1>{title}</h1></div><p>{description}</p></div><div className="heading-actions">{action}</div></div>{children}</div>
}
function TablePanel({title,children,tools}) { return <section className="glass-panel resource-table-panel"><div className="resource-table-heading"><h2>{title}</h2>{tools}</div>{children}</section> }
function SearchInput({value,onChange,placeholder='Buscar registros...'}) { return <label className="search-filter"><Search size={16}/><input value={value} onChange={e=>onChange(e.target.value)} placeholder={placeholder}/></label> }
function Status({status}) { return <span className={`status-pill status-${tone(status)}`}>{status}</span> }
function Actions({label}) { return <div className="row-actions"><button aria-label={`Ver ${label}`} onClick={()=>window.dispatchEvent(new CustomEvent('payload:notice',{detail:`Detalhes de ${label} serão implementados em uma próxima etapa.`}))}><Eye size={16}/></button><button aria-label={`Editar ${label}`} onClick={()=>window.dispatchEvent(new CustomEvent('payload:notice',{detail:`Edição de ${label} será implementada em uma próxima etapa.`}))}><Pencil size={15}/></button></div> }
function CreateModal({type,onClose,onCreate}) {
 const [name,setName]=useState(''), [category,setCategory]=useState('Informática'), [price,setPrice]=useState(''), [stock,setStock]=useState('')
 return <div className="modal-backdrop" role="presentation" onMouseDown={e=>e.target===e.currentTarget&&onClose()}><form className="glass-panel create-modal" onSubmit={e=>{e.preventDefault();onCreate({name,category,price:Number(price),stock:Number(stock)});onClose()}}><div className="modal-title"><div><span className="eyebrow">NOVO REGISTRO</span><h2>Cadastrar {type}</h2></div><button type="button" className="modal-close" onClick={onClose}>×</button></div><label>Nome do produto<input required value={name} onChange={e=>setName(e.target.value)} placeholder="Ex.: Notebook Ultra 15"/></label><label>Categoria<select value={category} onChange={e=>setCategory(e.target.value)}><option>Informática</option><option>Acessórios</option><option>Periféricos</option><option>Outros</option></select></label><div className="modal-fields"><label>Preço (R$)<input required min="0.01" step="0.01" type="number" value={price} onChange={e=>setPrice(e.target.value)}/></label><label>Estoque<input required min="0" type="number" value={stock} onChange={e=>setStock(e.target.value)}/></label></div><div className="modal-actions"><button type="button" className="button" onClick={onClose}>Cancelar</button><button className="button button-primary" type="submit"><Plus size={16}/>Salvar produto</button></div></form></div>
}
export function ProdutosPage({searchTerm=''}) {
 const [items,setItems]=useState(initialProducts),[filter,setFilter]=useState(searchTerm),[modal,setModal]=useState(false)
 const filtered=useMemo(()=>items.filter(p=>`${p.id} ${p.name} ${p.category}`.toLowerCase().includes((filter||searchTerm).toLowerCase())),[items,filter,searchTerm])
 const create=item=>setItems(old=>[{...item,id:`PRD-${String(old.length+1).padStart(3,'0')}`},...old])
 return <PageShell title="Produtos" description="Gerencie o catálogo, os preços e a disponibilidade do seu estoque." icon={Package} action={<button className="button button-primary" onClick={()=>setModal(true)}><Plus size={16}/>Novo produto</button>}>
 <div className="resource-stats"><div className="glass-panel mini-stat"><span>Total de produtos</span><strong>{items.length}</strong><Package size={19}/></div><div className="glass-panel mini-stat"><span>Estoque baixo</span><strong>{items.filter(p=>p.stock<8).length}</strong><span className="mini-stat-mark">!</span></div><div className="glass-panel mini-stat"><span>Valor em catálogo</span><strong>{money(items.reduce((sum,p)=>sum+p.price*p.stock,0))}</strong><ShoppingCart size={19}/></div></div>
 <TablePanel title="Catálogo de produtos" tools={<SearchInput value={filter} onChange={setFilter} placeholder="Buscar produto..."/>}><div className="table-wrap"><table className="data-table"><thead><tr><th>Produto</th><th>Código</th><th>Categoria</th><th>Preço</th><th>Estoque</th><th>Situação</th><th>Ações</th></tr></thead><tbody>{filtered.map(p=><tr key={p.id}><td><span className="table-product-icon"><Package size={17}/></span><strong>{p.name}</strong></td><td>{p.id}</td><td>{p.category}</td><td>{money(p.price)}</td><td><b className={p.stock<5?'stock-critical':p.stock<8?'stock-warning':''}>{p.stock}</b></td><td><Status status={p.stock===0?'Sem estoque':p.stock<8?'Estoque baixo':'Disponível'}/></td><td><Actions label={p.name}/></td></tr>)}</tbody></table>{!filtered.length&&<div className="empty-state"><strong>Nenhum produto encontrado</strong>Tente alterar os termos da busca.</div>}</div></TablePanel>
 {modal&&<CreateModal type="produto" onClose={()=>setModal(false)} onCreate={create}/>}
 </PageShell>
}
export function ClientesPage({searchTerm=''}) {
 const [filter,setFilter]=useState(searchTerm)
 const filtered=initialCustomers.filter(c=>`${c.name} ${c.email} ${c.id}`.toLowerCase().includes((filter||searchTerm).toLowerCase()))
 return <PageShell title="Clientes" description="Consulte os clientes e acompanhe o relacionamento com a loja." icon={Users} action={<button className="button button-primary" onClick={()=>window.dispatchEvent(new CustomEvent('payload:notice',{detail:'O formulário de clientes será implementado na próxima etapa.'}))}><Plus size={16}/>Novo cliente</button>}>
 <div className="resource-stats"><div className="glass-panel mini-stat"><span>Clientes cadastrados</span><strong>128</strong><Users size={19}/></div><div className="glass-panel mini-stat"><span>Novos neste mês</span><strong>16</strong><span className="mini-stat-trend">+12%</span></div><div className="glass-panel mini-stat"><span>Ticket médio por cliente</span><strong>R$ 286,40</strong><CreditCard size={19}/></div></div>
 <TablePanel title="Base de clientes" tools={<SearchInput value={filter} onChange={setFilter} placeholder="Buscar cliente..."/>}><div className="table-wrap"><table className="data-table"><thead><tr><th>Cliente</th><th>Identificador</th><th>E-mail</th><th>Pedidos</th><th>Total gasto</th><th>Ações</th></tr></thead><tbody>{filtered.map(c=><tr key={c.id}><td><span className="customer-avatar">{c.name.split(' ').map(n=>n[0]).slice(0,2).join('')}</span><strong>{c.name}</strong></td><td>{c.id}</td><td>{c.email}</td><td>{c.orders}</td><td>{money(c.spent)}</td><td><Actions label={c.name}/></td></tr>)}</tbody></table>{!filtered.length&&<div className="empty-state">Nenhum cliente encontrado.</div>}</div></TablePanel>
 </PageShell>
}
export function PedidosPage({searchTerm=''}) {
 const [filter,setFilter]=useState(searchTerm),[status,setStatus]=useState('Todos')
 const filtered=initialOrders.filter(o=>`${o.id} ${o.customer} ${o.status}`.toLowerCase().includes((filter||searchTerm).toLowerCase())&&(status==='Todos'||o.status===status))
 return <PageShell title="Pedidos" description="Acompanhe os pedidos, valores e etapas de processamento." icon={ShoppingCart} action={<button className="button button-primary" onClick={()=>window.dispatchEvent(new CustomEvent('payload:notice',{detail:'A criação de pedidos será implementada na próxima etapa.'}))}><Plus size={16}/>Novo pedido</button>}>
 <div className="resource-stats"><div className="glass-panel mini-stat"><span>Total de pedidos</span><strong>148</strong><ShoppingCart size={19}/></div><div className="glass-panel mini-stat"><span>Em processamento</span><strong>12</strong><span className="mini-stat-mark">↗</span></div><div className="glass-panel mini-stat"><span>Valor médio</span><strong>R$ 84,12</strong><CreditCard size={19}/></div></div>
 <TablePanel title="Todos os pedidos" tools={<><SearchInput value={filter} onChange={setFilter} placeholder="Buscar pedido..."/><select className="filter-select" value={status} onChange={e=>setStatus(e.target.value)}><option>Todos</option>{['Em processamento','Pago','Pendente','Entregue','Cancelado'].map(s=><option key={s}>{s}</option>)}</select></>}><div className="table-wrap"><table className="data-table"><thead><tr><th>Pedido</th><th>Cliente</th><th>Data</th><th>Valor total</th><th>Situação</th><th>Ações</th></tr></thead><tbody>{filtered.map(o=><tr key={o.id}><td className="id-cell">{o.id}</td><td>{o.customer}</td><td>{o.date}</td><td>{money(o.value)}</td><td><Status status={o.status}/></td><td><Actions label={o.id}/></td></tr>)}</tbody></table>{!filtered.length&&<div className="empty-state">Nenhum pedido encontrado.</div>}</div></TablePanel>
 </PageShell>
}
export function PagamentosPage({searchTerm=''}) {
 const [filter,setFilter]=useState(searchTerm)
 const filtered=initialPayments.filter(p=>`${p.id} ${p.order} ${p.method} ${p.status}`.toLowerCase().includes((filter||searchTerm).toLowerCase()))
 return <PageShell title="Pagamentos" description="Consulte as transações e acompanhe a situação dos pagamentos." icon={CreditCard}>
 <div className="resource-stats"><div className="glass-panel mini-stat"><span>Valor aprovado</span><strong>R$ 1.065,80</strong><CreditCard size={19}/></div><div className="glass-panel mini-stat"><span>Aguardando confirmação</span><strong>R$ 129,50</strong><span className="mini-stat-mark">!</span></div><div className="glass-panel mini-stat"><span>Taxa de aprovação</span><strong>94,2%</strong><span className="mini-stat-trend">+2,4%</span></div></div>
 <TablePanel title="Histórico de transações" tools={<SearchInput value={filter} onChange={setFilter} placeholder="Buscar transação..."/>}><div className="table-wrap"><table className="data-table"><thead><tr><th>Transação</th><th>Pedido</th><th>Método</th><th>Data</th><th>Valor</th><th>Situação</th><th>Ações</th></tr></thead><tbody>{filtered.map(p=><tr key={p.id}><td className="id-cell">{p.id}</td><td>{p.order}</td><td>{p.method}</td><td>{p.date}</td><td>{money(p.value)}</td><td><Status status={p.status}/></td><td><Actions label={p.id}/></td></tr>)}</tbody></table>{!filtered.length&&<div className="empty-state">Nenhuma transação encontrada.</div>}</div></TablePanel>
 </PageShell>
}

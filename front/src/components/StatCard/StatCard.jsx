import { ArrowDownRight, ArrowUpRight, MoreVertical } from 'lucide-react'
export default function StatCard({ title, value, change, icon: Icon, trend = 'up', sparkline = [] }) {
  const points = sparkline.length ? sparkline : [10,18,14,25,20,30,23,35,31,42]
  const max = Math.max(...points), min = Math.min(...points)
  const coords = points.map((v,i) => `${(i/(points.length-1))*100},${34-((v-min)/(max-min || 1))*27}`).join(' ')
  return (
    <article className="stat-card glass-panel">
      <div className="stat-top"><div className="stat-icon"><Icon size={22}/></div><button className="quiet-icon" aria-label={`Mais opções: ${title}`} onClick={() => window.dispatchEvent(new CustomEvent('payload:notice',{detail:'Mais opções serão adicionadas nesta etapa.'}))}><MoreVertical size={18}/></button></div>
      <div className="stat-copy"><span>{title}</span><strong>{value}</strong></div>
      <div className="stat-change"><span className={trend === 'up' ? 'trend-up' : 'trend-down'}>{trend === 'up' ? <ArrowUpRight size={16}/> : <ArrowDownRight size={16}/>} {change}</span><small>vs. mês anterior</small></div>
      <svg className="stat-sparkline" viewBox="0 0 100 40" preserveAspectRatio="none" aria-hidden="true">
        <defs><linearGradient id="sparkFill" x1="0" y1="0" x2="0" y2="1"><stop offset="0%" stopColor="#f5c518" stopOpacity=".32"/><stop offset="100%" stopColor="#f5c518" stopOpacity="0"/></linearGradient></defs>
        <polygon points={`0,40 ${coords} 100,40`} fill="url(#sparkFill)"/>
        <polyline points={coords} fill="none" stroke="#f5c518" strokeWidth="1.5" vectorEffect="non-scaling-stroke"/>
      </svg>
    </article>
  )
}

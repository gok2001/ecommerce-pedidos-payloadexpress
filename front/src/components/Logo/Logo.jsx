export default function Logo({ compact = false }) {
  return (
    <div className={`brand-lockup ${compact ? 'brand-compact' : ''}`} aria-label="Payload Express">
      <svg className="brand-symbol" viewBox="0 0 100 100" role="img" aria-label="PX">
        <path d="M50 4 88 26v48L50 96 12 74V26Z" fill="rgba(245,197,24,.035)" stroke="currentColor" strokeWidth="2"/>
        <path d="M50 11 82 30v40L50 89 18 70V30Z" fill="none" stroke="currentColor" strokeWidth="1.2" opacity=".7"/>
        <path d="M27 32h20c11 0 16 6 16 15S58 62 47 62H39v12H27Zm12 10v9h8c3 0 5-1 5-5s-2-4-5-4Z" fill="currentColor"/>
        <path d="m57 33 10 0 7 10 7-10h10L79 50l12 17H80l-7-10-7 10H56l12-17Z" fill="currentColor"/>
        <circle cx="50" cy="4" r="2.2" fill="currentColor"/><circle cx="88" cy="26" r="2.2" fill="currentColor"/><circle cx="12" cy="74" r="2.2" fill="currentColor"/>
      </svg>
      {!compact && <div className="brand-wordmark"><span>PAYLOAD</span><strong>EXPRESS</strong><small>PEDIDOS EM MOVIMENTO</small></div>}
    </div>
  )
}

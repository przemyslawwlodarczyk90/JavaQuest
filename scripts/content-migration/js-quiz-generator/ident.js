const fs=require('fs');
for(const f of process.argv.slice(2)){
 const j=JSON.parse(fs.readFileSync(f,'utf8'));
 j.quiz.forEach((q,i)=>{
  const ids=q.question.match(/[A-Za-z_][A-Za-z0-9_]*/g)||[];
  const m=Object.create(null);ids.forEach(x=>{const k=x.toLowerCase();(m[k]=m[k]||new Set()).add(x)});
  const bad=Object.values(m).filter(s=>s.size>1&&[...s].some(x=>/^[A-Z]/.test(x))&&[...s].some(x=>/^[a-z]/.test(x)));
  if(bad.length)console.log(f.split('/').pop(),'#'+i,bad.map(s=>[...s].join('/')).join(' ; '));
 });
 const consts=j.quiz.filter(q=>/const [A-Z][A-Z_]{3,} =/.test(q.question)).length;
 const lower=j.quiz.filter(q=>/const (tlumaczenia|mapa|stawki)/.test(q.question)).length;
 console.log(f.split('/').pop(),'UPPER consts in questions:',consts);
}

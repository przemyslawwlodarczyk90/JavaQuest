const fs=require('fs');const {run,fmt}=require('./lib.js');
const fmtLoose=v=>typeof v==='string'?v:fmt(v);
let allok=true;
for(const f of process.argv.slice(2)){
  const raw=fs.readFileSync(f,'utf8');let j;
  try{j=JSON.parse(raw)}catch(e){console.log(f,'JSON ERR',e.message);allok=false;continue}
  const bad=[];
  if(/[\u0400-\u04FF]/.test(raw))bad.push('cyrylica');
  if(raw.includes('native code'))bad.push('native code');
  let codeQ=0;
  j.quiz.forEach((q,i)=>{
    if(new Set(Object.values(q.options)).size!==4)bad.push('dup'+i);
    if(Object.keys(q.options).join('')!=='ABCD')bad.push('optkeys'+i);
    if(!q.options[q.correct])bad.push('corr'+i);
    if(!q.explanation)bad.push('expl'+i);
    if(Object.values(q.options).some(v=>!String(v).trim()))bad.push('emptyopt'+i);
    if(q.question.startsWith('Co wypisze')){
      codeQ++;
      const code=q.question.split('\n').slice(1).join('\n');
      const res=run(code);let c;
      if(res.err)c=res.err;else if(res.logs.length===1&&res.logs[0].length===1)c=fmt(res.logs[0][0]);else if(!res.logs.length)c='nic (brak wypisania)';else c=res.logs.map(a=>a.map(fmtLoose).join(' ')).join(', ');
      if(q.options[q.correct]!==c)bad.push('WRONG#'+i+' opt='+q.options[q.correct]+' actual='+c);
    }
  });
  j.exercises.forEach((e,i)=>{if(!e.prompt||!e.hint||!e.solution)bad.push('ex'+i);else{const r=run(e.solution);if(r.err&&!/DOM|document|window|fetch|setInterval|clearInterval|localStorage|sessionStorage|location\./.test(e.solution))bad.push('exerr'+i+':'+r.err)}});
  if(new Set(j.quiz.map(q=>q.question)).size!==j.quiz.length)bad.push('dupQ');
  console.log(f.split('/').pop(),'T',j.theory.length,'E',j.exercises.length,'Q',j.quiz.length,'code',codeQ,bad.length?'BAD: '+bad.join(' | '):'OK');
  if(bad.length)allok=false;
}
process.exit(allok?0:1);

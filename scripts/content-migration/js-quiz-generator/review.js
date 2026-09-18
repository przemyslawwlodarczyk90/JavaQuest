const j=JSON.parse(require('fs').readFileSync(process.argv[2],'utf8'));
const from=+process.argv[3]||0;
j.quiz.slice(from).forEach((q,i)=>{ if(!q.question.startsWith('Co wypisze'))return; const o=q.options; console.log((i+from)+' ['+q.correct+'] '+o[q.correct]+'  || '+Object.entries(o).filter(([k])=>k!==q.correct).map(([k,v])=>v).join(' ; ')+'\n    '+q.explanation.slice(0,105));});

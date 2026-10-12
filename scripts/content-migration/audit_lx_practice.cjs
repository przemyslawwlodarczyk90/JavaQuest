// Audyt calego kursu; brak praktyki jest raportowany oddzielnie od blednej praktyki.
// Opcja --require-complete wymaga pelnego uzupelnienia wszystkich 437 lekcji.
const fs=require('fs'),path=require('path');
const root=path.resolve(__dirname,'../..');
const short=new Set([1,2,8,9,19,20,23,29,43,44,46]);
const chapters=[],missing=[],errors=[];
let total=0,ready=0;
for(const directory of fs.readdirSync(path.join(root,'src/main/resources/content')).filter(x=>x.startsWith('_lx_')).sort()){
  const chapter=Number(directory.slice(4,6));
  let completed=0,lessons=0;
  for(const name of fs.readdirSync(path.join(root,'src/main/resources/content',directory)).filter(x=>x.endsWith('.json')).sort()){
    const id=directory+'/'+name,j=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content',id),'utf8'));
    lessons++;total++;
    if(!j.exercises.length&&!j.quiz.length){missing.push(id);continue;}
    ready++;completed++;
    const ex=short.has(chapter)&&!(chapter===1&&name.startsWith('01_'))?20:30;
    const questions=ex===20?60:100;
    if(j.exercises.length!==ex||j.quiz.length!==questions)errors.push(`${id}: ${j.exercises.length}/${j.quiz.length}, expected ${ex}/${questions}`);
    const seen=new Set(),answers={A:0,B:0,C:0,D:0};
    for(const [index,q] of j.quiz.entries()){
      const tag=`${id} question ${index+1}`;
      if(Object.keys(q.options).sort().join('')!=='ABCD'||!q.options[q.correct])errors.push(tag+': invalid choices/answer');
      if(!q.question.trim()||!q.explanation.trim())errors.push(tag+': empty text');
      // Wielkosc liter ma znaczenie w nazwach, poleceniach i flagach Linuksa.
      if(new Set(Object.values(q.options).map(x=>x.trim())).size!==4)errors.push(tag+': identical choices');
      const question=q.question.trim();
      if(seen.has(question))errors.push(tag+': identical question');
      seen.add(question);answers[q.correct]++;
    }
    if(Object.values(answers).some(x=>x!==questions/4))errors.push(id+': unbalanced correct letters');
    for(const [index,e] of j.exercises.entries()){
      if(!e.prompt.trim()||!e.hint.trim()||!e.solution.trim())errors.push(`${id} exercise ${index+1}: empty field`);
      if(/[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]/.test(e.solution))errors.push(`${id} exercise ${index+1}: Polish character in solution`);
    }
    const txt=path.join(root,'scripts/content-migration/lx_practice',`_lx_${String(chapter).padStart(2,'0')}_${name.slice(0,2)}.txt`);
    if(!fs.existsSync(txt))errors.push(id+': missing source TXT');
    else{
      const source=fs.readFileSync(txt,'utf8');
      if((source.match(/^#EX\s*$/gm)||[]).length!==ex||(source.match(/^#Q\s*$/gm)||[]).length!==questions)errors.push(id+': source count differs');
    }
  }
  chapters.push({chapter,completed,lessons});
}
if(total!==437)errors.push(`Expected 437 lessons, got ${total}`);
console.log(JSON.stringify({total,ready,remaining:missing.length,next:missing[0]||null,chapters,errors},null,2));
if(errors.length||(process.argv.includes('--require-complete')&&missing.length))process.exitCode=1;

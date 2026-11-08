// Lekcja 23/01 "Trening całościowy": pula pytań z całego toru React i pytania przekrojowe.
// Teoria zostaje bez zmian (react_build.cjs); ten skrypt zastępuje tylko "exercises" i "quiz".
// Do puli nie trafiają pytania wymagające kontekstu lekcji: kod importujący pliki projektu,
// odwołania typu "w tej lekcji" albo "ten kod" bez kodu, a także powtórki pytań z innej lekcji.
// Uruchomienie: node scripts/content-migration/react/react_final_training.cjs
const fs=require('node:fs'),path=require('node:path'),assert=require('node:assert/strict');
const {crossQuestions,crossExercises}=require('./react_final_cross.cjs');
const root=path.resolve(__dirname,'../../../src/main/resources/content');
const target=path.join(root,'_react_23_podsumowanie','01_FinalTraining.json');

const NEEDS_LESSON=/(tej lekcji|tym modelu|tego modelu|w modelu|powtórka|tym projekcie|tego projektu|w tym przykładzie|powyżej|poniżej|lab\b)/i;
const NEEDS_CODE=/(ten kod|tym kodzie|tego kodu|ten fragment|tym fragmencie|tego fragmentu|tej funkcji|ta funkcja|ten komponent|tym komponencie|tego komponentu|ten test|tego testu|ta klasa|tej klasy|ten plik|tym pliku|tego pliku)/i;
const LOCAL_IMPORT=/from\s+["']\.\.?\/|from\s+["']@\//;
// Pytania-próbki z lekcji odwołują się do funkcji projektu nawet bez importu względnego w treści.
const LOCAL_CALL=/\b(observe[A-Z]\w*|probe[A-Z]\w*|lab[A-Z]\w*)\(/;

function usableQuestion(item){
  if(NEEDS_LESSON.test(item.question))return false;
  if(item.code)return !LOCAL_IMPORT.test(item.code)&&!LOCAL_CALL.test(item.code);
  return !NEEDS_CODE.test(item.question);
}

// Równomiernie z dostępnych, żeby jedna lekcja nie zdominowała zakładki ćwiczeń.
function pickExercises(list,limit){
  const eligible=list.filter(item=>item.solution.length<=1200&&!NEEDS_LESSON.test(item.prompt)&&!NEEDS_CODE.test(item.prompt)&&!/from\s+["']\.\.?\//.test(item.solution));
  if(eligible.length<=limit)return eligible;
  const step=eligible.length/limit;
  return Array.from({length:limit},(_,index)=>eligible[Math.floor(index*step)]);
}

const quiz=[],exercises=[],seen=new Set(),perChapter={};
let considered=0;
const chapters=fs.readdirSync(root).filter(name=>/^_react_\d{2}_/.test(name)&&name!=='_react_23_podsumowanie').sort();
for(const chapter of chapters){
  const number=chapter.slice(7,9);
  const lessons=fs.readdirSync(path.join(root,chapter)).filter(name=>name.endsWith('.json')).sort();
  for(const file of lessons){
    const lesson=JSON.parse(fs.readFileSync(path.join(root,chapter,file),'utf8'));
    assert.ok(lesson.quiz.length>0&&lesson.exercises.length>0,'Lekcja bez praktyki: '+chapter+'/'+file);
    for(const item of lesson.quiz){
      considered++;
      if(!usableQuestion(item))continue;
      const key=item.question+'\n'+(item.code??'')+'\n'+item.options[item.correct];
      if(seen.has(key))continue;
      seen.add(key);
      quiz.push(item);
      perChapter[number]=(perChapter[number]??0)+1;
    }
    for(const item of pickExercises(lesson.exercises,4)){
      exercises.push({...item,prompt:'('+number+'/'+file.slice(0,2)+') '+item.prompt});
    }
  }
}

// Pytania przekrojowe: powtarzalne rozmieszczenie poprawnej odpowiedzi, równo na A–D.
const positions=Array.from({length:crossQuestions.length},(_,index)=>index%4);
let seed=20261108;
for(let i=positions.length-1;i>0;i--){
  seed=(Math.imul(seed,1664525)+1013904223)>>>0;
  const j=seed%(i+1);
  [positions[i],positions[j]]=[positions[j],positions[i]];
}
crossQuestions.forEach((row,index)=>{
  assert.ok(row.length===6||row.length===7,row[0]);
  const [question,answer,b,c,d,explanation,code]=row;
  const choices=[b,c,d];
  choices.splice(positions[index],0,answer);
  assert.equal(new Set(choices).size,4,'Powtórzona opcja: '+question);
  const key=question+'\n'+(code??'')+'\n'+answer;
  assert.ok(!seen.has(key),'Powtórzone pytanie: '+question);
  seen.add(key);
  quiz.push({question,...(code?{code}:{}),options:Object.fromEntries(choices.map((value,i)=>['ABCD'[i],value])),correct:'ABCD'[positions[index]],explanation});
});
exercises.push(...crossExercises.map(([prompt,hint,solution])=>({prompt,hint,solution})));

for(const item of quiz){
  assert.equal(Object.keys(item.options).length,4,item.question);
  assert.ok(item.options[item.correct],item.question);
  assert.ok(item.explanation?.trim(),item.question);
}
for(const item of exercises)for(const field of ['prompt','hint','solution'])assert.ok(item[field]?.trim(),item.prompt);
assert.equal(new Set(exercises.map(item=>item.prompt)).size,exercises.length,'Powtórzone ćwiczenie');
const text=JSON.stringify({quiz,exercises});
assert.doesNotMatch(text,/rekrutacj/i,'Treść nie używa tego słowa');

const lesson=JSON.parse(fs.readFileSync(target,'utf8'));
fs.writeFileSync(target,JSON.stringify({...lesson,exercises,quiz},null,2)+'\n');
const letters=Object.fromEntries('ABCD'.split('').map(letter=>[letter,quiz.filter(item=>item.correct===letter).length]));
console.log('Rozważono pytań:',considered,'| w puli:',quiz.length,'(przekrojowych:',crossQuestions.length+')');
console.log('Ćwiczenia:',exercises.length,'(przekrojowych:',crossExercises.length+')');
console.log('Poprawne odpowiedzi:',letters);
console.log('Rozmiar MB:',(fs.statSync(target).size/1048576).toFixed(1));
console.log('Pytania według rozdziałów:',perChapter);

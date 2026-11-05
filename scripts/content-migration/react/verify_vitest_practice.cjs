// Ogólny weryfikator ćwiczeń i quizów rozdziałów 15+ (React od środka, wydajność, React 19 …).
// Dla każdej lekcji: kontrola treści JSON, TypeScript (projekt, rozwiązania, kod pytań), prawdziwy Vitest w jsdom
// (testy projektu + próbki quizu: ostatnie `probeCount` pytań musi wypisać dokładnie odpowiedź przez console.log)
// oraz mutanty, które muszą oblać testy zachowania.
// Uruchomienie: node scripts/content-migration/react/verify_vitest_practice.cjs [slug...]
const fs=require('node:fs'),path=require('node:path'),assert=require('node:assert/strict');
const {execFileSync}=require('node:child_process');
const root=path.resolve(__dirname,'../../..'),deps=path.join(root,'target/react-testing-deps');
const ts=require(path.join(root,'target/react-typescript-qa/node_modules/typescript'));
const entries=[
  ['_react_15_react_od_srodka','01_ElementsAndVirtualDom','./practice_inside_01.cjs',30,100],
  ['_react_15_react_od_srodka','02_Reconciliation','./practice_inside_02.cjs',30,100],
  ['_react_15_react_od_srodka','03_FiberArchitecture','./practice_inside_03.cjs',30,100],
];
const requested=process.argv.slice(2);
const selected=requested.length?entries.filter(entry=>requested.includes(entry[1])):entries;
assert.equal(selected.length,requested.length||entries.length,'Nieznana lekcja');
const defaultConfig='import {defineConfig} from "vitest/config";\nexport default defineConfig({test:{environment:"jsdom",include:["src/**/*.test.ts","src/**/*.test.tsx"],setupFiles:["./src/setup.ts"],restoreMocks:true}});\n';
const defaultSetup='import {afterEach} from "vitest";\nimport {cleanup} from "@testing-library/react";\nimport "@testing-library/jest-dom/vitest";\n(globalThis as {IS_REACT_ACT_ENVIRONMENT?:boolean}).IS_REACT_ACT_ENVIRONMENT=true;\nafterEach(()=>{cleanup();document.body.innerHTML="";});\n';
function writeProject(files,dir){for(const [file,code] of Object.entries(files)){fs.mkdirSync(path.dirname(path.join(dir,file)),{recursive:true});fs.writeFileSync(path.join(dir,file),code);}}
function withDefaults(project){return {'package.json':'{"type":"module"}\n','vitest.config.ts':defaultConfig,'src/setup.ts':defaultSetup,...project};}
function probeTest(row,index){
  const [question,answer,,,,,code]=row,imports=[];
  const body=code.replace(/^import\s+[\s\S]*?\s+from\s+["'][^"']+["'];?/gm,match=>{imports.push(match);return '';});
  return '// @vitest-environment jsdom\nimport {test as probeTest,expect as probeExpect,vi as probeVi} from "vitest";\n'+imports.join('\n')+
    '\nprobeTest('+JSON.stringify('pytanie '+(index+1)+': '+question)+',async()=>{const observed:string[]=[];const log=probeVi.spyOn(console,"log").mockImplementation((...values:unknown[])=>{observed.push(values.map(String).join(" "));});try{\n'+body+
    '\n;probeExpect(observed).toEqual(['+JSON.stringify(answer)+']);}finally{log.mockRestore();}});\n';
}
function runVitest(dir){
  const report=path.join(dir,'vitest-results.json'),env={...process.env,NODE_ENV:'test'};let failed=false;
  try{execFileSync(process.execPath,[path.join(deps,'node_modules/vitest/vitest.mjs'),'run','--root',dir,'--reporter=json','--outputFile',report],{cwd:dir,env,stdio:'pipe',timeout:300000});}catch{failed=true;}
  return {failed,report:JSON.parse(fs.readFileSync(report,'utf8'))};
}
function failures(report){return report.testResults.flatMap(file=>file.assertionResults.filter(a=>a.status==='failed').map(a=>a.fullName+': '+(a.failureMessages??[]).join(' ').split('\n').slice(0,4).join(' | ')).concat(file.status==='failed'&&!file.assertionResults.length?[path.basename(file.name)+': '+String(file.message).slice(0,600)]:[]));}
for(const [chapter,slug,sourcePath,ec,qc] of selected){
  const source=require(sourcePath),lesson=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content',chapter,slug+'.json'),'utf8'));
  assert.equal(lesson.exercises.length,ec);assert.equal(lesson.quiz.length,qc);
  assert.equal(new Set(lesson.exercises.map(e=>e.prompt)).size,ec);assert.equal(new Set(lesson.quiz.map(q=>q.question+'\n'+(q.code??''))).size,qc);
  for(const e of lesson.exercises)for(const field of ['prompt','hint','solution'])assert.ok(e[field]?.trim());
  for(const q of lesson.quiz){assert.equal(new Set(Object.values(q.options)).size,4);assert.ok(q.options[q.correct]);assert.ok(q.explanation.trim());}
  for(const letter of 'ABCD')assert.equal(lesson.quiz.filter(q=>q.correct===letter).length,qc/4);
  const project=withDefaults(source.project);
  // TypeScript: katalog QA w środowisku testów, więc pakiety rozwiązują się zwykłą ścieżką node_modules.
  const qa=path.join(deps,'qa',slug),sources=new Map();
  for(const [file,code] of Object.entries(project))if(/\.tsx?$/.test(file))sources.set(path.join(qa,file),code);
  lesson.exercises.forEach((e,i)=>{if(/^(?:import |export |type |const |function |\/\/ @check)/.test(e.solution))sources.set(path.join(qa,'src','exercise'+(i+1)+'.tsx'),e.solution+'\nexport {};');});
  lesson.quiz.forEach((q,i)=>{if(q.code&&!/^\/\/ @check-skip/.test(q.code))sources.set(path.join(qa,'src','question'+(i+1)+'.tsx'),q.code+'\nexport {};');});
  const options={strict:true,noEmit:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.ESNext,moduleResolution:ts.ModuleResolutionKind.Bundler,jsx:ts.JsxEmit.ReactJSX,skipLibCheck:true,types:[],lib:['lib.es2023.d.ts','lib.dom.d.ts','lib.dom.iterable.d.ts']};
  const host=ts.createCompilerHost(options),read=host.readFile.bind(host),exists=host.fileExists.bind(host),directory=host.directoryExists.bind(host);
  host.readFile=file=>sources.get(path.resolve(file))??read(file);host.fileExists=file=>sources.has(path.resolve(file))||exists(file);host.directoryExists=dir=>[...sources.keys()].some(file=>file.startsWith(path.resolve(dir)+path.sep))||directory(dir);
  const diagnostics=ts.getPreEmitDiagnostics(ts.createProgram([...sources.keys()],options,host));
  assert.equal(diagnostics.length,0,ts.formatDiagnosticsWithColorAndContext(diagnostics,{getCurrentDirectory:()=>root,getCanonicalFileName:f=>f,getNewLine:()=>'\n'}));
  // Vitest: testy projektu i próbki quizu w jednym przebiegu.
  const probes=source.probeCount??0,fixture=fs.mkdtempSync(path.join(deps,'lesson-'));
  writeProject(project,fixture);
  source.quiz.slice(source.quiz.length-probes).forEach((row,i)=>{assert.ok(row[6],'Próbka bez kodu: '+row[0]);fs.writeFileSync(path.join(fixture,'src','quiz-'+i+'.test.tsx'),probeTest(row,source.quiz.length-probes+i));});
  const clean=runVitest(fixture);
  assert.equal(clean.failed,false,'Vitest '+slug+':\n'+failures(clean.report).join('\n'));
  assert.ok(clean.report.numPassedTests>=probes+1);
  let rejected=0;
  for(const mutation of source.mutants??[]){
    const dir=fs.mkdtempSync(path.join(deps,'mutant-'));writeProject(project,dir);
    const file=path.join(dir,mutation.file),original=fs.readFileSync(file,'utf8');
    assert.ok(original.includes(mutation.before),'Brak miejsca mutacji: '+mutation.before);
    fs.writeFileSync(file,original.replace(mutation.before,mutation.after));
    const run=runVitest(dir);
    assert.ok(run.failed&&run.report.numFailedTests>0,'Mutant przeszedł testy: '+mutation.file+' / '+mutation.before);
    rejected++;
  }
  console.log(`OK ${slug}: ${sources.size} plików TypeScript, ${clean.report.numPassedTests} testów Vitest (w tym ${probes} próbek quizu), ${rejected} mutantów odrzuconych.`);
}

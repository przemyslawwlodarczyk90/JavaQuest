// Weryfikacja lekcji E2E: treść, typy, build Vite, prawdziwe testy Playwright w Chromium i mutanty aplikacji.
// Wymaga: target/react-e2e-deps z @playwright/test 1.64.1 oraz `npx playwright install chromium`.
// Uruchomienie: node scripts/content-migration/react/verify_e2e_practice.cjs [slug]
const fs=require('node:fs'),path=require('node:path'),net=require('node:net'),assert=require('node:assert/strict');
const {execFileSync}=require('node:child_process'),{pathToFileURL}=require('node:url');
const root=path.resolve(__dirname,'../../..'),deps=path.join(root,'target/react-e2e-deps'),base=path.join(root,'frontend/node_modules');
const ts=require(path.join(root,'target/react-typescript-qa/node_modules/typescript'));
const entries=[['07_PlaywrightE2e','./practice_test_07.cjs',30,100,15]];
const requested=process.argv.slice(2);
const selected=requested.length?entries.filter(entry=>requested.includes(entry[0])):entries;
assert.equal(selected.length,requested.length||entries.length,'Nieznana lekcja');
function freePort(){return new Promise((resolve,reject)=>{const server=net.createServer();server.once('error',reject);server.listen(0,'127.0.0.1',()=>{const {port}=server.address();server.close(()=>resolve(port));});});}
function writeProject(project,dir){for(const [file,code] of Object.entries(project)){fs.mkdirSync(path.dirname(path.join(dir,file)),{recursive:true});fs.writeFileSync(path.join(dir,file),code);}}
async function buildApp(dir){
  const {build}=await import(pathToFileURL(path.join(base,'vite/dist/node/index.js')));
  const {default:react}=await import(pathToFileURL(path.join(base,'@vitejs/plugin-react/dist/index.js')));
  await build({root:dir,configFile:false,logLevel:'error',plugins:[react()],resolve:{alias:[
    {find:/^react-router$/,replacement:base+'/react-router/dist/development/index.mjs'},
    {find:/^react$/,replacement:base+'/react/index.js'},{find:/^react\/(.*)$/,replacement:base+'/react/$1'},
    {find:/^react-dom$/,replacement:base+'/react-dom/index.js'},{find:/^react-dom\/(.*)$/,replacement:base+'/react-dom/$1'}]}});
  assert.ok(fs.existsSync(path.join(dir,'dist/index.html')));
}
async function runPlaywright(dir){
  const port=await freePort(),env={...process.env,PORT:String(port)};delete env.CI;
  let failed=false;
  try{execFileSync(process.execPath,[path.join(deps,'node_modules/@playwright/test/cli.js'),'test','--reporter=json'],{cwd:dir,env:{...env,PLAYWRIGHT_JSON_OUTPUT_NAME:'e2e-results.json'},stdio:'pipe',timeout:240000});}catch{failed=true;}
  const report=JSON.parse(fs.readFileSync(path.join(dir,'e2e-results.json'),'utf8'));
  return {failed,report};
}
function failures(report){const out=[];const walk=suite=>{for(const spec of suite.specs??[])for(const test of spec.tests)for(const result of test.results)if(result.status!=='passed')out.push(spec.title+': '+(result.error?.message??result.status).split('\n')[0]);for(const child of suite.suites??[])walk(child);};for(const suite of report.suites)walk(suite);return out;}
(async()=>{
  for(const [slug,sourcePath,ec,qc,testCount] of selected){
    const source=require(sourcePath),lesson=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content/_react_14_testy',slug+'.json'),'utf8'));
    assert.equal(lesson.exercises.length,ec);assert.equal(lesson.quiz.length,qc);
    assert.equal(new Set(lesson.exercises.map(e=>e.prompt)).size,ec);assert.equal(new Set(lesson.quiz.map(q=>q.question+'\n'+(q.code??''))).size,qc);
    for(const q of lesson.quiz){assert.equal(new Set(Object.values(q.options)).size,4);assert.ok(q.options[q.correct]);assert.ok(q.explanation.trim());}
    for(const letter of 'ABCD')assert.equal(lesson.quiz.filter(q=>q.correct===letter).length,qc/4);
    // Typy: projekt, rozwiązania zadań i kod pytań w jednym programie TypeScript.
    const qa=path.join(root,'target/react-e2e-qa',slug),sources=new Map();
    for(const [file,code] of Object.entries(source.project))if(/\.tsx?$/.test(file))sources.set(path.join(qa,file),code);
    lesson.exercises.forEach((e,i)=>{if(!/^(?:import |export |type |const )/.test(e.solution))return;const dir=e.solution.includes('from "./fixtures"')?'e2e':'src';sources.set(path.join(qa,dir,'exercise'+(i+1)+'.tsx'),e.solution+'\nexport {};');});
    lesson.quiz.forEach((q,i)=>{if(q.code)sources.set(path.join(qa,'e2e','question'+(i+1)+'.ts'),q.code+'\nexport {};');});
    const options={strict:true,noEmit:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.ESNext,moduleResolution:ts.ModuleResolutionKind.Bundler,jsx:ts.JsxEmit.ReactJSX,types:['node'],typeRoots:[path.join(root,'target/react-api-deps/node_modules/@types')],skipLibCheck:true,lib:['lib.es2023.d.ts','lib.dom.d.ts','lib.dom.iterable.d.ts'],
      paths:{'@playwright/test':[path.join(deps,'node_modules/@playwright/test/index.d.ts')],'react-router':[base+'/react-router/dist/development/index.d.mts'],react:[base+'/@types/react/index.d.ts'],'react/*':[base+'/@types/react/*'],'react-dom':[base+'/@types/react-dom/index.d.ts'],'react-dom/*':[base+'/@types/react-dom/*']}};
    const host=ts.createCompilerHost(options),read=host.readFile.bind(host),exists=host.fileExists.bind(host),directory=host.directoryExists.bind(host);
    host.readFile=file=>sources.get(path.resolve(file))??read(file);host.fileExists=file=>sources.has(path.resolve(file))||exists(file);host.directoryExists=dir=>[...sources.keys()].some(file=>file.startsWith(path.resolve(dir)+path.sep))||directory(dir);
    const diagnostics=ts.getPreEmitDiagnostics(ts.createProgram([...sources.keys()],options,host));
    assert.equal(diagnostics.length,0,ts.formatDiagnosticsWithColorAndContext(diagnostics,{getCurrentDirectory:()=>root,getCanonicalFileName:f=>f,getNewLine:()=>'\n'}));
    // Prawdziwy przebieg: build, serwer z webServer, Chromium.
    const fixture=fs.mkdtempSync(path.join(deps,'lesson-'));writeProject(source.project,fixture);await buildApp(fixture);
    const clean=await runPlaywright(fixture);
    assert.equal(clean.failed,false,'Testy E2E nie przeszły:\n'+failures(clean.report).join('\n'));
    assert.equal(clean.report.stats.expected,testCount,'Liczba testów E2E');assert.equal(clean.report.stats.unexpected,0);
    let mutantsRejected=0,traces=0;
    for(const mutation of source.mutants){
      const dir=fs.mkdtempSync(path.join(deps,'mutant-'));writeProject(source.project,dir);
      const file=path.join(dir,mutation.file),original=fs.readFileSync(file,'utf8');
      assert.ok(original.includes(mutation.before),'Brak miejsca mutacji: '+mutation.before);
      fs.writeFileSync(file,original.replace(mutation.before,mutation.after));
      await buildApp(dir);
      const run=await runPlaywright(dir);
      assert.ok(run.failed&&run.report.stats.unexpected>0,'Mutant przeszedł testy: '+mutation.file+' / '+mutation.before);
      mutantsRejected++;
      const found=[];const walk=d=>{for(const entry of fs.readdirSync(d,{withFileTypes:true})){const p=path.join(d,entry.name);if(entry.isDirectory())walk(p);else if(entry.name==='trace.zip')found.push(p);}};
      if(fs.existsSync(path.join(dir,'test-results')))walk(path.join(dir,'test-results'));
      assert.ok(found.length>0,'Brak trace.zip dla nieudanego testu');traces+=found.length;
    }
    console.log(`OK e2e ${slug}: ${sources.size} plików TypeScript, ${clean.report.stats.expected} testów Playwright w Chromium, ${mutantsRejected} mutantów odrzuconych, ${traces} śladów trace.zip dla błędów.`);
  }
})().catch(error=>{console.error(error);process.exitCode=1;});

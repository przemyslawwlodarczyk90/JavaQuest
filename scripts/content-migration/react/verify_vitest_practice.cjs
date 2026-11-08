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
  ['_react_15_react_od_srodka','04_RenderAndCommitPhases','./practice_inside_04.cjs',30,100],
  ['_react_15_react_od_srodka','05_BatchingSchedulingAndLanes','./practice_inside_05.cjs',30,100],
  ['_react_15_react_od_srodka','06_HooksUnderTheHood','./practice_inside_06.cjs',30,100],
  ['_react_15_react_od_srodka','07_BuildMiniReact','./practice_inside_07.cjs',30,100],
  ['_react_16_wydajnosc','01_RenderModelAndProfiler','./practice_perf_01.cjs',30,100],
  ['_react_16_wydajnosc','02_Memoization','./practice_perf_02.cjs',30,100],
  ['_react_16_wydajnosc','03_ReactCompiler','./practice_perf_03.cjs',30,100],
  ['_react_16_wydajnosc','04_DrillMemoization','./practice_perf_04.cjs',30,100],
  ['_react_16_wydajnosc','05_LazyAndSuspense','./practice_perf_05.cjs',30,100],
  ['_react_16_wydajnosc','06_TransitionsAndDeferredValues','./practice_perf_06.cjs',30,100],
  ['_react_16_wydajnosc','07_VirtualizingLongLists','./practice_perf_07.cjs',30,100],
  ['_react_16_wydajnosc','08_BundleAnalysisAndCodeSplitting','./practice_perf_08.cjs',30,100],
  ['_react_16_wydajnosc','09_WebVitalsAndAssets','./practice_perf_09.cjs',30,100],
  ['_react_16_wydajnosc','10_BossFindTheBottleneck','./practice_perf_10.cjs',30,100],
  ['_react_17_react_19','01_UseHookAndPromises','./practice_react19_01.cjs',30,100],
  ['_react_17_react_19','02_SuspenseForData','./practice_react19_02.cjs',30,100],
  ['_react_17_react_19','03_UseOptimistic','./practice_react19_03.cjs',30,100],
  ['_react_17_react_19','04_ActionsAndTransitions','./practice_react19_04.cjs',30,100],
  ['_react_17_react_19','05_DocumentMetadataAndResources','./practice_react19_05.cjs',30,100],
  ['_react_17_react_19','06_ActivityAndViewTransitions','./practice_react19_06.cjs',30,100],
  ['_react_17_react_19','07_ServerComponentsConcept','./practice_react19_07.cjs',30,100],
  ['_react_17_react_19','08_DrillReact19Hooks','./practice_react19_08.cjs',30,100],
  ['_react_18_frameworki','01_RenderingStrategies','./practice_framework_01.cjs',30,100],
  ['_react_18_frameworki','02_HydrationAndItsErrors','./practice_framework_02.cjs',30,100],
  ['_react_18_frameworki','03_NextJsAppRouterBasics','./practice_framework_03.cjs',30,100],
  ['_react_18_frameworki','04_NextJsServerActionsAndCaching','./practice_framework_04.cjs',30,100],
  ['_react_18_frameworki','05_ReactRouterFrameworkMode','./practice_framework_05.cjs',30,100],
  ['_react_18_frameworki','06_ChoosingAFramework','./practice_framework_06.cjs',30,100],
  ['_react_19_jakosc','01_AccessibilityFundamentals','./practice_quality_01.cjs',30,100],
  ['_react_19_jakosc','02_AriaPatternsAndScreenReaders','./practice_quality_02.cjs',30,100],
  ['_react_19_jakosc','03_AccessibilityPassDevDesk','./practice_quality_03.cjs',30,100],
  ['_react_19_jakosc','04_Internationalization','./practice_quality_04.cjs',30,100],
  ['_react_19_jakosc','05_SecurityInReact','./practice_quality_05.cjs',30,100],
  ['_react_19_jakosc','06_ErrorBoundariesAndMonitoring','./practice_quality_06.cjs',30,100],
  ['_react_19_jakosc','07_StorybookAndComponentDocs','./practice_quality_07.cjs',30,100],
  ['_react_20_animacje_i_ux','01_CssTransitionsAndAnimations','./practice_ux_01.cjs',30,100],
  ['_react_20_animacje_i_ux','02_MotionLibrary','./practice_ux_02.cjs',30,100],
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
function runVitest(dir,dependencyRoot=deps,extraEnv={}){
  const temporary=path.join(dir,'.tmp');fs.mkdirSync(temporary,{recursive:true});
  const report=path.join(dir,'vitest-results.json'),env={...process.env,NODE_ENV:'test',TEMP:temporary,TMP:temporary,...extraEnv};let failed=false;
  try{execFileSync(process.execPath,[path.join(dependencyRoot,'node_modules/vitest/vitest.mjs'),'run','--root',dir,'--reporter=json','--outputFile',report],{cwd:dir,env,stdio:'pipe',timeout:300000});}catch{failed=true;}
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
  assert.match(source.dependencyRoot??'target/react-testing-deps',/^target\/react-(?:testing|next|astro)-deps$/);
  const activeDeps=path.join(root,source.dependencyRoot??'target/react-testing-deps');
  const project=withDefaults(source.project);
  // TypeScript: katalog QA w środowisku testów, więc pakiety rozwiązują się zwykłą ścieżką node_modules.
  const qa=path.join(activeDeps,'qa',slug),sources=new Map();
  for(const [file,code] of Object.entries(project))if(/\.tsx?$/.test(file))sources.set(path.join(qa,file),code);
  const jsonFiles=new Map(Object.entries(project).filter(([file])=>/^src\/.*\.json$/.test(file)).map(([file,code])=>[path.join(qa,file),code]));
  lesson.exercises.forEach((e,i)=>{if(/^(?:import |export |type |const |function |\/\/ @check)/.test(e.solution))sources.set(path.join(qa,'src','exercise'+(i+1)+'.tsx'),e.solution+'\nexport {};');});
  lesson.quiz.forEach((q,i)=>{if(q.code&&!/^\/\/ @check-skip/.test(q.code))sources.set(path.join(qa,'src','question'+(i+1)+'.tsx'),q.code+'\nexport {};');});
  const options={resolveJsonModule:true,baseUrl:qa,paths:{"@/*":["src/*"]},strict:true,noEmit:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.ESNext,moduleResolution:ts.ModuleResolutionKind.Bundler,jsx:ts.JsxEmit.ReactJSX,skipLibCheck:true,types:[],lib:['lib.es2023.d.ts','lib.dom.d.ts','lib.dom.iterable.d.ts']};
  const host=ts.createCompilerHost(options),read=host.readFile.bind(host),exists=host.fileExists.bind(host),directory=host.directoryExists.bind(host);
  host.readFile=file=>sources.get(path.resolve(file))??jsonFiles.get(path.resolve(file))??read(file);host.fileExists=file=>sources.has(path.resolve(file))||jsonFiles.has(path.resolve(file))||exists(file);host.directoryExists=dir=>[...sources.keys(),...jsonFiles.keys()].some(file=>file.startsWith(path.resolve(dir)+path.sep))||directory(dir);
  const diagnostics=ts.getPreEmitDiagnostics(ts.createProgram([...sources.keys()],options,host));
  assert.equal(diagnostics.length,0,ts.formatDiagnosticsWithColorAndContext(diagnostics,{getCurrentDirectory:()=>root,getCanonicalFileName:f=>f,getNewLine:()=>'\n'}));
  // Vitest: testy projektu i próbki quizu w jednym przebiegu.
  const probes=source.probeCount??0,fixture=fs.mkdtempSync(path.join(activeDeps,'lesson-'));
  writeProject(project,fixture);
  source.quiz.slice(source.quiz.length-probes).forEach((row,i)=>{assert.ok(row[6],'Próbka bez kodu: '+row[0]);fs.writeFileSync(path.join(fixture,'src','quiz-'+i+'.test.tsx'),probeTest(row,source.quiz.length-probes+i));});
  const clean=runVitest(fixture,activeDeps);
  assert.equal(clean.failed,false,'Vitest '+slug+':\n'+failures(clean.report).join('\n'));
  assert.ok(clean.report.numPassedTests>=probes+1);
  let rejected=0;
  for(const mutation of source.mutants??[]){
    const dir=fs.mkdtempSync(path.join(activeDeps,'mutant-'));writeProject(project,dir);
    const file=path.join(dir,mutation.file),original=fs.readFileSync(file,'utf8');
    assert.ok(original.includes(mutation.before),'Brak miejsca mutacji: '+mutation.before);
    fs.writeFileSync(file,original.replace(mutation.before,mutation.after));
    const run=runVitest(dir,activeDeps,{LESSON_MUTANT:"1"});
    assert.ok(run.failed&&run.report.numFailedTests>0,'Mutant przeszedł testy: '+mutation.file+' / '+mutation.before);
    rejected++;
  }
  console.log(`OK ${slug}: ${sources.size} plików TypeScript, ${clean.report.numPassedTests} testów Vitest (w tym ${probes} próbek quizu), ${rejected} mutantów odrzuconych.`);
}

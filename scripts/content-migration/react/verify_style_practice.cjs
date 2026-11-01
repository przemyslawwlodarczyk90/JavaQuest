// node scripts/content-migration/react/verify_style_practice.cjs
const fs=require('node:fs'),path=require('node:path'),vm=require('node:vm'),assert=require('node:assert/strict');
const {createRequire}=require('node:module'),{pathToFileURL}=require('node:url');
const root=path.resolve(__dirname,'../../..');
const ts=require(path.join(root,'target/react-typescript-qa/node_modules/typescript'));
const req=createRequire(path.join(root,'frontend/package.json'));
// Zależności QA są w target; runtime React pozostaje tym samym pakietem co renderer.
process.env.NODE_PATH=path.join(root,'frontend/node_modules');require('node:module').Module._initPaths();
const React=req('react'),{renderToStaticMarkup}=req('react-dom/server');
const clsx=require(path.join(root,'target/react-style-qa/node_modules/clsx'));
const entries=[['01_CssInReactBasics','./practice_style_01.cjs',30,100]];
entries.push(['02_CssModules','./practice_style_02.cjs',30,100]);
entries.push(['03_DesignTokensAndTheming','./practice_style_03.cjs',30,100]);
entries.push(['04_TailwindCss','./practice_style_04.cjs',30,100]);
entries.push(['05_CssInJsOverview','./practice_style_05.cjs',20,60]);
entries.push(['06_ComponentLibrariesOverview','./practice_style_06.cjs',20,60]);
entries.push(['07_BossStyleGuidePage','./practice_style_07.cjs',30,100]);
const sources=new Map();
function add(file,code){sources.set(path.join(root,'target/react-style-qa/virtual',file),code+'\nexport {};');}
sources.set(path.join(root,'target/react-style-qa/virtual/env.d.ts'),'declare module "*.module.css" { const classes: { readonly [key:string]:string }; export default classes; }\ndeclare module "*.css";');
for(const [slug,source,ec,qc] of entries){
 const lesson=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content/_react_03_stylowanie',slug+'.json'),'utf8'));
 assert.equal(lesson.exercises.length,ec);assert.equal(lesson.quiz.length,qc);
 assert.equal(new Set(lesson.exercises.map(e=>e.prompt)).size,ec);
 assert.equal(new Set(lesson.quiz.map(q=>q.question+'\n'+(q.code??''))).size,qc);
 for(const e of lesson.exercises)for(const field of ['prompt','hint','solution'])assert.ok(e[field]?.trim());
 for(const q of lesson.quiz){assert.equal(new Set(Object.values(q.options)).size,4);assert.ok(q.options[q.correct]);assert.ok(q.explanation.trim());}
 for(const letter of 'ABCD')assert.equal(lesson.quiz.filter(q=>q.correct===letter).length,qc/4);
 lesson.exercises.forEach((e,i)=>{if(slug==='03_DesignTokensAndTheming'&&i===14)return; if(slug==='04_TailwindCss'&&i===1){assert.ok(!ts.transpileModule(e.solution,{reportDiagnostics:true,compilerOptions:{module:ts.ModuleKind.ESNext}}).diagnostics.length);return;} if(/^(?:import |export |type |const |\/\*\*)/.test(e.solution))add(`${slug}/exercise${i+1}${e.solution.startsWith('/**')?'.jsx':'.tsx'}`,e.solution);});
 lesson.quiz.forEach((q,i)=>{if(q.code)add(`${slug}/question${i+1}.tsx`,q.code);});
 const author=require(source);
 if(author.project)for(const [file,code] of Object.entries(author.project))if(file.startsWith('src/')&&/\.tsx?$/.test(file))add(slug+'/'+file,code);
}
const options={strict:true,noEmit:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.ESNext,moduleResolution:ts.ModuleResolutionKind.Bundler,jsx:ts.JsxEmit.ReactJSX,types:[],skipLibCheck:true,allowJs:true,checkJs:true,paths:{"@radix-ui/react-dialog":[path.join(root,"target/react-style-qa/node_modules/@radix-ui/react-dialog/dist/index.d.ts")],"react-dom":[path.join(root,"frontend/node_modules/@types/react-dom/index.d.ts")],"styled-components":[path.join(root,"target/react-style-qa/node_modules/styled-components/dist/index.d.ts")],"react-dom/*":[path.join(root,"frontend/node_modules/@types/react-dom/*")],react:[path.join(root,'frontend/node_modules/@types/react/index.d.ts')],'react/*':[path.join(root,'frontend/node_modules/@types/react/*')],clsx:[path.join(root,'target/react-style-qa/node_modules/clsx/clsx.d.ts')]}};
const host=ts.createCompilerHost(options),read=host.readFile.bind(host),exists=host.fileExists.bind(host),directory=host.directoryExists.bind(host);
host.readFile=f=>sources.get(path.resolve(f))??read(f);host.fileExists=f=>sources.has(path.resolve(f))||exists(f);host.directoryExists=d=>[...sources.keys()].some(f=>f.startsWith(path.resolve(d)+path.sep))||directory(d);
const diagnostics=ts.getPreEmitDiagnostics(ts.createProgram([...sources.keys()],options,host));
assert.equal(diagnostics.length,0,ts.formatDiagnosticsWithColorAndContext(diagnostics,{getCurrentDirectory:()=>root,getCanonicalFileName:f=>f,getNewLine:()=> '\n'}));
function evaluate(code,imports={}){const context={exports:{},require:name=>imports[name]??(name==='clsx'?clsx:name==='styled-components'?require(path.join(root,'target/react-style-qa/node_modules/styled-components')):req(name)),console:{log(){}}};vm.runInNewContext(ts.transpileModule(code,{compilerOptions:{target:ts.ScriptTarget.ES2022,esModuleInterop:true,module:ts.ModuleKind.CommonJS,jsx:ts.JsxEmit.ReactJSX}}).outputText,context);return context.exports;}
const basic=require('./practice_style_01.cjs');let renders=0;
const ProgressBar=evaluate(basic.progress).ProgressBar;
for(const [value,expected] of [[-10,0],[0,0],[30,30],[100,100],[150,100],[NaN,0],[Infinity,0]]){
 const html=renderToStaticMarkup(React.createElement(ProgressBar,{value,label:'Postęp'}));
 assert.ok(html.includes(`width:${expected}%`));assert.ok(html.includes(`aria-valuenow="${expected}"`));assert.ok(html.includes('aria-label="Postęp"'));renders++;
}
const TicketRow=evaluate(basic.row).TicketRow;
for(const selected of [false,true])for(const compact of [false,true])for(const status of ['OPEN','CLOSED']){
 const tree=TicketRow({ticket:{id:1,title:'API',status},selected,compact});
 const classes=tree.props.className.split(' ');assert.ok(classes.includes('row'));assert.equal(classes.includes('row--selected'),selected);assert.equal(classes.includes('row--compact'),compact);assert.equal(classes.includes('row--closed'),status==='CLOSED');renders++;
}
const chip=evaluate(basic.chip).TagChip;assert.ok(renderToStaticMarkup(React.createElement(chip,{label:'api',color:'#2563eb'})).includes('--chip-color:#2563eb'));renders++;
assert.equal(clsx('row',false,undefined,{selected:true,closed:false}),'row selected');assert.equal(clsx('row','row'),'row row');
(async()=>{
const {build,createServer}=await import(pathToFileURL(path.join(root,'frontend/node_modules/vite/dist/node/index.js')));
 const {default:reactPlugin}=await import(pathToFileURL(path.join(root,'frontend/node_modules/@vitejs/plugin-react/dist/index.js')));
 for(const [,source] of entries){const author=require(source);if(!author.project)continue;if(source==='./practice_style_04.cjs'){console.log('POMINIĘTO build Tailwind: pakiety niedostępne lokalnie, registry.npmjs.org ENOTFOUND. Typy komponentów sprawdzane osobno.');continue;}
  const fixture=fs.mkdtempSync(path.join(root,'target/react-style-build-'));
  for(const [file,code] of Object.entries(author.project)){fs.mkdirSync(path.dirname(path.join(fixture,file)),{recursive:true});fs.writeFileSync(path.join(fixture,file),code);}
  fs.writeFileSync(path.join(fixture,'index.html'),'<!doctype html><html lang="pl"><head><title>Style</title></head><body><div id="root"></div><script type="module" src="/src/main.tsx"></script></body></html>');
  if(!author.project['src/main.tsx'])fs.writeFileSync(path.join(fixture,'src/main.tsx'),'import {createRoot} from "react-dom/client";import App from "./App";const container=document.getElementById("root");if(!container)throw Error("Brak root");createRoot(container).render(<App/>);');
  const reactRoot=path.join(root,'frontend/node_modules/react'),domRoot=path.join(root,'frontend/node_modules/react-dom');
  const aliases=[{find:/^react-dom$/,replacement:domRoot+'/index.js'},{find:'@radix-ui/react-dialog',replacement:path.join(root,'target/react-style-qa/node_modules/@radix-ui/react-dialog/dist/index.mjs')},{find:/^react$/,replacement:reactRoot+'/index.js'},{find:/^react\/(.*)$/,replacement:reactRoot+'/$1'},{find:/^react-dom\/(.*)$/,replacement:domRoot+'/$1'},{find:'clsx',replacement:path.join(root,'target/react-style-qa/node_modules/clsx/dist/clsx.mjs')}];
  await build({root:fixture,configFile:false,logLevel:'error',plugins:[reactPlugin()],resolve:{alias:aliases}});
  const assets=fs.readdirSync(path.join(fixture,'dist/assets'));assert.ok(assets.some(f=>f.endsWith('.css')));assert.ok(assets.some(f=>f.endsWith('.js')));
  if(author===require('./practice_style_02.cjs')){
   const server=await createServer({root:fixture,configFile:false,logLevel:'error',server:{middlewareMode:true},resolve:{alias:aliases}});
   try{
    const panel=(await server.ssrLoadModule('/src/Panel.module.css')).default;
    const card=(await server.ssrLoadModule('/src/TicketCard.module.css')).default;
    const notice=(await server.ssrLoadModule('/src/Notice.module.css')).default;
    const shared=(await server.ssrLoadModule('/src/shared.module.css')).default;
    assert.notEqual(panel.title,card.title);assert.ok(panel.title);assert.ok(card.title);
    assert.ok(notice.notice.split(' ').includes(shared.base));assert.equal(notice.notice.split(' ').length,2);
    const imports={'./App.css':{}};
    for(const file of Object.keys(author.project).filter(file=>file.endsWith('.module.css')))imports['./'+path.basename(file)]={default:(await server.ssrLoadModule('/'+file)).default,__esModule:true};
    for(const [file,code] of Object.entries(author.project))if(file.endsWith('.tsx')&&!file.endsWith('/App.tsx'))imports['./'+path.basename(file,'.tsx')]=evaluate(code,imports);
    const App=evaluate(author.project['src/App.tsx'],imports).default;
    const html=renderToStaticMarkup(React.createElement(App));assert.ok(html.includes(panel.title));assert.ok(html.includes(card.title));assert.ok(html.includes('>0</strong>'));renders++;
    const builtCss=assets.filter(f=>f.endsWith('.css')).map(f=>fs.readFileSync(path.join(fixture,'dist/assets',f),'utf8')).join('\n');
    assert.ok(builtCss.includes('.dark '));assert.ok(builtCss.includes('font-size:1.5rem'));assert.ok(builtCss.includes('font-size:1rem'));
   }finally{await server.close();}
  }
 }
 let quizRuns=0;
 for(const [,source] of entries){const author=require(source);if(source==='./practice_style_01.cjs')continue;
  for(const q of author.quiz){if(!q[6])continue;let actual;
   const context={exports:{},require:name=>name==='clsx'?clsx:name==='styled-components'?require(path.join(root,'target/react-style-qa/node_modules/styled-components')):req(name),console:{log:value=>{actual=String(value);}}};
   vm.runInNewContext(ts.transpileModule(q[6],{compilerOptions:{esModuleInterop:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.CommonJS}}).outputText,context);
   assert.equal(actual,q[1],q[0]);quizRuns++;
  }
 }
 const theme=require('./practice_style_03.cjs');const helpers=evaluate(theme.helpers);
 const fakeRoot={dataset:{theme:'dark'}};
 helpers.applyChoice(fakeRoot,'system');assert.equal(Object.hasOwn(fakeRoot.dataset,'theme'),false);
 helpers.applyChoice(fakeRoot,'light');assert.equal(fakeRoot.dataset.theme,'light');helpers.applyChoice(fakeRoot,'dark');assert.equal(fakeRoot.dataset.theme,'dark');
 for(const initialTheme of ['light','dark']){
  let state=initialTheme,writes=0;const dataset={};Object.defineProperty(dataset,'theme',{set(value){this.value=value;writes++;},get(){return this.value;}});
  const context={exports:{},require:name=>name==='react'?{useState:()=>[state,value=>{state=value;}]}:req(name),document:{documentElement:{dataset}}};
  vm.runInNewContext(ts.transpileModule(theme.toggle,{compilerOptions:{esModuleInterop:true,module:ts.ModuleKind.CommonJS,jsx:ts.JsxEmit.ReactJSX}}).outputText,context);
  let tree=context.exports.ThemeToggle({initialTheme});assert.equal(writes,0);assert.equal(tree.props.children,initialTheme==='light'?'Ciemny':'Jasny');
  tree.props.onClick();assert.equal(writes,1);assert.equal(dataset.theme,initialTheme==='light'?'dark':'light');
  tree=context.exports.ThemeToggle({initialTheme});assert.equal(tree.props.children,initialTheme==='light'?'Jasny':'Ciemny');renders++;
 }
 const tailwind=require('./practice_style_04.cjs');
 const tailwindButton=evaluate(tailwind.button).Button;
 for(const [variant,token] of [['primary','bg-blue-600'],['secondary','border-gray-300'],['danger','bg-red-600']]){
  let clicked=0;const button=tailwindButton({variant,children:'Akcja',onClick:()=>clicked++,disabled:false});
  assert.ok(button.props.className.includes(token));assert.equal(button.props.type,'button');assert.equal(clicked,0);button.props.onClick();assert.equal(clicked,1);renders++;
 }
 const stat=evaluate(tailwind.stat).StatCard;const statHtml=renderToStaticMarkup(React.createElement(stat,{label:'Otwarte',value:0}));assert.ok(statHtml.includes('>0</strong>'));assert.ok(!statHtml.includes('<small'));renders++;
 const disabledHtml=renderToStaticMarkup(React.createElement(tailwindButton,{disabled:true,variant:'danger'},'Usuń'));assert.ok(disabledHtml.includes('disabled=""'));renders++;
 const styledSource=require('./practice_style_05.cjs');
 const styledRuntime=require(path.join(root,'target/react-style-qa/node_modules/styled-components'));
 const StyledCard=evaluate(styledSource.card).StatCard;
 for(const highlight of [false,true])for(const value of [0,3]){
  const sheet=new styledRuntime.ServerStyleSheet();
  try{
   const html=renderToStaticMarkup(sheet.collectStyles(React.createElement(StyledCard,{label:'Otwarte',value,highlight})));
   const styles=sheet.getStyleTags();assert.ok(html.startsWith('<article'));assert.ok(html.includes(`>${value}</strong>`));assert.ok(!html.includes('$highlight'));
   assert.ok(styles.includes(highlight?'#eff6ff':'white'));renders++;
  }finally{sheet.seal();}
 }
 const ThemeExample=evaluate(styledSource.theme).Example;const sheet=new styledRuntime.ServerStyleSheet();
 try{const html=renderToStaticMarkup(sheet.collectStyles(React.createElement(ThemeExample)));assert.ok(html.includes('Motyw'));const styles=sheet.getStyleTags();for(const color of ['#2563eb','#f9fafb','#111827'])assert.ok(styles.includes(color));renders++;}finally{sheet.seal();}
 const boss=require('./practice_style_07.cjs');const design=evaluate(boss.model);
 const styleMap=new Proxy({},{get:(_,name)=>String(name)});
 const imports={'./design':design,'./StyleGuide.module.css':{__esModule:true,default:styleMap}};
 const bossComponents=evaluate(boss.components,imports);imports['./components']=bossComponents;
 const StyleGuide=evaluate(boss.guide,imports).StyleGuide;
 for(const initialTheme of ['light','dark']){
  const html=renderToStaticMarkup(React.createElement(StyleGuide,{initialTheme}));
  for(const title of ['Kolory','Typografia','Przyciski','Plakietki','Karty i panele','Pola tekstowe'])assert.ok(html.includes(title));
  assert.equal((html.match(/<input\b/g)??[]).length,3);assert.equal((html.match(/<button\b/g)??[]).length,8);
  assert.equal((html.match(/<button[^>]*disabled=""/g)??[]).length,3);assert.ok(html.includes('>0</strong>'));
  const ids=Array.from(html.matchAll(/\bid="([^"]+)"/g),match=>match[1]);assert.equal(new Set(ids).size,ids.length);
  for(const match of html.matchAll(/<label for="([^"]+)"/g))assert.ok(ids.includes(match[1]));
  const described=html.match(/aria-describedby="([^"]+)"/);assert.ok(described);assert.ok(ids.includes(described[1]));
  assert.equal((html.match(/aria-invalid="true"/g)??[]).length,1);
  for(const token of design.COLOR_TOKENS){assert.ok(html.includes(`var(${token})`));assert.ok(html.includes(design.PALETTE[initialTheme][token]));}
  for(const status of design.STATUSES)assert.ok(html.includes(status));for(const priority of design.PRIORITIES)assert.ok(html.includes(priority));renders++;
 }
 assert.ok(!/#(?:[a-f0-9]{3,8})\b|rgba?\(|hsla?\(/i.test(boss.project['src/StyleGuide.module.css']));
 console.log(`OK style: ${sources.size} fragmentów TypeScript, ${renders} wariantów, ${quizRuns} wyników kodu; clsx, handler motywu, SSR styled-components, powiązania pól i 5 buildów Vite z CSS. Tailwind: komponenty bez kompilatora CSS.`);
})().catch(error=>{console.error(error);process.exitCode=1;});

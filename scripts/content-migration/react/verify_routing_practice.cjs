// Sprawdzenia treści, typów i logiki. Testy handlerów nie odtwarzają natywnego submitu/fokusu DOM.
const fs=require('node:fs'),path=require('node:path'),vm=require('node:vm'),assert=require('node:assert/strict');
const {createRequire}=require('node:module'),{pathToFileURL}=require('node:url');
const root=path.resolve(__dirname,'../../..'),ts=require(path.join(root,'target/react-typescript-qa/node_modules/typescript'));
process.env.NODE_PATH=path.join(root,'frontend/node_modules');require('node:module').Module._initPaths();
const req=createRequire(path.join(root,'frontend/package.json')),React=req('react'),{renderToStaticMarkup}=req('react-dom/server');
const entries=[['01_RouterBasics','./practice_route_01.cjs',30,100],['02_LayoutAndNestedRoutes','./practice_route_02.cjs',30,100]];
entries.push(['03_RouteParams','./practice_route_03.cjs',30,100]);
entries.push(['04_SearchParamsAsState','./practice_route_04.cjs',30,100]);
const sources=new Map();
sources.set(path.join(root,'target/react-routing-qa/env.d.ts'),'declare module "*.module.css"{const classes:{readonly [key:string]:string};export default classes;}\ndeclare module "*.css";');
for(const [slug,source,ec,qc] of entries){
 const lesson=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content/_react_12_routing',slug+'.json'),'utf8'));
 assert.equal(lesson.exercises.length,ec);assert.equal(lesson.quiz.length,qc);assert.equal(new Set(lesson.exercises.map(e=>e.prompt)).size,ec);assert.equal(new Set(lesson.quiz.map(q=>q.question+'\n'+(q.code??''))).size,qc);
 for(const e of lesson.exercises)for(const field of ['prompt','hint','solution'])assert.ok(e[field]?.trim());
 for(const q of lesson.quiz){assert.equal(new Set(Object.values(q.options)).size,4);assert.ok(q.options[q.correct]);assert.ok(q.explanation.trim());}
 for(const letter of 'ABCD')assert.equal(lesson.quiz.filter(q=>q.correct===letter).length,qc/4);
 lesson.exercises.forEach((e,i)=>{if(/^(?:import |export |type |const |\/\*\*)/.test(e.solution))sources.set(path.join(root,`target/react-routing-qa/${slug}/exercise${i+1}${e.solution.includes('/** @param')?'.jsx':'.tsx'}`),e.solution+'\nexport {};');});
 lesson.quiz.forEach((q,i)=>{if(q.code)sources.set(path.join(root,`target/react-routing-qa/${slug}/question${i+1}.tsx`),q.code+'\nexport {};');});
 for(const [file,code] of Object.entries(require(source).project??{}))if(/\.tsx?$/.test(file))sources.set(path.join(root,'target/react-routing-qa',slug,'project',file),code);
 for(const [file,code] of Object.entries(require(source).project??{}))if(/^src\/.*\.tsx?$/.test(file))sources.set(path.join(root,'target/react-routing-qa',slug,file.slice(4)),code);
}
const options={strict:true,noEmit:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.ESNext,moduleResolution:ts.ModuleResolutionKind.Bundler,jsx:ts.JsxEmit.ReactJSX,allowJs:true,checkJs:true,types:["node"],typeRoots:[path.join(root,"target/react-api-deps/node_modules/@types")],skipLibCheck:true,paths:{'react-router':[path.join(root,'frontend/node_modules/react-router/dist/development/index.d.mts')],'react-router/dom':[path.join(root,'frontend/node_modules/react-router/dist/development/dom-export.d.mts')],react:[path.join(root,'frontend/node_modules/@types/react/index.d.ts')],'react/*':[path.join(root,'frontend/node_modules/@types/react/*')],'react-dom':[path.join(root,'frontend/node_modules/@types/react-dom/index.d.ts')],'react-dom/*':[path.join(root,'frontend/node_modules/@types/react-dom/*')],'zod':[path.join(root,'target/react-form-deps/node_modules/zod/index.d.ts')],'@hookform/resolvers/zod':[path.join(root,'target/react-form-deps/node_modules/@hookform/resolvers/zod/dist/zod.d.ts')],'react-hook-form':[path.join(root,'target/react-form-deps/node_modules/react-hook-form/dist/index.d.ts')],'openapi-fetch':[path.join(root,'target/react-openapi-deps/node_modules/openapi-fetch/dist/index.d.mts')],'@stomp/stompjs':[path.join(root,'target/react-api-deps/node_modules/@stomp/stompjs/esm6/index.d.ts')],'@tanstack/react-query':[path.join(root,'target/react-api-deps/node_modules/@tanstack/react-query/build/modern/index.d.ts')],'msw/http':[path.join(root,'target/react-api-deps/node_modules/msw/lib/http/index.d.ts')],'msw/browser':[path.join(root,'target/react-api-deps/node_modules/msw/lib/browser/index.d.ts')],'msw/utils/delay':[path.join(root,'target/react-api-deps/node_modules/msw/lib/utils/delay.d.ts')],'@radix-ui/react-slot':[path.join(root,'target/react-style-qa/node_modules/@radix-ui/react-slot/dist/index.d.ts')],'@radix-ui/react-dialog':[path.join(root,'target/react-style-qa/node_modules/@radix-ui/react-dialog/dist/index.d.ts')]}};
const host=ts.createCompilerHost(options),read=host.readFile.bind(host),exists=host.fileExists.bind(host),directory=host.directoryExists.bind(host);
host.readFile=file=>sources.get(path.resolve(file))??read(file);host.fileExists=file=>sources.has(path.resolve(file))||exists(file);host.directoryExists=dir=>[...sources.keys()].some(file=>file.startsWith(path.resolve(dir)+path.sep))||directory(dir);
const diagnostics=ts.getPreEmitDiagnostics(ts.createProgram([...sources.keys()],options,host));assert.equal(diagnostics.length,0,ts.formatDiagnosticsWithColorAndContext(diagnostics,{getCurrentDirectory:()=>root,getCanonicalFileName:f=>f,getNewLine:()=> '\n'}));
const apiReq=createRequire(path.join(root,"target/react-api-deps/package.json"));
const openapiReq=createRequire(path.join(root,"target/react-openapi-deps/package.json"));
function practiceRequire(name){if(name==="openapi-fetch")return openapiReq(name);if(name.startsWith("msw/")||name.startsWith("@tanstack/")||name==="@stomp/stompjs")return apiReq(name);if(name==='@radix-ui/react-slot')return require(path.join(root,'target/react-style-qa/node_modules',name));if(name==='zod'||name==='react-hook-form'||name.startsWith('@hookform/resolvers'))return require(path.join(root,'target/react-form-deps/node_modules',name));return req(name);}
function evaluate(code,globals={}){const context={exports:{},require:practiceRequire,Error,TypeError,RangeError,URL,URLSearchParams,Headers,Request,Response,AbortController,DOMException,FormData,File,console:{log(){}},...globals};const compiled=ts.transpileModule(code,{compilerOptions:{esModuleInterop:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.CommonJS,jsx:ts.JsxEmit.ReactJSX}}).outputText;Function(...Object.keys(context),compiled)(...Object.values(context));return context.exports;}
let checks=0,quizRuns=0;
for(const [,source]of entries)for(const q of require(source).quiz){if(!q[6])continue;let actual;evaluate(q[6],{console:{log:value=>actual=String(value)}});assert.equal(actual,q[1],q[0]);quizRuns++;}
function loadProject(source){const cache=new Map();function load(name){const key='src/'+name.replace(/^\.\//,'');if(cache.has(key))return cache.get(key);const file=[key,key+'.ts',key+'.tsx'].find(file=>source.project[file]);if(!file)return practiceRequire(name);const result=evaluate(source.project[file],{require:target=>target.startsWith('./')?load(target):practiceRequire(target)});cache.set(key,result);return result;}return load;}
(async()=>{
const {createMemoryRouter,RouterProvider,matchRoutes}=req('react-router');
for(const [,sourcePath]of entries){const source=require(sourcePath),load=loadProject(source),routes=load('./routes').routes;
await load('./historyCheck').verifyHistory();checks+=3;
for(const [url,id] of [['/','dashboard'],['/tickets','tickets'],['/archive','archive'],['/settings',(source.model||source.ids||source.filters)?'profile':'settings'],['/missing','not-found']]){assert.equal(matchRoutes(routes,url).at(-1).route.id,id);checks++;}
const router=createMemoryRouter(routes,{initialEntries:['/archive']});try{const html=renderToStaticMarkup(React.createElement(load('./providers').AppProviders,null,React.createElement(RouterProvider,{router})));assert.match(html,/aria-current="page"[^>]*[^]*?Archiwum/);assert.match(html,/href="\/tickets"/);assert.equal((html.match(/<main/g)||[]).length,1);checks+=3;}finally{router.dispose();}
}
// Parametry i logika asynchroniczna: setup/cleanup uruchamiane jawnie, bez udawania DOM.
{
 const source=require('./practice_route_03.cjs'),load=loadProject(source),ids=load('./ticketIds');
 const good=[['1',1],['42',42],['9007199254740991',Number.MAX_SAFE_INTEGER]];
 for(const [raw,id]of good){assert.equal(ids.parseTicketId(raw),id);checks++;}
 for(const raw of [undefined,'','0','01','1e3','42abc',' 42 ','1.5','-1','9007199254740992']){assert.equal(ids.parseTicketId(raw),null);checks++;}
 assert.throws(()=>ids.ticketPath(0),RangeError);assert.throws(()=>ids.ticketPath(NaN),RangeError);checks+=2;
 const {routes}=load('./routes');for(const [url,id,params]of [['/tickets/42/activity','activity',{ticketId:'42'}],['/tickets/7/comments/9','comment',{ticketId:'7',commentId:'9'}],['/files/a/b','files',{'*':'a/b'}],['/help','help',{}],['/tickets/new','new',{}]]){const match=matchRoutes(routes,url).at(-1);assert.equal(match.route.id,id);for(const [key,value]of Object.entries(params))assert.equal(match.params[key],value);checks++;}
 const queue=[],slots=[],effects=[];let cursor=0;const fakeReact={useState(initial){const index=cursor++;if(!(index in slots))slots[index]=initial;return [slots[index],value=>slots[index]=value];},useEffect(setup,deps){effects.push({setup,deps});}};
 const TicketFailure=load('./ticketApi').TicketFailure;
 const hook=evaluate(source.query,{require:name=>name==='react'?fakeReact:{TicketFailure,fetchTicket:(id,signal)=>new Promise((resolve,reject)=>queue.push({id,signal,resolve,reject}))}}).useTicket;
 const render=id=>{cursor=0;effects.length=0;return hook(id);};
 assert.equal(render(null).status,'loading');assert.equal(effects[0].setup(),undefined);assert.equal(queue.length,0);checks++;
 render(7);const cleanup7=effects[0].setup();assert.equal(queue[0].id,7);cleanup7();assert.equal(queue[0].signal.aborted,true);checks+=2;
 render(42);const cleanup42=effects[0].setup();queue[1].resolve({id:42,title:'Nowy',description:''});await Promise.resolve();assert.equal(render(42).status,'ready');checks++;
 queue[0].resolve({id:7,title:'Stary',description:''});await Promise.resolve();assert.equal(render(42).ticket.id,42);checks++;
 assert.equal(render(481).status,'loading');cleanup42();const cleanup481=effects[0].setup();queue[2].reject(new TicketFailure(404));await Promise.resolve();await Promise.resolve();assert.equal(render(481).status,'missing');cleanup481();checks+=2;
 render(9);const cleanup9=effects[0].setup();queue[3].reject(new Error('sieć'));await Promise.resolve();await Promise.resolve();assert.equal(render(9).status,'error');cleanup9();checks++;
}
// Funkcje stanu listy: semantyka URL, historia i rzeczywista selekcja danych.
{
 const source=require('./practice_route_04.cjs'),load=loadProject(source),f=load('./filters'),data=load('./listData');
 assert.equal(f.readFilters(new URLSearchParams('page=01&status=WRONG&sort=evil')).page,1);assert.equal(f.readFilters(new URLSearchParams('page=01&status=WRONG&sort=evil')).status,null);checks+=2;
 const old=new URLSearchParams('q=api&page=4&utm=kurs&tag=ui&tag=api'),next=f.patchFilters(old,{status:'OPEN'});assert.equal(old.get('page'),'4');assert.equal(next.has('page'),false);assert.equal(next.get('q'),'api');assert.equal(next.get('utm'),'kurs');assert.deepEqual(next.getAll('tag'),['ui','api']);checks+=5;
 assert.throws(()=>f.patchFilters(old,{page:0}),RangeError);assert.throws(()=>f.patchFilters(old,{page:1.5}),RangeError);checks+=2;
 assert.equal(f.patchFilters(old,{page:3},false).get('page'),'3');assert.equal(f.clearFilters(old).toString(),'utm=kurs&tag=ui&tag=api');checks+=2;
 assert.deepEqual(f.listKey(f.readFilters(new URLSearchParams('status=OPEN&page=2'))),f.listKey(f.readFilters(new URLSearchParams('page=2&status=OPEN'))));assert.equal(f.apiParams(f.readFilters(old)).has('utm'),false);checks+=2;
 const before=JSON.stringify(data.rows),page=data.selectPage(data.rows,{status:'OPEN',q:'',sort:'priority:desc',page:1});assert.deepEqual(page.items.map(row=>row.id),[483,7]);assert.equal(page.total,3);assert.equal(page.pageCount,2);assert.equal(JSON.stringify(data.rows),before);checks+=4;
 assert.deepEqual(data.selectPage(data.rows,{status:'OPEN',q:'',sort:'priority:desc',page:2}).items.map(row=>row.id),[482]);assert.equal(data.selectPage(data.rows,{status:'OPEN',q:'API',sort:'createdAt:desc',page:1}).total,1);checks+=2;
 const router=createMemoryRouter(load('./routes').routes,{initialEntries:['/tickets?status=OPEN&page=2']});try{await router.navigate('/tickets?status=OPEN&page=3');await router.navigate(-1);assert.equal(router.state.location.search,'?status=OPEN&page=2');const html=renderToStaticMarkup(React.createElement(load('./providers').AppProviders,null,React.createElement(RouterProvider,{router})));assert.match(html,/API wyszukiwania/);assert.match(html,/Strona 2/);checks+=3;}finally{router.dispose();}
}
const {build}=await import(pathToFileURL(path.join(root,'frontend/node_modules/vite/dist/node/index.js')));const {default:reactPlugin}=await import(pathToFileURL(path.join(root,'frontend/node_modules/@vitejs/plugin-react/dist/index.js')));let builds=0;
for(const [,source]of entries){const fixture=fs.mkdtempSync(path.join(root,'target/react-routing-build-'));for(const [file,code]of Object.entries(require(source).project)){fs.mkdirSync(path.dirname(path.join(fixture,file)),{recursive:true});fs.writeFileSync(path.join(fixture,file),code);}fs.writeFileSync(path.join(fixture,'index.html'),'<!doctype html><html lang="pl"><head><title>Routing</title></head><body><div id="root"></div><script type="module" src="/src/main.tsx"></script></body></html>');const base=path.join(root,'frontend/node_modules');await build({root:fixture,configFile:false,logLevel:'error',plugins:[reactPlugin()],resolve:{alias:[{find:'react-router/dom',replacement:base+'/react-router/dist/development/dom-export.mjs'},{find:/^react-router$/,replacement:base+'/react-router/dist/development/index.mjs'},{find:/^react$/,replacement:base+'/react/index.js'},{find:/^react\/(.*)$/,replacement:base+'/react/$1'},{find:/^react-dom$/,replacement:base+'/react-dom/index.js'},{find:/^react-dom\/(.*)$/,replacement:base+'/react-dom/$1'}]}});assert.ok(fs.existsSync(path.join(fixture,'dist/index.html')));builds++;}
console.log(`OK routing: ${sources.size} fragmentów TypeScript, ${quizRuns} wyników quizu, ${checks} kontroli historii/dopasowania/HTML, ${builds} buildów Vite. Natywne kliknięcia, fokus i fallback wymagają osobnego testu przeglądarki i serwera.`);
})().catch(error=>{console.error(error);process.exitCode=1;});

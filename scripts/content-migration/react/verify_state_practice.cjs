// Kontrola treści i jednostkowa kontrola handlerów. Nie zastępuje dispatch zdarzeń w przeglądarce.
const fs=require('node:fs'),path=require('node:path'),vm=require('node:vm'),assert=require('node:assert/strict');
const {createRequire}=require('node:module');
const root=path.resolve(__dirname,'../../..'),ts=require(path.join(root,'target/react-typescript-qa/node_modules/typescript'));
const req=createRequire(path.join(root,'frontend/package.json')),React=req('react'),{renderToStaticMarkup}=req('react-dom/server');
const qaRequire=createRequire(path.join(root,'target/react-state-qa/package.json'));
process.env.NODE_PATH=path.join(root,'frontend/node_modules');require('node:module').Module._initPaths();
const entries=[['01_EventsInReact','./practice_state_01.cjs',30,100]];
entries.push(['02_UseStateBasics','./practice_state_02.cjs',30,100]);
entries.push(['03_StateAsSnapshotAndBatching','./practice_state_03.cjs',30,100]);
entries.push(['04_UpdatingObjectsAndArrays','./practice_state_04.cjs',30,100]);
entries.push(['05_ControlledSearchInput','./practice_state_05.cjs',30,100]);
entries.push(['06_DerivedDataFiltering','./practice_state_06.cjs',30,100]);
entries.push(['07_SortableColumns','./practice_state_07.cjs',30,100]);
entries.push(['08_LocalPagination','./practice_state_08.cjs',30,100]);
entries.push(['09_DrillEventsAndUseState','./practice_state_09.cjs',30,100]);
entries.push(['10_DrillImmutableUpdatesAndDerivedData','./practice_state_10.cjs',30,100]);
entries.push(['11_BossProductCatalog','./practice_state_11.cjs',30,100]);
const sources=new Map();
sources.set(path.join(root,'target/react-state-qa/env.d.ts'),'declare module "*.module.css" {const classes:{readonly [key:string]:string};export default classes;}\ndeclare module "*.css";');
for(const [slug,source,ec,qc] of entries){
 const lesson=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content/_react_04_state_i_tabela',slug+'.json'),'utf8'));
 assert.equal(lesson.exercises.length,ec);assert.equal(lesson.quiz.length,qc);
 assert.equal(new Set(lesson.exercises.map(e=>e.prompt)).size,ec);assert.equal(new Set(lesson.quiz.map(q=>q.question+'\n'+(q.code??''))).size,qc);
 for(const e of lesson.exercises)for(const field of ['prompt','hint','solution'])assert.ok(e[field]?.trim());
 for(const q of lesson.quiz){assert.equal(new Set(Object.values(q.options)).size,4);assert.ok(q.options[q.correct]);assert.ok(q.explanation.trim());}
 for(const letter of 'ABCD')assert.equal(lesson.quiz.filter(q=>q.correct===letter).length,qc/4);
 lesson.exercises.forEach((e,i)=>{if(/^(?:export |import |type |const |\/\*\*)/.test(e.solution))sources.set(path.join(root,`target/react-state-qa/${slug}/exercise${i+1}${e.solution.startsWith('/**')?'.jsx':'.tsx'}`),e.solution+'\nexport {};');});
 lesson.quiz.forEach((q,i)=>{if(q.code)sources.set(path.join(root,`target/react-state-qa/${slug}/question${i+1}.tsx`),q.code+'\nexport {};');});
 for(const [file,code] of Object.entries(require(source).project??{}))if(/\.tsx?$/.test(file))sources.set(path.join(root,'target/react-state-qa',slug,'project',file),code);
}
const options={strict:true,noEmit:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.ESNext,moduleResolution:ts.ModuleResolutionKind.Bundler,jsx:ts.JsxEmit.ReactJSX,allowJs:true,checkJs:true,types:[],skipLibCheck:true,paths:{"react-dom/*":[path.join(root,"frontend/node_modules/@types/react-dom/*")],immer:[path.join(root,"target/react-state-qa/node_modules/immer/dist/immer.d.ts")],"use-immer":[path.join(root,"target/react-state-qa/node_modules/use-immer/dist/index.d.ts")],react:[path.join(root,'frontend/node_modules/@types/react/index.d.ts')],'react/*':[path.join(root,'frontend/node_modules/@types/react/*')]}};
const host=ts.createCompilerHost(options),read=host.readFile.bind(host),exists=host.fileExists.bind(host),directory=host.directoryExists.bind(host);
host.readFile=file=>sources.get(path.resolve(file))??read(file);host.fileExists=file=>sources.has(path.resolve(file))||exists(file);host.directoryExists=dir=>[...sources.keys()].some(file=>file.startsWith(path.resolve(dir)+path.sep))||directory(dir);
const diagnostics=ts.getPreEmitDiagnostics(ts.createProgram([...sources.keys()],options,host));assert.equal(diagnostics.length,0,ts.formatDiagnosticsWithColorAndContext(diagnostics,{getCurrentDirectory:()=>root,getCanonicalFileName:f=>f,getNewLine:()=> '\n'}));
function evaluate(code,globals={}){const context={exports:{},require:name=>["immer","use-immer"].includes(name)?qaRequire(name):req(name),console:{log(){}},...globals};vm.runInNewContext(ts.transpileModule(code,{compilerOptions:{esModuleInterop:true,target:ts.ScriptTarget.ES2023,module:ts.ModuleKind.CommonJS,jsx:ts.JsxEmit.ReactJSX}}).outputText,context);return context.exports;}
function logicInstance(code,name,initialProps={},imports={}){const slots=[];let cursor=0;const react={useState(initial){const index=cursor++;if(!(index in slots))slots[index]=typeof initial==='function'?initial():initial;return [slots[index],update=>{slots[index]=typeof update==='function'?update(slots[index]):update;}];},useId(){return `test-id-${cursor++}`;}};const Component=evaluate(code,{require:name=>name==='react'?react:imports[name]??req(name)})[name];return {slots,render(props=initialProps){cursor=0;return Component(props);}};}
const events=require('./practice_state_01.cjs');let checks=0;
const TicketRow=evaluate(events.row).TicketRow;
const calls=[],ticket=Object.freeze({id:42,title:'API',status:'OPEN'});
const element=TicketRow({ticket,onSelect:id=>calls.push(['select',id]),onClose:id=>calls.push(['close',id])});assert.equal(calls.length,0);
let stopped=0;const event={stopPropagation:()=>stopped++};
element.props.children[1].props.children.props.onClick(event);assert.deepEqual(calls,[['select',42]]);assert.equal(stopped,1);
element.props.children[2].props.children.props.onClick(event);assert.deepEqual(calls,[['select',42],['close',42]]);assert.equal(stopped,2);
element.props.onClick();assert.deepEqual(calls[2],['select',42]);checks+=3;
const closed=TicketRow({ticket:{...ticket,status:'CLOSED'},onSelect(){},onClose(){}});assert.equal(closed.props.children[2].props.children.props.disabled,true);checks++;
const Table=evaluate(events.table).TicketTable;const empty=renderToStaticMarkup(React.createElement(Table,{tickets:[],onSelect(){},onClose(){}}));assert.ok(empty.includes('colSpan="3"'));assert.ok(empty.includes('Brak ticketów'));checks++;
const Search=evaluate(events.search).Search;let query,cleared=0;
const input=Search({onQuery:value=>query=value,onClear:()=>cleared++}).props.children[1];
input.props.onChange({currentTarget:{value:'react'}});assert.equal(query,'react');
for(const [key,isComposing] of [['Escape',false],['Enter',false],['Escape',true]])input.props.onKeyDown({key,nativeEvent:{isComposing}});assert.equal(cleared,1);checks+=4;
const created=[];let prevented=0;
class TestFormData{constructor(form){this.form=form;}get(key){return this.form[key]??null;}}
const CreateForm=evaluate(events.form,{FormData:TestFormData}).CreateForm;
for(const title of ['  API  ','   ','',null])CreateForm({onCreate:value=>created.push(value)}).props.onSubmit({preventDefault:()=>prevented++,currentTarget:{title}});
assert.deepEqual(created,['API']);assert.equal(prevented,4);checks+=4;
(async()=>{
 for(const failure of [false,true]){let copied=0,error='',written='';
  const CopyButton=evaluate(events.copy,{navigator:{clipboard:{writeText:async text=>{written=text;if(failure)throw Error('Odmowa');}}}}).CopyButton;
  const button=CopyButton({url:'https://example.test/tickets/42',onCopied:()=>copied++,onError:message=>error=message});assert.equal(written,'');await button.props.onClick();assert.equal(written,'https://example.test/tickets/42');assert.equal(copied,failure?0:1);assert.equal(Boolean(error),failure);checks++;
 }
 let quizRuns=0;for(const [,source] of entries)for(const q of require(source).quiz){if(!q[6])continue;let actual;evaluate(q[6],{console:{log:value=>actual=value===''?'Pusty napis':String(value)}});assert.equal(actual,q[1],q[0]);quizRuns++;}
 const basics=require('./practice_state_02.cjs');const TicketDescription=evaluate(basics.description).TicketDescription;
 for(const length of [0,120,121]){const html=renderToStaticMarkup(React.createElement(TicketDescription,{text:'a'.repeat(length)}));assert.equal(html.includes('Pokaż całość'),length>120);assert.equal(html.includes('…'),length>120);checks++;}
 let state=null;const mockReact={useState:()=>[state,next=>{state=typeof next==='function'?next(state):next;}]};
 const main=evaluate(basics.dashboard,{require:name=>name==='react'?mockReact:req(name)}).Main;
 const data=[{id:0,title:'Zero',description:'Krótki'},{id:42,title:'API',description:'Opis'}];
 let tree=main({tickets:data});assert.equal(tree.props.children[1].props.ticket,null);
 tree.props.children[0].props.onSelect(0);tree=main({tickets:data});assert.equal(tree.props.children[0].props.selectedId,0);assert.equal(tree.props.children[1].props.ticket.title,'Zero');
 tree.props.children[0].props.onSelect(42);tree=main({tickets:[{...data[1],title:'Nowe API'}]});assert.equal(tree.props.children[1].props.ticket.title,'Nowe API');
 tree=main({tickets:[]});assert.equal(tree.props.children[1].props.ticket,null);assert.equal(state,42);
 tree.props.children[1].props.onClear();assert.equal(state,null);checks+=5;
 const snapshots=require('./practice_state_03.cjs');const applyQueue=evaluate(snapshots.queue).applyQueue;
 for(const [source,name,result] of [[snapshots.direct,'DirectCounter',1],[snapshots.functional,'CommentCounter',3]]){
  const updates=[],logs=[];const react={useState:()=>[0,update=>updates.push(update)]};
  const Component=evaluate(source,{require:name=>name==='react'?react:req(name),console:{log:value=>logs.push(value)}})[name];
  const tree=Component();assert.equal(updates.length,0);tree.props.onClick();assert.equal(updates.length,3);assert.equal(applyQueue(0,updates),result);if(name==='DirectCounter')assert.deepEqual(logs,[0]);checks++;
 }
 const timers=[],reports=[];let snapshot=2;
 const reportReact={useState:()=>[snapshot,update=>{snapshot=typeof update==='function'?update(snapshot):update;}]};
 const DelayedReport=evaluate(snapshots.delayed,{require:name=>name==='react'?reportReact:req(name),setTimeout:callback=>timers.push(callback)}).DelayedReport;
 const reportTree=DelayedReport({onReport:value=>reports.push(value)});reportTree.props.children[1].props.onClick();reportTree.props.children[0].props.onClick();assert.equal(snapshot,3);timers[0]();assert.deepEqual(reports,[2]);checks++;
 const immutableSource=require('./practice_state_04.cjs');const immutable=evaluate(immutableSource.model+'\n'+immutableSource.helpers);
 const frozen=Object.freeze([Object.freeze({id:1,title:'API',status:'OPEN',assignee:null,tags:Object.freeze(['api']),comments:Object.freeze([Object.freeze({id:10,text:'A',likes:1}),Object.freeze({id:11,text:'B',likes:0})]),createdAt:'start'}),Object.freeze({id:2,title:'Eksport',status:'OPEN',assignee:null,tags:Object.freeze([]),comments:Object.freeze([]),createdAt:'start'})]);
 const before=JSON.stringify(frozen);const closedTickets=immutable.closeTicket(frozen,1);
 assert.notEqual(closedTickets,frozen);assert.notEqual(closedTickets[0],frozen[0]);assert.equal(closedTickets[1],frozen[1]);assert.equal(closedTickets[0].status,'CLOSED');assert.equal(frozen[0].status,'OPEN');checks++;
 assert.deepEqual(Array.from(immutable.deleteTicket(frozen,1),t=>t.id),[2]);assert.equal(immutable.assignTicket(frozen,1,'anna')[0].assignee,'anna');checks+=2;
 const tagged=immutable.addTag(frozen,1,'pilne');assert.notEqual(tagged[0].tags,frozen[0].tags);assert.deepEqual(Array.from(tagged[0].tags),['api','pilne']);assert.equal(immutable.addTag(frozen,1,'api')[0],frozen[0]);checks++;
 const liked=immutable.likeComment(frozen[0],10);assert.notEqual(liked.comments,frozen[0].comments);assert.notEqual(liked.comments[0],frozen[0].comments[0]);assert.equal(liked.comments[1],frozen[0].comments[1]);assert.equal(liked.comments[0].likes,2);checks++;
 assert.equal(immutable.nextTicketId([]),1);assert.throws(()=>immutable.nextTicketId([{id:Number.MAX_SAFE_INTEGER}]),error=>error.name==='RangeError');checks+=2;
 const twice=immutable.prependQuickTicket(immutable.prependQuickTicket(frozen,'a'),'b');assert.deepEqual(Array.from(twice,t=>t.id),[4,3,1,2]);assert.equal(twice[2],frozen[0]);assert.equal(twice[0].createdAt,'b');assert.equal(JSON.stringify(frozen),before);checks++;
 const controlledSource=require('./practice_state_05.cjs');const RealSearch=evaluate(controlledSource.search).SearchInput;
 const searchHtml=renderToStaticMarkup(React.createElement('div',null,React.createElement(RealSearch,{value:'api',onChange(){}}),React.createElement(RealSearch,{value:'',onChange(){}})));
 const searchIds=Array.from(searchHtml.matchAll(/<input[^>]*id="([^"]+)"/g),match=>match[1]);assert.equal(searchIds.length,2);assert.equal(new Set(searchIds).size,2);for(const id of searchIds)assert.ok(searchHtml.includes(`for="${id}"`));assert.equal((searchHtml.match(/aria-label="Wyczyść wyszukiwanie"/g)??[]).length,1);checks++;
 const changed=[];const searchMock={useId:()=>'search-id'};
 const ControlledSearch=evaluate(controlledSource.search,{require:name=>name==='react'?searchMock:req(name)}).SearchInput;
 const controlledTree=ControlledSearch({value:'api',onChange:value=>changed.push(value)});controlledTree.props.children[1].props.onChange({currentTarget:{value:'react'}});controlledTree.props.children[2].props.onClick();
 let escapeDefault=0;controlledTree.props.children[1].props.onKeyDown({key:'Escape',nativeEvent:{isComposing:false},preventDefault:()=>escapeDefault++});controlledTree.props.children[1].props.onKeyDown({key:'Escape',nativeEvent:{isComposing:true},preventDefault:()=>escapeDefault++});
 assert.deepEqual(changed,['react','','']);assert.equal(escapeDefault,1);checks+=3;
 let tagState='';const added=[];const tagMock={useId:()=>'tag-id',useState:()=>[tagState,next=>tagState=next]};
 const TagInput=evaluate(controlledSource.tag,{require:name=>name==='react'?tagMock:req(name)}).TagInput;
 let tagTree=TagInput({onAdd:value=>added.push(value)});assert.equal(tagTree.props.children[3].props.disabled,true);tagTree.props.children[1].props.onChange({currentTarget:{value:'API UI'}});assert.equal(tagState,'api-ui');
 tagTree=TagInput({onAdd:value=>added.push(value)});tagTree.props.onSubmit({preventDefault(){}});assert.deepEqual(added,['api-ui']);assert.equal(tagState,'');TagInput({onAdd:value=>added.push(value)}).props.onSubmit({preventDefault(){}});assert.equal(added.length,1);checks+=3;
 const filteringSource=require('./practice_state_06.cjs');const filtering=evaluate(filteringSource.model+'\n'+filteringSource.helpers+'\n'+filteringSource.sample+'\n'+filteringSource.select);
 const filterData=Object.freeze(Array.from(filtering.tickets,ticket=>Object.freeze({...ticket,tags:Object.freeze(Array.from(ticket.tags))})));const filterBefore=JSON.stringify(filterData);
 const api=filtering.filterTickets(filterData,' API ','ALL');assert.deepEqual(Array.from(api,t=>t.id),[1,3]);assert.equal(api[0],filterData[0]);assert.deepEqual(Object.values(filtering.summarize(filterData)),[5,3,2]);checks+=2;
 const closedData=filterData.map(ticket=>ticket.id===1?{...ticket,status:'CLOSED'}:ticket);assert.equal(filtering.filterTickets(closedData,'api','OPEN').length,0);assert.deepEqual(Array.from(filtering.filterTickets(closedData,'api','CLOSED'),t=>t.id),[1,3]);assert.deepEqual(Object.values(filtering.summarize(closedData)),[5,2,1]);checks++;
 assert.equal(JSON.stringify(filterData),filterBefore);const statusChanges=[];const statusControl=filtering.StatusSelect({value:'ALL',onChange:value=>statusChanges.push(value)}).props.children[1];statusControl.props.onChange({currentTarget:{value:'DONE'}});statusControl.props.onChange({currentTarget:{value:'OPEN'}});assert.deepEqual(statusChanges,['OPEN']);checks++;
 const FilterMain=evaluate(filteringSource.main).Main;const filterHtml=renderToStaticMarkup(React.createElement(FilterMain,{initialTickets:filterData}));assert.ok(filterHtml.includes('Znaleziono: 5 z 5'));assert.ok(filterHtml.includes('Otwarte: 3; pilne niezakończone: 2'));checks++;
 const sortingSource=require('./practice_state_07.cjs');const sorting=evaluate(sortingSource.model+'\n'+sortingSource.helpers+'\n'+sortingSource.header+'\n'+sortingSource.sample);
 const sortData=Object.freeze(Array.from(sorting.tickets,ticket=>Object.freeze(ticket)));const sortBefore=JSON.stringify(sortData);
 for(const [key,ascending] of [['id',[1,2,3,4]],['title',[1,2,3,4]],['priority',[4,2,3,1]],['createdAt',[3,4,1,2]]]){
  assert.deepEqual(Array.from(sorting.sortTickets(sortData,{key,direction:'asc'}),ticket=>ticket.id),ascending);assert.deepEqual(Array.from(sorting.sortTickets(sortData,{key,direction:'desc'}),ticket=>ticket.id),ascending.toReversed());checks+=2;
 }
 assert.equal(JSON.stringify(sortData),sortBefore);const tied=[{...sortData[0],id:8,priority:'HIGH'},{...sortData[0],id:7,priority:'HIGH'}];assert.deepEqual(Array.from(sorting.sortTickets(tied,{key:'priority',direction:'desc'}),ticket=>ticket.id),[8,7]);checks++;
 const currentSort=Object.freeze({key:'createdAt',direction:'desc'});assert.deepEqual(Object.values(sorting.nextSort(currentSort,'title')),['title','asc']);assert.deepEqual(Object.values(sorting.nextSort(currentSort,'createdAt')),['createdAt','asc']);checks+=2;
 const sortHeaders=renderToStaticMarkup(React.createElement('table',null,React.createElement('thead',null,React.createElement('tr',null,...['id','title','priority','createdAt'].map(column=>React.createElement(sorting.SortHeader,{key:column,label:column,column,sort:currentSort,onSort(){}}))))));
 assert.equal((sortHeaders.match(/aria-sort=/g)??[]).length,1);assert.ok(sortHeaders.includes('aria-sort="descending"'));let selectedSort;sorting.SortHeader({label:'Tytuł',column:'title',sort:currentSort,onSort:key=>selectedSort=key}).props.children.props.onClick();assert.equal(selectedSort,'title');checks+=2;
 const paginationSource=require('./practice_state_08.cjs');const pagination=evaluate(paginationSource.paginate+'\n'+paginationSource.pager+'\n'+paginationSource.size+'\n'+paginationSource.range);
 const records=Object.freeze(Array.from({length:120},(_,index)=>Object.freeze({id:index+1})));
 for(const [pageSize,pageCount] of [[10,12],[25,5],[50,3]]){
  const combined=[];
  for(let page=1;page<=pageCount;page++){const result=pagination.paginate(records,page,pageSize);assert.equal(result.pageCount,pageCount);assert.equal(result.currentPage,page);assert.equal(result.from,result.pageItems[0].id);assert.equal(result.to,result.pageItems.at(-1).id);combined.push(...result.pageItems);}
  assert.deepEqual(combined,Array.from(records));const last=pagination.paginate(records,999,pageSize);assert.equal(last.currentPage,pageCount);assert.equal(last.to,120);checks++;
 }
 const emptyPage=pagination.paginate([],99,10);assert.deepEqual([emptyPage.currentPage,emptyPage.pageCount,emptyPage.from,emptyPage.to],[1,1,0,0]);checks++;
 for(const [page,pageSize] of [[1,0],[1,-1],[1,NaN],[1,Infinity],[1,2.5],[NaN,10],[Infinity,10],[1.5,10]]){assert.throws(()=>pagination.paginate([],page,pageSize),error=>error.name==='RangeError');checks++;}
 const pages=[];const firstPager=pagination.Pager({page:1,pageCount:3,onPageChange:page=>pages.push(page)});firstPager.props.children[0].props.onClick();firstPager.props.children[2].props.onClick();firstPager.props.children[1][2].props.onClick();assert.deepEqual(pages,[2,3]);checks++;
 const pagerHtml=renderToStaticMarkup(React.createElement(pagination.Pager,{page:3,pageCount:3,onPageChange(){}}));assert.equal((pagerHtml.match(/aria-current="page"/g)??[]).length,1);assert.ok(pagerHtml.includes('aria-label="Strona 3"'));assert.equal((pagerHtml.match(/disabled=""/g)??[]).length,1);checks++;
 const sizes=[];const sizeControl=pagination.PageSizeSelect({value:10,onChange:size=>sizes.push(size)}).props.children[1];for(const value of ['25','abc','0','50'])sizeControl.props.onChange({currentTarget:{value}});assert.deepEqual(sizes,[25,50]);checks++;
 assert.ok(renderToStaticMarkup(React.createElement(pagination.RangeInfo,{from:0,to:0,total:0})).includes('Brak wyników'));checks++;
 const drill=require('./practice_state_09.cjs');const bounded=logicInstance(drill.bounded,'BoundedCounter',{step:3});const observed=[0];for(let i=0;i<4;i++){bounded.render().props.children[2].props.onClick();observed.push(bounded.render().props.children[1].props.children);}assert.deepEqual(observed,[0,3,6,9,10]);assert.equal(bounded.render().props.children[2].props.disabled,true);bounded.render().props.children[3].props.onClick();assert.equal(bounded.slots[0],0);checks++;
 const like=logicInstance(drill.like,'LikeButton',{initialLikes:12});like.render().props.onClick();assert.equal(like.render().props.children[2],13);like.render().props.onClick();assert.equal(like.render().props.children[2],12);checks++;
 const score=logicInstance(drill.score,'ScoreBoard');score.render().props.children[2].props.children[1].props.onClick();assert.deepEqual(score.slots,[4,0]);score.render().props.children[3].props.children[1].props.onClick();assert.deepEqual(score.slots,[4,4]);assert.equal(score.render().props.children[0].props.children[1],'Remis');checks++;
 const colors=[{id:'teal',name:'Morski',hex:'#0e7490'}];const picker=logicInstance(drill.color,'ColorPicker',{colors});picker.render().props.children[0].props.children[0].props.onClick();assert.equal(picker.slots[0],'teal');assert.ok(picker.render({colors:[{...colors[0],name:'Nowa nazwa'}]}).props.children[1].props.children.includes('Nowa nazwa'));picker.render().props.children[0].props.children[0].props.onClick();assert.equal(picker.slots[0],null);checks++;
 const cartOps=evaluate(drill.cartModel);const cartStart=Object.freeze({});const c1=cartOps.addUnit(cartStart,7),c2=cartOps.addUnit(c1,7),c3=cartOps.removeUnit(c2,7),c4=cartOps.removeUnit(c3,7);assert.deepEqual([cartOps.cartCount(cartStart),cartOps.cartCount(c1),cartOps.cartCount(c2),cartOps.cartCount(c3),cartOps.cartCount(c4)],[0,1,2,1,0]);assert.equal(Object.hasOwn(c4,7),false);assert.equal(cartOps.removeUnit(c4,7),c4);assert.equal(cartOps.cartTotal(c2,[{id:7,name:'A',priceGrosze:1299}]),2598);checks++;
 const deepDrill=require('./practice_state_10.cjs');const todoOps=evaluate(deepDrill.model+'\n'+deepDrill.ops+'\n'+deepDrill.selection+'\n'+deepDrill.history);
 const todos=Object.freeze([Object.freeze({id:'a',title:'A',done:false}),Object.freeze({id:'b',title:'B',done:false}),Object.freeze({id:'c',title:'C',done:true})]);const todoBefore=JSON.stringify(todos);
 assert.deepEqual(Array.from(todoOps.moveTodo(todos,'b',-1),todo=>todo.id),['b','a','c']);assert.deepEqual(Array.from(todoOps.moveTodo(todos,'b',1),todo=>todo.id),['a','c','b']);assert.equal(todoOps.moveTodo(todos,'a',-1),todos);assert.equal(todoOps.moveTodo(todos,'c',1),todos);checks+=2;
 const toggled=todoOps.toggleTodo(todos,'a');assert.notEqual(toggled[0],todos[0]);assert.equal(toggled[1],todos[1]);assert.equal(toggled[0].done,true);assert.equal(JSON.stringify(todos),todoBefore);assert.throws(()=>todoOps.addTodo(todos,todos[0]));checks+=2;
 const oldSet=new Set(['a']);const newSet=todoOps.toggleSelection(oldSet,'b');assert.deepEqual(Array.from(oldSet),['a']);assert.deepEqual(Array.from(newSet),['a','b']);assert.equal(todoOps.allSelected([],newSet),false);assert.equal(todoOps.selectedTodos(todos,new Set(['a','missing'])).length,1);checks+=2;
 const profileOps=evaluate(deepDrill.profile);const oldProfile=Object.freeze({name:'Anna',address:Object.freeze({city:'Łódź',street:'Długa'}),contact:Object.freeze({email:'anna@example.test'})});const moved=profileOps.updateCity(oldProfile,'Gdańsk');assert.notEqual(moved.address,oldProfile.address);assert.equal(moved.contact,oldProfile.contact);assert.equal(oldProfile.address.city,'Łódź');checks++;
 const immerOps=evaluate(deepDrill.immer);const immerMoved=immerOps.updateCityImmer(oldProfile,'Gdańsk');assert.deepEqual(JSON.parse(JSON.stringify(immerMoved)),JSON.parse(JSON.stringify(moved)));assert.equal(immerMoved.contact,oldProfile.contact);checks++;
 const expenseOps=evaluate(deepDrill.expenseOps);const totals=expenseOps.totalsByCategory([{id:'a',category:'__proto__',amountGrosze:10},{id:'b',category:'__proto__',amountGrosze:15},{id:'c',category:'toString',amountGrosze:7}]);assert.equal(totals.get('__proto__'),25);assert.equal(totals.get('toString'),7);checks++;
 const h0={versions:[todos],index:0};const h1=todoOps.commitHistory(h0,current=>todoOps.toggleTodo(current,'a'));const h2=todoOps.commitHistory(h1,current=>todoOps.toggleTodo(current,'b'));const h3=todoOps.commitHistory(h2,current=>todoOps.renameTodo(current,'a','Nowe A'));const rewound=todoOps.undo(todoOps.undo(h3));const branch=todoOps.commitHistory(rewound,current=>todoOps.removeTodo(current,'c'));assert.equal(branch.index,2);assert.equal(branch.versions.length,3);assert.equal(todoOps.redo(branch),branch);assert.equal(h3.versions[3][0].title,'Nowe A');assert.equal(h0.versions[0][0].done,false);checks+=2;
 const catalogSource=require('./practice_state_11.cjs');const catalogModel=evaluate(catalogSource.model);const catalogLogic=evaluate(catalogSource.standalone+'\n'+catalogSource.sample);const generated=evaluate(catalogSource.data,{require:name=>name==='./model'?catalogModel:req(name)}).products;
 assert.equal(generated.length,57);assert.equal(new Set(Array.from(generated,product=>product.id)).size,57);assert.equal(generated.filter(product=>product.inStock).length,45);assert.ok(generated.every(product=>product.rating>=1&&product.rating<=5&&Number.isSafeInteger(product.priceGrosze)));checks+=2;
 const catalogData=Object.freeze(Array.from(catalogLogic.products,product=>Object.freeze(product)));const catalogBefore=JSON.stringify(catalogData);
 const catalogCart=catalogLogic.addToCart(catalogLogic.addToCart(catalogLogic.addToCart({},catalogData[0]),catalogData[0]),catalogData[2]);assert.equal(catalogLogic.cartCount(catalogCart),3);assert.equal(catalogLogic.cartTotal(catalogCart,catalogData),264799);assert.equal(catalogLogic.addToCart(catalogCart,catalogData[1]),catalogCart);assert.equal(JSON.stringify(catalogData),catalogBefore);checks++;
 const styles=new Proxy({},{get:(_,name)=>String(name)});const catalogImports={'./model':catalogModel,'./logic':catalogLogic,'./pagination':pagination,'./Catalog.module.css':{default:styles,__esModule:true}};
 const catalogCard=evaluate(catalogSource.card,{require:name=>catalogImports[name]??req(name)});catalogImports['./ProductCard']=catalogCard;
 for(const [rating,count] of [[0,0],[3.2,3],[4.8,5],[5,5],[NaN,0],[5.9,5]]){const html=renderToStaticMarkup(React.createElement(catalogCard.RatingStars,{rating}));assert.equal((html.match(/★/g)??[]).length,count);assert.ok(html.includes('aria-label="Ocena '));checks++;}
 const Catalog=evaluate(catalogSource.catalog,{require:name=>catalogImports[name]??req(name)}).Catalog;const catalogHtml=renderToStaticMarkup(React.createElement(Catalog,{products:generated}));assert.equal((catalogHtml.match(/<article/g)??[]).length,12);assert.ok(catalogHtml.includes('Produkty 1–12 z 57'));assert.ok(catalogHtml.includes('Koszyk: 0 szt.'));checks++;
 const catalog=logicInstance(catalogSource.catalog,'Catalog',{products:generated},catalogImports);let catalogTree=catalog.render();assert.equal(catalog.slots.length,6);catalog.slots[4]=5;catalogTree=catalog.render();catalogTree.props.children[1].props.children[0].props.children[1].props.onChange({currentTarget:{value:'LUMEN'}});assert.equal(catalog.slots[4],1);assert.equal(catalog.slots[0],'LUMEN');checks++;
 catalog.slots[0]='nieistniejący produkt';catalog.slots[1]='Audio';catalog.slots[2]=true;catalog.slots[3]='rating';catalog.slots[4]=5;catalog.slots[5]={2:3};catalogTree=catalog.render();catalogTree.props.children[3].props.children[1].props.onClick();assert.deepEqual(catalog.slots.slice(0,5),['','ALL',false,'rating',1]);assert.equal(catalogLogic.cartCount(catalog.slots[5]),3);checks++;
 const {pathToFileURL}=require('node:url');const {build}=await import(pathToFileURL(path.join(root,'frontend/node_modules/vite/dist/node/index.js')));const {default:reactPlugin}=await import(pathToFileURL(path.join(root,'frontend/node_modules/@vitejs/plugin-react/dist/index.js')));
 const fixture=fs.mkdtempSync(path.join(root,'target/react-catalog-build-'));for(const [file,code] of Object.entries(catalogSource.project)){fs.mkdirSync(path.dirname(path.join(fixture,file)),{recursive:true});fs.writeFileSync(path.join(fixture,file),code);}
 fs.writeFileSync(path.join(fixture,'index.html'),'<!doctype html><html lang="pl"><head><meta name="viewport" content="width=device-width, initial-scale=1"><title>Katalog produktów</title></head><body><div id="root"></div><script type="module" src="/src/main.tsx"></script></body></html>');
 const reactRoot=path.join(root,'frontend/node_modules/react'),domRoot=path.join(root,'frontend/node_modules/react-dom');await build({root:fixture,configFile:false,logLevel:'error',plugins:[reactPlugin()],resolve:{alias:[{find:/^react$/,replacement:reactRoot+'/index.js'},{find:/^react\/(.*)$/,replacement:reactRoot+'/$1'},{find:/^react-dom$/,replacement:domRoot+'/index.js'},{find:/^react-dom\/(.*)$/,replacement:domRoot+'/$1'}]}});assert.ok(fs.existsSync(path.join(fixture,'dist/index.html')));checks++;
 console.log(`OK stan: ${sources.size} fragmentów TypeScript, ${checks} kontroli logiki i renderu, ${quizRuns} wyników quizu; zamrożone dane, referencje, pola, sortowanie, paginacja, Immer, historia i build katalogu Vite. Modele hooków/settera/kolejki służą testom logiki; propagacja DOM i natywne disabled wymagają przeglądarki.`);
})().catch(error=>{console.error(error);process.exitCode=1;});

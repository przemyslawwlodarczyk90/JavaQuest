// node scripts/content-migration/react/verify_components_practice.cjs [katalog-typescript]
const fs = require('node:fs'), path = require('node:path'), vm = require('node:vm');
const assert = require('node:assert/strict');
const { createRequire } = require('node:module');
const { pathToFileURL } = require('node:url');
const { execFileSync } = require('node:child_process');
const root = path.resolve(__dirname, '../../..');
const ts = require(path.resolve(process.argv[2] ?? path.join(root, 'target/react-typescript-qa/node_modules/typescript')));
const frontendRequire = createRequire(path.join(root, 'frontend/package.json'));
const React = frontendRequire('react'), { renderToStaticMarkup } = frontendRequire('react-dom/server');
const chapter = '_react_02_komponenty';
const entries = [ ['01', '01_AppShellAndFirstComponent', 20, 60], ['02', '02_JsxInDepth', 30, 100], ['03', '03_ConditionalRendering', 30, 100], ['04', '04_PropsBasics', 30, 100], ['05', '05_PropsVariantsAndBadges', 30, 100], ['06', '06_ListsAndKeys', 30, 100], ['07', '07_CompositionAndChildren', 30, 100], ['08', '08_PureComponentsAndRenderRules', 30, 100] ];
entries.push(['09', '09_DrillJsxPropsChildren', 30, 100], ['10', '10_BossUserDashboard', 30, 100]);
const sources = new Map(), expected = new Map(), lessons = new Map();
function add(relative, code, errors = []) {
  const file = path.join(root, 'target/react-components-qa', relative);
  sources.set(file, code + '\nexport {};'); expected.set(file, errors);
}
for (const [number, slug, ec, qc] of entries) {
  const file = path.join(root, 'src/main/resources/content', chapter, slug + '.json');
  const lesson = JSON.parse(fs.readFileSync(file, 'utf8')); lessons.set(number, lesson);
  assert.equal(lesson.exercises.length, ec); assert.equal(lesson.quiz.length, qc);
  assert.equal(new Set(lesson.exercises.map(e => e.prompt)).size, ec);
  assert.equal(new Set(lesson.quiz.map(q => q.question + '\n' + (q.code ?? ''))).size, qc);
  for (const e of lesson.exercises) for (const field of ['prompt', 'hint', 'solution']) assert.ok(e[field]?.trim());
  for (const q of lesson.quiz) { assert.equal(new Set(Object.values(q.options)).size, 4); assert.ok(q.options[q.correct]); assert.ok(q.explanation.trim()); }
  for (const letter of 'ABCD') assert.equal(lesson.quiz.filter(q => q.correct === letter).length, qc / 4);
  lesson.exercises.forEach((e, index) => {
    if (number === '01' && [9, 19].includes(index)) return; // przykłady z wieloma plikami, osobna kontrola projektu
    if (/^(?:export |function |const |type |import |\/\/ @ts-expect-error|\/\*\*)/.test(e.solution)) add(`${number}/exercise${index + 1}${e.solution.startsWith('/**') ? '.jsx' : '.tsx'}`, e.solution);
  });
  lesson.quiz.forEach((q, index) => { if (q.code) add(`${number}/question${index + 1}.tsx`, q.code, number === '02' && index === 82 ? [2783] : []); });
}
const shell = require('./practice_components_01.cjs').project;
for (const [file, code] of Object.entries(shell)) if (file.endsWith('.tsx')) add('01/' + file.replace(/^src\//, ''), code);
const options = { strict: true, noEmit: true, target: ts.ScriptTarget.ES2023, module: ts.ModuleKind.ESNext,
  moduleResolution: ts.ModuleResolutionKind.Bundler, jsx: ts.JsxEmit.ReactJSX, types: [], skipLibCheck: true, allowJs: true, checkJs: true,
  paths: { react: [path.join(root, 'frontend/node_modules/@types/react/index.d.ts')], 'react/*': [path.join(root, 'frontend/node_modules/@types/react/*')] } };
const host = ts.createCompilerHost(options), read = host.readFile.bind(host), exists = host.fileExists.bind(host), directory = host.directoryExists.bind(host);
host.readFile = file => sources.get(path.resolve(file)) ?? read(file);
host.fileExists = file => sources.has(path.resolve(file)) || exists(file);
host.directoryExists = dir => [...sources.keys()].some(file => file.startsWith(path.resolve(dir) + path.sep)) || directory(dir);
const program = ts.createProgram([...sources.keys()], options, host);
const diagnostics = ts.getPreEmitDiagnostics(program);
assert.ok(diagnostics.every(d => d.file), 'Nieoczekiwana diagnostyka globalna');
for (const [file, errors] of expected) {
  const actual = diagnostics.filter(d => d.file && path.resolve(d.file.fileName) === file).map(d => d.code).sort();
  assert.deepEqual(actual, [...errors].sort(), path.relative(root, file));
}
function evaluate(code, imports = {}) {
  const context = { exports: {}, require: name => imports[name] ?? frontendRequire(name), console: { log() {} } };
  const js = ts.transpileModule(code, { compilerOptions: { target: ts.ScriptTarget.ES2022, module: ts.ModuleKind.CommonJS, jsx: ts.JsxEmit.ReactJSX } }).outputText;
  vm.runInNewContext(js, context); return context.exports;
}
const compiled = {};
for (const name of ['Header', 'Sidebar', 'Main']) compiled['./components/' + name] = evaluate(shell[`src/components/${name}.tsx`]);
const App = evaluate(shell['src/App.tsx'], { ...compiled, './App.css': {} }).default;
const appHtml = renderToStaticMarkup(React.createElement(App));
for (const text of ['DevDesk', 'Filtry', 'Zgłoszenia', 'app-shell', 'app-header', 'app-sidebar', 'app-main']) assert.ok(appHtml.includes(text));
let renderCount = 1;
const jsx = lessons.get('02');
for (const [index, name, props, text] of [
  [1, 'App', {}, 'DevDesk'], [2, 'OpenCount', {}, 'Otwarte: 3'], [3, 'Sum', {}, '>5<'],
  [4, 'Summary', {}, 'Liczba: 3'], [5, 'Message', {}, 'Są tickety'], [6, 'Message', {}, 'Są tickety'],
  [10, 'SearchLabel', {}, 'for="search"'], [11, 'ReadOnlySearch', {}, 'maxLength="50"'],
  [12, 'CloseButton', {}, 'aria-label="Zamknij"'], [13, 'AlertTitle', {}, 'margin-top:8px'],
  [14, 'Heading', {}, 'margin-bottom:4px'], [15, 'Warning', {}, 'color:red'],
  [16, 'Tab', { isActive: true }, 'tab--active'], [18, 'SearchInput', {}, 'type="search"'],
  [20, 'TicketTitle', {}, '#42 Eksport'], [21, 'TextExample', {}, '&lt;b&gt;API&lt;/b&gt;'],
  [22, 'OpenStats', {}, 'Otwarte: 2'], [23, 'EmptyStats', {}, '0%'],
  [25, 'DateLabel', {}, '1.10.2026'], [27, 'Values', {}, '|||0'], [28, 'Tags', {}, '<li>api</li>'],
]) {
  const Component = evaluate(jsx.exercises[index - 1].solution)[name];
  const html = renderToStaticMarkup(React.createElement(Component, props));
  assert.ok(html.includes(text), name + ': ' + html); renderCount++;
}
const Summary = evaluate(jsx.exercises[29].solution).Summary;
const fullHtml = renderToStaticMarkup(React.createElement(Summary, { tickets: [{ id: 1, status: 'OPEN' }, { id: 2, status: 'CLOSED' }, { id: 3, status: 'OPEN' }], dateLabel: '1.10.2026' }));
assert.ok(fullHtml.includes('2 tickety · 67%'));
const emptyHtml = renderToStaticMarkup(React.createElement(Summary, { tickets: [], dateLabel: '1.10.2026' }));
assert.ok(emptyHtml.includes('0 ticketów · 0%')); assert.ok(!emptyHtml.includes('NaN')); renderCount += 2;
const conditions = lessons.get('03');
function renderCondition(index, name, props) {
  const Component = evaluate(conditions.exercises[index - 1].solution)[name];
  renderCount++; return renderToStaticMarkup(React.createElement(Component, props));
}
assert.equal(renderCondition(14, 'Notice', { visible: false }), '');
assert.ok(!renderCondition(15, 'Parent', { visible: false }).includes('<p>'));
assert.ok(renderCondition(16, 'HiddenNotice', { visible: false }).includes('hidden=""'));
assert.ok(renderCondition(17, 'Save', { canSave: false }).includes('disabled=""'));
assert.ok(!renderCondition(17, 'Save', { canSave: true }).includes('disabled'));
assert.ok(!renderCondition(6, 'UnreadBadge', { count: 0 }).includes('<b>'));
assert.ok(!renderCondition(6, 'UnreadBadge', { count: 0 }).replace(/<[^>]*>/g, '').includes('0'));
assert.ok(renderCondition(6, 'UnreadBadge', { count: 2 }).includes('(2 nowe)'));
assert.ok(renderCondition(7, 'ResultCount', { count: 0 }).includes('Wyniki: 0'));
assert.ok(renderCondition(23, 'SelectedLabel', { id: 0 }).includes('Wybrano #0'));
for (const [state, text] of [
  [{ status: 'loading' }, 'Ładowanie'], [{ status: 'error', error: 'Brak sieci' }, 'Błąd: Brak sieci'],
  [{ status: 'success', data: [] }, 'Brak ticketów'], [{ status: 'success', data: ['API'] }, 'Wyniki: 1'],
]) assert.ok(renderCondition(13, 'Results', { state }).includes(text));
assert.ok(renderCondition(30, 'DetailPanel', { ticket: null, comments: 0 }).includes('Wybierz ticket'));
for (const priority of ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL']) {
  const ticket = { id: 42, title: 'API', priority, assignee: null, tags: [] };
  const html = renderCondition(30, 'DetailPanel', { ticket, comments: 0 });
  assert.equal(html.includes('aria-label="pilne"'), priority === 'HIGH' || priority === 'CRITICAL');
  assert.ok(html.includes('Nieprzypisany')); assert.ok(!html.includes('Tagi:')); assert.ok(!html.includes('<b>'));
}
const assigned = renderCondition(30, 'DetailPanel', { ticket: { id: 7, title: 'Login', priority: 'HIGH', assignee: 'anna', tags: ['auth', 'ui'] }, comments: 2 });
for (const text of ['Przypisany: anna', 'Tagi: auth, ui', '(2 nowe)']) assert.ok(assigned.includes(text));
const RetryMessage = evaluate(conditions.exercises[19].solution).RetryMessage;
let retries = 0;
const retry = RetryMessage({ error: 'Brak sieci', onRetry: () => retries++ });
retry.props.children[1].props.onClick(); assert.equal(retries, 1);
let quizRuns = 0;
for (const lesson of lessons.values()) for (const q of lesson.quiz) {
  if (!q.code?.includes('console.log')) continue;
  const output = [];
  const js = ts.transpileModule(q.code, { compilerOptions: { target: ts.ScriptTarget.ES2022, module: ts.ModuleKind.CommonJS, jsx: ts.JsxEmit.ReactJSX } }).outputText;
  vm.runInNewContext(js, { exports: {}, require: frontendRequire, console: { log: value => output.push(String(value)) } });
  if (output.length === 0) continue; // console.log tylko wewnątrz niewywołanego callbacka
  assert.deepEqual(output, [q.options[q.correct]], q.question); quizRuns++;
}
const propsLesson = lessons.get('04');
const Dashboard = evaluate(propsLesson.exercises[29].solution).Dashboard;
const tickets = [
  { id: 1, status: 'OPEN', priority: 'HIGH', assignee: 'anna' },
  { id: 2, status: 'CLOSED', priority: 'LOW', assignee: null },
  { id: 3, status: 'IN_PROGRESS', priority: 'CRITICAL', assignee: 'anna' },
];
const originalTickets = JSON.stringify(tickets);
const dashboard = renderToStaticMarkup(React.createElement(Dashboard, { tickets, currentUser: 'anna' }));
const values = [...dashboard.matchAll(/<strong>(\d+)<\/strong>/g)].map(match => Number(match[1]));
assert.deepEqual(values, [1, 2, 2]); renderCount++;
const differentUser = renderToStaticMarkup(React.createElement(Dashboard, { tickets, currentUser: 'jan' }));
assert.deepEqual([...differentUser.matchAll(/<strong>(\d+)<\/strong>/g)].map(match => Number(match[1])), [1, 2, 0]); renderCount++;
const emptyDashboard = renderToStaticMarkup(React.createElement(Dashboard, { tickets: [], currentUser: 'anna' }));
assert.equal([...emptyDashboard.matchAll(/<strong>0<\/strong>/g)].length, 3); renderCount++;
assert.equal(JSON.stringify(tickets), originalTickets);
const CloseButton = evaluate(propsLesson.exercises[12].solution).CloseButton;
let closed;
CloseButton({ ticket: tickets[0], onClose: id => { closed = id; } }).props.onClick();
assert.equal(closed, 1); assert.equal(tickets[0].status, 'OPEN');
const variants = evaluate(lessons.get('05').exercises[29].solution);
for (const tone of ['neutral', 'success', 'warning', 'danger']) for (const size of ['sm', 'md']) {
  const html = renderToStaticMarkup(React.createElement(variants.Badge, { tone, size }, 'Test'));
  assert.ok(html.includes(`badge badge--${tone} badge--${size}`)); assert.ok(html.includes('Test')); renderCount++;
}
const defaultBadge = renderToStaticMarkup(React.createElement(variants.Badge, null, 'Nowy'));
assert.ok(defaultBadge.includes('badge--neutral badge--md')); renderCount++;
for (const [status, tone, label] of [['OPEN', 'success', 'Otwarty'], ['IN_PROGRESS', 'warning', 'W toku'], ['CLOSED', 'neutral', 'Zamknięty']]) {
  const html = renderToStaticMarkup(React.createElement(variants.StatusBadge, { status }));
  assert.ok(html.includes(`badge--${tone}`)); assert.ok(html.includes(label)); renderCount++;
}
for (const [priority, tone, label] of [['LOW', 'neutral', 'Niski'], ['MEDIUM', 'neutral', 'Średni'], ['HIGH', 'warning', 'Wysoki'], ['CRITICAL', 'danger', 'Krytyczny']]) {
  const html = renderToStaticMarkup(React.createElement(variants.PriorityBadge, { priority }));
  assert.ok(html.includes(`badge--${tone} badge--sm`)); assert.ok(html.includes(label)); renderCount++;
}
const lists = lessons.get('06');
const listData = [{ id: 3, title: 'Eksport', status: 'CLOSED', assignee: null }, { id: 1, title: 'API', status: 'OPEN', assignee: 'anna' }, { id: 2, title: 'Login', status: 'IN_PROGRESS', assignee: null }];
const listSnapshot = JSON.stringify(listData);
const TicketList = evaluate(lists.exercises[2].solution).TicketList;
const keysBefore = TicketList({ tickets: listData }).props.children.map(element => element.key);
const keysAfter = TicketList({ tickets: listData.slice(1) }).props.children.map(element => element.key);
assert.deepEqual(Array.from(keysBefore), ['3', '1', '2']); assert.deepEqual(Array.from(keysAfter), ['1', '2']);
const SortedList = evaluate(lists.exercises[14].solution).SortedList;
assert.deepEqual(Array.from(SortedList({ tickets: listData }).props.children, element => element.key), ['1', '2', '3']);
const ActivePreview = evaluate(lists.exercises[29].solution).ActivePreview;
const listHtml = renderToStaticMarkup(React.createElement(ActivePreview, { tickets: listData })); renderCount++;
assert.ok(!listHtml.includes('Eksport')); assert.ok(listHtml.indexOf('API') < listHtml.indexOf('Login'));
const emptyListHtml = renderToStaticMarkup(React.createElement(ActivePreview, { tickets: [] })); renderCount++;
assert.ok(emptyListHtml.includes('colSpan="4"')); assert.ok(emptyListHtml.includes('Brak ticketów'));
assert.equal(JSON.stringify(listData), listSnapshot);
const composition = evaluate(lessons.get('07').exercises[29].solution);
for (const [actions, exists] of [[undefined, false], [null, false], [0, true], ['Akcje', true], [false, true]]) {
  const html = renderToStaticMarkup(React.createElement(composition.Panel, { title: 'X', actions }, 'Treść'));
  assert.equal(html.includes('panel__actions'), exists); assert.ok(html.includes('Treść'));
  if (actions === 0) assert.ok(html.includes('panel__actions">0</div>')); renderCount++;
}
const actionCalls = [];
function visitButtons(element) {
  if (!React.isValidElement(element)) return;
  if (typeof element.type === 'function') return visitButtons(element.type(element.props));
  if (element.type === 'button') element.props.onClick();
  React.Children.forEach(element.props.children, visitButtons);
}
visitButtons(composition.Main({ onNew: () => actionCalls.push('new'), onExport: () => actionCalls.push('export') }));
assert.deepEqual(actionCalls, ['new', 'export']);
const pure = lessons.get('08');
const CleanTable = evaluate(pure.exercises[29].solution).CleanTable;
const frozenTickets = Object.freeze([Object.freeze({ id: 2, title: 'Login' }), Object.freeze({ id: 1, title: 'API' })]);
const pureFirst = renderToStaticMarkup(React.createElement(CleanTable, { tickets: frozenTickets }));
const pureSecond = renderToStaticMarkup(React.createElement(CleanTable, { tickets: frozenTickets })); renderCount += 2;
assert.equal(pureFirst, pureSecond); assert.ok(pureFirst.includes('Wierszy: 2')); assert.ok(pureFirst.indexOf('API') < pureFirst.indexOf('Login'));
assert.deepEqual(frozenTickets.map(t => t.id), [2, 1]);
const ModernTable = evaluate(pure.exercises[28].solution).TicketTable;
assert.ok(renderToStaticMarkup(React.createElement(ModernTable, { tickets: [] })).includes('Brak ticketów')); renderCount++;
const drill = require('./practice_components_09.cjs');
const stock = evaluate(drill.stockCode).StockInfo;
for (const [quantity, text] of [[null, ''], [0, 'Brak w magazynie'], [1, 'Ostatnie sztuki: 1'], [4, 'Ostatnie sztuki: 4'], [5, 'Dostępne: 5 szt.']]) {
  const html = renderToStaticMarkup(React.createElement(stock, { quantity }));
  assert.ok(text ? html.includes(text) : html === ''); renderCount++;
}
const tags = evaluate(drill.tagCode).TagList;
for (const [values, max, text] of [[[], 3, 'Brak tagów'], [['a','b','c','d'], 3, '+1 więcej'], [['a','b'], 0, '+2 więcej']]) {
  assert.ok(renderToStaticMarkup(React.createElement(tags, { tags: values, max })).includes(text)); renderCount++;
}
let rated;
const stars = evaluate(drill.ratingCode).RatingStars({ value: 2, max: 5, onRate: value => { rated = value; } });
assert.equal(stars.props.children.length, 5); assert.equal(stars.props.children[1].props.children, '★'); assert.equal(stars.props.children[2].props.children, '☆');
stars.props.children[3].props.onClick(); assert.equal(rated, 4);
const bossSource = require('./practice_components_10.cjs');
const boss = evaluate(bossSource.dashboard + '\n' + bossSource.sample);
const bossUsers = Object.freeze(Array.from(boss.users, user => Object.freeze(user)));
for (const [data, counts] of [[bossUsers, [5,4,1]], [[], [0,0,0]], [[{id:9,login:'admin',name:null,role:'admin',active:false}], [1,0,1]]]) {
  assert.deepEqual(Object.values(boss.summarize(data)), counts);
  const html = renderToStaticMarkup(React.createElement(boss.UsersPage, { users: data, onInvite() {} }));
  assert.equal((html.match(/class="panel"/g) ?? []).length, 2); assert.ok(html.includes('+Zaproś'));
  if (!data.length) assert.ok(html.includes('Brak użytkowników')); renderCount++;
}
const bossHtml = renderToStaticMarkup(React.createElement(boss.UsersPage, { users: bossUsers, onInvite() {} }));
assert.ok(bossHtml.includes('opacity:0.5')); assert.ok(bossHtml.includes('(nieaktywny)')); assert.ok(bossHtml.includes('kasia'));
for (const tone of ['danger','info','neutral']) assert.ok(bossHtml.includes(`badge--${tone}`));
assert.equal(bossHtml, renderToStaticMarkup(React.createElement(boss.UsersPage, { users: bossUsers, onInvite() {} }))); renderCount += 2;
assert.equal(boss.initials('  Anna   Nowak  '), 'AN'); assert.equal(boss.initials('  '), '');
assert.equal(boss.displayName({...bossUsers[0], name:''}), '');
let invited = 0;
const bossTree = boss.UsersPage({users:bossUsers,onInvite:()=>invited++}); assert.equal(invited,0); visitButtons(bossTree); assert.equal(invited,1);
const bossKeys = data => Array.from(boss.UserTable({users:data}).props.children[1].props.children, child=>child.key);
assert.deepEqual(bossKeys(bossUsers), ['1','2','3','4','5']); assert.deepEqual(bossKeys(bossUsers.filter(user=>user.id!==2)), ['1','3','4','5']);
(async () => {
  const { build } = await import(pathToFileURL(path.join(root, 'frontend/node_modules/vite/dist/node/index.js')));
  const { default: reactPlugin } = await import(pathToFileURL(path.join(root, 'frontend/node_modules/@vitejs/plugin-react/dist/index.js')));
  const fixture = fs.mkdtempSync(path.join(root, 'target/react-components-build-'));
  for (const [file, code] of Object.entries(shell)) { fs.mkdirSync(path.dirname(path.join(fixture, file)), { recursive: true }); fs.writeFileSync(path.join(fixture, file), code); }
  fs.writeFileSync(path.join(fixture, 'index.html'), '<!doctype html><html lang="pl"><head><title>DevDesk</title></head><body><div id="root"></div><script type="module" src="/src/main.tsx"></script></body></html>');
  fs.writeFileSync(path.join(fixture, 'src/main.tsx'), 'import {createRoot} from "react-dom/client"; import App from "./App"; const container=document.getElementById("root"); if(!container) throw Error("Brak root"); createRoot(container).render(<App/>);');
  const reactRoot = path.join(root, 'frontend/node_modules/react'), domRoot = path.join(root, 'frontend/node_modules/react-dom');
  await build({ root: fixture, configFile: false, logLevel: 'error', plugins: [reactPlugin()], resolve: { alias: [
    { find: /^react$/, replacement: reactRoot + '/index.js' }, { find: /^react\/(.*)$/, replacement: reactRoot + '/$1' }, { find: /^react-dom\/(.*)$/, replacement: domRoot + '/$1' },
  ] } });
  assert.ok(fs.existsSync(path.join(fixture, 'dist/index.html')));
  console.log(`OK: ${sources.size} fragmentów sprawdzonych TypeScriptem, ${renderCount} wariantów renderowania, ${quizRuns} wyników quizu i build wieloplikowego AppShell; callback Ponów.`);
})().catch(error => { console.error(error); process.exitCode = 1; });

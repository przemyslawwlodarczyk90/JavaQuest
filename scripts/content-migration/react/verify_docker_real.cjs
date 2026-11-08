// Sprawdza pliki lekcji 21/04 w prawdziwym Dockerze: nginx -t, obraz wieloetapowy i odpowiedzi serwera.
// Wymaga działającego demona Dockera i sieci do pobrania obrazów node:22-alpine oraz nginx:1.27-alpine.
// Backend, bazy i Spring Boot nie są tu uruchamiane: /api/ wskazuje atrapę nazwy "backend".
// Uruchomienie: node scripts/content-migration/react/verify_docker_real.cjs
const fs=require('node:fs'),os=require('node:os'),path=require('node:path'),assert=require('node:assert/strict');
const {execFileSync}=require('node:child_process');
const {project}=require('./practice_prod_04.cjs');
const dir=fs.mkdtempSync(path.join(os.tmpdir(),'devdesk-docker-'));
const image='devdesk-frontend-lesson:test',name='devdesk-lesson-'+process.pid;
const docker=(args,opts={})=>execFileSync('docker',args,{encoding:'utf8',stdio:['ignore','pipe','pipe'],timeout:600000,...opts});
for(const file of ['Dockerfile','.dockerignore','nginx.conf','docker/config.template.json']){
  const target=path.join(dir,file);fs.mkdirSync(path.dirname(target),{recursive:true});fs.writeFileSync(target,project[file]);
}
// Minimalna aplikacja: prawdziwy npm ci i vite build wewnątrz etapu build.
fs.writeFileSync(path.join(dir,'package.json'),JSON.stringify({name:'devdesk-lesson',private:true,type:'module',scripts:{build:'vite build'},devDependencies:{vite:'8.3.5'}},null,2));
fs.writeFileSync(path.join(dir,'index.html'),'<!doctype html><html lang="pl"><head><meta charset="utf-8"/><title>DevDesk</title></head><body><div id="root"></div><script type="module" src="/main.js"></script></body></html>');
fs.writeFileSync(path.join(dir,'main.js'),'document.getElementById("root").textContent="DevDesk "+import.meta.env.VITE_APP_VERSION+" '+'x'.repeat(4000)+'";');
execFileSync(process.platform==='win32'?'npm.cmd':'npm',['install','--package-lock-only','--no-audit','--no-fund'],{cwd:dir,stdio:'pipe',shell:process.platform==='win32',timeout:300000});
async function main(){
  docker(['build','--build-arg','VITE_APP_VERSION=1.2.3','-t',image,dir]);
  const sizeMb=Number(docker(['image','inspect',image,'--format','{{.Size}}']).trim())/1048576;
  console.log('Rozmiar obrazu MB:',sizeMb.toFixed(1));
  assert.ok(sizeMb<120,'Obraz powinien mieć kilkadziesiąt MB');
  const contents=docker(['run','--rm','--add-host','backend:127.0.0.1','--entrypoint','sh',image,'-c','ls /usr/share/nginx/html; which node || echo brak-node; ls /usr/share/nginx/html/main.js 2>/dev/null || echo brak-zrodel; nginx -t 2>&1']);
  assert.match(contents,/brak-node/);assert.match(contents,/brak-zrodel/);assert.match(contents,/syntax is ok/);assert.match(contents,/test is successful/);
  console.log('nginx -t: OK; obraz bez Node i bez źródeł.');
  docker(['run','-d','--name',name,'--add-host','backend:127.0.0.1','-e','API_URL=/inne-api','-e','SENTRY_DSN=','-p','127.0.0.1::80',image]);
  try{
    const port=docker(['port',name,'80']).trim().split(':').pop();
    const origin='http://127.0.0.1:'+port;
    let up=false;
    for(let i=0;i<40&&!up;i++){try{up=(await fetch(origin+'/healthz')).ok;}catch{await new Promise(done=>setTimeout(done,250));}}
    assert.ok(up,'Serwer nie odpowiada');
    const check=async(url,status,test)=>{const res=await fetch(origin+url,{headers:{'Accept-Encoding':'gzip'}});assert.equal(res.status,status,url);await test?.(res,await res.text());console.log('OK',status,url);};
    await check('/healthz',200,(_r,t)=>assert.equal(t,'ok'));
    await check('/tickets/42',200,(r,t)=>{assert.equal(r.headers.get('cache-control'),'no-cache');assert.match(t,/id="root"/);assert.equal(r.headers.get('x-content-type-options'),'nosniff');});
    const html=await (await fetch(origin+'/')).text();
    const asset=/src="(\/assets\/[^"]+)"/.exec(html)[1];
    await check(asset,200,(r,t)=>{assert.match(r.headers.get('cache-control'),/immutable/);assert.match(t,/1\.2\.3/);assert.equal(r.headers.get('x-content-type-options'),'nosniff');});
    await check('/assets/stary-aaaaaaaa.js',404,(_r,t)=>assert.doesNotMatch(t,/id="root"/));
    await check('/config.json',200,(r,t)=>{assert.equal(r.headers.get('cache-control'),'no-cache');assert.deepEqual(JSON.parse(t),{apiUrl:'/inne-api',sentryDsn:''});});
    const api=await fetch(origin+'/api/tickets');
    assert.notEqual(api.status,200,'API bez backendu nie może udawać sukcesu');
    assert.doesNotMatch(await api.text(),/id="root"/);
    console.log('OK',api.status,'/api/tickets bez backendu nie zwraca index.html');
    const gz=await new Promise((resolve,reject)=>require('node:http').get(origin+asset,{headers:{'Accept-Encoding':'gzip'}},res=>{res.resume();console.log('Typ pliku JS:',res.headers['content-type']);resolve(res.headers['content-encoding']);}).on('error',reject));
    assert.equal(gz,'gzip');console.log('OK kompresja gzip dla pliku JS');
  }finally{
    try{docker(['rm','-f',name]);}catch{}
  }
  console.log('Weryfikacja Dockera zakończona.');
}
main().catch(error=>{console.error(error.stderr?.toString()||error);process.exitCode=1;}).finally(()=>{
  try{docker(['image','rm','-f',image]);}catch{}
  fs.rmSync(dir,{recursive:true,force:true});
});

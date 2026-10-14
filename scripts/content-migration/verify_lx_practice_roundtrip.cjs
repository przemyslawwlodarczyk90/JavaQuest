const fs=require('fs'),path=require('path'),os=require('os'),cp=require('child_process');
const root=path.resolve(__dirname,'../..'),max=Number(process.argv[2]||46);
if(!Number.isInteger(max)||max<1||max>46)throw Error('Chapter must be 1..46');
const short=new Set([1,2,8,9,19,20,23,29,43,44,46]);
const tempRoot=fs.realpathSync(os.tmpdir()),folder=fs.mkdtempSync(path.join(tempRoot,'javaquest-lx-roundtrip-'));
let checked=0;
try{
 for(const chapter of fs.readdirSync(path.join(root,'src/main/resources/content')).filter(x=>x.startsWith('_lx_')).sort()){
  const n=Number(chapter.slice(4,6));if(n>max)continue;
  for(const name of fs.readdirSync(path.join(root,'src/main/resources/content',chapter)).filter(x=>x.endsWith('.json')).sort()){
   const original=JSON.parse(fs.readFileSync(path.join(root,'src/main/resources/content',chapter,name),'utf8'));
   const ex=short.has(n)&&!(n===1&&name.startsWith('01_'))?20:30,q=ex===20?60:100;
   if(original.exercises.length!==ex||original.quiz.length!==q)throw Error(chapter+'/'+name+': incomplete');
   const source=path.join(root,'scripts/content-migration/lx_practice','_lx_'+String(n).padStart(2,'0')+'_'+name.slice(0,2)+'.txt');
   const destination=path.join(folder,name);
   fs.writeFileSync(destination,JSON.stringify(original));
   cp.execFileSync(process.execPath,[path.join(__dirname,'lx_build_practice.js'),source,destination,String(ex),String(q)],{encoding:'utf8',stdio:['ignore','pipe','pipe']});
   const rebuilt=JSON.parse(fs.readFileSync(destination,'utf8'));
   if(JSON.stringify(rebuilt)!==JSON.stringify(original))throw Error(chapter+'/'+name+': TXT/JSON mismatch');
   fs.unlinkSync(destination);checked++;
  }
 }
 console.log('OK: '+checked+' lessons, chapters 1-'+max+': exact TXT rebuild, unchanged theory, converter validation.');
}finally{
 if(path.dirname(folder)!==tempRoot||!path.basename(folder).startsWith('javaquest-lx-roundtrip-')||fs.realpathSync(folder)!==folder)throw Error('Unexpected cleanup target');
 fs.rmSync(folder,{recursive:true});
}


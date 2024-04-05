window.onload =(()=>{
	
	btn=document.getElementById("addselect").addEventListener("click",addSelect);
	btn1=document.getElementById("addingrediente").addEventListener("click",tabellaAlimenti);
})

function addSelect(){
	
	fetch("/Ricetta/addSelectAlimento"
	).then(risposta =>risposta.json())
	.then(data=>{
		let div=document.getElementById("divRicetta");
	let slt=document.createElement("select");
	let op=document.createElement("option");
	let aCapo=document.createElement("br");
	let gr=document.createElement("input");
	
	

    slt.style.marginRight="4px";
	op.text="Seleziona Alimento";
	slt.appendChild(op);
	
	for(let i=0;i<data.length;i++){
		let opn=document.createElement("option");
		opn.text=data[i].nome;
		slt.appendChild(opn);
		slt.name="alimento";
		slt.classList="inputClassReg"
		
	}
	let c=document.querySelectorAll('select[name="alimento"]');
	slt.id=c.length;
	gr.name="gr";
	gr.id="gr"+(c.length);
	gr.type="number";
	gr.placeholder="gr";
	gr.classList="inputClassRicetta";
	div.appendChild(slt);
	div.appendChild(gr);
	div.appendChild(aCapo);
	
	
	})
}
function tabellaAlimenti(){
	
	let lungh=document.querySelectorAll('select[name=alimento]');
	let nome;
	let gr;
	
	let tabDati={};
	for(let i=0;i<lungh.length;i++){
		nome=document.getElementById(i).value;
		console.log(document.getElementById(i));
		gr=document.getElementById("gr"+i).value;
		tabDati[nome]=gr;
	}
	console.log(tabDati);
	fetch("/Ricetta/tabellaRicetta",{
		method : "post",
		headers:{
			'Content-Type':'application/json;charset=UTF-8'},
			body:JSON.stringify(tabDati)
	}).then(risposta=>risposta.json())
	.then(data=>{
		console.log(data)
		if(data!==null){
			let tabella="<table><tr><th>Nome</th><th>Calorie</th><th>Carboidrati</th><th>Grassi</th><th>Proteine</th></tr>";
			
			for(let i=0;i<data.length;i++){
				tabella += "<tr><td>"+data[i].nome+ "</td><td>" +Number((data[i].calorie).toFixed(1))+ 
				"</td><td>" +Number((data[i].carboidrati).toFixed(1))+ "</td><td>" +Number((data[i].grassi).toFixed(1))+ "</td><td>" 
				+Number((data[i].proteine).toFixed(1)) +"</td></tr>";
			}
			
			tabella+="</table>";
			
			let tab=document.getElementById("tabellaRicetta");
			tab.innerHTML= tabella;
		}
	})
}


















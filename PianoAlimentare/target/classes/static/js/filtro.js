window.onload =(()=>{
	
	loadCategorie();
});

function loadCategorie(){
	
	let selezionato=document.getElementById("cate").value;
	let divtab= document.getElementById("divtabella");
	
	fetch("/nuovaTab").then(risposta=>risposta.json())
	.then(data=>{
	
		if (data !== null) {
				let tabella = `<table><tr><th>Nome</th><th>Calorie</th><th>Carboidrati</th><th>Grassi</th><th>Proteine</th></tr>`;
				for (let i = 0; i < data.length; i++) {
					if(selezionato=="Seleziona"){
					tabella += "<tr><td>" + data[i].nome + "</td><td>" + Number((data[i].calorie).toFixed(1)) +
						"</td><td>" + Number((data[i].carboidrati).toFixed(1)) + "</td><td>" + Number((data[i].grassi).toFixed(1)) + "</td><td>"
						+ Number((data[i].proteine).toFixed(1)) + "</td></tr>";	
					
					}else{
						if(data[i].categoria==selezionato){
						tabella += "<tr><td>" + data[i].nome + "</td><td>" + Number((data[i].calorie).toFixed(1)) +
						"</td><td>" + Number((data[i].carboidrati).toFixed(1)) + "</td><td>" + Number((data[i].grassi).toFixed(1)) + "</td><td>"
						+ Number((data[i].proteine).toFixed(1)) + "</td></tr>";
					}
					}
				}
				tabella += "</table>";
				divtab.innerHTML = tabella;
			}
	})
}
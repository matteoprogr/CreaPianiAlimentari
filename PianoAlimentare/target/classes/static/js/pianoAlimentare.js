window.onload = (() => {

	
})
function addSelect(tipoSelect,pasto) {

	fetch("/Ricetta/addSelect" + tipoSelect
	).then(risposta => risposta.json())
		.then(data => {
			let div = document.getElementById(tipoSelect+pasto);
			let slt = document.createElement("select");
			let op = document.createElement("option");
			let aCapo = document.createElement("br");
			let gr = document.createElement("input");

			slt.style.marginRight = "4px";
			op.text = "Seleziona " + tipoSelect;
			slt.appendChild(op);

			for (let i = 0; i < data.length; i++) {
				let opn = document.createElement("option");
				opn.text = data[i].nome;
				slt.appendChild(opn);
				slt.name = pasto;
				slt.classList="marginB";

			}
			let c = document.querySelectorAll(`select[name=${pasto}]`);

			slt.id = pasto+c.length;
			div.appendChild(slt);
			if (tipoSelect+pasto == "Alimento"+pasto) {
				gr.name = "gr";
				gr.id =pasto+ "gr" + (c.length);
				gr.type = "number";
				gr.placeholder = "gr";
				gr.classList = "inputClassRicetta";
				div.appendChild(gr);
			}

			div.appendChild(aCapo);

		})
}

function tabellaPiano(pasto) {


	let lungh = document.querySelectorAll(`select[name=${pasto}]`);
	let nome;
	let gr;

	let tabDati = {};
	for (let i = 0; i < lungh.length; i++) {
		
	     try{
			 nome = document.getElementById(pasto+i).value
			 
			 if ((gr=document.getElementById(pasto+"gr" + i).value) != null ){
				
				 tabDati[nome] = gr;
			 }
		
		 }catch{tabDati[nome] = 0;}
	}

	fetch("/piano/tabellaPiano", {
		method: "post",
		headers: {
			'Content-Type': 'application/json;charset=UTF-8'
		},
		body: JSON.stringify(tabDati)
	}).then(risposta => risposta.json())
		.then(data => {
			
			if (data !== null) {
				let tabella = `<table id=tab${pasto}><tr><th>Nome</th><th>Calorie</th><th>Carboidrati</th><th>Grassi</th><th>Proteine</th></tr>`;
				for (let i = 0; i < data.length; i++) {
					tabella += "<tr><td>" + data[i].nome + "</td><td>" + Number((data[i].calorie).toFixed(1)) +
						"</td><td>" + Number((data[i].carboidrati).toFixed(1)) + "</td><td>" + Number((data[i].grassi).toFixed(1)) + "</td><td>"
						+ Number((data[i].proteine).toFixed(1)) + "</td></tr>";
				}
				tabella += "</table>";

				let tab = document.getElementById("tabella"+pasto);
				tab.innerHTML = tabella;
			}
		})
}
function bottoneCliccatoSelectAlimento(btnN){
	
	let pasto=btnN.form.name;
	
	
		addSelect("Alimento",pasto)
}
function bottoneCliccatoSelectRicetta(btnN){
	
	let pasto=btnN.form.name;
	
	
		addSelect("Ricetta",pasto)
}
function bottoneCliccatoTabella(btnN){
	let pasto=btnN.form.name;
    disabilitaSave();
		
		tabellaPiano(pasto)
}
function ottieniValoriDaTabelle() {
	abilitaSave();
	if((t=document.getElementById("tabTotale"))!=null){
		t.remove();
	}
    // Array per memorizzare i valori da tutte le tabelle
    let valoriTabelle = [];

    // Seleziona tutte le tabelle presenti nella pagina
    let tabelle = document.querySelectorAll('table');

    // Itera attraverso ogni tabella
    tabelle.forEach(tabella => {
        // Array per memorizzare i valori della tabella corrente
        let valoriTabella = [];

        // Seleziona tutte le righe della tabella corrente
        let righe = tabella.querySelectorAll('tr');

        // Itera attraverso ogni riga della tabella corrente
        righe.forEach(riga => {
            // Seleziona tutte le celle della riga corrente
            let celle = riga.querySelectorAll('td');
            // Itera attraverso ogni cella della riga corrente
            celle.forEach(cell => {
                // Ottieni il valore della cella corrente e aggiungilo all'array dei valori della tabella corrente
            
                valoriTabella.push(cell.textContent.trim());
            });
        });

        // Aggiungi l'array dei valori della tabella corrente all'array dei valori di tutte le tabelle
        valoriTabelle.push(valoriTabella);
    });
    let Proteine=0;
    let Grassi=0;
    let Carboidrati=0;
    let Calorie=0;
    valoriTabelle.forEach(tabella=>{
		let [a,b,c,d]=tabella.reverse();
		Proteine += Number(a);
		Grassi+=Number(b);
		Carboidrati+=Number(c);
		Calorie+=Number(d);
		
	})
	

    // Restituisci l'array contenente i valori di tutte le tabelle
    let tabella = "<table id='tabTotale'><tr><th>Calorie</th><th>Carboidrati</th><th>Grassi</th><th>Proteine</th></tr>";
				
					tabella += "<tr><td>" + Number(Calorie.toFixed(1)) +
						"</td><td>" + Number(Carboidrati.toFixed(1)) + "</td><td>" + Number(Grassi.toFixed(1)) + "</td><td>"
						+ Number(Proteine.toFixed(1)) + "</td></tr>";
				
				tabella += "</table>";

				let tab = document.getElementById("tabellaTotale");
				
				tab.innerHTML = tabella;	
}

function savePiano(){
	
	let valoriTabelle = [];

    let tabelle = document.querySelectorAll('table');
    let tab=[];
    tabelle.forEach(tabella => {
        tab.push(tabella.id);
        
        let valoriTabella = [];

        let righe = tabella.querySelectorAll('tr');

        righe.forEach(riga => {
            let celle = riga.querySelectorAll('td');
            
            celle.forEach(cell => {
            
                valoriTabella.push(cell.textContent.trim());
            });
        });
        valoriTabelle.push(valoriTabella);
    });
    pasti={
		"Giorno":"",
		"Colazione":"",
		"SpuntinoM":"",
		"Pranzo":"",
		"SpuntinoP":"",
		"Cena":"",
		"Totale":"",
		};
		
		let j=0;
    valoriTabelle.forEach(tabella=>{
		
		
		let i=0;
		let k=0;
		let qu=0;
		let pasto="";
		let a="";
		let valori="";
		pasto=tab[j].substring(3);
		
		
			try{
				if(pasto!="Totale"){
					for(i=0;i<tabella.length-5;i+=5){
			   qu=0;
			if(k==1){k+=1;}
			
			      a=tabella[i];
			    
				if((qu=document.getElementById(`${pasto}gr`+k)).value!==0){
					valori+=` ${a} ${qu.value}gr  `;
					pasti[pasto]=valori;}
					
				k++;
				}
				}else{
					let [a1,b,c,d]=tabella;
					valori+=` Calorie ${a1}, Carboidrati ${b}, Grassi ${c}, Proteine ${d}`;
					pasti[pasto]+=valori;
				}
					
			}catch{let a=tabella[i];
			 valori+=` ${a},${0}`;
				pasti[pasto]=valori;}
				j++;
				
      })
      pasti["Giorno"]=document.getElementById("Giorno").value;
      console.log(pasti)
      if(pasti["Giorno"]!="Seleziona Giorno"){
		  
      fetch("/piano/savePiano",{
		  method:"post",
		  headers: {
			'Content-Type': 'application/json;charset=UTF-8'
		},
		body: JSON.stringify(pasti)
		
	  }).then(risposta=>{
		  risposta.text().then(testo=>{
			  if(testo=="login"){
				  console.log(testo)
				  window.location.href="/login";
				  alert("Effettuare il login per salvare il piano");
			  }
			  else if(testo=="successo"){
				  console.log(testo)
				  alert("Piano della gionata salvato con successo");
				  location.reload();
			  }
			  else if(testo=="esiste"){
				  let scelta=prompt("Esiste già un piano per questo giorno lo vuoi sovrascrivere? scrivere si per modificare il piano");
				   console.log(scelta)
				   
				  if(scelta.toLowerCase()=="si"){
					  console.log(scelta)
					  fetch("/piano/confermaPiano",{
		  method:"put",
		  headers: {
			'Content-Type': 'application/json;charset=UTF-8'
		},
	  }).then(risposta=>{
		  risposta.text().then(testo=>{
			  if(testo=="successo"){
				  alert("Piano della gionata salvato con successo");
				  location.reload();
			  }
		  })
	  })	  
					  
				  }
				 
			  }
		  })
	  })
	  }
	  else{
		  alert("Devi scegliere un giorno")
	  }
}
function abilitaSave(){
	document.getElementById("save").disabled= false;
}
function disabilitaSave(){
	document.getElementById("save").disabled= true;
}


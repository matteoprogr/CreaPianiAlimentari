function calcola(){
	let peso=document.getElementById("Peso").value;
	let obiettivo=document.getElementById("Obiettivo").value;
	let stile=document.getElementById("stile").value;
	let fabbisogno=document.getElementById("Fabbisogno");
	let eta=document.getElementById("Eta").value;
	let sesso=document.getElementById("Sesso").value;
	let user=document.getElementById("Nome").value;
	let pass=document.getElementById("Pass").value;
	let alt=document.getElementById("alt").value;
	let hidden=document.getElementById("Hidden");
	
	fabbisogno.textContent=calcoloFab(peso,alt,eta,obiettivo,stile,sesso)+" kcal";
	console.log(fabbisogno)
	hidden.value=fabbisogno.textContent;
	console.log(hidden)
	if(peso!=null && fabbisogno!=null && obiettivo!="Obiettivo" && stile!="Stile di vita" && user!= "" && pass != "" && eta!=null && sesso!=null && alt!=null){
		document.getElementById("reg").disabled= false;
	}
}
function calcoloFab(peso,alt,eta,obiettivo,stile,sesso){
	let fab="";
	if(sesso=="Uomo"){
		fab=66.5+(13.75*peso)+(5*alt)-(6.75*eta);
	}else if(sesso=="Donna"){
		fab=655+(9.56*peso)+(1.85*alt)-(4.67*eta);
	}
	
	let tenPerc=fab/100*10;
	fab+=tenPerc*2;
	
	if(stile=="Sedentario"){
		if(obiettivo=="Perdere Peso"){
		fab-=350;
	   }
	   if(obiettivo=="Mantenere peso"){
		fab-=0;
	   }
	   if(obiettivo=="Aumentare Peso"){
		fab+=350;
	   }
	}
	else if(stile=="Moderatamente attivo"){
	   if(obiettivo=="Perdere Peso"){
		fab+=tenPerc;
		fab-=350;
	   }
	   if(obiettivo=="Mantenere peso"){
		fab+=tenPerc;
		fab-=0;
	   }
	   if(obiettivo=="Aumentare Peso"){
		fab+=tenPerc;
		fab+=350;
	   }
	}
	else if(stile=="Attivo"){
		if(obiettivo=="Perdere Peso"){
	    fab+=tenPerc*2;
		fab-=350;
	   }
	   if(obiettivo=="Mantenere peso"){
		fab+=tenPerc*2;
		fab-=0;
	   }
	   if(obiettivo=="Aumentare Peso"){
		fab+=tenPerc*2;
		fab+=350;
	   }
	}

	return fab.toFixed(0);
	
}


















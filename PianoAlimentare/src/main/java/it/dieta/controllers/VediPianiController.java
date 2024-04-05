package it.dieta.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import it.dieta.models.PianoAlimentare;
import it.dieta.models.Utente;
import it.dieta.services.LogicaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/paginaPiani")
public class VediPianiController {

	@Autowired
	private LogicaService ls;
	
	@GetMapping
	public String pagPiani(Model model,HttpSession sessione) {
		
		boolean flag=true;
		model.addAttribute("Lunedi", true);
		model.addAttribute("Martedi", true);
		model.addAttribute("Mercoledi", true);
		model.addAttribute("Giovedi", true);
		model.addAttribute("Venerdi", true);
		model.addAttribute("Sabato", true);
		model.addAttribute("Domenica", true);
		
		Utente user=(Utente) sessione.getAttribute("Username");
		if(user!=null) {
			flag=false;
			
			int id=user.getIdUtente();
			List<PianoAlimentare> piani= ls.loadPianById(id);
			sessione.setAttribute("piano", piani);
			for(var v :piani) {
				if(v.getGiorno().equals("Lunedì")) {
					PianoAlimentare lunedi=v;
					model.addAttribute("pastiLunedi", lunedi);
					model.addAttribute("Lunedi", false);
				}
				else if(v.getGiorno().equals("Martedì")) {
					PianoAlimentare martedi=v;
					model.addAttribute("pastiMartedi", martedi);
					model.addAttribute("Martedi", false);
				}
				else if(v.getGiorno().equals("Mercoledì")) {
					PianoAlimentare mercoledi=v;
					model.addAttribute("pastiMercoledi", mercoledi);
					model.addAttribute("Mercoledi", false);
				}
				else if(v.getGiorno().equals("Giovedì")) {
					PianoAlimentare giovedi=v;
					model.addAttribute("pastiGiovedi", giovedi);
					model.addAttribute("Giovedi", false);
				}
				else if(v.getGiorno().equals("Venerdì")) {
					PianoAlimentare venerdi=v;
					model.addAttribute("pastiVenerdi", venerdi);
					model.addAttribute("Venerdi", false);
				}
				else if(v.getGiorno().equals("Sabato")) {
					PianoAlimentare sabato=v;
					model.addAttribute("pastiSabato", sabato);
					model.addAttribute("Sabato", false);
				}
				else if(v.getGiorno().equals("Domenica")) {
					PianoAlimentare domenica=v;
					model.addAttribute("pastiDomenica", domenica);
					model.addAttribute("Domenica", false);
				}
			}
		}
		model.addAttribute("mostraTrue",flag);
	   
		return "pianiPDF";
	}
	
	public int riga(String s,PDPageContentStream c,PDDocument document,int finefoglio,int altezzariga) throws IOException {
		
		
		if(s.length()>70) {
			int dim = (int) Math.ceil((double) s.length() / 70);
			
			String [] arr = new String[dim];
			int startIndex = 0;
			for (int i = 0; i < dim; i++) {
		        int endIndex = Math.min(startIndex + 70, s.length());
		        arr[i] = s.substring(startIndex, endIndex);
		        startIndex = endIndex;
		    }
			
			for(int i=0;i<arr.length;i++) {
				c.showText(arr[i]);
				c.newLine();
				finefoglio-=altezzariga;
			}
		}else {
			c.showText(s);
			c.newLine();
			finefoglio-=altezzariga;
		}
		return finefoglio;
	}
	
	@GetMapping("/download")
	@ResponseBody
	public void downLoadPDF(HttpServletResponse response, HttpSession sessione) throws Exception{
		
		 response.setContentType("application/pdf");
		 response.setHeader("Content-Disposition", "attachment; file-name=\"PianoAlimentare.pdf\"");
		 List<PianoAlimentare> piani=(List<PianoAlimentare>) sessione.getAttribute("piano");
		 
		 int finefoglio=700;
    	 int altezzaRiga=12;
    	 
    		 try (PDDocument document = new PDDocument()) {
    			 for(var v : piani) {
    			
 		        while(finefoglio>650) {
 		        	finefoglio= voltaPagina(document, finefoglio, altezzaRiga, v);
 		        }
 		       
 		        finefoglio=700;
 		        }
    			 document.save(response.getOutputStream());
    	 }
    	 
	}
	
	
	public int voltaPagina(PDDocument document,int finefoglio,int altezzaRiga, PianoAlimentare piano) throws IOException {
		
		
			 PDPage currentpage = new PDPage();
		try (PDPageContentStream contentStream = new PDPageContentStream(document, currentpage)) {
   		 contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
	        	contentStream.beginText();
	        	contentStream.newLineAtOffset(80,700);
	        	contentStream.setLeading(34.5f);	
	        	document.addPage(currentpage);
       		
					System.out.println(finefoglio);
						finefoglio=riga(piano.getGiorno(),contentStream,document,finefoglio,altezzaRiga);
						contentStream.showText("Colazione: ");
						contentStream.newLine();
						finefoglio=riga("-"+piano.getColazione(),contentStream,document,finefoglio,altezzaRiga);
						contentStream.showText("Spuntino Mattina: ");
						contentStream.newLine();
						finefoglio=riga("-"+piano.getSpuntinoMattina(),contentStream,document,finefoglio,altezzaRiga);
						contentStream.showText("Pranzo: ");
						contentStream.newLine();
						finefoglio=riga("-"+piano.getPranzo(),contentStream,document,finefoglio,altezzaRiga);
						contentStream.showText("Spuntino Pomeriggio: ");
						contentStream.newLine();
						finefoglio=riga("-"+piano.getSpuntinoPomeriggio(),contentStream,document,finefoglio,altezzaRiga);
						contentStream.showText("Cena: ");
						contentStream.newLine();
						finefoglio=riga("-"+piano.getCena(),contentStream,document,finefoglio,altezzaRiga);
						contentStream.newLine();
						
						
						 
						contentStream.endText();
						}
		
		     
				
       	
		
		return finefoglio;
		
	}
	
	
}

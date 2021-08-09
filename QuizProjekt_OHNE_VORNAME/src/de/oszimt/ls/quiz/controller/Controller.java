package de.oszimt.ls.quiz.controller;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.oszimt.ls.quiz.model.Model;
import de.oszimt.ls.quiz.model.Schueler;
import de.oszimt.ls.quiz.model.XMLParser;

public class Controller {

	private Model model;
	private XMLParser xmlParser;
	private int frageZeiger;
	public final int FRAGEANZAHL = 10;

	public Controller(String xmlPfad) {
		model = new Model();
		xmlParser = new XMLParser(xmlPfad);
		model = xmlParser.laden();
	}

	public int getFrageZeiger() {
		return frageZeiger;
	}
	
	public boolean Spielende() {
		return frageZeiger == FRAGEANZAHL;
	}

	public Schueler getGewaehlterSchueler() {
		return model.getGewaehlterSchueler();
	}

	public String getSpielstand() {
		return model.getSpielstand().getSpielstand();
	}
	
	public void getZufallsSchueler() {
		
			Random rand = new Random();
			List<Schueler> schuelerlein = model.getSchuelerlein();
			int maxFragen = 0;

			for (Schueler s : schuelerlein){
				if (s.getFragen() > maxFragen && s.getIstAnwesend()){
					maxFragen = s.getFragen();
				}
			}

			List<Schueler> glueckslos = new LinkedList<Schueler>();

			for (Schueler s : schuelerlein){
				if (s.getIstAnwesend()){
					for (int i = 0; i < (maxFragen + 1) - s.getFragen(); i++){
						glueckslos.add(s);
					}
				}
			}

			if (glueckslos.size() == 0){
				return;
			}

			int klassengroesse = glueckslos.size();
			model.setGewaehlterSchueler(glueckslos.get(rand.nextInt(klassengroesse)));
	}

	private void speichernInDateien() {
		xmlParser.speichern(model);
	}

	public void heimGewonnen() {
		model.heimGewonnen();
		speichernInDateien();
	}

	public void gastGewonnen() {
		model.gastGewonnen();
		speichernInDateien();
	}

	public void jokerBenutzt() {
		model.jokerBenutzt();
		speichernInDateien();
	}

	public void frageBeantwortet() {
		model.frageBeantwortet();
		frageZeiger++;
		speichernInDateien();
	}

	public void blamiert() {
		model.blamiert();
		speichernInDateien();
	}

	public void nichtDa() {
		model.nichtDa();
		speichernInDateien();
	}

}

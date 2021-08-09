package de.oszimt.ls.quiz.model;
import java.util.List;
import java.util.LinkedList;

public class Model {

	private List<Schueler> schuelerlein = new LinkedList<Schueler>();
	private Spielstand spielstand;
	private Schueler gewaehlterSchueler;

	public Schueler getGewaehlterSchueler() {
		return gewaehlterSchueler;
	}

	public void setGewaehlterSchueler(Schueler gewaehlterSchueler) {
		this.gewaehlterSchueler = gewaehlterSchueler;
	}

	public List<Schueler> getSchuelerlein() {
		return schuelerlein;
	}

	public Spielstand getSpielstand() {
		return spielstand;
	}

	public void setSpielstand(Spielstand spielstand) {
		this.spielstand = spielstand;
	}

	public void heimGewonnen() {
		this.getSpielstand().heimGewinnt();
	}

	public void gastGewonnen() {
		this.getSpielstand().gastGewinnt();
	}

	public void jokerBenutzt() {
		for (Schueler s : getSchuelerlein()) {
			if (s.equals(gewaehlterSchueler))
				s.jokerEingesetzt();
		}
	}

	public void frageBeantwortet() {
		for (Schueler s : getSchuelerlein()) {
			if (s.equals(gewaehlterSchueler))
				s.gefragt();
		}
	}

	public void blamiert() {
		for (Schueler s : getSchuelerlein()) {
			if (s.equals(gewaehlterSchueler))
				s.blamiert();
		}
	}

	public void nichtDa() {
		for (Schueler s : getSchuelerlein()) {
			if (s.equals(gewaehlterSchueler))
				s.nichtDa();
		}
	}
}

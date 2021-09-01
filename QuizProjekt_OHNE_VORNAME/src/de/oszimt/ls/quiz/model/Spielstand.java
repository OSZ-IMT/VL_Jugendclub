package de.oszimt.ls.quiz.model;

public class Spielstand {

	// Attribute
	private Partei heim;
	private Partei gast;

	/**
	 * Create Spielstand
	 * 
	 * @param parteiHeim
	 * @param pktHeim
	 * @param parteiGast
	 * @param pktGast
	 */
	public Spielstand(String parteiHeim, int pktHeim, String parteiGast, int pktGast) {
		this.heim = new Partei(parteiHeim, pktHeim);
		this.gast = new Partei(parteiGast, pktGast);
	}

	// Methoden
	public String toString() {
		return heim.getName() + " " + heim.getPunkte() + " : " + gast.getPunkte() + " " + gast.getName();
	}

	public int getPunkteHeim() {
		return heim.getPunkte();
	}

	public int getPunkteGast() {
		return gast.getPunkte();
	}

	public String getParteiHeim() {
		return heim.getName();
	}

	public String getParteiGast() {
		return gast.getName();
	}

	public void heimGewinnt() {
		this.heim.gewonnen();
	}

	public void gastGewinnt() {
		this.gast.gewonnen();
	}

}

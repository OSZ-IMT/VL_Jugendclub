package de.oszimt.ls.quiz.model;
public class Schueler {

	// Anfang Attribute
	private String name;
	private int joker;
	private int blamiert;
	private int fragen;
	private boolean istAnwesend;
	// Ende Attribute

	// Konstruktor
	public Schueler(String name, int joker, int blamiert, int fragen) {
		this.name = name;
		this.joker = joker;
		this.blamiert = blamiert;
		this.fragen = fragen;
		this.istAnwesend = true;
	}

	// Anfang Methoden
	public String getName() {
		return name;
	}

	
	public boolean getIstAnwesend() {
		return this.istAnwesend;
	}	

	public int getJoker() {
		return joker;
	}

	public int getBlamiert() {
		return blamiert;
	}

	public int getFragen() {
		return fragen;
	}

	public void blamiert() {
		this.blamiert++;
	}

	public void gefragt() {
		this.fragen++;
	}

	public void nichtDa() {
		this.fragen--;
		this.istAnwesend = false;
	}

	public void jokerEingesetzt() {
		this.joker++;
	}
	// Ende Methoden
}

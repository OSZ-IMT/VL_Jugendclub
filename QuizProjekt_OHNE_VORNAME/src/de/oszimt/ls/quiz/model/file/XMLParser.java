package de.oszimt.ls.quiz.model.file;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import de.oszimt.ls.quiz.model.Model;
import de.oszimt.ls.quiz.model.Schueler;
import de.oszimt.ls.quiz.model.Spielstand;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.oszimt.ls.quiz.view.StartQuiz;

public class XMLParser {

	private File datei;

	/**
	 * Erstelle XML Datei
	 * 
	 * @param pfad
	 */
	public XMLParser(String pfad) {
		this.datei = new File(pfad);

		// Datei existiert noch nicht
		if (datei.exists() && datei.length() != 0) {
			// Dateiinhalt laden
			laden();

		} else {
			// Datei exisitert nicht
			try {
				// Anlegen der Datei
				this.datei.createNewFile();
			} catch (Exception e) {
				StartQuiz.showException(e, "Dokument konnte nicht erzeugt werden.");
			}
		}
	}

	/**
	 * Lädt die XML Datei
	 * @return Model der XML-Datei
	 */
	public Model laden() {
		Model model = new Model();
		try {
			// Auslesen vorbereiten
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// Datei ausw�hlen
			Document doc = dBuilder.parse(this.datei);
			doc.getDocumentElement().normalize();

			// Auslesen
			// Ebene Klasse.xml
			NodeList nList = doc.getChildNodes().item(0).getChildNodes();// Spielstand (0), Mitspieler (1)

			// Ebene Spielstand
			NodeList stand = nList.item(0).getChildNodes();

			// Spielstand
			String parteiHeim = stand.item(0).getAttributes().item(0).getNodeValue();
			int pktHeim = Integer.parseInt(stand.item(0).getTextContent());
			String parteiGast = stand.item(1).getAttributes().item(0).getNodeValue();
			int pktGast = Integer.parseInt(stand.item(1).getTextContent());
			model.setSpielstand(new Spielstand(parteiHeim, pktHeim, parteiGast, pktGast));

			// Ebene Mitspieler
			NodeList mitspieler = nList.item(1).getChildNodes();

			// Sch�ler auslesen
			for (int i = 0; i < mitspieler.getLength(); i++) {
				String name = mitspieler.item(i).getAttributes().item(0).getNodeValue();
				int joker = Integer.parseInt(mitspieler.item(i).getChildNodes().item(0).getTextContent());
				int blamiert = Integer.parseInt(mitspieler.item(i).getChildNodes().item(1).getTextContent());
				int fragen = Integer.parseInt(mitspieler.item(i).getChildNodes().item(2).getTextContent());

				model.getAlleSchueler().add(new Schueler(name, joker, blamiert, fragen));
			}

		} catch (Exception e) {
			StartQuiz.showException(e, "Lesen der Datei fehlgeschlagen!");
		}
		return model;
	}

	/**
	 * speichert alle Nutzereingaben in eine XML-Datei
	 * @param model, Model
	 */
	public void speichern(Model model) {
		try {
			// XML-Dokument vorbereiten
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			// XML-Dokument mit Daten f�llen
			Element klasse = doc.createElement(datei.getName());
			doc.appendChild(klasse);

			// Spielstand speichern
			Element sstand = doc.createElement("Spielstand");
			klasse.appendChild(sstand);

			Element partei = doc.createElement("Partei");

			partei.setAttribute("name", model.getSpielstand().getParteiHeim());
			sstand.appendChild(partei);

			partei.appendChild(doc.createTextNode(model.getSpielstand().getPunkteHeim() + ""));

			partei = doc.createElement("Partei");

			partei.setAttribute("name", model.getSpielstand().getParteiGast());
			sstand.appendChild(partei);

			partei.appendChild(doc.createTextNode(model.getSpielstand().getPunkteGast() + ""));

			// Mitspieler speichern
			Element mitspieler = doc.createElement("Mitspieler");
			klasse.appendChild(mitspieler);

			// Sch�ler eintragen
			for (Schueler s : model.getAlleSchueler()) {
				Element e = doc.createElement("Schueler");
				e.setAttribute("name", s.getName());
				mitspieler.appendChild(e);
				Element joker = doc.createElement("Joker");
				joker.appendChild(doc.createTextNode(s.getJoker() + ""));
				e.appendChild(joker);
				Element blamiert = doc.createElement("Blamiert");
				blamiert.appendChild(doc.createTextNode(s.getBlamiert() + ""));
				e.appendChild(blamiert);
				Element fragen = doc.createElement("Fragen");
				fragen.appendChild(doc.createTextNode(s.getFragen() + ""));
				e.appendChild(fragen);
			}

			// XML-File schreiben vorbereiten
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			// Ziel setzen
			StreamResult result = new StreamResult(this.datei);
			// Datei schreiben
			transformer.transform(source, result);

		} catch (Exception e) {
			StartQuiz.showException(e, "Datei konnte nicht gespeichert werden!");
		}
	}

}

package de.oszimt.ls.quiz.model;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLParser {

	private File datei;

	// Konstruktor
	public XMLParser(String pfad) {
		this.datei = new File(pfad);
		// Datei existiert noch nicht
		if (datei.exists() && datei.length() != 0) {
			// Dateiinhalt laden
			laden();
		} else { // Datei exisitert nicht
			try {
				// Anlegen der Datei
				this.datei.createNewFile();
			} catch (Exception e) {
				System.out.println("Dokument konnte nicht erzeugt werden.");
			}
		}
	}

	// Methoden
	public Model laden() {
		Model model = new Model();
		try {
			// Auslesen vorbereiten
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			// Datei auswählen
			Document doc = dBuilder.parse(this.datei);
			doc.getDocumentElement().normalize();

			// Auslesen
			// Ebene Klasse.xml
			NodeList nList = doc.getChildNodes().item(0).getChildNodes();// Spielstand (0), Mitspieler (1)

			// Ebene Spielstand
			NodeList stand = nList.item(0).getChildNodes();

			// Spielstand
			model.setSpielstand(new Spielstand(stand.item(0).getAttributes().item(0).getNodeValue(),
					Integer.parseInt(stand.item(0).getTextContent()),
					stand.item(1).getAttributes().item(0).getNodeValue(),
					Integer.parseInt(stand.item(1).getTextContent())));

			// Ebene Mitspieler
			NodeList mitspieler = nList.item(1).getChildNodes();

			// Schüler auslesen
			for (int i = 0; i < mitspieler.getLength(); i++) {
				model.getSchuelerlein()
						.add(new Schueler(mitspieler.item(i).getAttributes().item(0).getNodeValue(),
								Integer.parseInt(mitspieler.item(i).getChildNodes().item(0).getTextContent()),
								Integer.parseInt(mitspieler.item(i).getChildNodes().item(1).getTextContent()),
								Integer.parseInt(mitspieler.item(i).getChildNodes().item(2).getTextContent())));
			}

		} catch (Exception e) {
			System.out.println("Lesen der Datei fehlgeschlagen!");
			System.out.println(e);
		}
		return model;
	}

	// speichert alle Nutzereingaben in eine XML-Datei
	public void speichern(Model model) {
		try {
			// XML-Dokument vorbereiten
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			// XML-Dokument mit Daten füllen
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

			// Schüler eintragen
			for (Schueler s : model.getSchuelerlein()) {
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
			System.out.println("Datei konnte nicht gespeichert werden!");
		}
	}

}

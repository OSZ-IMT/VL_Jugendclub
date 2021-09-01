package de.oszimt.ls.quiz.model.file;

import de.oszimt.ls.quiz.model.Model;
import de.oszimt.ls.quiz.view.StartQuiz;

import java.io.File;

public class CSVParser {
    private File datei;

    /**
     * Erstelle XML Datei
     *
     * @param pfad, Pfad zur Datei
     */
    public CSVParser(String pfad) {
        this.datei = new File(pfad);
    }

    /**
     * Lädt die XML Datei
     * @return Model der XML-Datei
     */
    public Model laden() {
        Model model = new Model();

        // Datei laden
        throw new RuntimeException("Not implemented");
        //throw new Exception("Not implemented");

        //return model;
    }
}

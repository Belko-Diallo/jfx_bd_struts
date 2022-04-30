module revisionJFX {
    exports vues;
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    opens vues to java.base, javafx.fxml;
    opens modele to javafx.base;
    opens fr.univ.orleans.pnt to javafx.fxml;
    exports fr.univ.orleans.pnt;
}
module lectureEDT {
    requires java.base;
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    requires org.apache.commons.io;
    requires java.sql;
    exports facade;
    exports modele;
    opens facade to java.desktop, java.sql;
    opens vues to java.base, javafx.fxml;
    opens modele to javafx.base, java.sql;
    opens application to javafx.fxml;
    exports application;
}
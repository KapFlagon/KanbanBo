module KanbanBo.main {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires afterburner.fx;
    requires ormlite.core;
    requires ormlite.jdbc;
    requires java.sql;
    requires java.prefs;

    opens driver to javafx.fxml;

    exports driver;
}
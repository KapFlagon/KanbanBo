module KanbanBo.main {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires afterburner.fx;

    opens driver to javafx.fxml;

    exports driver;
}
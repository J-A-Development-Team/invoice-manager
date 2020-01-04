module JADevelopmentTeam {
    requires javafx.controls;
    requires javafx.fxml;

    opens JADevelopmentTeam to javafx.fxml;
    exports JADevelopmentTeam;
}
package ba.unsa.etf.rpr.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PregledTerminaController {
    public Label labTerminZakazao;
    public Label labNazivObjekta;
    public Label labAdresaObjekta;
    public Label labDatumVrijemeTermina;
    public Label labZaduzeniInspektor;
    public Label labTerminObavljen;
    public TextArea areaNapomeneTermina;

    public void closeBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) labTerminObavljen.getScene().getWindow();
        stage.close();
    }
}

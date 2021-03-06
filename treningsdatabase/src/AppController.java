import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML TextArea screen;

    @FXML TextField aparatOvelse;
    @FXML TextField aparatAparat;
    @FXML TextField aparatKg;
    @FXML TextField aparatSett;
    @FXML TextField utenOvelse;
    @FXML TextArea utenBeskrivelse;

    private String errors = "";
    private TreningsApp app;


    public AppController() {
        this.app = new TreningsApp();
    }

    @FXML public void initialize(){
        updateScreen(this.app.testConnection());
    }

    @FXML public void regOvingAparat(){
        if (aparatOvelse.getText().equals("") ||
            aparatAparat.getText().equals("") ||
            aparatKg.getText().equals("") ||
            aparatSett.getText().equals(""))
        {
            this.errors += "Ett eller flere tomme felter.";
            updateScreen();
            return;
        }
        try {
            Double.parseDouble(aparatKg.getText());
            Integer.parseInt(aparatSett.getText());
        } catch (Exception e) {
            this.errors += "Ulovlig input " + e.getMessage();
            updateScreen();
            return;
        }
        try {
            this.addOvelse(
                new Ovelse(
                    aparatOvelse.getText(),
                    aparatAparat.getText(),
                    Double.parseDouble(aparatKg.getText()),
                    Integer.parseInt(aparatSett.getText())
                )
            );
            clearFields();
        } catch(Exception e ) {
            this.errors = e.getMessage();
        }
        updateScreen();
    }

    @FXML public void regOvingUten(){
        if (utenOvelse.getText().equals("") ||
            utenBeskrivelse.getText().equals(""))
        {
            errors = "Ett eller flere tomme felter.";
            updateScreen();
            return;
        }
        try {
            this.addOvelse(
                new Ovelse(
                    utenOvelse.getText(),
                    utenBeskrivelse.getText()
                )
            );
            clearFields();
        } catch(Exception e ) {
            this.errors = e.getMessage();
        }
        updateScreen();
    }

    private void addOvelse(Ovelse o) {
        this.app.addOvelse(o);
    }
    private void updateScreen(String message) {
        String s = message;
        s += ("\n" + this.errors);
        screen.setText(s);
        this.errors = "";
    }

    private void updateScreen() {
        screen.setText(this.app.listOvelser() + this.errors);
        this.errors = "";
    }

    @FXML
    private void showApparater() {
        String[] kolonner = {"apparatid", "apparatnavn", "apparatbrukbeskrivelse"};
        String tabell = "apparat";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML
    private void showOvelser() {
        String[] kolonner = {"ovelseid", "navn"};
        String tabell = "ovelse";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML
    private void showOvelsesgrupper() {
        String[] kolonner = {"ovelsesgruppeid", "ovelsesgruppenavn"};
        String tabell = "ovelsesgruppe";
        this.updateScreen(app.listTable(kolonner, tabell));
    }

    @FXML
    private void showTreningsokter() {
        String[] kolonner = {"oktid", "dato", "tidspunkt", "varighet", "form", "prestasjon"};
        String tabell = "treningsokt";
        this.updateScreen(app.listTable(kolonner, tabell));
    }

    private void clearFields() {
        this.aparatOvelse.setText("");
        this.aparatAparat.setText("");
        this.aparatKg.setText("");
        this.aparatSett.setText("");
        this.utenOvelse.setText("");
        this.utenBeskrivelse.setText("");
    }


}

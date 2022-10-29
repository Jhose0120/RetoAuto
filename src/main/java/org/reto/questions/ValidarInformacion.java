package org.reto.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.WebElement;
import org.reto.userinterface.RetoNewMeetingPage;
import org.reto.userinterface.RetoPage;

public class ValidarInformacion implements Question<Boolean> {
    public static ValidarInformacion reunion() {
        return new ValidarInformacion();
    }
    @Override
    public Boolean answeredBy(Actor actor) {
        WebElement mensajeError = RetoNewMeetingPage.LBL_DATA_ERRONEA.resolveFor(actor);
        return mensajeError.isDisplayed();
    }
}

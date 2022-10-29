package org.reto.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.WebElement;
import org.reto.userinterface.RetoPage;

public class ValidarLogeo implements Question<Boolean> {

    public static ValidarLogeo erroneo() {
        return new ValidarLogeo();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebElement mensajeError = RetoPage.LBL_LOGIN_ERRONEO.resolveFor(actor);
        return mensajeError.isDisplayed();
    }
}

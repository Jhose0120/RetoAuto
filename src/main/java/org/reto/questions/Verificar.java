package org.reto.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.reto.userinterface.RetoReunionesPage;

import java.util.List;

public class Verificar implements Question<Boolean> {
    private static String validador;

    public Verificar(String validador) {
        this.validador = validador;
    }

    public static Verificar creacionReunion(String validador) {
        return new Verificar(validador);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebElement listLocation = RetoReunionesPage.LBL_MEETING_NAME.resolveFor(actor);
        List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
        for (WebElement i : options) {
            if (i.getText().equals(validador)){
                return true;
            }
        }
        return false;
    }
}

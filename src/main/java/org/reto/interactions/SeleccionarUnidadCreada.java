package org.reto.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SeleccionarUnidadCreada implements Interaction {

    private final Target target;
    private final String nombreUnidadDeNegocio;


    public SeleccionarUnidadCreada(Target element, String bussinessName) {
        this.target = element;
        this.nombreUnidadDeNegocio = bussinessName;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        WebElement listLocation = target.resolveFor(actor);
        List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
        for (WebElement i : options) {
            if (i.getText().equals(nombreUnidadDeNegocio)){
                i.click();
                break;
            }
        }
    }

    public static SeleccionarUnidadCreada on(Target element, String bussinessName) {
        return Instrumented.instanceOf(SeleccionarUnidadCreada.class).withProperties(element, bussinessName);
    }
}
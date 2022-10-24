package org.reto.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SeleccionarAleatorio implements Interaction{
    private final Target target;
    public SeleccionarAleatorio(Target target) {
        this.target = target;
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        WebElement listLocation = target.resolveFor(actor);
        List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
        options.get((int)(Math.random()*(options.size()))).click();
    }
    public static SeleccionarAleatorio on(Target target) {
        return Instrumented.instanceOf(SeleccionarAleatorio.class).withProperties(target);
    }
}

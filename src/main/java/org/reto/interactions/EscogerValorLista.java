package org.reto.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class EscogerValorLista implements Interaction {

    private final Target target;
    private int opcion;

    public EscogerValorLista(Target element) {
        this.target = element;
        this.opcion = 0;
    }
    public EscogerValorLista(Target element, int opcion) {
        this.target = element;
        this.opcion = opcion;
    }
    @Override
    public <T extends Actor> void performAs(T actor) {

        WebElement hour = target.resolveFor(actor);
        hour.click();
        String text = hour.toString().substring(10);
        List<WebElement> lista= hour.findElements(By.xpath(""+text+"/option"));
        Select listSelect = new Select(hour);
        if (opcion == 0) {
            opcion=(int)(Math.random()*lista.size());
        }
        listSelect.selectByIndex(opcion);
    }

    public static EscogerValorLista index(Target target1){
        return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1);
    }
    public static EscogerValorLista index(Target target1, int opcion){
        return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1, opcion);
    }
}

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

    public EscogerValorLista(Target element) {
        this.target = element;

    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        WebElement hour = target.resolveFor(actor);
        hour.click();
        String text = hour.toString().substring(10);
        List<WebElement> lista= hour.findElements(By.xpath(""+text+"/option"));
        Select listSelect = new Select(hour);
        listSelect.selectByIndex((int)(Math.random()*lista.size()));
    }

    public static EscogerValorLista index(Target target1){
        return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1);
    }
}

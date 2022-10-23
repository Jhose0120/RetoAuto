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

    private final Target element;
    private final int index;


    public EscogerValorLista(Target element, int index) {
        this.element = element;
        this.index = index;

    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        WebElement hour = element.resolveFor(actor);
        hour.click();
        String text = hour.toString().substring(10);
        List<WebElement> lista= hour.findElements(By.xpath(""+text+"/option"));
        System.out.println(lista.size()+" "+text);
        Select listSelect = new Select(hour);
        listSelect.selectByIndex(index);
    }

    public static EscogerValorLista index(Target element, int index){
        return Instrumented.instanceOf(EscogerValorLista.class).withProperties(element, index);
    }
}

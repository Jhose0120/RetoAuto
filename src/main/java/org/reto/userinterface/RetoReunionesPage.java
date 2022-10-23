package org.reto.userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RetoReunionesPage extends PageObject {
    public static final Target LBL_MEETING = Target.the("Desplegar opciones de reunion").located(By.xpath("//a[contains(@href,'#nav_menu')]/span[contains(text(),'Meeting')]"));
    public static final Target LBL_MEETINGS = Target.the("Seleccionar reunion").located(By.xpath("//span[contains(text(),'Meetings')]"));
    public static final Target BTN_ADD_MEETING = Target.the("Solicitar la creación de una reunion").located(By.xpath("//span[contains(text(),' New Meeting')]"));
    public static final Target LBL_MEETING_NAME = Target.the("NOMBRE DE LA REUNION").located(By.xpath("//div[@class='slick-cell l1 r1']/a"));
}

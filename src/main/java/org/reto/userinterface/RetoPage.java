package org.reto.userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://serenity.is/demo/")
public class RetoPage extends PageObject {
    public static final Target USENAME = Target.the("Usuario").located(By.xpath("//*[@name='Username']"));
    public static final Target PASSWORD = Target.the("Contraseña").located(By.xpath("//*[@name='Password']"));
}

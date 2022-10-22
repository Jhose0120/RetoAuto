package org.reto.userinterface;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RetoOrganizacionPage extends PageObject {
    public static final Target LBL_ORGANIZATION = Target.the("Desplegar opciones").located(By.xpath("//span[contains(text(),'Organization')]"));
    public static final Target LBL_BUSINESS_UNITS = Target.the("Seleccionar la opcion unidad de negocio").located(By.xpath("//span[contains(text(),'Business Units')]"));
    public static final Target BTN_INSERT_BUSINESS_UNIT = Target.the("Agregar unidad de negocio").located(By.xpath("//span[contains(text(),' New Business Unit')]"));
    public static final Target NAME_BUSINESS_UNIT = Target.the("Ingresar nombre de la unidad").located(By.xpath("//*[@name='Name']"));
    public static final Target PARENT_BUSINESS_UNIT = Target.the("Seleccionar parent unit").located(By.xpath("//span[@class='select2-chosen']"));
    public static final Target TEXT_FOR_BUSINESS_UNIT = Target.the("Buscar o seleccionar parent business unit").located(By.xpath("//li[contains(@class, 'select2-results-dept-0 select2-result select2-result-selectable')]"));//input[@class='select2-input']
    public static final Target BTN_SAVE = Target.the("Guardar business units").located(By.xpath("//span[contains(text(),' Save')]"));
}

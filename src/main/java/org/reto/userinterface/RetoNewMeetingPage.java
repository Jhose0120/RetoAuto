package org.reto.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RetoNewMeetingPage {
    public static final Target INPT_MEETING_NAME = Target.the("Ingresar nombre de la reunion").located(By.xpath("//*[@name='MeetingName']"));
    public static final Target DIPLOY_MEETING_TYPE = Target.the("Desplegar lista tipo de reunion").located(By.xpath("//*[@id='select2-chosen-6']"));
    public static final Target SELECT_MEETING_TYPE = Target.the("Seleccionar tipo de reunion").located(By.xpath("//div[@class='select2-result-label']"));
    public static final Target INPT_MEETING_NUMBER = Target.the("Ingresar numero de reunion").located(By.xpath("//*[@name='MeetingNumber']"));
    public static final Target INPT_START_DATE = Target.the("").located(By.xpath("//*[@name='StartDate']"));
    public static final Target SLCT_START_HOUR = Target.the("").located(By.xpath("//div[@class='field StartDate col-sm-6']//select"));
    public static final Target INPT_END_DATE = Target.the("").located(By.xpath("//*[@name='EndDate']"));
    public static final Target SLCT_END_HOUR = Target.the("").located(By.xpath("//div[@class='field EndDate col-sm-6']//select"));
    public static final Target DIPLOY_LOCATION = Target.the("").located(By.xpath("//*[@id='select2-chosen-7']"));
    public static final Target SELECT_LOCATION = Target.the("").located(By.xpath("//li[contains(@class,'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_UNIT = Target.the("Unit").located(By.xpath("//*[@id='select2-chosen-8']"));
    public static final Target SELECT_UNIT = Target.the("").located(By.xpath("//li[contains(@class,'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_ORGANIZED_BY = Target.the("Organized by").located(By.xpath("//*[@id='select2-chosen-9']"));
    public static final Target SELECT_ORGANIZED_BY = Target.the("").located(By.xpath("//li[contains(@class,'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_REPORTER = Target.the("Reporter").located(By.xpath("//*[@id='select2-chosen-10']"));
    public static final Target SELECT_REPORTER = Target.the("").located(By.xpath("//li[contains(@class, 'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_ATTENDEE_LIST = Target.the("Atendee list").located(By.xpath("//*[@id='select2-chosen-12']"));
    public static final Target SELECT_ATTENDEE_LIST = Target.the("").located(By.xpath("//li[contains(@class, 'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target BTN_SAVE = Target.the("Guardar programacion").located(By.xpath("//span[contains(text(),'Save')]"));
    public static final Target LBL_DATA_ERRONEA = Target.the("Mensaje de error").located(By.xpath("//div[@class='toast toast-error']"));
}

package org.reto.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;
import org.reto.userinterface.RetoOrganizacionPage;

public class Registrar implements Task {
    public static Registrar unidad() {
        return Tasks.instrumented(Registrar.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoOrganizacionPage.BTN_INSERT_BUSINESS_UNIT),
                Enter.theValue("namex").into(RetoOrganizacionPage.NAME_BUSINESS_UNIT),
                Click.on(RetoOrganizacionPage.PARENT_BUSINESS_UNIT),
                Click.on(RetoOrganizacionPage.TEXT_FOR_BUSINESS_UNIT),
                Click.on(RetoOrganizacionPage.BTN_SAVE)
        );
    }
}

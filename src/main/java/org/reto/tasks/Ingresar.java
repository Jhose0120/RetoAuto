package org.reto.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.reto.userinterface.RetoOrganizacionPage;

public class Ingresar implements Task {
    public static Ingresar businessUnits() {
        return Tasks.instrumented(Ingresar.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoOrganizacionPage.LBL_ORGANIZATION),
                Click.on(RetoOrganizacionPage.LBL_BUSINESS_UNITS)
        );
    }
}

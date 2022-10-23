package org.reto.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.reto.userinterface.RetoReunionesPage;

public class IngresarAReuniones implements Task {
    public static IngresarAReuniones solicitarCreacion() {
        return Tasks.instrumented(IngresarAReuniones.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoReunionesPage.LBL_MEETING),
                Click.on(RetoReunionesPage.LBL_MEETINGS),
                Click.on(RetoReunionesPage.BTN_ADD_MEETING)
        );
    }
}

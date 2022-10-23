package org.reto.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.selectactions.SelectByIndexFromBy;
import net.serenitybdd.screenplay.actions.selectactions.SelectByIndexFromTarget;
import org.reto.interactions.EscogerValorLista;
import org.reto.interactions.SeleccionarUnidadCreada;
import org.reto.userinterface.RetoNewMeetingPage;

public class Programar implements Task {
    public static Programar reunion() {
        return Tasks.instrumented(Programar.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue("Meeting name").into(RetoNewMeetingPage.INPT_MEETING_NAME),
                Click.on(RetoNewMeetingPage.DIPLOY_MEETING_TYPE),
                Click.on(RetoNewMeetingPage.SELECT_MEETING_TYPE),
                Enter.theValue("2684").into(RetoNewMeetingPage.INPT_MEETING_NUMBER),
                Enter.theValue("10/22/2022").into(RetoNewMeetingPage.INPT_START_DATE),
                EscogerValorLista.index(RetoNewMeetingPage.SLCT_START_HOUR,2),
                Enter.theValue("10/23/2022").into(RetoNewMeetingPage.INPT_END_DATE),
                EscogerValorLista.index(RetoNewMeetingPage.SLCT_END_HOUR,280),
                Click.on(RetoNewMeetingPage.DIPLOY_LOCATION),
                Click.on(RetoNewMeetingPage.SELECT_LOCATION),
                Click.on(RetoNewMeetingPage.DIPLOY_UNIT),
                SeleccionarUnidadCreada.on(RetoNewMeetingPage.SELECT_UNIT,"Marketing"),
                Click.on(RetoNewMeetingPage.DIPLOY_ORGANIZED_BY),
                Click.on(RetoNewMeetingPage.SELECT_ORGANIZED_BY),
                Click.on(RetoNewMeetingPage.DIPLOY_REPORTER),
                Click.on(RetoNewMeetingPage.SELECT_REPORTER),
                Click.on(RetoNewMeetingPage.DIPLOY_ATTENDEE_LIST),
                Click.on(RetoNewMeetingPage.SELECT_ATTENDEE_LIST));
    }
}

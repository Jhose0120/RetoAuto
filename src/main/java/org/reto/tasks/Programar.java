package org.reto.tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.reto.interactions.EscogerValorLista;
import org.reto.interactions.SeleccionarAleatorio;
import org.reto.interactions.SeleccionarUnidadCreada;
import org.reto.userinterface.RetoNewMeetingPage;
import java.util.Date;
public class Programar implements Task {
    private String nombreUnidad;
    private String nombreReunion;
    private String numeroReunion;
    private String caso;
    public Programar(String nombreUnidad, String nombreReunion, String numeroReunion){
        this.nombreUnidad = nombreUnidad;
        this.nombreReunion = nombreReunion;
        this.numeroReunion = numeroReunion;
        this.caso = "0";
    }
    public Programar(String nombreUnidad, String nombreReunion, String numeroReunion, String caso){
        this.nombreUnidad = nombreUnidad;
        this.nombreReunion = nombreReunion;
        this.numeroReunion = numeroReunion;
        this.caso = caso;
    }
    String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
    String fechaM = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
    public static Programar reunion(String nombreUnidad, String nombreReunion, String numeroReunion) {
        return Tasks.instrumented(Programar.class, nombreUnidad, nombreReunion, numeroReunion);
    }
    public static Programar reunion(String nombreUnidad, String nombreReunion, String numeroReunion, String caso) {
        return Tasks.instrumented(Programar.class, nombreUnidad, nombreReunion, numeroReunion, caso);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        if(caso.equals("1")){
            actor.attemptsTo(Enter.theValue(fechaM).into(RetoNewMeetingPage.INPT_START_DATE),
                    EscogerValorLista.index(RetoNewMeetingPage.SLCT_START_HOUR),
                    Enter.theValue(fechaActual).into(RetoNewMeetingPage.INPT_END_DATE),
                    EscogerValorLista.index(RetoNewMeetingPage.SLCT_END_HOUR),
                    Click.on(RetoNewMeetingPage.DIPLOY_UNIT),
                    new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_UNIT)
            );
        }else if (caso.equals("2")){
            actor.attemptsTo(Enter.theValue(fechaActual).into(RetoNewMeetingPage.INPT_START_DATE),
                    EscogerValorLista.index(RetoNewMeetingPage.SLCT_START_HOUR,3),
                    Enter.theValue(fechaActual).into(RetoNewMeetingPage.INPT_END_DATE),
                    EscogerValorLista.index(RetoNewMeetingPage.SLCT_END_HOUR, 2),
                    Click.on(RetoNewMeetingPage.DIPLOY_UNIT),
                    new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_UNIT)
            );
        }else {
            actor.attemptsTo(Enter.theValue(fechaActual).into(RetoNewMeetingPage.INPT_START_DATE),
                    EscogerValorLista.index(RetoNewMeetingPage.SLCT_START_HOUR),
                    Enter.theValue(fechaM).into(RetoNewMeetingPage.INPT_END_DATE),
                    EscogerValorLista.index(RetoNewMeetingPage.SLCT_END_HOUR),
                    Click.on(RetoNewMeetingPage.DIPLOY_UNIT),
                    SeleccionarUnidadCreada.on(RetoNewMeetingPage.SELECT_UNIT,nombreUnidad)
            );
        }
        actor.attemptsTo(
                Enter.theValue(nombreReunion).into(RetoNewMeetingPage.INPT_MEETING_NAME),
                Click.on(RetoNewMeetingPage.DIPLOY_MEETING_TYPE),
                new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_MEETING_TYPE),
                Enter.theValue(numeroReunion).into(RetoNewMeetingPage.INPT_MEETING_NUMBER),
                /*Enter.theValue(fechaActual).into(RetoNewMeetingPage.INPT_START_DATE),
                EscogerValorLista.index(RetoNewMeetingPage.SLCT_START_HOUR),
                Enter.theValue(fechaM).into(RetoNewMeetingPage.INPT_END_DATE),
                EscogerValorLista.index(RetoNewMeetingPage.SLCT_END_HOUR),*/
                Click.on(RetoNewMeetingPage.DIPLOY_LOCATION),
                new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_LOCATION),
                //Click.on(RetoNewMeetingPage.DIPLOY_UNIT),
                //SeleccionarUnidadCreada.on(RetoNewMeetingPage.SELECT_UNIT,nombreUnidad),
                Click.on(RetoNewMeetingPage.DIPLOY_ORGANIZED_BY),
                new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_ORGANIZED_BY),
                Click.on(RetoNewMeetingPage.DIPLOY_REPORTER),
                new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_REPORTER),
                Click.on(RetoNewMeetingPage.DIPLOY_ATTENDEE_LIST),
                new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_ATTENDEE_LIST),
                Click.on(RetoNewMeetingPage.BTN_SAVE)
        );
    }
}

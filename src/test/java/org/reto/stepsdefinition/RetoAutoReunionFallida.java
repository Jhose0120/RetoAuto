package org.reto.stepsdefinition;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.reto.questions.ValidarInformacion;
import org.reto.tasks.IngresarAReuniones;
import org.reto.tasks.Login;
import org.reto.tasks.OpenThe;
import org.reto.tasks.Programar;

public class RetoAutoReunionFallida {
    @Before
    public void setStage(){
        OnStage.setTheStage(new OnlineCast());
    }
    @Given("^J Abre la pagina y se loguea con el usuario (.*) y la contrasena (.*)$")
    public void jAbreLaPaginaYSeLogueaConElUsuarioAdminYLaContrasenaSerenity(String usuario, String contrasena) throws Exception {
        OnStage.theActorCalled("J").wasAbleTo(OpenThe.page(), Login.onThePage(usuario,contrasena));
    }

    @When("^J intenta programar una reunion con horarios incongruentes caso (.*)$")
    public void jIntentaProgramarUnaReunionConHorariosIncongruentesCaso(String caso) throws Exception {
        OnStage.theActorInTheSpotlight().attemptsTo(IngresarAReuniones.solicitarCreacion(),
                Programar.reunion("nombreUnidad", "nombreReunion", "numeroReunion",caso)
        );
    }

    @Then("^El visualiza mensaje de error$")
    public void elVisualizaMensajeDeError() throws Exception {
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(ValidarInformacion.reunion()));
    }
}

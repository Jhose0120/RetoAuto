package org.reto.stepsdefinition;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.reto.questions.ValidarLogeo;
import org.reto.tasks.Login;
import org.reto.tasks.OpenThe;

public class RetoAutoLoginDefinition {
    @Before
    public void setStage(){
        OnStage.setTheStage(new OnlineCast());
    }
    @Given("^J Abre la pagina y se loguea con datos erroneos usuario (.*) y la contrasena (.*)$")
    public void jAbreLaPaginaYSeLogueaConElUsuarioYLaContrasena(String usuario, String contrasena) throws Exception {
        OnStage.theActorCalled("J").wasAbleTo(OpenThe.page(), Login.onThePage(usuario,contrasena));
    }

    @Then("^J valida que se muestre mensaje de error$")
    public void jValidaQueSeMuestreMensajeDeError() throws Exception {
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(ValidarLogeo.erroneo()));
    }
}

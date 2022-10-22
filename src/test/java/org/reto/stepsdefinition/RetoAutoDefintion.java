package org.reto.stepsdefinition;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.reto.tasks.Login;
import org.reto.tasks.OpenThe;

public class RetoAutoDefintion {
    @Before
    public void setStage(){
        OnStage.setTheStage(new OnlineCast());
    }
    @Given("^Abro la pagina y me logueo$")
    public void abroLaPaginaYMeLogueo() throws Exception {
        OnStage.theActorCalled("J").wasAbleTo(OpenThe.page(), Login.onThePage("admin","serenity"));
    }


    @When("^Creo unidad de negocio$")
    public void creoUnidadDeNegocio() throws Exception {
        //OnStage.theActorInTheSpotlight().attemptsTo(Registrar.unidad(), Programar.reunion(), Verificar.creacionReunion());
    }

    @Then("^Genero programacion y valido$")
    public void generoProgramacionYValido() throws Exception {
        //dsa
    }
}

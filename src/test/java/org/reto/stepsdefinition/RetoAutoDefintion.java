package org.reto.stepsdefinition;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.reto.questions.Verificar;
import org.reto.tasks.*;
import org.reto.util.GenerarData;

public class RetoAutoDefintion {
    GenerarData data = new GenerarData();
    String nombreUnidad = data.generarData();
    String nombreReunion = data.generarData();
    String numeroReunion = data.generarData();

    @Before
    public void setStage(){
        OnStage.setTheStage(new OnlineCast());
    }
    @Given("^J Abre la pagina y se loguea con el usuario (.*) y la contraseña (.*)$")
    public void abroLaPaginaYMeLogueo(String usuario, String contrasena) throws Exception {
        OnStage.theActorCalled("J").wasAbleTo(OpenThe.page(), Login.onThePage(usuario,contrasena));
    }
    @When("^El crea una unidad de negocio y programa una reunion con esta$")
    public void creoUnidadDeNegocio() throws Exception {
        OnStage.theActorInTheSpotlight().attemptsTo(Ingresar.businessUnits(), Registrar.unidad(nombreUnidad),
                IngresarAReuniones.solicitarCreacion(),Programar.reunion(nombreUnidad, nombreReunion, numeroReunion)
        );
    }
    @Then("^J valida la creacion de la reunion$")
    public void generoProgramacionYValido() throws Exception {
        OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(Verificar.creacionReunion(nombreReunion)));
    }
}

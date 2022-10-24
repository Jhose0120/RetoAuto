package org.reto.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;
import org.reto.userinterface.RetoPage;

public class Login implements Task {
    private RetoPage retoPage;
    private String username;
    private String password;
    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }
    public static Login onThePage(String username, String password) {
        return Tasks.instrumented(Login.class, username, password);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(username).into(RetoPage.USENAME),
                Enter.theValue(password).into(RetoPage.PASSWORD).thenHit(Keys.ENTER));
    }
}

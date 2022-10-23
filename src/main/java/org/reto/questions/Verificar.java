package org.reto.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class Verificar implements Question<Boolean> {
    public static Verificar creacionReunion() {
        return new Verificar();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return null;
    }
}

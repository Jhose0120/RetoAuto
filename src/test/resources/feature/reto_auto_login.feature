Feature: Reto automatizacion escenario login erroneo
 Scenario Outline: Loguearse con credenciales erroneas
    Given J Abre la pagina y se loguea con datos erroneos usuario <usuario> y la contrasena <contrasena>
    Then J valida que se muestre mensaje de error
    Examples:
    |usuario      |contrasena  |
    |admin        |losanda     |
    |serb         |serenity    |
    |dadrw        |vermsa      |
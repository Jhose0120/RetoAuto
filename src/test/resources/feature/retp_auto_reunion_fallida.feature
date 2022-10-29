Feature: Reto automatizacion validacion control de errores en datos
  Scenario Outline: Datos incongruentes
    Given J Abre la pagina y se loguea con el usuario <usuario> y la contrasena <contrasena>
    When J intenta programar una reunion con horarios incongruentes caso <caso>
    Then El visualiza mensaje de error

    Examples:
      |usuario|contrasena|caso|
      |admin  |serenity  |1   |
      |admin  |serenity  |2   |
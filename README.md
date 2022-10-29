# Reto de automatización 
## Condiciones
### 1. Describa los diferentes escenarios que bajo su criterio validaría para asegurar la calidad de la página.
En general se debe validar la mantenibilidad de los flujos criticos del aplicativo pero dado a que no se cuenta con información suficiente se establecen las dos validaciones siguientes.
- Debido a que no existe un requerimiento específico con respecto la página se plantea la prueba basica de seguridad en el login bajo 3 escenarios, 
  - Usuarios y contraseñas correctas
  - Usuario correcto y contraseña incorrecta.
  - Usuario incorrecto y contraseña correcta
- Respecto a las opciones que nos presenta el aplicativo otro aspecto a validar el control sobre incongruencia de la información:
  - Caso puntual programación de reuniones con fechas incoherentes
    - Dia inicio mayor a día fin
    - Mismo dia hora inicio mayor a hora fin.
  - En general el no diligeniciamiento de campos obligatorios en formularios


### 2. Elija al menos dos escenarios y automatícelos.
Dado que el ejercicio práctico se debe trabajar bajo el arquetipo screenplay se maneja la siguiente estructura de carpetas:
```
src
	|-main
		|-java
			|-org.reto
				|-interactions
				|-questions
				|-tasks
				|-userinterface
				|-util
	|-test
		|-java
			|-org.reto
				|-runner
				|-stepdefinition
		|-resources
			|-driver
			|-feature
```
En la cual cada carpeta tiene asociada una funcionalidad específica:

***Runner:*** En esta carpeta se encuentra el ejecutor de los features.

***UserInterface:*** las UI son el mapeo de la interfaz, donde capturaremos todos los elementos con los cuales podríamos llegar a interactuar durante la automatización. Además, se le puede añadir la URL donde se iniciará la prueba.

***Features:*** los features son las historias de usuario que se llevarán a cabo en las pruebas y proveerá los métodos que utilizaremos más adelante para los StepDefinitions.

***StepDefinitions:*** los Step Definitions son la traducción de los features a código. Los métodos que se utilizaran son los definidos en el feature.

***Task:*** son las interacciones que se llevarán a cabo para cumplir con las historias de usuarios planteadas. Las tasks se pueden caracterizar porque no se habla en términos de clic, set data o select. Son verbos más amplios como loguearse formulario, cerrar sesión y buscar.

***Interactions:*** Contiene clases usadas para indicar acciones como dar clic, select, enviar datos, scroll, entre otras cosas.

***Questions:*** son lo assert a llevar a cabo para asegurar el cumplimiento de ciertos parámetros.

***Util:*** Contiene clases con los métodos unitarios que pueden ser utilizados libremente por otras clases del proyecto.

#### Escenario 1: validación básica de seguridad de logueo con parte de la información correcta
Se define el siguiente feature en el cual se validan 3 escenarios
1. Usuario correcto y contraseña incorrecta
2. Usuario incorrecto y contraseña correcta
3. Ambos datos erroneos

En este caso usamos Scenario Outline para recibir los datos de cada escenario a validar desde el feature.

```
Feature: Reto automatizacion escenario login erroneo
 Scenario Outline: Loguearse con credenciales erroneas
    Given J Abre la pagina y se loguea con datos erroneos usuario <usuario> y la contrasena <contrasena>
    Then J valida que se muestre mensaje de error
    Examples:
    |usuario      |contrasena  |
    |admin        |losanda     |
    |serb         |serenity    |
    |dadrw        |vermsa      |
```
##### RetoAutoLoginDefinition
En esta clase de definición usamos los escenarios establecidos en el feature, agregando los datos indicados de logueo y validando que se muestre el resultado esperado
```java
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
```
##### OpenThe
Esta clase se genera únicamente para abir la página sobre la cual ejecutara la automatización la cual llama a la clase RetoPage.
```java
public class OpenThe implements Task {
    private RetoPage retoPage;
    public static OpenThe page() {
        return Tasks.instrumented(OpenThe.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.browserOn(retoPage));
    }
}
```
##### RetoPage
Esta clase se encuentra en la carpeta userinterface y extiende la clase PageObject, con el fin de almacenar el URL de la página con la cual se va a interactuar y además los XPATHs de los Web elements requeridos para hacer login, no cree una nueva clase para los web elements de login debido a que estos se encuentran en la misma página.
```java
@DefaultUrl("https://serenity.is/demo/")
public class RetoPage extends PageObject {
  public static final Target USENAME = Target.the("Usuario").located(By.xpath("//*[@name='Username']"));
  public static final Target PASSWORD = Target.the("Contraseña").located(By.xpath("//*[@name='Password']"));
  public static final Target LBL_LOGIN_ERRONEO = Target.the("Mensaje de error logueo").located(By.xpath("//div[@class='toast-message']"));
}
```
##### Login
En el Task Login ingresamos el usuario, la contraseña y damos un enter para loguearnos, usando los targets definidos en la clase RetoPage.
```java
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
```
##### ValidarLogeo
En este Question validamos que se visualice el mensaje de error al ingresar datos erroneos, usando los targets definidos en la clase RetoPage.
```java
public class ValidarLogeo implements Question<Boolean> {

  public static ValidarLogeo erroneo() {
    return new ValidarLogeo();
  }

  @Override
  public Boolean answeredBy(Actor actor) {
    WebElement mensajeError = RetoPage.LBL_LOGIN_ERRONEO.resolveFor(actor);
    return mensajeError.isDisplayed();
  }
}
```
#### Escenario 2: Validación de información congruente
Para este escenario se validará que no permita ingresar información incongruente de fechas a la hora de programar reuniones variando fecha y hora.
```
Feature: Reto automatizacion validacion control de errores en datos
  Scenario Outline: Datos incongruentes
    Given J Abre la pagina y se loguea con el usuario <usuario> y la contrasena <contrasena>
    When J intenta programar una reunion con horarios incongruentes caso <caso>
    Then El visualiza mensaje de error

    Examples:
      |usuario|contrasena|caso|
      |admin  |serenity  |1   |
      |admin  |serenity  |2   |
```
Mantenemos la estrucura del feature usada en el escenario uno para usuario y contraseña, aunque no es necesario debido a que la validación a realizar requiere un logueo efectivo, pero de igual forma se puede usar ingresando información correcta en las credenciales de acceso y el cambio se refleja en escenario a realizar de la siguiente forma:
1. "1" representa el ingreso de una fecha inical mayor que la final
2. "2" representa el ingreso de una hora inicial mayor a la final en un mismo dia

Las clases OpenThe y login mantienen la misma funcionalidad expresada en el escenario 1, por ello y por el hecho que tienen la misma forma de enviar la información de logueo podrían estar en un mismo feature y realizar la ejecución individual por medio de tags.

##### IngresarAReuniones
Esta clase de la carpeta Task, se utiliza para ingresar a la página de Reuniones interactuando con el menú de la parte izquierda, utilizando los targets definidos en la clase RetoReunionesPage.
```java
public class IngresarAReuniones implements Task {
    public static IngresarAReuniones solicitarCreacion() {
        return Tasks.instrumented(IngresarAReuniones.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoReunionesPage.LBL_MEETING),
                Click.on(RetoReunionesPage.LBL_MEETINGS),
                Click.on(RetoReunionesPage.BTN_ADD_MEETING)
        );
    }
}
```
##### RetoReunionesPage
```java
public class RetoReunionesPage extends PageObject {
    public static final Target LBL_MEETING = Target.the("Desplegar opciones de reunion").located(By.xpath("//a[contains(@href,'#nav_menu')]/span[contains(text(),'Meeting')]"));
    public static final Target LBL_MEETINGS = Target.the("Seleccionar reunion").located(By.xpath("//span[contains(text(),'Meetings')]"));
    public static final Target BTN_ADD_MEETING = Target.the("Solicitar la creación de una reunion").located(By.xpath("//span[contains(text(),' New Meeting')]"));
    public static final Target LBL_MEETING_NAME = Target.the("NOMBRE DE LA REUNION").located(By.xpath("//div[@class='slick-cell l1 r1']/a"));
}
```
##### Programar
En esta clase de la carpeta Task, recibimos los datos generados en el stepDefinition, generamos fechas con los comandos LocalDate de java aúnque se podia dejar este dato quemado lo realice con estos comandos para mantener fechas actualizadas en caso de que se actualice el aplicativo a que solo permita fechas a partir del día actual y poder seleccionar la hora con la interacción EscogerValorLista de forma aletoria finalizando la automatización de forma exitosa, de acuerdo a si se envia la variable caso podremos ejecutar un escenario u otro en el cual usemos todos los datos recibidos o solo los contemplados por el escenario activado, teniendo en cuenta si se recibe la variable caso, aparte de esta interacción utilizamos SeleccionarUnidadCreada con la cual seleccionamos la unidad creada anteriormente.
```java
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
            Click.on(RetoNewMeetingPage.DIPLOY_LOCATION),
            new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_LOCATION),
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
```
##### EscogerValorLista
En esta interacción recibimos interactuamos con una lista tipo selecto donde tenemos dos opciones, enviar el indice del valor que queremos seleccionar o no enviarlo y que el sistema seleccione un valor aleatorio entre las opciones disponibles, para este escenario usaremos ambas opciones.
```java
public class EscogerValorLista implements Interaction {

  private final Target target;
  private int opcion;

  public EscogerValorLista(Target element) {
    this.target = element;
    this.opcion = 0;
  }
  public EscogerValorLista(Target element, int opcion) {
    this.target = element;
    this.opcion = opcion;
  }
  @Override
  public <T extends Actor> void performAs(T actor) {

    WebElement hour = target.resolveFor(actor);
    hour.click();
    String text = hour.toString().substring(10);
    List<WebElement> lista= hour.findElements(By.xpath(""+text+"/option"));
    Select listSelect = new Select(hour);
    if (opcion == 0) {
      opcion=(int)(Math.random()*lista.size());
    }
    listSelect.selectByIndex(opcion);
  }

  public static EscogerValorLista index(Target target1){
    return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1);
  }
  public static EscogerValorLista index(Target target1, int opcion){
    return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1, opcion);
  }
}
```
##### SeleccionarAleatorio
Esta clase de la carpeta interaction, nos permite seleccionar de forma aleatoria un valor de una lista que no es de tipo select.
```java
public class SeleccionarAleatorio implements Interaction{
  private final Target target;
  public SeleccionarAleatorio(Target target) {
    this.target = target;
  }
  @Override
  public <T extends Actor> void performAs(T actor) {
    WebElement listLocation = target.resolveFor(actor);
    List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
    options.get((int)(Math.random()*(options.size()))).click();
  }
  public static SeleccionarAleatorio on(Target target) {
    return Instrumented.instanceOf(SeleccionarAleatorio.class).withProperties(target);
  }
}
```

##### ValidarInformacion
Esta Question usa el target asociado al mensaje de error que se genera al ingresar datos incongruentes, por lo cual valida si este mensaje es visible luego de intentar generar guardar una programación de reunión con datos no consistentes. 
```java
public class ValidarInformacion implements Question<Boolean> {
  public static ValidarInformacion reunion() {
    return new ValidarInformacion();
  }
  @Override
  public Boolean answeredBy(Actor actor) {
    WebElement mensajeError = RetoNewMeetingPage.LBL_DATA_ERRONEA.resolveFor(actor);
    return mensajeError.isDisplayed();
  }
}
```
## Ejercicio practicó

### 1.	Se genera feature a partir del requerimiento.
El feature se genera lo más sencillo posible, y además aparte del usuario y la contraseña no se ingresa otro dato por medio de este debido a que el requerimiento indica que la data usada queda a consideración de la automatización, por lo cual para cada ejecución se está tomando data aleatoria, esto se permite dado que no hay una definición particular para las listas que se seleccionan en el transcurso de la automatización, es decir, si seleccionamos el valor "xyz" en la lista "Unidad" se habilitara el campo "z" de obligatorio diligenciamiento.
```
Feature: Reto automatizacion
  Scenario: Crear unidad de negocio y programacion
    Given J Abre la pagina y se loguea con el usuario admin y la contraseña serenity
    When El crea una unidad de negocio y programa una reunion con esta
    Then J valida la creacion de la reunion
```

### 2.	A partir del feature se genera el stepdefinition
```java
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
```
En las primeras cuatro líneas del stepdefinition se generan los datos a usar para los inputs, debido a que el requerimiento no indica que se deba validar con datos especificos, para ello se creo una clase GenerarData en la carpeta util, la cual se muestra a continuación: 
#### Clase GenerarData
En esta clase se genera un String de 10 caracteres combinando números y letras al azar.
```java
public class GenerarData {
    public GenerarData() {
    }
    String data;
    Random rnd = new Random();
    public String generarData(){
        data = "";
        for (int i = 0; i < 10; i++) {
            int y = rnd.nextInt(2);
            if(y==0){
                data +=(char)(rnd.nextInt(26)+65);
            }else {
                data +=(char)(rnd.nextInt(9)+48);
            }
        }
        return data;
    }
}
```
Luego de generar los datos aleatorios requeridos se inicializa el escenario e iniciamos con la definición del primer paso del escenario dentro del cual encontramos los tasks OpenThe.page() y Login.onThePage()
#### OpenThe
Esta clase se genera únicamente para abir la página sobre la cual ejecutara la automatización la cual llama a la clase RetoPage.
```java
public class OpenThe implements Task {
    private RetoPage retoPage;
    public static OpenThe page() {
        return Tasks.instrumented(OpenThe.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.browserOn(retoPage));
    }
}
```
#### RetoPage
Esta clase se encuentra en la carpeta userinterface y extiende la clase PageObject, con el fin de almacenar el URL de la página con la cual se va a interactuar y además los XPATHs de los Web elements requeridos para hacer login, no cree una nueva clase para los web elements de login debido a que estos se encuentran en la misma página.
```java
@DefaultUrl("https://serenity.is/demo/")
public class RetoPage extends PageObject {
  public static final Target USENAME = Target.the("Usuario").located(By.xpath("//*[@name='Username']"));
  public static final Target PASSWORD = Target.the("Contraseña").located(By.xpath("//*[@name='Password']"));
  public static final Target LBL_LOGIN_ERRONEO = Target.the("Mensaje de error logueo").located(By.xpath("//div[@class='toast-message']"));
}
```
#### Login
En el Task Login ingresamos el usuario, la contraseña y damos un enter para loguearnos, usando los targets definidos en la clase RetoPage.
```java
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
```
### En el When definimos las acciones requeridas para crear unidad de negocio y generar la reunión.

#### Ingresar
En esta clase de la carpeta Task, interactuamos con la pantalla de inicio y seleccionamos la opción Unidad de negocio en la barra izquierda con los targets definidos en la clase RetoOrganizaciónPage.
```java
public class Ingresar implements Task {
    public static Ingresar businessUnits() {
        return Tasks.instrumented(Ingresar.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoOrganizacionPage.LBL_ORGANIZATION),
                Click.on(RetoOrganizacionPage.LBL_BUSINESS_UNITS)
        );
    }
}
```
#### RetoOrganizacionPage
```java
public class RetoOrganizacionPage extends PageObject {
    public static final Target LBL_ORGANIZATION = Target.the("Desplegar opciones").located(By.xpath("//span[contains(text(),'Organization')]"));
    public static final Target LBL_BUSINESS_UNITS = Target.the("Seleccionar la opcion unidad de negocio").located(By.xpath("//span[contains(text(),'Business Units')]"));
    public static final Target BTN_INSERT_BUSINESS_UNIT = Target.the("Agregar unidad de negocio").located(By.xpath("//span[contains(text(),' New Business Unit')]"));
    public static final Target NAME_BUSINESS_UNIT = Target.the("Ingresar nombre de la unidad").located(By.xpath("//*[@name='Name']"));
    public static final Target PARENT_BUSINESS_UNIT = Target.the("Seleccionar parent unit").located(By.xpath("//span[@class='select2-chosen']"));
    public static final Target TEXT_FOR_BUSINESS_UNIT = Target.the("Buscar o seleccionar parent business unit").located(By.xpath("//li[contains(@class, 'select2-results-dept-0 select2-result select2-result-selectable')]"));//input[@class='select2-input']
    public static final Target BTN_SAVE = Target.the("Guardar business units").located(By.xpath("//span[contains(text(),' Save')]"));
}
```
#### Registrar
En esta clase ingresamos la información requerida para crear una unidad de negocio, como se mencionaba al inicio en el stepdefinition se genera el nombre de la unidad de forma aleatoria con la clase GenerarData y para la seleccion del campo tipo lista se creo la interacción SeleccionarAleatorio, para realizar este proceso con data aleatoria debido a que no hay una especificación para cada opción de la lista, por lo cual es indiferente la selección realizada, y realizando esta selección aleatoria podemos validar unidades al azar, teniendo mayor cobertura.
```java
public class Registrar implements Task {
    private String nombreUnidad;
    public Registrar(String nombre){
        this.nombreUnidad=nombre;
    }
    public static Registrar unidad(String name) {
        return Tasks.instrumented(Registrar.class, name);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoOrganizacionPage.BTN_INSERT_BUSINESS_UNIT),
                Enter.theValue(nombreUnidad).into(RetoOrganizacionPage.NAME_BUSINESS_UNIT),
                Click.on(RetoOrganizacionPage.PARENT_BUSINESS_UNIT),
                new SeleccionarAleatorio(RetoOrganizacionPage.TEXT_FOR_BUSINESS_UNIT),
                Click.on(RetoOrganizacionPage.BTN_SAVE)
        );
    }
}
```
#### SeleccionarAleatorio
Esta clase de la carpeta interaction, nos permite seleccionar de forma aleatoria un valor de una lista que no es de tipo select.
```java
public class SeleccionarAleatorio implements Interaction{
  private final Target target;
  public SeleccionarAleatorio(Target target) {
    this.target = target;
  }
  @Override
  public <T extends Actor> void performAs(T actor) {
    WebElement listLocation = target.resolveFor(actor);
    List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
    options.get((int)(Math.random()*(options.size()))).click();
  }
  public static SeleccionarAleatorio on(Target target) {
    return Instrumented.instanceOf(SeleccionarAleatorio.class).withProperties(target);
  }
}
```
#### IngresarAReuniones
Esta clase de la carpeta Task, se utiliza para ingresar a la página de Reuniones interactuando con el menú de la parte izquierda, utilizando los targets definidos en la clase RetoReunionesPage.
```java
public class IngresarAReuniones implements Task {
    public static IngresarAReuniones solicitarCreacion() {
        return Tasks.instrumented(IngresarAReuniones.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(RetoReunionesPage.LBL_MEETING),
                Click.on(RetoReunionesPage.LBL_MEETINGS),
                Click.on(RetoReunionesPage.BTN_ADD_MEETING)
        );
    }
}
```
#### RetoReunionesPage
```java
public class RetoReunionesPage extends PageObject {
    public static final Target LBL_MEETING = Target.the("Desplegar opciones de reunion").located(By.xpath("//a[contains(@href,'#nav_menu')]/span[contains(text(),'Meeting')]"));
    public static final Target LBL_MEETINGS = Target.the("Seleccionar reunion").located(By.xpath("//span[contains(text(),'Meetings')]"));
    public static final Target BTN_ADD_MEETING = Target.the("Solicitar la creación de una reunion").located(By.xpath("//span[contains(text(),' New Meeting')]"));
    public static final Target LBL_MEETING_NAME = Target.the("NOMBRE DE LA REUNION").located(By.xpath("//div[@class='slick-cell l1 r1']/a"));
}
```
#### Programar
En esta clase de la carpeta Task, recibimos los datos generados en el stepDefinition, generamos fechas con los comandos LocalDate de java aúnque se podia dejar este dato quemado lo realice con estos comandos para mantener fechas actualizadas en caso de que se actualice el aplicativo a que solo permita fechas a partir del día actual y poder seleccionar la hora con la interacción EscogerValorLista de forma aletoria finalizando la automatización de forma exitosa, de acuerdo a si se envia la variable caso podremos ejecutar un escenario u otro en el cual usemos todos los datos recibidos o solo los contemplados por el escenario activado, teniendo en cuenta si se recibe la variable caso, para este escenario no enviaremos la variable caso y se ejecutara la interacción SeleccionarUnidadCreada con la cual seleccionamos la unidad creada anteriormente.
```java
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
            Click.on(RetoNewMeetingPage.DIPLOY_LOCATION),
            new SeleccionarAleatorio(RetoNewMeetingPage.SELECT_LOCATION),
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
```
#### EscogerValorLista
En esta interacción recibimos el target definido en la clase RetoNewMeetingPage, le damos clic para habilitar las opciones y para hacer esta interación aleatoria, se crea una lista de WebElements para hallar encontrar cuantos elementos tiene, y luego con las opciones de la clase Math generamos un número aleatorio, que será el índice a usar de la lista para escoger este valor. 
```java
public class EscogerValorLista implements Interaction {

  private final Target target;
  private int opcion;

  public EscogerValorLista(Target element) {
    this.target = element;
    this.opcion = 0;
  }
  public EscogerValorLista(Target element, int opcion) {
    this.target = element;
    this.opcion = opcion;
  }
  @Override
  public <T extends Actor> void performAs(T actor) {

    WebElement hour = target.resolveFor(actor);
    hour.click();
    String text = hour.toString().substring(10);
    List<WebElement> lista= hour.findElements(By.xpath(""+text+"/option"));
    Select listSelect = new Select(hour);
    if (opcion == 0) {
      opcion=(int)(Math.random()*lista.size());
    }
    listSelect.selectByIndex(opcion);
  }

  public static EscogerValorLista index(Target target1){
    return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1);
  }
  public static EscogerValorLista index(Target target1, int opcion){
    return Instrumented.instanceOf(EscogerValorLista.class).withProperties(target1, opcion);
  }
}
```
#### SeleccionarUnidadCreada
Esta interacción recibimos el target y el nombre de la unidad de negocio, con el target obtenemos los valores en una lista de WebElements y vamos comparando el texto de cada WebElement con el nombre de la unidad de negocio recibida, se usa una comparación tipo contains, porque de acuerdo a la unidad seleccionada en la creación de la unidad de negocio el nombre de la misma puede variar respecto al valor ingresado, y como los nombres generados son casi unicos puede tenerse casi la certeza que ninguna otra unidad contendra los mismos caracteres, cuando le encotramos le damos clic a la opción que contenga dichos caracteres.
```java
public class SeleccionarUnidadCreada implements Interaction {
  private final Target target;
  private final String nombreUnidadDeNegocio;

  public SeleccionarUnidadCreada(Target element, String bussinessName) {
    this.target = element;
    this.nombreUnidadDeNegocio = bussinessName;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    WebElement listLocation = target.resolveFor(actor);
    List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
    for (WebElement i : options) {
      if (i.getText().contains(nombreUnidadDeNegocio)) {
        i.click();
        break;
      }
    }
  }

  public static SeleccionarUnidadCreada on(Target element, String bussinessName) {
    return Instrumented.instanceOf(SeleccionarUnidadCreada.class).withProperties(element, bussinessName);
  }
}
```
#### RetoNewMeetingPage
```java
public class RetoNewMeetingPage {
    public static final Target INPT_MEETING_NAME = Target.the("Ingresar nombre de la reunion").located(By.xpath("//*[@name='MeetingName']"));
    public static final Target DIPLOY_MEETING_TYPE = Target.the("Desplegar lista tipo de reunion").located(By.xpath("//*[@id='select2-chosen-6']"));
    public static final Target SELECT_MEETING_TYPE = Target.the("Seleccionar tipo de reunion").located(By.xpath("//div[@class='select2-result-label']"));
    public static final Target INPT_MEETING_NUMBER = Target.the("Ingresar numero de reunion").located(By.xpath("//*[@name='MeetingNumber']"));
    public static final Target INPT_START_DATE = Target.the("").located(By.xpath("//*[@name='StartDate']"));
    public static final Target SLCT_START_HOUR = Target.the("").located(By.xpath("//div[@class='field StartDate col-sm-6']//select"));
    public static final Target INPT_END_DATE = Target.the("").located(By.xpath("//*[@name='EndDate']"));
    public static final Target SLCT_END_HOUR = Target.the("").located(By.xpath("//div[@class='field EndDate col-sm-6']//select"));
    public static final Target DIPLOY_LOCATION = Target.the("").located(By.xpath("//*[@id='select2-chosen-7']"));
    public static final Target SELECT_LOCATION = Target.the("").located(By.xpath("//li[contains(@class,'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_UNIT = Target.the("Unit").located(By.xpath("//*[@id='select2-chosen-8']"));
    public static final Target SELECT_UNIT = Target.the("").located(By.xpath("//li[contains(@class,'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_ORGANIZED_BY = Target.the("Organized by").located(By.xpath("//*[@id='select2-chosen-9']"));
    public static final Target SELECT_ORGANIZED_BY = Target.the("").located(By.xpath("//li[contains(@class,'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_REPORTER = Target.the("Reporter").located(By.xpath("//*[@id='select2-chosen-10']"));
    public static final Target SELECT_REPORTER = Target.the("").located(By.xpath("//li[contains(@class, 'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target DIPLOY_ATTENDEE_LIST = Target.the("Atendee list").located(By.xpath("//*[@id='select2-chosen-12']"));
    public static final Target SELECT_ATTENDEE_LIST = Target.the("").located(By.xpath("//li[contains(@class, 'select2-results-dept-0 select2-result select2-result-selectable')]/div"));
    public static final Target BTN_SAVE = Target.the("Guardar programacion").located(By.xpath("//span[contains(text(),'Save')]"));
}
```
### Finalmente en el Then definimos la comprobación de la programación de la reunión.
#### Verificar
En esta clase de la carpeta question, validamos que se cree la reunión usando el nombre de reunión generado en el stepdefinition, antes de iniciar el proceso de leer los WebElements se realiza una pausa de 1000 ms debido a que no estaba encontrando los elementos y por ello para darle tiempo a mostrarse le asigne una pequeña pausa mientras carga la página, luego de esto se toma de la pagina una lista de WebElements y se compara su texto con el nombre de la reunión y en caso de encontrarlos devolvemos true y en caso contrario usa false.
```java
public class Verificar implements Question<Boolean> {
    private static String validador;
    public Verificar(String validador) {
        this.validador = validador;
    }
    public static Verificar creacionReunion(String validador) {
        return new Verificar(validador);
    }
    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement listLocation = RetoReunionesPage.LBL_MEETING_NAME.resolveFor(actor);
        List<WebElement> options = listLocation.findElements(By.xpath(listLocation.toString().substring(10)));
        for (WebElement i : options) {
            if (i.getText().equals(validador)){
                return true;
            }
        }
        return false;
    }
}
```
#### 
```java
```

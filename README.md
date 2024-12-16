# Batidos de frutas

Por favor, genera código para desarrollar esta app en java consola:

Queremos que crees una aplicación ligera para demostrar tus habilidades de desarrollo de
software.

La aplicación permitirá a un vendedor ambulante vender batidos de frutas y llevar un registro
de su inventario de ingredientes.

## Requerimientos

### El propósito de esta prueba es el siguiente:

1. Asegurarnos de que tienes una comprensión sólida de la plataforma de desarrollo, el
   lenguaje y las bibliotecas.
2. Ver cómo desarrollas un modelo orientado a objetos para la aplicación.
3. Ver cómo implementas algunas lógicas básicas de negocio.
4. Ver qué decisiones tomas cuando los requisitos están vagamente definidos.

### Especificaciones técnicas e instrucciones

1. Por favor, desarrolla una aplicación de consola usando Java.
2. Importante: Asegúrate de que el lenguaje de las variables y mensajes en el código
   fuente y/o aplicación estén en "Inglés".
3. No es necesario que los datos persistan cuando el usuario sale de la aplicación (no se
   requiere base de datos).
4. No es necesaria una interfaz de usuario, puede ejecutarse a través de la línea de
   comandos.
5. La aplicación debe ser construible y ejecutable.
6. Por favor, incluye tanto el código fuente como el código objeto en tu presentación.

### Detalles de las recetas de los batidos de frutas del vendedor

1. Para cada 100 ml de batido de frutas, la receta requiere 50 ml de frutas mezcladas, 30
   ml de hielo, 20 ml de leche condensada y 8 g de azúcar.
2. El vendedor ofrece 3 sabores de batidos de frutas: a. Fresa: Requiere 100 g de fresas
   por cada 100 ml de batido de fresas. b. Plátano: Requiere 120 g de plátanos por cada
   100 ml de batido de plátanos. c. Mango: Requiere 140 g de mango por cada 100 ml de
   batido de mango.

### Por favor, implementa las siguientes funciones básicas (en orden de prioridad)

1. Quemar con código el inventario inicial de ingredientes del vendedor.
2. Proporciona una función para listar el inventario actual de ingredientes del vendedor.
3. Permite al vendedor vender un batido y reducir el inventario en consecuencia.
4. Niega una venta cuando no haya suficientes ingredientes para hacer el batido.
5. Proporciona una advertencia cuando cualquier ingrediente esté por debajo del nivel
   requerido para hacer 4 batidos más.
6. Permite al vendedor vender un batido de frutas mezcladas (por ejemplo, plátano y
   fresa) y reducir el inventario en consecuencia.
7. Permite al vendedor vender 3 tamaños (pequeño, mediano y grande).
8. Proporciona los costos de cada ingrediente y establece los precios de cada batido para
   asegurarse de que el vendedor obtenga ganancias.
9. Permite ventas a lo largo del tiempo y proporciona un informe de ventas diarias.

## Compilación y ejecución

### Requisitos

- Java 23

### Compilación

mvn clean install

### Diagrama de clases

                         ┌────────────────┐
                         │     Main       │
                         └─────┬──────────┘
                               │
                               │ uses
                               │
                      ┌────────▼─────────────────────────┐
                      │   ShakeFactory                   │
                      ├──────────────────────────────────┤
                      │ - recipes: Map<String,           │
                      │   RecipeDefinition>              │
                      ├──────────────────────────────────┤
                      │ + ShakeFactory()                 │
                      │ + registerRecipe(type:           │
                      │   String, def:RecipeDefinition)  │
                      │ + createShake(type:String):Shake │
                      └──────────────────────────────────┘
                               │
                               │ has
                               │
                      ┌────────▼────────────────────────────────────┐
                      │  RecipeDefinition                           │
                      ├─────────────────────────────────────────────┤
                      │ - ingredientStrategy: IngredientStrategy    │
                      │ - pricingStrategy: PricingStrategy          │
                      ├─────────────────────────────────────────────┤
                      │ + RecipeDefinition(IngredientStrategy,      │
                      │                     PricingStrategy)        │
                      │ + getIngredientStrategy():IngredientStrategy│
                      │ + getPricingStrategy():PricingStrategy      │
                      └─────────────────────────────────────────────┘
                      ┌───────────────────────────────────────────────────────┐
                      │   IngredientStrategy                                  │
                      │       <<interface>>                                   │
                      ├───────────────────────────────────────────────────────┤
                      │ + getIngredientsNeeded(volume:int):Map<String,Double> │
                      └───────────────────────────────────────────────────────┘
             ┌─────────────────┼───────────────────────┐           ... (Otras estrategias)
             │  has            │                       │

┌────────────▼────────┐ ┌────▼──────────────┐ ┌────▼──────────────────────┐
│StrawberryIngredient │ │BananaIngredient │ │MixedStrawberryBananaIngred│
│Strategy │ │Strategy │ │ientStrategy │
└─────────────────────┘ └───────────────────┘ └───────────────────────────┘
(Implementan IngredientStrategy)                         (y otras Mixed...)

                               ┌──────────────────┐
                               │  PricingStrategy  │
                               │    <<interface>>  │
                               ├───────────────────┤
                               │ + getPrice(size:ShakeSize):double │
                               └───────────────────┘
             ┌──────────────────┼──────────────────┐         ... (Otras estrategias)
             │                  │                  │
             ▼                  ▼                  ▼

┌─────────────────────┐ ┌────────────────────┐ ┌─────────────────────────┐
│StrawberryPricing │ │BananaPricing │ │MixedStrawberryBananaPric│
│Strategy │ │Strategy │ │ingStrategy │
└─────────────────────┘ └────────────────────┘ └─────────────────────────┘
(Implementan PricingStrategy)

                               ┌───────────────────┐
                               │       Shake        │
                               ├───────────────────┤
                               │ - recipeDefinition:RecipeDefinition │
                               ├───────────────────┤
                               │ + getIngredientsNeeded(volume:int):Map<String,Double>│
                               │ + getPricingStrategy():PricingStrategy               │
                               └───────────────────┘

                                      ┌────────────────┐
                                      │   ShakeSize    │
                                      │    <<enum>>    │
                                      ├────────────────┤
                                      │ SMALL(200)      │
                                      │ MEDIUM(300)     │
                                      │ LARGE(500)      │
                                      └────────────────┘


          ┌───────────────────┐
          │     Inventory      │
          ├───────────────────┤
          │ - ingredients:Map<String,Double> │
          ├───────────────────┤
          │ + sellShake(shake:Shake,size:ShakeSize):boolean │
          │ + showLowInventoryWarning()                     │
          │ + listInventory()                               │
          └───────────────────┘


          ┌───────────────────┐
          │    SalesReport     │
          ├───────────────────┤
          │ - sales: List<Sale>│
          ├───────────────────┤
          │ + recordSale(type:String,size:String,price:double)│
          │ + printReport()                                   │
          └───┬────────────────┘
              │ contains
              ▼
           ┌────────────┐
           │    Sale     │
           ├────────────┤
           │- type:String│
           │- size:String│
           │- price:double│
           └────────────┘


        ┌─────────────────────┐
        │    BaseRecipeUtils  │
        ├─────────────────────┤
        │ + baseIngredientsForVolume(volume:int):Map<String,Double>│
        │ + sizeFactor(size:ShakeSize):double                      │
        └─────────────────────┘

Este diagrama muestra las clases principales, las interfaces y las relaciones entre ellas:

- Main usa ShakeFactory, Inventory, SalesReport y coordina la interacción.
- ShakeFactory contiene y gestiona RecipeDefinition.
- RecipeDefinition encapsula IngredientStrategy y PricingStrategy.
- Existen múltiples implementaciones concretas de IngredientStrategy y PricingStrategy para
  distintos tipos de batidos.
- Shake utiliza la RecipeDefinition para obtener ingredientes y precio.
- Inventory controla la disponibilidad de ingredientes.
- SalesReport guarda y muestra las ventas.
- BaseRecipeUtils ofrece métodos utilitarios.
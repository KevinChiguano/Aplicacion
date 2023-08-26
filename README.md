# Marvel: App Dispositivos Móviles

Proyecto final individual de dispositivos móviles.

App con funcionalidades de notificación, cámara, autenticación biometrica, localización, busqueda por voz, y consumo de de api Restful.

## Caracteristicas de la app

* Identificación, Registro y recuperación de clave de Usuarios: Creación de cuentas personalizadas y acceso mediante correo electrónico y contraseña segura.

* Fragmentos: Estructura modular de interfaz para navegación suave y adaptación a diferentes tamaños de pantalla.

* Autenticación Biométrica: Utilización de características biométricas para autenticación.

* Almacenamiento Local Inteligente: Uso de base de datos interna para almacenar datos localmente de los personajes de marvel.

* Recordatorios: Envío de recordatorios personalizados mediante notificaciones programadas.

* Interfaz Visual con Shimmer: Implementación de efectos visuales durante la carga para la autenticación biometrica.

* Uso de Swipe: Integración de SwipeRefreshLayout para actualizar contenido de los personajes de marvel al deslizar.

* Búsqueda Interactiva por Voz: Uso de búsqueda por voz para obtener resultados en internet.

* Exploración Detallada de Personajes de Marvel: Presentación  de información sobre personajes icónicos de Marvel, incluyendo nombres, descripciones e imagenes.

* Uso de ViewModel: Componente de arquitectura que se utiliza para separar la lógica de la interfaz de usuario de la gestión de datos y la lógica de negocio. Su principal propósito es almacenar y administrar datos que deben sobrevivir a los cambios de configuración. 

## Bibliotecas Usadas

### Importaciones del proyecto por defecto
* androidx.core:core-ktx:1.10.1: Librería principal de componentes de Android para Kotlin, que proporciona funciones y extensiones útiles para mejorar el desarrollo de aplicaciones Android.

* androidx.appcompat:appcompat:1.4.1: Biblioteca que permite usar las características más recientes de Android en versiones anteriores del sistema, brindando una experiencia coherente en diferentes dispositivos.

* com.google.android.material:material:1.9.0: Biblioteca de diseño de Material Components para Android, que proporciona estilos y componentes visuales modernos y coherentes siguiendo las directrices de diseño de Google.

* androidx.constraintlayout:constraintlayout:2.1.3: Biblioteca para crear diseños flexibles y complejos en Android utilizando el sistema de restricciones.

### Navegación entre Fragmentos
* androidx.navigation:navigation-fragment-ktx:2.5.2 y androidx.navigation:navigation-ui-ktx:2.5.2: Bibliotecas para la implementación de navegación entre fragmentos y vistas en la aplicación.

* androidx.legacy:legacy-support-v4:1.0.0: Librería de soporte para versiones anteriores de Android que proporciona utilidades y clases de compatibilidad.

### Lifecycle
* androidx.lifecycle:lifecycle-livedata-ktx:2.6.1 y androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1: Bibliotecas de arquitectura de componentes de Android para administrar ciclos de vida y datos de la aplicación.

### Pruebas unitarias
* junit:junit:4.13.2: Biblioteca de pruebas unitarias para Java.

* androidx.test.ext:junit:1.1.5 y androidx.test.espresso:espresso-core:3.5.1: Bibliotecas de pruebas para ejecutar y automatizar pruebas en Android.

### Uso de Gif
* pl.droidsonroids.gif:android-gif-drawable:1.2.23: Biblioteca para mostrar imágenes GIF en aplicaciones Android.

### Diseño Imagenes
* com.squareup.picasso:picasso:2.8: Biblioteca para la carga y visualización eficiente de imágenes en aplicaciones Android.

### Swipe
* androidx.swiperefreshlayout:swiperefreshlayout:1.1.0: Biblioteca para agregar la funcionalidad de actualización por deslizamiento en las vistas.

### Conecta a la api para tener una respuesta
* com.squareup.retrofit2:retrofit:2.9.0: Biblioteca para realizar solicitudes de red a través de APIs HTTP y convertir las respuestas en objetos Java/Kotlin.

### Convertidor de JSON a objeto
* com.squareup.retrofit2:converter-gson:2.9.0: Convertidor de Retrofit para trabajar con formato JSON utilizando la biblioteca Gson.

### Corrutinas
* org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3: Biblioteca para trabajar con corutinas en Android, permitiendo la programación asíncrona más concisa y legible.

### To use Kotlin annotation processing tool (kapt)
* androidx.room:room-runtime:2.5.0: Biblioteca de persistencia que simplifica el trabajo con bases de datos SQLite en Android.

### ViewModels
### Fragment
* androidx.datastore:datastore-preferences:1.0.0: Biblioteca para almacenar datos clave-valor de manera eficiente y segura.

### Activity
* com.google.android.gms:play-services-location:21.0.1: Biblioteca de Google Play Services para acceder y gestionar la ubicación del dispositivo.

### ViewModel
* androidx.fragment:fragment-ktx:1.6.1 y androidx.activity:activity-ktx:1.7.2: Bibliotecas para trabajar con fragmentos y actividades de manera más eficiente en Kotlin.

### LiveData
* androidx.lifecycle:lifecycle-livedata-ktx:2.6.1: Biblioteca para usar LiveData con Kotlin de manera más sencilla.

### Biometrico
* androidx.biometric:biometric:1.2.0-alpha05: Biblioteca para implementar autenticación biométrica de manera segura y sencilla.

### Shimmer
* com.facebook.shimmer:shimmer:0.1.0@aar: Biblioteca para agregar efectos visuales de brillo a las vistas durante la carga de contenido.

### FireBase
* implementation(platform("com.google.firebase:firebase-bom:32.2.2")): Plataforma de Firebase que administra automáticamente las versiones de las bibliotecas de Firebase utilizadas en el proyecto, garantizando la compatibilidad entre ellas.

* implementation("com.google.firebase:firebase-analytics-ktx"): Biblioteca de Firebase para integrar y gestionar análisis de aplicaciones. Proporciona información sobre el comportamiento y la interacción de los usuarios con la aplicación.

* implementation("com.google.firebase:firebase-auth-ktx"): Biblioteca de Firebase para implementar la autenticación de usuarios. Permite la creación de cuentas, inicio de sesión y autenticación segura mediante diferentes métodos.

## Resultados

![Registro](https://github.com/KevinChiguano/Aplicacion/assets/105686372/965cfa16-d3c6-4bcc-961d-be95e96e8458)
![Login](https://github.com/KevinChiguano/Aplicacion/assets/105686372/1e1dc32c-fb61-4af7-b158-139ad383d34d)

![Twitter1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/95993d96-d17c-451b-9b42-590eb8d5ffc3)
![Facebook1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/2b4810aa-05f7-4bd5-8bc2-b8a3fc402e1e)


![MenuPrincipal](https://github.com/KevinChiguano/Aplicacion/assets/105686372/f94d235c-deed-4772-bb4c-ea08ff6cf3c0)

![Biometrico1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/667b613d-64bc-4d51-bbc7-9c603a3a4400)

![MarvelInicio](https://github.com/KevinChiguano/Aplicacion/assets/105686372/77699fa4-4ecd-4bc4-804b-de8896cb6b74)
![MarvelBuscar](https://github.com/KevinChiguano/Aplicacion/assets/105686372/20c8733b-979b-4b67-bb52-08d63d12cafd)
![MarvelAgregarFavorito](https://github.com/KevinChiguano/Aplicacion/assets/105686372/d7b9fca0-37ee-48f8-af9b-2ff33138dacf)
![MarvelFavorito](https://github.com/KevinChiguano/Aplicacion/assets/105686372/048b7a9f-9400-414b-8f45-205517021c62)
![MarvelDetalle](https://github.com/KevinChiguano/Aplicacion/assets/105686372/d65d1f17-1a84-47e8-86f0-9588b7b139e5)

![Camara1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/26267fa1-242c-4cf6-98d6-736fd2decb29)
![Camara2](https://github.com/KevinChiguano/Aplicacion/assets/105686372/87350707-0556-4dda-a44f-b4e1df540809)
![Camara3](https://github.com/KevinChiguano/Aplicacion/assets/105686372/ab0b8050-d044-4416-94e6-5a306d85c171)

![Notificacion1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/1dabd78f-8591-42c0-aaf1-8a998807c0db)

![Busqueda1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/6c0aae97-f799-4914-a447-10de1005bd30)

![Busqueda2](https://github.com/KevinChiguano/Aplicacion/assets/105686372/f8955f26-7b8b-4f93-b652-f846601e8c1d)

![Localizacion1](https://github.com/KevinChiguano/Aplicacion/assets/105686372/88772e6e-154d-4c5c-8bbc-b667e823b9b6)
![Localizacion2](https://github.com/KevinChiguano/Aplicacion/assets/105686372/1972400b-bff1-4a87-9bbf-3a31e3db4381)

## Recomendaciones
* Usar el siguiente link para utilizar el proyecto https://github.com/KevinChiguano/Aplicacion.git

* Usar el branch main ya que este tiene la versión final del proyecto. 

* Utilizar el emulador de android con el API 33 para evitar problemas con la app.



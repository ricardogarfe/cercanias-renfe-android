# Mis Horarios Cercanías

[![Build Status](https://travis-ci.org/ricardogarfe/cercanias-renfe-android.png?branch=master)](https://travis-ci.org/ricardogarfe/cercanias-renfe-android)

*Aplicación Android* cliente para la visualización de los horarios de Cercanías Renfe en todo el territorio español adaptada para todo tipo de dispositivos Android.

## Aplicación Android

Esta aplicación provee de los horarios de cercanías de las redes españolas.

Selección de Núcleo urbano:

![Filtro Nucleo](https://raw.github.com/ricardogarfe/cercanias-renfe-android/development/screenshots/filtro_nucleos.png)

Selección de estación:

![Seleccionar Estaciones](https://raw.github.com/ricardogarfe/cercanias-renfe-android/development/screenshots/seleccionar_estaciones.png)

Próximos Trenes:

![Próximos Trenes](https://raw.github.com/ricardogarfe/cercanias-renfe-android/development/screenshots/proximos_trenes.png)

Selección de fecha:

![Selección de Fecha viaje](https://raw.github.com/ricardogarfe/cercanias-renfe-android/development/screenshots/seleccionar_fecha_viaje.png)

Información sobre las líneas disponibles en cada Núcleo urbano:

![Información Estaciones Línea/Nucleo](https://raw.github.com/ricardogarfe/cercanias-renfe-android/development/screenshots/informacion_lineas_nucleo.png)

Visualización en el mapa:

![Mapa Estaciones Línea/Nucleo](https://raw.github.com/ricardogarfe/cercanias-renfe-android/development/screenshots/estaciones_linea_nucleo_mapa.png)

### Tests

Para el apartado de Test he utilizado `Robotium` para crearlos. Por lo tanto se encuentra dentro del proyecto de test.

### Maven

Maven se ha añadido pensando en la integración continua y los tests. Además el proyecto está enlazado con [Travis-CI](https://travis-ci.org/ricardogarfe/cercanias-renfe-android) para que automáticamente se compruebe si funciona la compilación y/o tests como indica la imagen en la cabecera del documento sobre el estado del proyecto.

**No está preparado** para lanzar ni empaquetar el proyecto con las claves por lo que estas instalaciones son inservibles. Si se quiere generar una instalación se ha de crear a partir de Eclipse.

Para lanzar los test del proyecto utilizando maven se han de seguir los siguientes pasos:

* Descargar el proyecto [Maven-SDK-Deployer](https://github.com/mosabua/maven-android-sdk-deployer) e instalar las dependencias necesarias.

* Crear un emulador de test sin interfaz gráfica y lanzar los tests en el directorio de `android`:

```shell
$ android delete avd -n test
$ echo no | android create avd --force -n test -t 'Google Inc.:Google APIs:10' --abi armeabi
$ emulator -avd test -no-skin -no-audio -no-window &
$ mvn clean install -Pintegration-tests -Dandroid.device=test
```
El comando `-t 'Google Inc.:Google APIs:10'` es el nombre del SDK que se va a utilizar en la compilación de este proyecto.

Es recomendable crear una máquina virtual a través de eclipse (completa) por si ocurre algún error.

## Documentación

Esta aplicación es de tipo cliente/servidor por lo que he intentado ser fiel a esta estructura y sabiendo el funcionamiento se puede seguir fácilmente el proceso.

Se ha documentado el código de la mejor forma posible, **es totalmente mejorable**.

## Licencia GPLv3

Esta apliación se ha publicado bajo licencia [GPLv3](http://www.gnu.org/licenses/gpl.html) después de un proceso de relicenciamiento para pasar de una versión MIT con variaciones para convertirla en Copyleft a utilizar los pasor para utilizar una Copyleft Real **GPLv3**.

Se ha utilizado el manual de la FSF (Free Software Foundation) para mantener los archivos de licencias permisivas de un proyecto al licenciarse como GPLv3:

* [Maintaining Permissive-Licensed Files in a GPL-Licensed Project: Guidelines for Developers](http://www.softwarefreedom.org/resources/2007/gpl-non-gpl-collaboration.html).

Por lo tanto, este proyecto de Software Libre obliga a que todas las modificaciones que se hagan se publiquen como Software Libre por ello el carácter de la licencia Copyleft.

Esta aplicación ha sido generada a partir del proyecto de Jon Segador (puedes ver el fork en el histórico del repositorior) con la intención de mejorar la aplicación y aprender a programar en Android.

La información sobre la versión de Jon se encuentra en la wiki del proyecto:

* [Versión 1](../../wiki/Version-1---Jon-Segador)
* Tag [Versión 1](https://github.com/ricardogarfe/cercanias-renfe-android/tree/version-jonseg)

## A mejorar

* Configuración completa con maven para generar paquetes firmados oficiales.
* Incluir `Google Services` en vez de `Google Maps API`.
* Generar más tests de navegabilidad.
* Viajes Favoritos y fácil accesibilidad a los mismos.

## Autor

* Ricardo García Fernández <ricardogarfe@gmail.com>
* Twitter *[@ricardogarfe](http://twitter.com/ricardogarfe)*
* Linkedin *[http://es.linkedin.com/in/ricardogarfe](http://es.linkedin.com/in/ricardogarfe)*
* Blog *[http://mastersfwlurjc.blogspot.com.es/)*

## Document License

<a href="http://creativecommons.org/licenses/by/3.0/" rel="Creative Commons Attribution 3.0">![Foo](http://i.creativecommons.org/l/by/3.0/88x31.png)</a>

This work by Ricardo Gracía Fernández - ricardogarfe [at] gmail [dot] com is licensed under a [Creative Commons Attribution 3.0 Unported License](http://creativecommons.org/licenses/by/3.0/).


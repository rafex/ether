# Éter
Esta biblioteca que hice es para no depender de #springboot, para crear servicios REST super ligeros y quitarse tanto framework. Lo tengo funcionando en producción ya un año y sin problemas por eso es momento de abrirlo al mundo. Y nombrarlo, y elegí "éter" porqué es lo que aveces no vemos pero llena esos huecos para unir las piezas y funcione.


# Para compilar
Requiere tener instalado Maven 3.x (yo use 3.6.3) y la versión del JDK con la que inicialmente empecé fue 14, pero recordando que muchos aún siguen en la versión 8 hice pruebas con esa versión y no hay problemas. Sin embargo la versión publicada en el repositorio central de Maven es con Java versión 11.
```shell
mvn clean compile
```

# Descarga la dependencia del repositorio central de Maven

[Repositorio Central de Maven](https://search.maven.org/search?q=dev.rafex)

En su proyecto deberán agregar lo siguiente para usar cualquiera de los modulos (jwt, rest, jdbc, etc...)

```Shell
<parent>
	<groupId>dev.rafex.ether.parent</groupId>
	<artifactId>ether-parent</artifactId>
	<version>2.0.0-v20201122</version>
	<relativePath/>
</parent>
```

Y ya en dependencias podrán agregar el modulo deaseado

```Shell
<dependencies>
	<dependency>
		<groupId>dev.rafex.ether.rest</groupId>
		<artifactId>ether-rest</artifactId>
		<version>2.0.0-v20201122</version>
	</dependency>
</dependencies>
```

### license-maven-plugin ###

  * `license:check`: verify if some files miss license header. This goal is attached to the verify phase if declared in your pom.xml like above.
  * `license:format`: add the license header when missing. If a header is existing, it is updated to the new one.
  * `license:remove`: remove existing license header
# Vuelta Ciclista: Ejercicio 3

**Crea un programa que calcule clasificaciones y estadísticas usando consultas SQL con JOIN y funciones de agregación:**
**1. Clasificación general por puntos:**
**• Para cada ciclista, sumar sus puntos totales en la tabla participacion (columna puntos).**
**• Mostrar: nombre_ciclista - nombre_equipo - puntos_totales.**
**• Ordenar de mayor a menor puntos (TOP 10 si quieres).**
**2. Clasificación por equipos:**
**• Para cada equipo, sumar los puntos de todos sus ciclistas.**
**• Mostrar: nombre_equipo - pais - puntos_equipo.**
**• Ordenar por puntos de equipo descendente.**
**3. Ranking de etapas largas:**
**Mostrar las 3 etapas con mayor distancia_km, indicando numero, origen, destino, distancia_km y la fecha.**
**De forma opcional, calcula el promedio de distancia de todas las etapas y muestra solo las etapas por encima de esa media.**

Al ejecutar el programa, en consola va salir tres estadisticas, como: 

1. Clasificación general por puntos
2. Clasificación por equipos
3. Ranking de etapas largas

En primer caso, con una consulta de sql sumamos puntos totales por cada ciclista en tabla participacion (SUM(PARTICIPACION.PUNTOS)) y con Resultset recorremos los resultados, para mostrar en la pantalla.
En segundo caso, sumamos los puntos de todos sus ciclista para cada equipo. He utilizado una subconsulta dentro SELECT ((SELECT SUM(PUNTOS) FROM PARTICIPACION JOIN CICLISTA USING(ID_CICLISTA)) para sumar los puntos.
Y tercer caso es mostrar las 3 etapas con mayor distancia (FETCH FIRST 3 ROWS ONLY) y mostrar las etapas que son por encima del promedio de distancia de todas las etapas (WHERE DISTANCIA_KM > (SELECT AVG(DISTANCIA_KM) FROM ETAPA))

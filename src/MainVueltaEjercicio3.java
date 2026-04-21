import java.sql.*;

public class MainVueltaEjercicio3 {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "RIBERA",
                "ribera"
        )) {
            String sqlClasificacion = "SELECT CICLISTA.NOMBRE AS NOMBRE_CICLISTA, EQUIPO.NOMBRE AS NOMBRE_EQUIPO, SUM(PARTICIPACION.PUNTOS) AS PUNTOS_TOTALES\n" +
                    "FROM CICLISTA JOIN EQUIPO USING(ID_EQUIPO)\n" +
                    "JOIN PARTICIPACION USING(ID_CICLISTA)\n" +
                    "GROUP BY CICLISTA.NOMBRE, EQUIPO.NOMBRE\n" +
                    "ORDER BY PUNTOS_TOTALES DESC";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlClasificacion);
            System.out.println("1. Clasificacion general por puntos: ");
            while (resultSet.next()) {
                String nombre = resultSet.getString("NOMBRE_CICLISTA");
                String equipo = resultSet.getString("NOMBRE_EQUIPO");
                double puntosTotales = resultSet.getDouble("PUNTOS_TOTALES");

                System.out.println(nombre + " - " + equipo + " - " + puntosTotales);
            }

            String sqlPorEquipos = "SELECT NOMBRE, PAIS, (SELECT SUM(PUNTOS) FROM PARTICIPACION JOIN CICLISTA USING(ID_CICLISTA) WHERE ID_EQUIPO = e.ID_EQUIPO) AS PUNTOS_TOTAL\n" +
                    "FROM EQUIPO e\n" +
                    "ORDER BY PUNTOS_TOTAL DESC";
            Statement statementPorEquipos = connection.createStatement();
            ResultSet resultSetPorEquipos = statementPorEquipos.executeQuery(sqlPorEquipos);
            System.out.println("2. Clasificacion por equipos: ");
            while (resultSetPorEquipos.next()) {
                String nombre = resultSetPorEquipos.getString(1);
                String pais = resultSetPorEquipos.getString(2);
                double puntosTotales = resultSetPorEquipos.getDouble(3);

                System.out.println(nombre + " - " + pais + " - " + puntosTotales);
            }

            String sqlEtapa = "SELECT NUMERO, ORIGEN, DESTINO, DISTANCIA_KM, FECHA\n" +
                    "FROM ETAPA\n" +
                    "ORDER BY DISTANCIA_KM DESC\n" +
                    "FETCH FIRST 3 ROWS ONLY";
            Statement statementEtapa = connection.createStatement();
            ResultSet resultSetEtapa = statementEtapa.executeQuery(sqlEtapa);
            System.out.println("3. Clasificacion por etapa: ");
            while (resultSetEtapa.next()) {
                int numero = resultSetEtapa.getInt(1);
                String origen = resultSetEtapa.getString(2);
                String destino = resultSetEtapa.getString(3);
                int distanciaKm = resultSetEtapa.getInt(4);
                Date fecha =  resultSetEtapa.getDate(5);

                System.out.println(numero + " - " + origen + " - " + destino + " - " + distanciaKm + " - " + fecha);
            }

            String sqlPromedio = "SELECT NUMERO, ORIGEN, DESTINO, DISTANCIA_KM, FECHA\n" +
                    "FROM ETAPA\n" +
                    "WHERE DISTANCIA_KM > (SELECT AVG(DISTANCIA_KM) FROM ETAPA)\n" +
                    "ORDER BY DISTANCIA_KM DESC";
            Statement statementPromedio = connection.createStatement();
            ResultSet resultSetPromedio = statementPromedio.executeQuery(sqlPromedio);
            System.out.println("3.2. Clasificacion por promedio: ");
            while (resultSetPromedio.next()) {
                int numero = resultSetPromedio.getInt(1);
                String origen = resultSetPromedio.getString(2);
                String destino = resultSetPromedio.getString(3);
                int distanciaKm = resultSetPromedio.getInt(4);
                Date fecha = resultSetPromedio.getDate(5);

                System.out.println(numero + " - " + origen + " - " + destino + " - " + distanciaKm + " - " + fecha);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
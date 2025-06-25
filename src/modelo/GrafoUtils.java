package modelo;

import interfaces.INodo;
import java.util.List;
import java.util.Map;

public class GrafoUtils {
    public static <T> void mostrarCamino(List<INodo<T>> camino) {
        if (camino.isEmpty()) {
            System.out.println("No hay camino disponible.");
            return;
        }

        int distanciaTotal = 0;
        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i).getValor());
            if (i < camino.size() - 1) {
                System.out.print(" → ");
                INodo<T> origen = camino.get(i);
                INodo<T> destino = camino.get(i + 1);
                Map<INodo<T>, Integer> vecinos = origen.getVecinosConPesos();
                Integer peso = vecinos.get(destino);
                if (peso != null) {
                    distanciaTotal += peso;
                } else {
                    System.out.print(" (Advertencia: no se encontró peso entre " + origen.getValor() + " y " + destino.getValor() + ")");
                }
            }
        }
        System.out.println("\nDistancia total: " + distanciaTotal + " km");
    }
}
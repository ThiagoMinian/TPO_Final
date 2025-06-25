package test;

import modelo.Grafo;
import modelo.GrafoUtils;
import interfaces.INodo;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Grafo<String> mapaCiudades = new Grafo<>();

        mapaCiudades.agregarNodo("Buenos Aires");
        mapaCiudades.agregarNodo("Rosario");
        mapaCiudades.agregarNodo("Córdoba");
        mapaCiudades.agregarNodo("Santa Fe");
        mapaCiudades.agregarNodo("Mendoza");

        mapaCiudades.agregarArista("Buenos Aires", "Rosario", 300);
        mapaCiudades.agregarArista("Rosario", "Córdoba", 400);
        mapaCiudades.agregarArista("Córdoba", "Mendoza", 600);
        mapaCiudades.agregarArista("Santa Fe", "Córdoba", 200);
        mapaCiudades.agregarArista("Buenos Aires", "Santa Fe", 500);

        mapaCiudades.mostrarListaAdyacenciaPonderada();

        System.out.println("\nCamino más corto de Buenos Aires a Mendoza:");
        List<INodo<String>> camino = mapaCiudades.encontrarCaminoMasCortoDijkstra("Buenos Aires", "Mendoza");
        GrafoUtils.mostrarCamino(camino);
    }
}

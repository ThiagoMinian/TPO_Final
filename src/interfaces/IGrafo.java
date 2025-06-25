package interfaces;

import java.util.List;
import java.util.Map;

public interface IGrafo<T extends Comparable<T>> {
    void agregarNodo(T valor);
    void agregarArista(T origen, T destino, int peso);
    List<INodo<T>> encontrarCaminoMasCortoDijkstra(T inicio, T destino);
    void mostrarListaAdyacenciaPonderada();
    Map<T, INodo<T>> getMapaNodos();
    int getNumNodosActuales();
}

// interfaces/INodo.java
package interfaces;

import java.util.Map;

public interface INodo<T> {
    int getId();
    T getValor();
    void agregarVecino(INodo<T> vecino, int peso);
    Map<INodo<T>, Integer> getVecinosConPesos();
package interfaces;

import java.util.List;
import java.util.Map;

public interface IGrafo<T extends Comparable<T>> {
    void agregarNodo(T valor);
    void agregarArista(T origenValor, T destinoValor, int peso);
    List<INodo<T>> encontrarCaminoMasCortoDijkstra(T inicioValor, T destinoValor);
    void mostrarListaAdyacenciaPonderada();
    Map<T, INodo<T>> getMapaNodos();
    int getNumNodosActuales();
}

package modelo;

import interfaces.IGrafo;
import interfaces.INodo;
import java.util.*;

public class Grafo<T extends Comparable<T>> implements IGrafo<T> {
    private Map<T, INodo<T>> nodosPorValor;
    private List<INodo<T>> nodosPorId;
    private int nextId;

    public Grafo() {
        this.nodosPorValor = new HashMap<>();
        this.nodosPorId = new ArrayList<>();
        this.nextId = 0;
    }

    @Override
    public void agregarNodo(T valor) {
        if (!nodosPorValor.containsKey(valor)) {
            INodo<T> nuevoNodo = new Nodo<>(nextId++, valor);
            nodosPorValor.put(valor, nuevoNodo);
            nodosPorId.add(nuevoNodo);
        }
    }

    @Override
    public void agregarArista(T origenValor, T destinoValor, int peso) {
        INodo<T> nodoOrigen = nodosPorValor.get(origenValor);
        INodo<T> nodoDestino = nodosPorValor.get(destinoValor);

        if (nodoOrigen == null || nodoDestino == null) {
            System.err.println("Error: Nodos de origen o destino no encontrados al intentar agregar arista.");
            return;
        }

        nodoOrigen.agregarVecino(nodoDestino, peso);
    }

    @Override
    public List<INodo<T>> encontrarCaminoMasCortoDijkstra(T inicioValor, T destinoValor) {
        INodo<T> nodoInicio = nodosPorValor.get(inicioValor);
        INodo<T> nodoDestino = nodosPorValor.get(destinoValor);

        if (nodoInicio == null || nodoDestino == null) {
            System.err.println("Error: Valor de nodo de inicio o destino no encontrado en el grafo.");
            return Collections.emptyList();
        }

        int[] distancias = new int[nodosPorId.size()];
        int[] predecesores = new int[nodosPorId.size()];
        Set<Integer> visitados = new HashSet<>();
        PriorityQueue<ParDistanciaNodo> colaPrioridad = new PriorityQueue<>();

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesores, -1);

        int inicioId = nodoInicio.getId();
        int destinoId = nodoDestino.getId();

        distancias[inicioId] = 0;
        colaPrioridad.add(new ParDistanciaNodo(0, inicioId));

        while (!colaPrioridad.isEmpty()) {
            ParDistanciaNodo actual = colaPrioridad.poll();
            int nodoU = actual.nodoId;

            if (!visitados.add(nodoU)) continue;

            INodo<T> nodoActual = nodosPorId.get(nodoU);
            for (Map.Entry<INodo<T>, Integer> entradaVecino : nodoActual.getVecinosConPesos().entrySet()) {
                INodo<T> nodoV = entradaVecino.getKey();
                int v = nodoV.getId();
                int pesoArista = entradaVecino.getValue();

                if (!visitados.contains(v) && distancias[nodoU] + pesoArista < distancias[v]) {
                    distancias[v] = distancias[nodoU] + pesoArista;
                    predecesores[v] = nodoU;
                    colaPrioridad.add(new ParDistanciaNodo(distancias[v], v));
                }
            }
        }

        return reconstruirCamino(predecesores, inicioId, destinoId);
    }

    private List<INodo<T>> reconstruirCamino(int[] predecesores, int inicioId, int destinoId) {
        LinkedList<INodo<T>> camino = new LinkedList<>();
        int actualId = destinoId;

        while (actualId != -1 && actualId < nodosPorId.size()) {
            camino.addFirst(nodosPorId.get(actualId));
            if (actualId == inicioId) break;
            actualId = predecesores[actualId];
        }

        if (camino.isEmpty() || camino.getFirst().getId() != inicioId) {
            System.err.println("Error reconstruyendo camino: ID de nodo inválido");
            return Collections.emptyList();
        }

        return camino;
    }

    @Override
    public void mostrarListaAdyacenciaPonderada() {
        System.out.println("Lista de Adyacencia Ponderada:");
        for (INodo<T> nodo : nodosPorId) {
            System.out.print(nodo.getValor() + " (ID: " + nodo.getId() + "): ");
            Map<INodo<T>, Integer> vecinos = nodo.getVecinosConPesos();
            if (vecinos.isEmpty()) {
                System.out.print("[Sin vecinos]");
            } else {
                for (Map.Entry<INodo<T>, Integer> entry : vecinos.entrySet()) {
                    System.out.print(" -> " + entry.getKey().getValor() + " (peso: " + entry.getValue() + ")");
                }
            }
            System.out.println();
        }
    }

    @Override
    public Map<T, INodo<T>> getMapaNodos() {
        return nodosPorValor;
    }

    @Override
    public int getNumNodosActuales() {
        return nodosPorId.size();
    }

    // Clase interna usada por el algoritmo de Dijkstra
    private static class ParDistanciaNodo implements Comparable<ParDistanciaNodo> {
        int distancia;
        int nodoId;

        ParDistanciaNodo(int distancia, int nodoId) {
            this.distancia = distancia;
            this.nodoId = nodoId;
        }

        @Override
        public int compareTo(ParDistanciaNodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }
}

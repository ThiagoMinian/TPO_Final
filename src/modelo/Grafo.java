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

    public void agregarNodo(T valor) {
        if (!nodosPorValor.containsKey(valor)) {
            INodo<T> nuevoNodo = new Nodo<>(nextId++, valor);
            nodosPorValor.put(valor, nuevoNodo);
            nodosPorId.add(nuevoNodo);
        }
    }

    public void agregarArista(T origen, T destino, int peso) {
        INodo<T> nodoOrigen = nodosPorValor.get(origen);
        INodo<T> nodoDestino = nodosPorValor.get(destino);
        if (nodoOrigen != null && nodoDestino != null) {
            nodoOrigen.agregarVecino(nodoDestino, peso);
        } else {
            System.err.println("Error: Nodos de origen o destino no encontrados al intentar agregar arista.");
        }
        nodoDestino.agregarVecino(nodoOrigen peso);
    }

    public List<INodo<T>> encontrarCaminoMasCortoDijkstra(T inicio, T destino) {
        if (!nodosPorValor.containsKey(inicio) || !nodosPorValor.containsKey(destino)) {
            System.err.println("Error: Valor de nodo de inicio o destino no encontrado en el grafo.");
            return Collections.emptyList();
        }

        int numNodos = nodosPorId.size();
        int[] distancias = new int[numNodos];
        int[] predecesores = new int[numNodos];
        Set<Integer> visitados = new HashSet<>();
        PriorityQueue<ParDistanciaNodo> colaPrioridad = new PriorityQueue<>();

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesores, -1);

        INodo<T> nodoInicio = nodosPorValor.get(inicio);
        distancias[nodoInicio.getId()] = 0;
        colaPrioridad.add(new ParDistanciaNodo(nodoInicio.getId(), 0));

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
                    colaPrioridad.add(new ParDistanciaNodo(v, distancias[v]));
                }
            }
        }

        return reconstruirCamino(predecesores, distancias, nodoInicio.getId(), nodosPorValor.get(destino).getId());
    }

    private List<INodo<T>> reconstruirCamino(int[] predecesores, int[] distancias, int inicioId, int destinoId) {
        LinkedList<INodo<T>> camino = new LinkedList<>();
        int actualId = destinoId;
        while (actualId != -1) {
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

    public void mostrarListaAdyacenciaPonderada() {
        System.out.println("Lista de Adyacencia Ponderada:");
        for (INodo<T> nodo : nodosPorValor.values()) {
            System.out.print(nodo.getValor() + " -> ");
            Map<INodo<T>, Integer> vecinos = nodo.getVecinosConPesos();
            if (vecinos.isEmpty()) {
                System.out.println("[Sin vecinos]");
            } else {
                for (Map.Entry<INodo<T>, Integer> entry : vecinos.entrySet()) {
                    System.out.print(entry.getKey().getValor() + " (" + entry.getValue() + ") ");
                }
                System.out.println();
            }
        }
    }

    public Map<T, INodo<T>> getMapaNodos() {
        return nodosPorValor;
    }

    public int getNumNodosActuales() {
        return nodosPorValor.size();
    }

    private static class ParDistanciaNodo implements Comparable<ParDistanciaNodo> {
        int nodoId;
        int distancia;

        public ParDistanciaNodo(int nodoId, int distancia) {
            this.nodoId = nodoId;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(ParDistanciaNodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }
}
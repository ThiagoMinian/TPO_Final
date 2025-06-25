package modelo;

public class ParDistanciaNodo implements Comparable<ParDistanciaNodo> {
    public int distancia;
    public int nodoId;

    public ParDistanciaNodo(int distancia, int nodoId) {
        this.distancia = distancia;
        this.nodoId = nodoId;
    }

    @Override
    public int compareTo(ParDistanciaNodo otro) {
        return Integer.compare(this.distancia, otro.distancia);
    }
}

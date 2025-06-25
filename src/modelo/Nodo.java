package modelo;

import interfaces.INodo;
import java.util.HashMap;
import java.util.Map;

public class Nodo<T> implements INodo<T> {
    private int id;
    private T valor;
    private Map<INodo<T>, Integer> vecinosConPesos;

    public Nodo(int id, T valor) {
        this.id = id;
        this.valor = valor;
        this.vecinosConPesos = new HashMap<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public T getValor() {
        return valor;
    }

    @Override
    public Map<INodo<T>, Integer> getVecinosConPesos() {
        return vecinosConPesos;
    }

    @Override
    public void agregarVecino(INodo<T> vecino, int peso) {
        vecinosConPesos.put(vecino, peso);
    }

    @Override
    public String toString() {
        return "Nodo [id=" + id + ", valor=" + valor + "]";
    }
}

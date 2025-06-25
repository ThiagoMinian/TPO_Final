package interfaces;

import java.util.Map;

public interface INodo<T> {
    int getId();
    T getValor();
    void agregarVecino(INodo<T> vecino, int peso);
    Map<INodo<T>, Integer> getVecinosConPesos();
}

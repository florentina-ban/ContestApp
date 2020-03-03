package repository;

import java.util.ArrayList;
import java.util.Collection;

public interface Repo<E> {

    public void adauga(E elem);
    public void sterge(Integer id);
    public E cauta(int id);
    public Collection<E> getAll();
    public void modifica(E elem);
    public int getSize();
}
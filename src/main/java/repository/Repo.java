package repository;

import myException.RepoException;

import java.util.Collection;

public interface Repo<E> {

    //public int adauga(E elem) throws RepoException;
    //public void sterge(Integer id);
    public E findOne(int id);
    public Collection<E> findAll();
    //public void modifica(E elem);
    public int getSize();
}

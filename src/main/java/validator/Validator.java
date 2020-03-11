package validator;

import myException.RepoException;

public interface Validator<E> {
    public void valideaza(E elem) throws RepoException;
}

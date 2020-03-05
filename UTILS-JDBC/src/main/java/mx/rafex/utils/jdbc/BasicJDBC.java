package mx.rafex.utils.jdbc;

import java.util.List;

public interface BasicJDBC<T> {

    T create(T entity);

    List<T> listAll();

    T find(Integer identificador);

    void delete(T entity);

    void update(T entity);

}

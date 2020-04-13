package mx.rafex.utils.interfaces;

import java.util.List;
import java.util.logging.Logger;

public interface InterfaceDao<T> {

    Logger LOGGER = Logger.getLogger(InterfaceDao.class.getName());

    T create(T entity);

    List<T> listAll();

    T find(Integer identificador);

    void delete(T entity);

    void update(T entity);
}

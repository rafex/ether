package mx.rafex.utils.jdbc;

import java.util.List;
import java.util.logging.Logger;

public interface BasicJDBC<EntityJDBC> {

    Logger LOGGER = Logger.getLogger(BasicJDBC.class.getName());

    EntityJDBC create(EntityJDBC entity);

    List<EntityJDBC> listAll();

    EntityJDBC find(Integer identificador);

    void delete(EntityJDBC entity);

    void update(EntityJDBC entity);

}

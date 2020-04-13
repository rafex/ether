package mx.rafex.utils.jdbc;

import java.util.List;

public interface BasicJDBC<EntityJDBC> {

    EntityJDBC create(EntityJDBC entity);

    List<EntityJDBC> listAll();

    EntityJDBC find(Integer identificador);

    void delete(EntityJDBC entity);

    void update(EntityJDBC entity);

}

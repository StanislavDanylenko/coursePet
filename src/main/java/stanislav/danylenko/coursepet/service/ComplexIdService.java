package stanislav.danylenko.coursepet.service;

public interface ComplexIdService<T, ID> extends GenericService<T> {

    T find(ID id);

    void delete(ID id);

}

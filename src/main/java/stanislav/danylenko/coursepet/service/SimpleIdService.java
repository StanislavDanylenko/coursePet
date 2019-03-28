package stanislav.danylenko.coursepet.service;

public interface SimpleIdService<T> extends GenericService<T> {

    T find(Long id);

    void delete(Long id);

    void delete(T item);

}

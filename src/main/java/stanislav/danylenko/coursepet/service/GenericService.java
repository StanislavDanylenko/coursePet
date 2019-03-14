package stanislav.danylenko.coursepet.service;

import org.springframework.stereotype.Component;

@Component
public interface GenericService<T> {

    T save(T item);

    void delete(T item);

    T update(T item);

    T find(Long id);

    Iterable<T> findAll();

    void delete(Long id);

}

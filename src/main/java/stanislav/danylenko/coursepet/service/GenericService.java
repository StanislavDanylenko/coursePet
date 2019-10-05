package stanislav.danylenko.coursepet.service;

import org.springframework.stereotype.Component;

@Component
public interface GenericService<T> {

    T save(T item);

    T update(T item);

    Iterable<T> findAll();

    T find(Long id);

    void delete(Long id);

    void delete(T item);

}

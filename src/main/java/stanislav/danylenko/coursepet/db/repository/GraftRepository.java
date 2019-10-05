package stanislav.danylenko.coursepet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanislav.danylenko.coursepet.db.entity.Graft;

@Repository
public interface GraftRepository extends JpaRepository<Graft, Long> {

    Graft findByName(String name);

}

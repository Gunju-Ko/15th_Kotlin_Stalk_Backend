package slipp.stalk.infra.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import slipp.stalk.domain.Member;
import slipp.stalk.domain.RelationShip;
import slipp.stalk.domain.RelationShipKey;

import java.util.List;

public interface RelationRepository extends JpaRepository<RelationShip, RelationShipKey> {

    @Query("select r from RelationShip r where r.id.from = ?1 and r.relationShipType = ?2")
    List<RelationShip> findByFromAndRelationShipType(Member from, RelationShip.RelationShipType type);
}

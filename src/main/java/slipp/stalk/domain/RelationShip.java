package slipp.stalk.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
public class RelationShip {

    @Id
    private RelationShipKey id;
    @Enumerated(EnumType.STRING)
    private RelationShipType relationShipType;

    private RelationShip() {
    }

    private RelationShip(Member from, Member to, RelationShipType relationShipType) {
        this.id = new RelationShipKey(from, to);
        this.relationShipType = Objects.requireNonNull(relationShipType);
    }

    private RelationShip createFriendShip(Member from, Member to) {
        return new RelationShip(from, to, RelationShipType.FRIENDSHIP);
    }

    public enum RelationShipType {
        FRIENDSHIP
    }
}
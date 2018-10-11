package slipp.stalk.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RelationShipKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "from_member_id", foreignKey = @ForeignKey(name = "fk_relation_ship_from_member_id"))
    private Member from;
    @ManyToOne
    @JoinColumn(name = "to_member_id", foreignKey = @ForeignKey(name = "fk_relation_ship_to_member_id"))
    private Member to;

    private RelationShipKey() {
    }

    public RelationShipKey(Member from, Member to) {
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
    }

    public Member getFrom() {
        return from;
    }

    public Member getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        RelationShipKey that = (RelationShipKey) o;
        return new EqualsBuilder()
            .append(from, that.from)
            .append(to, that.to)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(from)
            .append(to)
            .toHashCode();
    }
}

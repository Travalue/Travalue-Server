package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import com.deploy.Travalue.travel.domain.Travel;
import com.deploy.Travalue.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeTravel extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Travel travel;

    private LikeTravel(final User user, final Travel travel) {
        this.user = user;
        this.travel = travel;
    }

    public static LikeTravel newInstance(final User user, final Travel travel) {
        return new LikeTravel(user, travel);
    }
}

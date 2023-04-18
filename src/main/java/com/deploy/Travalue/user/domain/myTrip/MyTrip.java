package com.deploy.Travalue.user.domain.myTrip;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import com.deploy.Travalue.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyTrip extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(length = 10, nullable = false)
    private String emoji;

    @Column(nullable = false)
    private String travelTitle;

    private MyTrip(final User user, final String emoji, final String travelTitle) {
        this.user = user;
        this.emoji = emoji;
        this.travelTitle = travelTitle;
    }

    public static MyTrip newInstance(final User user, final String emoji, final String travelTitle) {
        return new MyTrip(user, emoji, travelTitle);
    }
}

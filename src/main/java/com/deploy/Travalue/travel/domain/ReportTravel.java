package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import com.deploy.Travalue.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportTravel extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_travel")
    private Travel reportedTravel;

    @Column(nullable = false)
    private String reason;

    private ReportTravel(final User user, final Travel reportedTravel, final String reason) {
        this.user = user;
        this.reportedTravel = reportedTravel;
        this.reason = reason;
    }

    public static ReportTravel newInstance(final User user, final Travel reportedTravel, final String reason) {
        return new ReportTravel(user, reportedTravel, reason);
    }
}

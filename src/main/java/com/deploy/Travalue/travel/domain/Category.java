package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
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
public class Category extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subject;

    @Column
    private String region;

    @Column(nullable = false, length = 2048)
    private String thumbnail;

    private Category(final User user, final String title, final String thumbnail, final String subject, final String region) {
        this.user = user;
        this.title = title;
        this.thumbnail = thumbnail;
        this.subject = subject;
        this.region = region;
    }

    public static Category newInstance(final User user, final String title, final String thumbnail, final String subject, final  String region) {
        return new Category(user, title, thumbnail, subject, region);
    }
}

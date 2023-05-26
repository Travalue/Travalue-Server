package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import com.deploy.Travalue.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Travel extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(nullable = false, length = 2048)
    private String thumbnail;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subTitle;

    private String region;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int viewCount;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean isPublic;

    public void increaseViewCount() {
        int currentViewCount = getViewCount();
        setViewCount(++currentViewCount);
    }
}

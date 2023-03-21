package com.deploy.Travalue.domain.travel;

import com.deploy.Travalue.domain.common.AuditingTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.deploy.Travalue.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private final List<Travel> travelList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String subject;
}

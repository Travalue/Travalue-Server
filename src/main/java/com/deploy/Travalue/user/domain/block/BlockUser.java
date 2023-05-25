package com.deploy.Travalue.user.domain.block;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import com.deploy.Travalue.user.domain.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockUser extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_user")
    private User blockUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_user")
    private User blockedUser;

    @Builder
    public BlockUser(User blockUser, User blockedUser) {
        this.blockUser = blockUser;
        this.blockedUser = blockedUser;
    }
}

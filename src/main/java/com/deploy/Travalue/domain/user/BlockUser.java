package com.deploy.Travalue.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

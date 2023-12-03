package com.jobf.finder.adapters.member.jpa.entity;

import com.jobf.finder.common.entity.AbstractEntity;
import com.jobf.finder.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames={"email"}))
public class MemberEntity extends AbstractEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member toModel() {
        return Member.builder().id(super.getId()).email(email).password(password).build();
    }
}

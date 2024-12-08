package com.example.projectboard.member;

import com.example.projectboard.BaseEntity;
import com.example.projectboard.support.annotation.jacoco.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Where(clause = "deleted = 'N'")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private final MemberRole memberRole = MemberRole.USER;

  @OneToOne(mappedBy = "member", orphanRemoval = true, cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE})
  private Level level;

  protected Member() {
  }

  public Member(Long id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.level = new Level(this, 0, 0);
  }

  public static Member of(String name, String email, String password) {
    return new Member(null, name, email, password);
  }

  public static Member of(Long id, String name, String email, String password) {
    return new Member(id, name, email, password);
  }

  public void encryptPassword(String password) {
    this.password = password;
  }

  public void modify(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public MemberRole getMemberRole() {
    return memberRole;
  }

  public Level getLevel() {
    return level;
  }

  @Generated
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Member)) {
      return false;
    }
    Member member = (Member) o;
    return Objects.equals(getId(), member.getId());
  }

  @Generated
  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  public static MemberBuilder builder() {
    return new MemberBuilder();
  }

  public static class MemberBuilder {

    private String name;
    private String email;
    private String password;

    MemberBuilder() {
    }

    public MemberBuilder name(String name) {
      this.name = name;
      return this;
    }

    public MemberBuilder email(String email) {
      this.email = email;
      return this;
    }

    public MemberBuilder password(String password) {
      this.password = password;
      return this;
    }

    public Member build() {
      return Member.of(name, email, password);
    }
  }
}

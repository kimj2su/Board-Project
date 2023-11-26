package com.example.projectboard.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    @EntityGraph(attributePaths = {"level"})
    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);
}

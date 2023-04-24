package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);

    Optional<Member> findByUsername(String username);
}

package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    // find 키워드를 사용하면 한개의 Entity만 가져오고 By 뒤에 키워드를 컬럼으로 조회한다
    Optional<User> findByName(String name);

    boolean existsByName(String name);
}

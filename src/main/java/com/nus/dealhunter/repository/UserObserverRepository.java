package com.nus.dealhunter.repository;


import com.nus.dealhunter.observer.UserObserver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserObserverRepository extends JpaRepository<UserObserver, Long> {

    List<UserObserver> findByUser_id(Long user_id);

    List<UserObserver> findByProduct_id(Long product_id);

    Boolean existsByUser_id(Long user_id);
}

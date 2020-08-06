package com.ngoctuan.domain.repository;

import com.ngoctuan.domain.entity.Guest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {
    Guest findById(long guestId);
}

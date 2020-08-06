package com.ngoctuan.domain.repository;

import com.ngoctuan.domain.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Room findByRoomNumber(String number);
}

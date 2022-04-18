package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class RoomId {

    @NonNull String roomId;

    public static Validation<String, RoomId> validate(String roomId){
        return Validation.valid(new RoomId(roomId));
    }

    public static RoomId unsafe(String roomId){
        return new RoomId(roomId);
    }


}

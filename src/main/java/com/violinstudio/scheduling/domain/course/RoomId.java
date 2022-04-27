package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class RoomId {

    @NonNull String roomId;

    public static Validation<String, RoomId> validate(String roomId){

        if (roomId.isEmpty())
            return invalid("Invalid address");

        return valid(new RoomId(roomId));
    }

    public static RoomId unsafe(String roomId){
        return new RoomId(roomId);
    }


}

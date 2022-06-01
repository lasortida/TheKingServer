package org.example.TheKing;

public class Reply {
    String idOfRoom;
    int numberOfWeek;
    int usersCount;
    boolean isGameStarted;
    boolean error;
    User user;

    private Reply(Room room, int userCode){
        if (room.users.get(userCode) == null){
            error = true;
        }
        else{
            error = false;
            idOfRoom = room.idOfRoom;
            numberOfWeek = room.numberOfWeek;
            usersCount = room.usersCount;
            isGameStarted = room.isGameStarted;
            user = room.users.get(userCode);
        }
    }

    public static Reply getFromRoom(Room room, int userCode){
        Reply reply = new Reply(room, userCode);
        return reply;
    }
}

package org.example.TheKing;

import java.util.ArrayList;

public class Room {
    String idOfRoom;
    ArrayList<User> users;
    boolean isGameStarted;

    int numberOfWeek;

    public Room(String idOfRoom){
        this.idOfRoom = idOfRoom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room){
            Room room = (Room) obj;
            if (room.idOfRoom.equals(this.idOfRoom)){
                return true;
            }
        }
        return false;
    }

    public void deleteUser(int userCode){
        users.set(userCode, null);
    }

    public int addUser(){
        if (users.contains(null)){
            int userCode = users.indexOf(null);
            users.set(userCode, new User(userCode));
            return userCode;
        }
        else{
            int userCode = users.size();
            users.add(new User(userCode));
            return userCode;
        }
    }
}

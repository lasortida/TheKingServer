package org.example.TheKing;

import java.util.ArrayList;

public class GameServer {
    ArrayList<Room> rooms;

    public GameServer(){
        rooms = new ArrayList<>();
        createRoom();
    }

    public void createRoom(){
        String idOfRoom = formId();
        Room room = new Room(idOfRoom);
        while (rooms.contains(room)){
            idOfRoom = formId();
            room = new Room(idOfRoom);
        }
        rooms.add(room);
    }

    public String formId(){
        StringBuilder builder = new StringBuilder();
        char[] words = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < 6; ++i){
            if ((int)(Math.random() * 2) == 0){
                builder.append(words[(int)(Math.random() * words.length)]);
            }
            else{
                builder.append(numbers[(int)(Math.random() * numbers.length)]);
            }
        }
        return builder.toString();
    }

    public void deleteUser(String idOfRoom, int userCode){
        int index = rooms.indexOf(new Room(idOfRoom));
        Room room = rooms.get(index);
        room.deleteUser(userCode);
        rooms.set(index, room);
    }

    public int addUser(String idOfRoom){
        int index = rooms.indexOf(new Room(idOfRoom));
        Room room = rooms.get(index);
        int userCode = room.addUser();
        rooms.set(index, room);
        return userCode;
    }

    public String getAvailableRoom(){
        Room room = rooms.get(rooms.size() - 1);
        if (room.isGameStarted) {
            createRoom();
            room = rooms.get(rooms.size() - 1);
        }
        return room.idOfRoom;
    }
}

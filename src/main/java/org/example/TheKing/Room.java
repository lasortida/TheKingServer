package org.example.TheKing;

import java.util.ArrayList;

public class Room {
    Storage storage;
    String idOfRoom;
    int secondsReminder;
    ArrayList<User> users;
    boolean isGameStarted;

    int numberOfWeek;

    public Room(String idOfRoom){
        this.idOfRoom = idOfRoom;
        users = new ArrayList<>();
        storage = new Storage();
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
        User user = users.get(userCode);
        Country country = user.country;
        storage.countries[country.id] = country;
        users.set(userCode, null);
    }

    public int addUser(){
        if (users.contains(null)){
            int userCode = users.indexOf(null);
            users.set(userCode, new User(userCode));
            setCountryToUser(userCode);
            return userCode;
        }
        else{
            int userCode = users.size();
            users.add(new User(userCode));
            setCountryToUser(userCode);
            if (users.size() == 2){
                new Thread(){
                    @Override
                    public void run() {
                        secondsReminder = 15;
                        while(secondsReminder > 0){
                            try {
                                Thread.sleep(1000);
                                secondsReminder--;
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                        isGameStarted = true;
                    }
                }.start();
            }
            return userCode;
        }
    }

    public void setCountryToUser(int userCode){
        int value = (int)(Math.random() * storage.countries.length);
        while (storage.countries[value] == null){
            value = (int)(Math.random() * storage.countries.length);
        }
        User user = users.get(userCode);
        user.country = storage.countries[value];
        storage.countries[value] = null;
        users.set(userCode, user);
    }

    public int getSecondsReminder(){
        return secondsReminder;
    }
}

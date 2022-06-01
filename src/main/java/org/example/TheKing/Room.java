package org.example.TheKing;

import java.util.ArrayList;

public class Room {
    Storage storage;
    String idOfRoom;
    int secondsReminder;
    public boolean isTimerStarted;
    int usersCount;
    boolean isGameStarted;
    boolean permission = true;

    ArrayList<User> users;
    int numberOfWeek;

    Thread timer = new Thread(){
        @Override
        public void run() {
            while (true){
                while (secondsReminder > 0 && permission){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    secondsReminder--;
                }
                if (secondsReminder == 0 && permission){
                    isGameStarted = true;
                    startGame();
                }
            }
        }
    };

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
        usersCount--;
        if (isTimerStarted && !isGameStarted){
            if (usersCount < 2){
                isTimerStarted = false;
                permission = false;
            }
            else{
                secondsReminder = 15;
            }
        }
    }

    public int addUser(){
        usersCount++;
        if (usersCount == 2){
            isTimerStarted = true;
            if (secondsReminder == 0){
                secondsReminder = 15;
                timer.start();
            }
            else{
                secondsReminder = 15;
                permission = true;
            }
        }
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

    public void startGame(){
        numberOfWeek = 1;
    }
}

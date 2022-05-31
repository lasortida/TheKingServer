package org.example.TheKing;

public class User {
    int userCode;

    Country country;

    public User(int userCode){
        this.userCode = userCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User u = (User) obj;
            if (u.userCode == this.userCode){
                return true;
            }
        }
        return false;
    }
}

package org.example;

import com.google.gson.Gson;
import org.example.TheKing.GameServer;
import org.example.TheKing.Reply;
import org.example.TheKing.Room;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.Media;

@Controller
public class TheKingController {
    GameServer gameServer = new GameServer();

    @GetMapping
    public String main() {
        return "index";
    }

    @GetMapping(value = "/theking", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getRoomId() {
        String idOfRoom = gameServer.getAvailableRoom();
        JSONObject object = new JSONObject();
        object.put("id", idOfRoom);
        return object.toJSONString();
    }

    @GetMapping(value = "/theking/room", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getUserCode(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code", required = false, defaultValue = "-1") int user
    ) {
        JSONObject object = new JSONObject();
        if (user == -1){
            if (gameServer.isRightId(idOfRoom)){
                int userCode = gameServer.addUser(idOfRoom);
                if (userCode != -1){
                    object.put("userCode", userCode);
                    object.put("error", false);
                    return object.toJSONString();
                }
                else{
                    object.put("error", true);
                    return object.toJSONString();
                }
            }
            object.put("error", true);
            return object.toJSONString();
        }
        else{
            if (gameServer.isRightIdAndCode(idOfRoom, user)){
                Room room = gameServer.getRoom(idOfRoom);
                object.put("error", false);
                object.put("time", room.getSecondsReminder());
                object.put("isTimerStarted", room.isTimerStarted);
                return object.toJSONString();
            }
            object.put("error", true);
        }
        return object.toJSONString();
    }

    @GetMapping(value = "/theking/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delUserFromRoom(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code") int userCode
    ) {
        gameServer.deleteUser(idOfRoom, userCode);
        return "{\"ok\":true}";
    }

    @GetMapping(value = "/theking/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getStartStatus(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code") int userCode
    ) {
        if (gameServer.isRightId(idOfRoom)){
            Gson gson = new Gson();
            Room room = gameServer.getRoom(idOfRoom);
            Reply reply = Reply.getFromRoom(room, userCode);
            String answer = gson.toJson(reply);
            return answer;
        }
        return "{\"error\": true}";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }
}

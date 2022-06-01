package org.example;

import org.example.TheKing.GameServer;
import org.example.TheKing.Room;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Controller
public class TheKingController {
    GameServer gameServer = new GameServer();

    @GetMapping
    public String main() {
        return "index";
    }

    @GetMapping("/theking")
    public String getRoomId(Model model) {
        String idOfRoom = gameServer.getAvailableRoom();
        JSONObject object = new JSONObject();
        object.put("id", idOfRoom);
        model.addAttribute("json", object.toJSONString());
        return "json";
    }

    @RequestMapping(value = "/theking/room", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getUserCode(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code", required = false, defaultValue = "-1") int user,
            Model model
    ) {
        JSONObject object = new JSONObject();
        if (user == -1){
            if (gameServer.isRightId(idOfRoom)){
                int userCode = gameServer.addUser(idOfRoom);
                if (userCode != -1){
                    object.put("userCode", userCode);
                    object.put("error", false);
                    model.addAttribute("json", object.toJSONString());
                    return object.toJSONString();
                }
                else{
                    object.put("error", true);
                    model.addAttribute("json", object.toJSONString());
                    return object.toJSONString();
                }
            }
            object.put("error", true);
            model.addAttribute("json", object.toJSONString());
            return object.toJSONString();
        }
        else{
            if (gameServer.isRightIdAndCode(idOfRoom, user)){
                Room room = gameServer.getRoom(idOfRoom);
                object.put("error", false);
                object.put("time", room.getSecondsReminder());
                model.addAttribute("json", object.toJSONString());
                return object.toJSONString();
            }
            object.put("error", true);
            model.addAttribute("json", object.toJSONString());
        }
        return object.toJSONString();
    }

    @GetMapping("/theking/delete")
    public String delUserFromRoom(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code") int userCode,
            Model model
    ) {
        gameServer.deleteUser(idOfRoom, userCode);
        model.addAttribute("json", "{\"ok\":true}");
        return "json";
    }

//    @GetMapping("/theking/start")
//    public String getStartStatus(
//            @RequestParam(name = "id") String idOfRoom,
//            @RequestParam(name = "user-code") int userCode,
//            Model model
//    ) {
//        return "json";
//    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }
}

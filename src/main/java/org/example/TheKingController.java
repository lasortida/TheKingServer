package org.example;

import org.example.TheKing.GameServer;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "roomId";
    }

    @GetMapping("/theking/room")
    public String getUserCode(
            @RequestParam(name = "id") String idOfRoom,
            Model model
    ) {
        // выдать ID юзера
        return "userId";
    }

    @GetMapping("/theking/delete")
    public String delUserFromRoom(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code") int userCode,
            Model model
    ) {
        // удалить юзера
        return "idk";
    }

    @GetMapping("/theking/start")
    public String getStartStatus(
            @RequestParam(name = "id") String idOfRoom,
            @RequestParam(name = "user-code") int userCode,
            Model model
    ) {
        return "idk";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }
}

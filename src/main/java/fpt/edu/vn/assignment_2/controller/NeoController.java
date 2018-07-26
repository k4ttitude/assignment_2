package fpt.edu.vn.assignment_2.controller;

import fpt.edu.vn.assignment_2.dao.neo4j.NeoConnect;
import fpt.edu.vn.assignment_2.model.Comment;
import fpt.edu.vn.assignment_2.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/neo")
public class NeoController {

    private NeoConnect neoConnect;

    @GetMapping("/getFriendIds")
    public List<String> getFriendIds(@RequestParam String userId) {
        this.neoConnect = new NeoConnect();
        return neoConnect.getFriendsList(userId);
    }

    @PutMapping("/makeFriend")
    public boolean makeFriend(@RequestParam(name = "userId") String userId,
                              @RequestParam(name = "friendId") String friendId) {
        this.neoConnect = new NeoConnect();
        return neoConnect.makeFriends(userId, friendId);
    }

    @PutMapping("/unfriend")
    public void unfriend(@RequestParam(name = "userId") String userId,
                         @RequestParam(name = "friendId") String friendId) {
        this.neoConnect = new NeoConnect();
        neoConnect.unFriend(userId, friendId);
    }

    @PutMapping("/likeDocument")
    public void likeDocument(@RequestParam(name = "userId") String userId,
                             @RequestParam(name = "documentId") String documentId) {
        this.neoConnect = new NeoConnect();
        neoConnect.likeADocument(userId, documentId);
    }

    @PutMapping("/unlikeDocument")
    public void unlikeDocument(@RequestParam(name = "userId") String userId,
                             @RequestParam(name = "documentId") String documentId) {
        this.neoConnect = new NeoConnect();
        neoConnect.unlikeADocument(userId, documentId);
    }

    @PutMapping("/rateDocument")
    public void rateDocument(@RequestParam(name = "userId") String userId,
                             @RequestParam(name = "documentId") String documentId,
                             @RequestParam(name = "score") int score) {
        this.neoConnect = new NeoConnect();
        neoConnect.rateADocument(userId, documentId, score);
    }

    @PutMapping("/commentDocument")
    public void commendDocument(@RequestBody Comment comment) {
        this.neoConnect = new NeoConnect();
        neoConnect.commentADocument(comment);
    }

}

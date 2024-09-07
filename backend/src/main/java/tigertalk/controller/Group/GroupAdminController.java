package tigertalk.controller.Group;

import org.springframework.beans.factory.annotation.Autowired;
import tigertalk.service.Group.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/groups/admin")
public class GroupAdminController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/addUsers/{groupId}")
    public ResponseEntity<String> addUsers(@PathVariable int groupId, @RequestBody String[] emails) {
        for (String email : emails) {
            Optional<String> error = groupService.joinGroup(email, groupId);
            if (error.isPresent()) {return ResponseEntity.status(400).body(error.get());}
        }
        return ResponseEntity.status(200).body("User added to group successfully");
    }

}












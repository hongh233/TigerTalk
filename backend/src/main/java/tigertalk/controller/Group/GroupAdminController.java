package tigertalk.controller.Group;

import tigertalk.service.Group.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/admin")
public class GroupAdminController {

    private final GroupController groupController;
    private final GroupService groupService;

    public GroupAdminController(GroupService groupService) {
        groupController = new GroupController(groupService);
        this.groupService = groupService;
    }

    /**
     * Adds a user to a group by their email and group ID.
     *
     * @param email the email of the user to be added
     * @param groupId the ID of the group to which the user is to be added
     * @return ResponseEntity with a success or error message
     */
    @PostMapping("/addUser/{email}/{groupId}")
    public ResponseEntity<String> addUser(@PathVariable String email, @PathVariable int groupId) {
        return groupService.joinGroupAdmin(email, groupId)
                .map(ResponseEntity.badRequest()::body)
                .orElseGet(() -> ResponseEntity.ok("User added to group successfully"));
    }
}

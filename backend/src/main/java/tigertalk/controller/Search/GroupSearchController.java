package tigertalk.controller.Search;

import tigertalk.model.Group.GroupDTO;
import tigertalk.service.Search.GroupSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/search")
public class GroupSearchController {

    @Autowired
    private GroupSearchService groupSearchService;


    @GetMapping("/group/{content}")
    public List<GroupDTO> searchGroups(@PathVariable String content) {
        return groupSearchService.search(content);
    }
}

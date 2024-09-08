package tigertalk;

import tigertalk.controller.Friend.FriendshipRequestController;
import tigertalk.model.User.UserProfile;
import tigertalk.repository.User.UserProfileRepository;
import tigertalk.service.Authentication.SignUpService;
import tigertalk.service.Friend.FriendshipRequestService;
import tigertalk.service.Group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/scripts")
public class Scripts {

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private FriendshipRequestService friendshipRequestService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FriendshipRequestController friendshipRequestController;

    @PostMapping("/setUp")
    public ResponseEntity<String> run() {

        String[][] userData = {
                {"a@dal.ca", "FirstA", "LastA", "AAAAA"},
                {"b@dal.ca", "FirstB", "LastB", "BBBBB"},
                {"c@dal.ca", "FirstC", "LastC", "CCCCC"},
                {"d@dal.ca", "FirstD", "LastD", "DDDDD"},
                {"e@dal.ca", "FirstE", "LastE", "EEEEE"},
                {"f@dal.ca", "FirstF", "LastF", "FFFFF"},
                {"g@dal.ca", "FirstG", "LastG", "GGGGG"},
                {"h@dal.ca", "FirstH", "LastH", "HHHHH"},
                {"i@dal.ca", "FirstI", "LastI", "IIIII"},
                {"j@dal.ca", "FirstJ", "LastJ", "JJJJJ"},
                {"k@dal.ca", "FirstK", "LastK", "KKKKK"},
                {"l@dal.ca", "FirstL", "LastL", "LLLLL"},
                {"m@dal.ca", "FirstM", "LastM", "MMMMM"},
                {"n@dal.ca", "FirstN", "LastN", "NNNNN"},
                {"o@dal.ca", "FirstO", "LastO", "OOOOO"},
                {"p@dal.ca", "FirstP", "LastP", "PPPPP"},
                {"q@dal.ca", "FirstQ", "LastQ", "QQQQQ"},
                {"r@dal.ca", "FirstR", "LastR", "RRRRR"},
                {"s@dal.ca", "FirstS", "LastS", "SSSSS"},
                {"t@dal.ca", "FirstT", "LastT", "TTTTT"},
                {"u@dal.ca", "FirstU", "LastU", "UUUUU"},
                {"v@dal.ca", "FirstV", "LastV", "VVVVV"},
                {"w@dal.ca", "FirstW", "LastW", "WWWWW"},
                {"x@dal.ca", "FirstX", "LastX", "XXXXX"},
                {"y@dal.ca", "FirstY", "LastY", "YYYYY"},
                {"z@dal.ca", "FirstZ", "LastZ", "ZZZZZ"}
        };
        for (String[] data : userData) {
            UserProfile user = new UserProfile(
                    data[0],
                    "aaaa1A@a",
                    "What was your favourite book as a child?",
                    "11111",
                    true,
                    "admin",
                    "instructor",
                    data[1],
                    data[2],
                    data[3],
                    "This is a biography example. I am a people!",
                    "they/them",
                    "offline",
                    "https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg",
                    "2010-07-28"
            );
            userProfileRepository.save(user);
        }
        return ResponseEntity.ok("Initialize data successfully");
    }


    @PostMapping("/dropTables")
    public ResponseEntity<String> dropTables() {
        String[] dropTablesSql = {
                "SET foreign_key_checks = 0;",

                "DROP TABLE IF EXISTS password_token;",

                "DROP TABLE IF EXISTS friendship_message;",
                "DROP TABLE IF EXISTS friendship;",
                "DROP TABLE IF EXISTS friendship_request;",
                "DROP TABLE IF EXISTS notification;",

                "DROP TABLE IF EXISTS group_post_like;",
                "DROP TABLE IF EXISTS group_post_comment;",
                "DROP TABLE IF EXISTS group_post;",
                "DROP TABLE IF EXISTS group_all;",
                "DROP TABLE IF EXISTS group_membership;",

                "DROP TABLE IF EXISTS post_like;",
                "DROP TABLE IF EXISTS post_comment;",
                "DROP TABLE IF EXISTS post;",

                "DROP TABLE IF EXISTS user_profile;",

                "SET foreign_key_checks = 1;"
        };
        try {
            for (String sql : dropTablesSql) {
                jdbcTemplate.execute(sql);
            }
            return ResponseEntity.ok("Tables dropped successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error dropping tables");
        }
    }

}
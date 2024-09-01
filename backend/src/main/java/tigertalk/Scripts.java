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
        UserProfile userA, userB, userC, userD, userE, userF, userG, userH, userI, userJ, userK, userL, userM,
                userN, userO, userP, userQ, userR, userS, userT, userU, userV, userW, userX, userY, userZ;
        userA = new UserProfile();userB = new UserProfile();userC = new UserProfile();userD = new UserProfile();
        userE = new UserProfile();userF = new UserProfile();userG = new UserProfile();userH = new UserProfile();
        userI = new UserProfile();userJ = new UserProfile();userK = new UserProfile();userL = new UserProfile();
        userM = new UserProfile();userN = new UserProfile();userO = new UserProfile();userP = new UserProfile();
        userQ = new UserProfile();userR = new UserProfile();userS = new UserProfile();userT = new UserProfile();
        userU = new UserProfile();userV = new UserProfile();userW = new UserProfile();userX = new UserProfile();
        userY = new UserProfile();userZ = new UserProfile();

        userA.setEmail("a@dal.ca");
        userA.setPassword("aaaa1A@a");
        userA.setSecurityQuestion("What was your favourite book as a child?");
        userA.setSecurityQuestionAnswer("1");
        userA.setValidated(true);
        userA.setUserLevel("admin");
        userA.setRole("instructor");
        userA.setFirstName("Apple");
        userA.setLastName("Axio");
        userA.setUserName("Hong");
        userA.setBiography("dafgiusdgiuadhfilshdlofadf");
        userA.setAge(32);
        userA.setGender("Male");
        userA.setOnlineStatus("offline");
        userA.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userB.setEmail("b@dal.ca");
        userB.setPassword("aaaa1A@a");
        userB.setSecurityQuestion("What was your favourite book as a child?");
        userB.setSecurityQuestionAnswer("1");
        userB.setValidated(true);
        userB.setUserLevel("admin");
        userB.setRole("instructor");
        userB.setFirstName("Bell");
        userB.setLastName("Berry");
        userB.setUserName("Benjamin");
        userB.setBiography("waibibaboasauahjaskfjaisfs");
        userB.setAge(32);
        userB.setGender("Male");
        userB.setOnlineStatus("offline");
        userB.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userC.setEmail("c@dal.ca");
        userC.setPassword("aaaa1A@a");
        userC.setSecurityQuestion("What was your favourite book as a child?");
        userC.setSecurityQuestionAnswer("1");
        userC.setValidated(true);
        userC.setUserLevel("admin");
        userC.setRole("instructor");
        userC.setFirstName("Cell");
        userC.setLastName("Cherry");
        userC.setUserName("Raphael");
        userC.setBiography("ashibaufaifauhdidashfiubwi");
        userC.setAge(32);
        userC.setGender("Male");
        userC.setOnlineStatus("offline");
        userC.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userD.setEmail("d@dal.ca");
        userD.setPassword("aaaa1A@a");
        userD.setSecurityQuestion("What was your favourite book as a child?");
        userD.setSecurityQuestionAnswer("1");
        userD.setValidated(true);
        userD.setUserLevel("admin");
        userD.setRole("instructor");
        userD.setFirstName("Diarea");
        userD.setLastName("Dollar");
        userD.setUserName("Tyson");
        userD.setBiography("tysonloveyousendbyashabfuia");
        userD.setAge(32);
        userD.setGender("Male");
        userD.setOnlineStatus("offline");
        userD.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userE.setEmail("e@dal.ca");
        userE.setPassword("aaaa1A@a");
        userE.setSecurityQuestion("What was your favourite book as a child?");
        userE.setSecurityQuestionAnswer("1");
        userE.setValidated(true);
        userE.setUserLevel("admin");
        userE.setRole("instructor");
        userE.setFirstName("Ella");
        userE.setLastName("Eric");
        userE.setUserName("ShuQiang");
        userE.setBiography("asafqiufbqifuiafavkjijahiad");
        userE.setAge(32);
        userE.setGender("Male");
        userE.setOnlineStatus("offline");
        userE.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userF.setEmail("f@dal.ca");
        userF.setPassword("aaaa1A@a");
        userF.setSecurityQuestion("What was your favourite book as a child?");
        userF.setSecurityQuestionAnswer("1");
        userF.setValidated(true);
        userF.setUserLevel("user");
        userF.setRole("student");
        userF.setFirstName("Fuck");
        userF.setLastName("Ferry");
        userF.setUserName("Funk");
        userF.setBiography("uafiaugidsnfajfjhdakfaivahjkfakjgfjkahkjhakbk");
        userF.setAge(64);
        userF.setGender("Female");
        userF.setOnlineStatus("offline");
        userF.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userG.setEmail("g@dal.ca");
        userG.setPassword("aaaa1A@a");
        userG.setSecurityQuestion("What was your favourite book as a child?");
        userG.setSecurityQuestionAnswer("1");
        userG.setValidated(true);
        userG.setUserLevel("user");
        userG.setRole("student");
        userG.setFirstName("Goal");
        userG.setLastName("Goat");
        userG.setUserName("God");
        userG.setBiography("uafiaugidsnfajfjhdakfaivahjkfakjgfjkahkjhakbk");
        userG.setAge(64);
        userG.setGender("Female");
        userG.setOnlineStatus("offline");
        userG.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userH.setEmail("h@dal.ca");
        userH.setPassword("aaaa1A@a");
        userH.setSecurityQuestion("What was your favourite book as a child?");
        userH.setSecurityQuestionAnswer("1");
        userH.setValidated(true);
        userH.setUserLevel("user");
        userH.setRole("student");
        userH.setFirstName("Hell");
        userH.setLastName("Halt");
        userH.setUserName("Holy");
        userH.setBiography("uafiaugidsnfajfjhdakfaivahjkfakjgfjkahkjhakbk");
        userH.setAge(64);
        userH.setGender("Female");
        userH.setOnlineStatus("offline");
        userH.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");

        userI.setEmail("i@dal.ca");
        userI.setPassword("aaaa1A@a");
        userI.setSecurityQuestion("What was your favourite book as a child?");
        userI.setSecurityQuestionAnswer("1");
        userI.setValidated(true);
        userI.setUserLevel("user");
        userI.setRole("student");
        userI.setFirstName("Hell");
        userI.setLastName("Halt");
        userI.setUserName("Holy");
        userI.setBiography("uafiaugidsnfajfjhdakfaivahjkfakjgfjkahkjhakbk");
        userI.setAge(64);
        userI.setGender("Female");
        userI.setOnlineStatus("offline");
        userI.setProfilePictureUrl("https://res.cloudinary.com/dp4j9a7ry/image/upload/v1719765852/rvfq7rtgnni1ahktelff.jpg");


        userProfileRepository.save(userA);
        userProfileRepository.save(userB);
        userProfileRepository.save(userC);
        userProfileRepository.save(userD);
        userProfileRepository.save(userE);
        userProfileRepository.save(userF);
        userProfileRepository.save(userG);
        userProfileRepository.save(userH);
        userProfileRepository.save(userI);

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
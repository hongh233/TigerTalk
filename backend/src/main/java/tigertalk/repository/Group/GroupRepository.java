package tigertalk.repository.Group;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tigertalk.model.Group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    // We don't care about uppercase or lowercase
    @Query("SELECT g FROM Group g " +
            "WHERE LOWER(g.groupName) LIKE LOWER(CONCAT('%', :groupName, '%')) " +
            "ORDER BY CASE " +
            "WHEN LOWER(g.groupName) = LOWER(:groupName) THEN 1 " +
            "WHEN LOWER(g.groupName) LIKE LOWER(CONCAT(:groupName, '%')) THEN 2 " +
            "WHEN LOWER(g.groupName) LIKE LOWER(CONCAT('%', :groupName)) THEN 3 " +
            "ELSE 4 END, g.groupName ASC")
    List<Group> searchByGroupName(@Param("groupName") String groupName);
}

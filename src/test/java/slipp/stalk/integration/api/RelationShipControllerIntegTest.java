package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.Friends;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RelationShipControllerIntegTest extends IntegTest {

    private final String defaultUser = "gunjuko92@gmail.com";
    private final String url = "/members/relations/friends";

    @Test
    public void should__return_friends() throws Exception {
        getAndAssertFriendsSize(defaultUser, 0);
    }

    @Test
    public void should__add_friends() throws Exception {
        long friendMemberId = 2L;

        addFriends(defaultUser, friendMemberId);
        getAndAssertFriendsSize(defaultUser, 1);

        ResponseEntity<Void> response = postForEntityWithJwtToken(defaultUser, url + "/" + friendMemberId, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        deleteFriends(defaultUser, friendMemberId);
    }

    @Test
    public void should__delete_friends() throws Exception {
        long friendMemberId = 2L;

        addFriends(defaultUser, friendMemberId);
        getAndAssertFriendsSize(defaultUser, 1);

        deleteFriends(defaultUser, friendMemberId);
        getAndAssertFriendsSize(defaultUser, 0);

        ResponseEntity<Void> response = deleteForEntityWithJwtToken(defaultUser, url + "/" + friendMemberId, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void deleteFriends(String defaultUser, long memberId) {
        ResponseEntity<Void> response = deleteForEntityWithJwtToken(defaultUser, url + "/" + memberId, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void addFriends(String defaultUser, long memberId) {
        ResponseEntity<Void> response = postForEntityWithJwtToken(defaultUser, url + "/" + memberId, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    public void getAndAssertFriendsSize(String user, int expectedSize) {
        ResponseEntity<Friends> response = getForEntityWithJwtToken(user, url, Friends.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Friends body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getFriends().size()).isEqualTo(expectedSize);
    }
}

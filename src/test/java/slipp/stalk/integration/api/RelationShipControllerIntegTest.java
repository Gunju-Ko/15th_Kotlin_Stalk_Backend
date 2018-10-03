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
        ResponseEntity<Friends> response = getForEntityWithJwtToken(defaultUser, url, Friends.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Friends body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getFriends().size()).isEqualTo(0);
    }
}

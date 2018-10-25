package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import slipp.stalk.controller.dto.Friends;
import slipp.stalk.controller.dto.ResponseDto;

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

        ResponseDto<Void> response = postForEntityWithJwtToken(defaultUser, url + "/" + friendMemberId, null, Void.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);

        deleteFriends(defaultUser, friendMemberId);
    }

    @Test
    public void should__delete_friends() throws Exception {
        long friendMemberId = 2L;

        addFriends(defaultUser, friendMemberId);
        getAndAssertFriendsSize(defaultUser, 1);

        deleteFriends(defaultUser, friendMemberId);
        getAndAssertFriendsSize(defaultUser, 0);

        ResponseDto<Void> response = deleteForEntityWithJwtToken(defaultUser, url + "/" + friendMemberId, null, Void.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
    }

    private void deleteFriends(String defaultUser, long memberId) {
        ResponseDto<Void> response = deleteForEntityWithJwtToken(defaultUser, url + "/" + memberId, null, Void.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
    }

    private void addFriends(String defaultUser, long memberId) {
        ResponseDto<Void> response = postForEntityWithJwtToken(defaultUser, url + "/" + memberId, null, Void.class);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
    }

    public void getAndAssertFriendsSize(String user, int expectedSize) {
        ResponseDto<Friends> response = getForEntityWithJwtToken(user, url, new ParameterizedTypeReference<ResponseDto<Friends>>() {});
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        Friends body = response.getData();
        assertThat(body).isNotNull();
        assertThat(body.getFriends().size()).isEqualTo(expectedSize);
    }
}

package slipp.stalk.commoon.security;

import org.junit.Before;
import org.junit.Test;
import slipp.stalk.domain.Member;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoleManagerTest {

    private RoleManager roleManager;
    private Member admin;
    private Member user;

    @Before
    public void setUp() throws Exception {
        roleManager = new RoleManager();
        admin = new Member();
        admin.setRole(Member.Role.ADMIN);

        user = new Member();
        user.setRole(Member.Role.USER);
    }

    @Test
    public void given_user__when_requiredRoleIsUser__then_returnTrue() {
        assertTrue(roleManager.hasAuthority(user, Member.Role.USER));
    }

    @Test
    public void given_admin__when_requiredRoleIsUser__then_returnTrue() {
        assertTrue(roleManager.hasAuthority(admin, Member.Role.USER));
    }

    @Test
    public void given_user__when_requiredRoleIsAdmin__then_returnFalse() {
        assertFalse(roleManager.hasAuthority(user, Member.Role.ADMIN));
    }

    @Test
    public void given_admin__when_requiredRoleIsAdmin__then_returnTrue() {
        assertTrue(roleManager.hasAuthority(admin, Member.Role.ADMIN));
    }
}
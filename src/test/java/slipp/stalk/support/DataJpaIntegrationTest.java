package slipp.stalk.support;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class DataJpaIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    public <T> T saveTestData(T entity) {
        assert entity != null;
        return entityManager.persist(entity);
    }
}

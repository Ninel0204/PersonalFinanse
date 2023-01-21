import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.HandlerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlerImplTest {
    Map<String, Integer> mapCosts = new HashMap<>();

    @BeforeEach
    void setUp() {
        mapCosts.put("еда", 50);
        mapCosts.put("одежда", 350);
        mapCosts.put("быт", 0);
    }

    @Test
    void clothesTrue() {
        Assertions.assertTrue(Objects.deepEquals(new String[]{"одежда", "350"}, HandlerImpl.category(mapCosts)));
    }

    @Test
    void eatFalse() {
        Assertions.assertFalse(Objects.deepEquals(new String[]{"еда", "50"}, HandlerImpl.category(mapCosts)));
    }

    @Test
    void modeFalse() {
        Assertions.assertFalse(Objects.deepEquals(new String[]{"быт", "0"}, HandlerImpl.category(mapCosts)));
    }
}


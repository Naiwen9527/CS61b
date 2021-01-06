import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet a = new AListFloorSet();
        RedBlackFloorSet b = new RedBlackFloorSet();
       for (int i = 0; i < 1000000; i++) {
           int temp = StdRandom.uniform(-5000, 5000);
           a.add(temp);
           b.add(temp);
       }
       for (int j = 0; j < 100000; j++) {
           int test_val = StdRandom.uniform(-5000, 5000);
           assertEquals(a.floor(test_val), b.floor(test_val), 0.001);
       }
    }
}

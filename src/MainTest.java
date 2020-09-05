import org.junit.Assert;

public class MainTest {

    @org.junit.Test
    public void solve() {
        String sum = Main.solve("12+32");
        Assert.assertEquals("12+32=44", sum);
    }
}
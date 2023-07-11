import java.util.Base64;

public class Demo {
    public static void main(String[] args) {
        System.out.println(new String(Base64.getEncoder().encode("Demo@123".getBytes())));
    }
}

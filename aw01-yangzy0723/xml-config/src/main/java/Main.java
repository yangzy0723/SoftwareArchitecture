import asciiPanel.AsciiPanel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        AsciiPanel asciiPanel = (AsciiPanel) ctx.getBean("asciiPanel");
        System.out.println(asciiPanel.getAsciiFont().getFontFilename());
    }
}

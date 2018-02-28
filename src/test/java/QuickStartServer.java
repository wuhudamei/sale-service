import org.eclipse.jetty.server.Server;
import org.springside.modules.test.jetty.JettyFactory;
import org.springside.modules.test.spring.Profiles;

/**
 * <dl>
 * <dd>描述:快速启动的Server</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>@创建时间：2015/12/9 11:34</dd>
 * <dd>@author：huyt</dd>
 * </dl>
 */
public class QuickStartServer {

    public static final int PORT = 8012;
    public static final String CONTEXT = "/";
    public static final String[] TLD_JAR_NAMES = new String[]{"spring-webmvc", "springside-core"};

    public static void main(String[] args) throws Exception {
        // 设定Spring的profile
        Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

        // 启动Jetty
        Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
        JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

        try {
            server.start();
            System.out.println("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
            System.out.println("[INFO] Server running a for [mdni-sale-service]");
            System.out.println("[HINT] Hit Enter to reload the application quickly");

            // 等待用户输入回车重载应用.
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    JettyFactory.reloadContext(server);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

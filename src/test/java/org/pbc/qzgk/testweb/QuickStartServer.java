package org.pbc.qzgk.testweb;

import org.eclipse.jetty.server.Server;
import org.springside.modules.test.jetty.JettyFactory;
import org.springside.modules.test.spring.Profiles;


/*
 * 使用Jetty运行调试web应用，在console输入回车快速重新加载应用
 */
public class QuickStartServer {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/quickstart";
	public static final String[] TLD_JAR_NAMES = new String[]{"sitemesh","spring-webmv","shiro-web","spingside-core"};
	
	public static void main(String[] args) throws Exception{
		//设定spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
		
		//启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
		
		try{
			server.start();
			
			System.out.println("[INFO] Server running at http://localhost:"+PORT + CONTEXT);
			System.out.println("[HINT] Hit Enter to reload the application quickly");
			
			//等待用户输入回车重载 应用
			while(true){
				char c = (char)System.in.read();
				if(c != '\n'){
					JettyFactory.reloadContext(server);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

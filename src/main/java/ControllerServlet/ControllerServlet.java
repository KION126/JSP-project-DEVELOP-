package ControllerServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CommandHandler.CommandHandler;


@WebServlet(urlPatterns = "*.do",
			initParams = {@WebInitParam(name= "config",
										value= "/WEB-INF/commandHandler.properties")})
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//<커맨드, 핸들러클래스> 매핑 정보 저장
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
	
	//서블릿을 생성하고 초기화할 때 제일 먼저 호출되는 init() 메소드를 이용
	public void init(ServletConfig config) throws ServletException {
	      String configFile = config.getInitParameter("config");
	      Properties prop = new Properties();
	      String configFilePath = config.getServletContext().getRealPath(configFile);
	      
	      //실제 경로에서 설정 파일을 로드
	      try(FileInputStream fis = new FileInputStream(configFilePath)){
	         prop.load(fis);
	      }catch (IOException e) {
	         throw new ServletException(e);
	      }
	      
	      //설정 파일을 하나씩 잃어서 키값(호출패턴, hello.do)과 실행명령 핸들러 클래스 맵에 저장
	      Iterator<Object> keyIter = prop.keySet().iterator();
	      while(keyIter.hasNext()) {
	         String command = (String) keyIter.next();
	         String handlerClassName = prop.getProperty(command);
	         
	         //문자열 클래스를 실제 클래스 인스턴스로 생성
	         try {
	            Class<?> handlerClass = Class.forName(handlerClassName);
				CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();
	            
	            commandHandlerMap.put(command, handlerInstance);
	         }catch (ClassNotFoundException | InstantiationException 
	               | IllegalAccessException e) {
	            throw new ServletException(e);
	         }
	      }
	   }
	
    public ControllerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//비즈니스 로직 처리
		String command = request.getRequestURI();
	      
	      command = command.substring(request.getContextPath().length() + 1);      
	      
	      CommandHandler handler = commandHandlerMap.get(command);
	      
	      String viewPage = null;

	      try {
	         viewPage = handler.process(request, response);
	      } catch (Exception e) {
	         throw new ServletException(e);
	      }
	      
	      //서블릿에서 다음 페이지로 데이터를 넘기는 방법
	      RequestDispatcher rd = null;
	      if(viewPage.equals("index") || viewPage.equals("registerAction")) {
	    	  rd = request.getRequestDispatcher("/" + viewPage + ".jsp");
	      } else {
	    	  rd = request.getRequestDispatcher("/WEB-INF/views/" + viewPage + ".jsp");
	      }
	      rd.forward(request, response);
	}

}

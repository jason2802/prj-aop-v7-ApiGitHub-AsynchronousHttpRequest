package com.example.aop.aspect;


import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.aop.service.AuthorizationImpl;

@Aspect
@Component
public class AuthentificationAdvice {
		
	Logger LOG = LoggerFactory.getLogger( AuthentificationAdvice.class );
	
	private final AuthorizationImpl authorizationImpl;
	
	@Inject
	public AuthentificationAdvice(AuthorizationImpl authorizationImpl ) {
		this.authorizationImpl = authorizationImpl;
	}
	
	/*
	 * COME USARE POINTCUT DENTRO UN ADVICE
	 * 
		@Pointcut(" execution (* com.test.someTask(..))")
		 public void pointcutTest() {}
		 
		 @After("pointcutTest(..))")
		 public void myAdvice(){
		 System.out.println("my advice...");
		 }
	 */
	
	
	/*	
	 * COME USARE POINTCUT DENTRO UN ADVICE
	 *  
	 	@Pointcut("@args(org.baeldung.aop.annotations.Entity)")
		public void methodsAcceptingEntities() {}
		
		
		To access the argument we should provide a JoinPoint argument to the advice:
		@Before("methodsAcceptingEntities()")
		public void logMethodAcceptionEntityAnnotatedBean(JoinPoint jp) {
		    logger.info("Accepting beans with @Entity annotation: " + jp.getArgs()[0]);
		}
	 
	 */
	
	
	//     https://blogs.ashrithgn.com/2018/07/19/custom-authorization-annotation/
	
	/*@Before("@annotation(com.example.aop.aspect.Authorized) && args(request,..)") - @annotation    NON FUNZIONA CON    args(request,..) */
	
	// @Pointcut("execution(* com.example.aop.*.*(..)) && args(request,..)") // KO	// 1st approach to retrieve ELEMENT FROM HTTPREQUEST: by using 'request,..' in the args expression I suppose that for sure i will have at least one argument with name request and i will have zero or more parameters at that point
	// @Pointcut("execution(* com.example.aop.*.*(..)) && args(..)") // OK
	// @Pointcut("execution(* com.example.aop.*.*(..)) && args(request,..)") // OK
	// @Pointcut("execution(* com.example.aop.aspect.Authorized) && args(..)") // OK
	// @Around("@annotation(com.example.aop.aspect.Authorized)") // KO
	
	 // @Around("@annotation(com.example.aop.aspect.Authentification) && args(..)") // OK OK
	 // @Pointcut("execution(* com.example.aop.*.*(..)) && args(..)") // OK OK
	@Before("@annotation(com.example.aop.aspect.Authentification)") // OK OK	
	public void authorisation_secondo_approach( JoinPoint joinPoint ) throws Throwable {
				
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().toString();		
		LOG.info( " ------ method invoked: " + className + " : " + methodName + "( )" + " - request : " );		
		
		HttpServletRequest request = (HttpServletRequest)joinPoint.getArgs()[0];
		
		if ( !( request instanceof HttpServletRequest) ) {
			throw new RuntimeException( " ------ request should be HttpServletRequest type " );
		}
		if ( request.getHeaderNames()== null ) {
			throw new RuntimeException( " ------ You must set headers " );
		}
		
		
		// STAMPIAMO la request
		printRequest(request);
		
		
		Object[] ar = joinPoint.getArgs();
		
		List<Object> args = Arrays.asList( ar );
		
		for ( Iterator<Object> iterator = args.iterator(); iterator.hasNext(); ) {
			Object object = (Object) iterator.next();
			LOG.info( " object = " + object.toString() ); 
		}
				
		String token_set_as_body_attribut = request.getParameterMap().entrySet().stream()
				.filter( entry -> entry.getKey().equalsIgnoreCase( "token" ) )
				.map( entry -> entry.getValue() )
				.toString();				
			   			//  .collect( Collectors.joining() );
								
		if ( authorizationImpl.authorize( token_set_as_body_attribut )) {			
			 request.setAttribute(
			           "userSession", 
			           "session information which cann be acces in controller"
			           );
		}else {
            throw new RuntimeException("auth error..!!!");
		}
		
		
		String base64_BasicAutentication = request.getHeader("Authorization");		
		if(authorizationImpl.authorize( base64_BasicAutentication )){
			request.setAttribute(
	           "userSession", 
	           "session information which cann be acces in controller"
	           );
	     }else {
	            throw new RuntimeException("auth error..!!!");
	     }
		
		
	}
	
	
	
	private void printRequest( HttpServletRequest request ){
		
		LOG.info("=== User ( Returns the login of the user making this request, if the user has been authenticated, or null if the user has not been authenticated.)  ===\n");
		LOG.info("Request Remote User  : " + request.getRemoteUser() );
		
		LOG.info("=== Paths ===\n");
		LOG.info("Request URL : " + request.getRequestURL());
		LOG.info("Request URI : " + request.getRequestURI());
		LOG.info("Servlet path : " + request.getServletPath());
		
		LOG.info("\n=== Headers ===\n");		
		Enumeration<String> e = request.getHeaderNames();
		while(e.hasMoreElements()){
			String param = (String) e.nextElement();
			LOG.info(param + " : " + request.getHeader(param));
		}
		
		LOG.info("\n=== Parameters ===\n");
		Map<String, String[]> paramsMap = request.getParameterMap();
		for (String key : paramsMap.keySet()) {
			LOG.info(key + " : " + request.getParameter(key));
		}
		
		LOG.info("\n=== Session ===\n");
		// returns 0:0:0:0:0:0:0:1 if executed from localhost
		LOG.info("Client IP address : " + request.getRemoteAddr());
		LOG.info("Session ID : " + request.getRequestedSessionId());
		
		// Cookie objects the client sent with this request
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				LOG.info(cookie.getName() + ";");
			}
		}
		
		
	}
	
	/* EXEMPLE DE VALORE LOGGE 
	 
	 2019-08-03 17:37:43.172  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : === User ( Returns the login of the user making this request, if the user has been authenticated, or null if the user has not been authenticated.)  ===

2019-08-03 17:37:43.172  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : Request Remote User  : null
2019-08-03 17:37:43.172  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : === Paths ===

2019-08-03 17:37:43.182  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : Request URL : http://localhost:8181/authentication/user
2019-08-03 17:37:43.182  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : Request URI : /authentication/user
2019-08-03 17:37:43.183  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : Servlet path : /authentication/user
2019-08-03 17:37:44.545  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : 
=== Headers ===

2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : content-type : application/json
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : accept : application/json
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : access-control-request-headers : test
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : user-agent : application/octet-stream
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : authorization : Basic QURIT0wwMVxHdWV0c2E6TmV4aTI4MDI0
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : cache-control : no-cache
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : postman-token : f8fd17d1-7150-4d7e-a7ac-f7e9f0d19716
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : host : localhost:8181
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : cookie : BCSI-CS-67e7214f470f46ea=2; JSESSIONID=74D722ED1136DD21CEAB353B80BD784D
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : accept-encoding : gzip, deflate
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : content-length : 65
2019-08-03 17:37:44.546  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : connection : keep-alive
2019-08-03 17:37:46.623  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : 
=== Parameters ===

2019-08-03 17:37:46.624  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : param1 : param_valore_1
2019-08-03 17:37:46.624  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : param2 : param_valore_2
2019-08-03 17:37:48.089  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : 
=== Session ===

2019-08-03 17:37:48.090  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : Client IP address : 0:0:0:0:0:0:0:1
2019-08-03 17:37:48.090  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : Session ID : 74D722ED1136DD21CEAB353B80BD784D
2019-08-03 17:37:49.729  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : BCSI-CS-67e7214f470f46ea;
2019-08-03 17:37:49.730  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    : JSESSIONID;
2019-08-03 17:37:56.109  INFO 21868 --- [nio-8181-exec-1] c.e.aop.aspect.AuthentificationAdvice    :  object = org.apache.catalina.connector.RequestFacade@37ce22e8
2019-08-03 17:38:06.343  INFO 21868 --- [nio-8181-exec-1] com.example.aop.aspect.LoggingAdvice     :  - method invoked: class com.example.aop.service.AuthorizationImpl : authorize( ) - Input Arguments : ["java.util.stream.ReferencePipeline$3@70eca8c3"]
2019-08-03 17:38:08.884  INFO 21868 --- [nio-8181-exec-1] com.example.aop.aspect.LoggingAdvice     :  - method executed: class com.example.aop.service.AuthorizationImpl : authorize( ) - Response : ["java.util.stream.ReferencePipeline$3@70eca8c3"]

	 
	 
	 */
	
	
}


// approach to retrieve ELEMENT FROM HTTPREQUEST - Get method arguments using Spring AOP?

	
/*
	FIRST, you can use the JoinPoint#getArgs() method which returns an Object[] containing all the arguments of the advised method. 
	You might have to do some casting depending on what you want to do with them.
*/


/*
	SECOND, you can use the args pointcut expression like so:
	
	// use '..' in the args expression if you have zero or more parameters at that point
	@Before("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..)) && args(yourString,..)")
	then your method can instead be defined as
	
	public void logBefore(JoinPoint joinPoint, String yourString) 
	
*/
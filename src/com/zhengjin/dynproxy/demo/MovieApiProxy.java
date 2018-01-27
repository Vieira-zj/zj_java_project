package com.zhengjin.dynproxy.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.zhengjin.apis.testutils.TestUtils;

public class MovieApiProxy {

	private static String host;
	
	public static MovieApi create(MovieApi api) {
		Annotation annotation = api.getClass().getAnnotation(Server.class);
		if (annotation == null) {
			throw new RuntimeException("movie api not set @Server annotation!");
		}
		host = ((Server) annotation).value();
		
		return (MovieApi) Proxy.newProxyInstance(api.getClass().getClassLoader(), api.getClass().getInterfaces(),
				new Handle());
	}

	private static class Handle implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Annotation[] annotations = method.getAnnotations();
			if (annotations.length == 0) {
				TestUtils.printLog("Get param not found!");
				return "null";
			}
			if (!(annotations[0] instanceof Get)) {
				TestUtils.printLog("Invalid http type!");
				return "null";
			}

			String httpType = "Get";
			String path = host + ((Get) annotations[0]).path();
			String description = ((Get) annotations[0]).description();

			Annotation[][] paramAnnotations = method.getParameterAnnotations();
			String query = "";
			if (paramAnnotations.length > 0) {
				for (int i = 0, len = paramAnnotations.length; i < len; i++) {
					Annotation[] annos = paramAnnotations[i];
					if (annos[0] instanceof Param) {
						query += String.format("%s=%s&", ((Param) annos[0]).value(), args[i]);
					}
				}
				query = query.substring(0, query.length() - 1);
			} else {
				TestUtils.printLog("No @annotation arguments found!");
			}

			return String.format("%s %s?%s (%s)", httpType, path, query, description);
		}
	}
}
	
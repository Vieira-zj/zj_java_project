package com.zhengjin.dynproxy.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.zhengjin.apis.testutils.TestUtils;

public class Client {

	public static void main(String[] args) {

		TestUtils.printLog("Proxy example 01");
		Subject realSubject = new RealSubject();
		InvocationHandler handler = new DynamicProxy(realSubject);
		Subject subject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
				realSubject.getClass().getInterfaces(), handler);

		System.out.println(subject.getClass().getName());
		subject.rent();
		subject.hello("world");

		TestUtils.printLog("\nProxy example 02");
		MockMovieApi mockApi = new MockMovieApi();
		TestUtils.printLog(mockApi.top10(1, 3));

		MovieApi api = MovieApiProxy.create(mockApi);
		TestUtils.printLog(api.top10(1, 3));
	}

}

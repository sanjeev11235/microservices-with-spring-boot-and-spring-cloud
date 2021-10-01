package com.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)", //define the regular expression, identifying the next thing as a segment
								"/currency-conversion-feign/${segment}"))//we'd want to use the same segment.
						.uri("lb://currency-conversion"))
				.build();
		
		//So, whatever follows currency-conversion-new, 
		//I would want to append it to currency-conversion-feign.
	} 
	
	
//	@Bean
//	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//		Function<PredicateSpec, Buildable<Route>> routeFuntion
//		= p -> p.path("/get")
//				.filters(f -> f
//						.addRequestHeader("MyHeader", "MyURI")
//						.addRequestParameter("Param", "MyValue"))
//				.uri("http://httpbin.org:80");
//		return builder.routes()
//				.route(routeFuntion)
//				.build();
//	} 
}

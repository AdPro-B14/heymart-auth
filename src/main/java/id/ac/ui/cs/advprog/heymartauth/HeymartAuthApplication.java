package id.ac.ui.cs.advprog.heymartauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@SpringBootApplication
@ConfigurationProperties(prefix = "app")
public class HeymartAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeymartAuthApplication.class, args);
	}

	String API_PREFIX = "/api/";
	String WILDCARD = "**";

	@Value("${app.gateway.store}")
	String STORE_SERVICE_HOST;

	@Value("${app.gateway.token}")
	private String X_GATEWAY_TOKEN;

	public ServerResponse addHeadersFilter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {
		String authorizationHeader = request.headers().header("Authorization").getFirst();
		ServerRequest updatedRequest = ServerRequest.from(request)
				.header("Authorization", authorizationHeader)
				.header("X-Gateway-Token", X_GATEWAY_TOKEN)
				.build();

		return next.handle(updatedRequest);
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStoreGet() {
		return route("store")
				.GET(API_PREFIX + "store/" + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStorePost() {
		return route("store")
				.POST(API_PREFIX + "store/" + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStorePut() {
		return route("store")
				.PUT(API_PREFIX + "store/" + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStoreDelete() {
		return route("store")
				.DELETE(API_PREFIX + "store/" + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}
}

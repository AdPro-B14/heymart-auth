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

	@Value("${app.gateway.order}")
	String ORDER_SERVICE_HOST;

	@Value("${app.gateway.token}")
	private String X_GATEWAY_TOKEN;

	private final String STORE_ID = "store";
	private final String STORE_PREFIX = "store/";
	private final String ORDER_ID = "order";
	private final String ORDER_PREFIX = "order/";

	public ServerResponse addHeadersFilter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {
		ServerRequest updatedRequest = ServerRequest.from(request)
				.header("X-Gateway-Token", X_GATEWAY_TOKEN)
				.build();

		return next.handle(updatedRequest);
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStoreGet() {
		return route(STORE_ID)
				.GET(API_PREFIX + STORE_PREFIX + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStorePost() {
		return route(STORE_ID)
				.POST(API_PREFIX + STORE_PREFIX + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStorePut() {
		return route(STORE_ID)
				.PUT(API_PREFIX + STORE_PREFIX + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteStoreDelete() {
		return route(STORE_ID)
				.DELETE(API_PREFIX + STORE_PREFIX + WILDCARD, http(STORE_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "store/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteOrderGet() {
		return route(ORDER_ID)
				.GET(API_PREFIX + ORDER_PREFIX + WILDCARD, http(ORDER_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "order/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteOrderPost() {
		return route(ORDER_ID)
				.POST(API_PREFIX + ORDER_PREFIX + WILDCARD, http(ORDER_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "order/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteOrderPut() {
		return route(ORDER_ID)
				.PUT(API_PREFIX + ORDER_PREFIX + WILDCARD, http(ORDER_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "order/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> apiRouteOrderDelete() {
		return route(ORDER_ID)
				.DELETE(API_PREFIX + ORDER_PREFIX + WILDCARD, http(ORDER_SERVICE_HOST))
				.before(rewritePath(API_PREFIX + "order/" + "(?<segment>.*)", "/${segment}"))
				.filter(this::addHeadersFilter)
				.build();
	}
}

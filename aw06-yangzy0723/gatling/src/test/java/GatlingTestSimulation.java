import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class GatlingTestSimulation extends Simulation {

    public final String hostname = "http://localhost:8080";

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(hostname)
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

    ScenarioBuilder scn = scenario("getProducts")
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_1").get("/productsService/products"))
            .pause(1)
            .exec(http("request_2").get("/ordersService/add/10167771016"))
            .pause(2)
            .exec(http("request_3").get("/ordersService/checkout"));
    {
        //注入用户，刚开始就一个，协议是http
        setUp(scn.injectOpen(atOnceUsers(20000))).protocols(httpProtocol);
    }
}
package hu.capsys.shell;

import feign.RequestInterceptor;
import hu.capsys.payment.api.PayeeApiClient;
import hu.capsys.paymentgateway.PayeeControllerApiClient;
import hu.capsys.paymentgateway.PaymentControllerApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
        PayeeApiClient.class,
        PayeeControllerApiClient.class,
        PaymentControllerApiClient.class,
})
public class CapsysClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsysClientApplication.class, args);
    }


    @Bean
    public RequestInterceptor feignInterceptor() {
        return (requestTemplate) -> {
            requestTemplate.header("X-Request-ID", "1111");
//            requestTemplate.header("X-B3-TraceId", "0100f0f5d23c7251");
//            requestTemplate.header("X-B3-SpanId", "0200f0f5d23c7251");
//            requestTemplate.header("X-B3-ParentSpanId", "0300f0f5d23c7251");
            requestTemplate.header("x-client-trace-id", "2222");
            requestTemplate.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGS1ZVQTFrd3czZmZBS3dGWnN1NWg4MEZPYkYxUjJiZXhzTGl6U1hZczFvIn0.eyJleHAiOjE2NDQxNjM2MzEsImlhdCI6MTY0NDE2MzAzMSwianRpIjoiM2Q4YzZkMTMtMGU3OS00YWU5LTg2YzUtMmE3ODRhZDFjZWRmIiwiaXNzIjoiaHR0cHM6Ly9pbmZyYS50ZXN0LmJhbmtiaXR0cy5jb20vYXV0aC9yZWFsbXMvY21vcGciLCJhdWQiOlsiY21vX2JhY2tlbmQiLCJhY2NvdW50Il0sInN1YiI6IjNkNDM4MWFiLTIwNDMtNGI2Mi05OTM3LTE5MGM5OWFkNWMxOSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImNtb19jcyIsInNlc3Npb25fc3RhdGUiOiI3MzI3YzQ0OC01NzI3LTQyZTAtOTg1MS02YTAyZTZlZjgxOWEiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtY21vcGciLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiY21vX2NzIjp7InJvbGVzIjpbInVtYV9wcm90ZWN0aW9uIl19LCJjbW9fYmFja2VuZCI6eyJyb2xlcyI6WyJQUkUiLCJQQUMiLCJTUkUiLCJUV1IiLCJQQ1IiLCJDV1IiLCJQQ0EiLCJTV1IiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiIiwic2lkIjoiNzMyN2M0NDgtNTcyNy00MmUwLTk4NTEtNmEwMmU2ZWY4MTlhIiwiY2xpZW50SWQiOiJjbW9fY3MiLCJjbGllbnRIb3N0IjoiMTAuMjQ0LjcuMSIsImNsaWVudEFkZHJlc3MiOiIxMC4yNDQuNy4xIn0.KKEE36g7vo-wZebWsubuj40e4jIUcabDo0y_J9uInP6x3QrDF8Lb6HBNXkEgUBeQZXQJ-fy8cJ89LdjODvwwpDy7M38aEDj8b9TyGLIoafhCG0OLkSRn46075XTpIjEeMVfXIyRL4Ia_KeEBGEGqyMbQobzz7k_uRyPMj6LK0VOIDRkAvji6l-RAfHcKr59QwWlBm6D47OkbyJhCFbBX7ViCLWfJ5UMIh-e0YM1B-Ua2jZNqbtxP8VQmisd9l2QMJGD3wiJnMcI96zU0P0i41mHVdJZQLtiiK7Srg-tYJSvIF0IA_l3GNi7SiVUH-mj21QY33zp0Mk-LoPcmeLCSnA");
            requestTemplate.header("keycloak_resource", "cmo_backend");
            requestTemplate.header("keycloak_use_resource_role_mappings", "true");
        };
    }
}

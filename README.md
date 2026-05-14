# comprehensive-api-test-project

This is a standalone Java fixture repository for testing API asset discovery.

It intentionally includes endpoint and type-resolution patterns that are easy
to miss during snippet extraction:

- Spring MVC controller mappings, class-level and method-level paths.
- Spring WebFlux `Mono<T>` and `Flux<T>` responses.
- Servlet `doGet` and `doPost` request parsing.
- JSP form, fetch, XMLHttpRequest, axios, and jQuery AJAX endpoint clues.
- DTO inheritance, nested objects, arrays, enums, Lombok, Jackson, validation,
  Swagger/OpenAPI annotations, and sensitive fields.
- Generic wrappers: `Result<T>`, `ApiResponse<T>`, `PageResult<T>`,
  `ResponseEntity<T>`, `HttpEntity<T>`, `Optional<T>`, `Map<K,V>`.
- Dynamic parameter extraction from `HttpServletRequest`, `ServletRequestUtils`,
  custom `RequestUtil`, custom `JsonUtil`, `ObjectMapper`, and Fastjson.
- Response inference from service methods, mapper methods, MapStruct-style
  conversion, BeanUtils copy, `Result.success(data)`, `ApiResponse.ok(data)`,
  `JSON.toJSONString`, and `response.getWriter().write`.
- MyBatis mapper XML result maps and SQL fragments.
- Authentication, authorization, tenant isolation, owner checks, and security
  configuration snippets.

Run the agent against this directory:

```powershell
python .\scripts\run_agent.py scan --repo .\examples\comprehensive-api-test-project --output-dir . --config .\config.example.yaml
```

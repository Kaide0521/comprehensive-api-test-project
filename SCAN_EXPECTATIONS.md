# Scan Expectations

This fixture is designed to verify the scanner requirements for snippet
extraction, recursive type resolution, dynamic parameter discovery, and response
schema inference.

## Expected Endpoint Families

- `OrderController`
  - `GET /api/v1/orders/{orderId}`
  - `POST /api/v1/orders/search`
  - `POST /api/v1/orders/dynamic`
  - `GET /api/v1/orders/{orderId}/map`
  - `PUT /api/v1/orders/{orderId}`
  - `GET /api/v1/orders/{orderId}/optional`
  - `GET /api/v1/orders/{orderId}/export`
  - `GET /api/v1/orders/view`
  - `GET /api/v1/orders/redirect`
  - `GET /api/v1/orders/public/recent`
- `ReactiveOrderController`
  - `GET /api/reactive/orders/{orderId}`
  - `GET /api/reactive/orders/stream`
- `LegacyReportServlet`
  - `/legacy/report`
  - `/legacy/report/*`
- JSP endpoint clues from `WEB-INF/jsp/order-detail.jsp`.
- Component exposure findings from `application.yml`.

## Snippet Context That Should Be Present

- Package name and relevant imports.
- Controller class JavaDoc, annotations, and security annotations.
- Endpoint method JavaDoc, annotations, parameters, and return type.
- Swagger/OpenAPI annotations from `@Operation`, `@Parameter`, and `@Schema`.
- Validation annotations such as `@Valid`, `@Min`, `@Max`, `@NotBlank`,
  `@Email`, and `@Pattern`.
- Jackson annotations such as `@JsonFormat`, `@JsonIgnore`, and `@JsonProperty`.
- Lombok annotations such as `@Data`, `@EqualsAndHashCode`, `@NoArgsConstructor`.
- Shiro and Spring Security annotations.
- Custom authorization annotations `@RequiresTenant` and `@RequiresOwner`.
- DTO, VO, Entity, base class, nested field, enum, array, set, map, and wrapper
  definitions.
- `Result<T>`, `ApiResponse<T>`, `PageResult<T>`, `ResponseEntity<T>`,
  `HttpEntity<T>`, `Optional<T>`, `Mono<T>`, and `Flux<T>` usages.
- Dynamic request parsing:
  - `request.getParameter`
  - `request.getHeader`
  - `request.getAttribute`
  - `request.getInputStream`
  - `request.getReader`
  - `JSON.parseObject`
  - `JSONObject.parseObject`
  - `ObjectMapper.readValue`
  - `ServletRequestUtils.getLongParameter`
  - custom `RequestUtil`, `ParamUtil`, and `JsonUtil`
- Response inference sources:
  - Direct DTO return.
  - `List<DTO>` response.
  - `PageResult<DTO>` response.
  - `Result<DTO>` response.
  - `ApiResponse<DTO>` response.
  - `ResponseEntity<DTO>` response.
  - `Map<String,Object>` response.
  - declared `Object` return with concrete `ApiResponse<OrderDetailVO>` value.
  - `response.getWriter().write(JSON.toJSONString(...))`.
  - `ModelAndView` and `redirect:` responses.
  - `@ControllerAdvice` exception models.
  - `ResponseBodyAdvice` global response wrapping.
- Related config:
  - `application.yml`
  - `application.properties`
  - `web.xml`
  - `mapper/OrderMapper.xml`
- Security and ownership logic:
  - `SecurityConfig`
  - `TenantFilter`
  - `TenantAccessEvaluator`
  - `CurrentUser`

## Embedded Component Expectations

The component scanner should detect at least:

- Spring Boot Actuator at `/actuator`
- Swagger UI at `/swagger-ui.html`
- Druid Monitor at `/druid`
- Prometheus metrics via `/actuator/prometheus`

The test config intentionally uses `admin/admin`-style weak Druid credentials
and an exposed actuator set so risk metadata can be validated.

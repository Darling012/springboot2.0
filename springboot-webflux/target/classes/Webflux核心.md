什么是 WebFlux? Spring WebFlux 是一套全新的 Reactive Web 栈技术，实现完全非阻塞，支持 Reactive Streams 背压等特性，并且运行环境不限于 Servlet 容器（Tomcat、Jetty、Undertow），如 Netty 等。Spring WebFlux 与 Spring MVC 可共存，在 Spring Boot 中，Spring MVC 优先级更高。为什么优先级高，大家可以看看我之前的文章 [https://blog.csdn.net/mikezzmeric/article/details/87209555](https://blog.csdn.net/mikezzmeric/article/details/87209555)

我们为什么要使用 web flux 呢？Spring 官方讲了两个主要原因，第一点是 servlet API 部分是阻塞式的，而且 Listner 和 Filter 是同步的方式。这个原因其实是错误的，我们早就知道 Servlet 在 3.1 的时候就已经可以异步的方式返回结果了。第二点原因是函数式编程的出现，这个原因可以算，但是不完全。所以个人觉得原因如下：从 Spring MVC 注解驱动的时代开始，Spring 官方有意识地去 Servlet 化。不过在 Spring MVC 的时代，Spring 扔拜托不了 Servlet 容器的依赖，然而 Spring 借助 Reactive Programming 的势头，WebFlux 将 Servlet 容器从必须项变为可选项，并且默认采用 Netty Web Server 作为基础，从而组件地形成 Spring 全新技术体系，包括数据存储等技术栈：

![](G:\小马哥\慕课网 Spring Boot 2.0深度实践之核心技术篇\资料\springboot2.0\springboot-webflux\src\main\resources\Webflux核心.assets\20190302144610529.png)

Webflux 的实现可以是函数式的实现也可以是注解式的实现，我们看一个例子：

函数映射：

```java
RouterFunction<ServerResponse> route =
route(GET("/person/{id}").and(accept(APPLICATION\_JSON)), handler::getPerson)
.andRoute(GET("/person").and(accept(APPLICATION\_JSON)), handler::listPeople)
.andRoute(POST("/person"), handler::createPerson);
```

对应的注解映射如下：

```java
@GetMapping(value="/person/{id}",consumes=APPLICATION\_JSON)
public void getPerson(HttpServletRequest request，HttpServletResponse) {
}
@GetMapping(value="/person",consumes=APPLICATION\_JSON)
public void listPeople(HttpServletRequest request，HttpServletResponse) {
}
@PostMapping(value="/person")
public void createPerson(HttpServletRequest request，HttpServletResponse) {
}
```

实现的功能一样，方式呢有所区别。

Spring MVC 和 Spring WebFlux 均能使用注解驱动 Controller，然而不同点在于并发模型和阻塞特性。Spring MVC 通常是 Servlet 应用，因此，可能被当前线程阻塞。以远程调用为例，由于阻塞的缘故，导致 Servlet 容器使用较大的线程池处理请求。Spring WebFlux 通常是非阻塞服务，不会发生阻塞，因此该阻塞服务器可使用少量、固定大小的线程池处理请求。

接下来介绍一下 WebFlux 的核心组件：

1.HttpHandler

是一种带有处理 HTTP 请求和响应方法的简单契约。

2.WebHandler

<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td>Bean 名称</td><td>Bean 类型</td><td>数量</td><td>描述</td></tr><tr><td>&nbsp;</td><td>WebExceptionHandler</td><td>0..N</td><td>Provide handling for exceptions from the chain of WebFilter 's and the target WebHandler</td></tr><tr><td>&nbsp;</td><td>WebFilter</td><td>0..N</td><td>Apply interception style logic to before and after the rest of the filter chain and the target WebHandler .</td></tr><tr><td>webHandler</td><td>WebHandler</td><td>1</td><td>The handler for the request.</td></tr><tr><td>webSessionManager</td><td>WebSessionManager</td><td>0..1</td><td>The manager for WebSession 's exposed through a method on ServerWebExchange . DefaultWebSessionManager by default.</td></tr><tr><td>serverCodecConfigurer</td><td>ServerCodecConfigurer</td><td>0..1</td><td>For access to HttpMessageReader 's for parsing form data and multipart data that’s then exposed through methods on ServerWebExchange .<br>ServerCodecConfigurer.create() by default.</td></tr><tr><td>localeContextResolver</td><td>LocaleContextResolver</td><td>0..1</td><td>The resolver for LocaleContext exposed through a method on ServerWebExchange . AcceptHeaderLocaleContextResolver by default.</td></tr></tbody></table>
webHandler 显得有一些抽象，我们可以通过对比 SpringMVC 的一些组件帮助大家理解一下在 WebFlux 中各个组件的作用：

<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td>核心组件</td><td>Spring Web MVC</td><td>Spring WebFlux</td></tr><tr><td>前端控制器 (Front Controller)</td><td>DispatcherServlet</td><td>DispatcherHandler</td></tr><tr><td>Handler 请求映射</td><td>o.s.w.servlet.HandlerMapping</td><td>o.s.w.reactive.HandlerMapping</td></tr><tr><td>Handler 请求适配器</td><td>o.s.w.servlet.HandlerAdapter</td><td>o.s.w.reactive.HandlerAdapter</td></tr><tr><td>Handler 异常处理器</td><td>o.s.w.servlet.HandlerExceptionResolver</td><td>HandlerResult.exceptionHandler</td></tr><tr><td>视图处理器</td><td>o.s.w.servlet.ViewResolver</td><td>o.s.w.reactive.r.v.ViewResolver</td></tr><tr><td>Locale 解析器</td><td>o.s.w.servlet.LocaleResolver&nbsp;&nbsp;</td><td>LocaleContextResolver</td></tr><tr><td>@Enable 模块注解</td><td>@EnableWebMvc</td><td>@EnableWebFlux</td></tr><tr><td>自定义配置器</td><td>WebMvcConfigurer</td><td>WebFluxConfigurer</td></tr><tr><td>内容协商配置器</td><td>ContentNegotiationConfigurer</td><td>RequestedContentTypeResolverBuilder</td></tr><tr><td>内容协商管理器</td><td>ContentNegotiationManager</td><td>无</td></tr><tr><td>内容协商策略</td><td>ContentNegotiationStrategy</td><td>RequestedContentTypeResolver</td></tr><tr><td>资源跨域注册器</td><td>o.s.w.servlet.c.a.CorsRegistry</td><td>o.s.w.reactive.c.CorsRegistry</td></tr><tr><td>HanderMethod 参数解析器</td><td>o.s.w.m.s.HandlerMethodArgumentResolver</td><td>o.s.w.reactive.r.m.HandlerMethodArgumentResolver</td></tr><tr><td>HanderMethod 返回值解析器</td><td>HandlerMethodReturnValueHandler</td><td>HandlerResultHandler</td></tr></tbody></table>
![](G:\小马哥\慕课网 Spring Boot 2.0深度实践之核心技术篇\资料\springboot2.0\springboot-webflux\src\main\resources\Webflux核心.assets\20190303103722917.png)

接下来我们看一下采用函数式的方式编写的时候，一个请求的处理流程是怎样的：

![](G:\小马哥\慕课网 Spring Boot 2.0深度实践之核心技术篇\资料\springboot2.0\springboot-webflux\src\main\resources\Webflux核心.assets\20190302175319337.png)

RouterFunctionMapping 中有 private RouterFunction<?> routerFunction; 这里面表面看起来只有一个 Bean，其实它里面组合了非常多的 RouterFunction，它是如何根据用户的请求找到对应的 Function 的呢？

```java
@Override
	protected Mono<?> getHandlerInternal(ServerWebExchange exchange) {
		if (this.routerFunction != null) {
			ServerRequest request = ServerRequest.create(exchange, this.messageReaders);
			return this.routerFunction.route(request)
					.doOnNext(handler -> setAttributes(exchange.getAttributes(), request, handler));
		}
		else {
			return Mono.empty();
		}
	}
```

关键部分就是通过它的成员变量 routerFunction 的 route 方法来找，其实就是通过用户写的 predicate 来判断请求是否相符合，如果符合就返回一个 Mono<HandlerFunction<T>>

![](https://img-blog.csdnimg.cn/20190303103821980.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L21pa2V6em1lcmlj,size_16,color_FFFFFF,t_70)

WebFlux 的使用场景, 我们根据一些测试报告来看看：

![](https://img-blog.csdnimg.cn/20190303104826658.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L21pa2V6em1lcmlj,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20190303104856739.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L21pa2V6em1lcmlj,size_16,color_FFFFFF,t_70)

1\. 性能考虑：

1.1WebFlux 提升的其实往往是系统的伸缩性，对于速度的提升没有太多的明显。所以它不适合注入 RPC 这类对 RT（响应时间）要求较高的应用。  
1.2 关注编程用户友好性，Reactive 编程尽管没有新增大量的代码，然而编码（和调试）却是变得更为复杂  
1.3 现在面临的最大问题是缺少文档。在生成测试应用中，它已经给我们造成了最大障碍，并使得我们可能已经缺少了关键点。因此，我们并不会太快地投入 Reactive 编程，同时等待关于它的更多反馈。因此，Spring WebFlux 尚未证明自身明显地优于 Spring MVC。

2\. 编程模型

注解驱动编程模型

函数式编程模型

到底选择哪一个呢？

如果 SpringMVC 应用工作的挺好的，就没必要切换成 webflux。

如果你想使用非阻塞的技术栈，可以考虑使用 WebFlux。当然也不一定非要 webflux，servlet3.1 之后我们也知道提供了非阻塞的方式。

如果想使用轻量级的函数式编程，可以考虑使用函数式编程模型。

![](https://img-blog.csdnimg.cn/20190303105417846.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L21pa2V6em1lcmlj,size_16,color_FFFFFF,t_70)

3\. 并发模型适用性

如果你的请求不需要实时的返回，那么可以使用异步的方式。

如果 RT 敏感，不要使用异步模型，因为涉及到线程切换或者等待可能引发超时。
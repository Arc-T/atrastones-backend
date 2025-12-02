DELETED AT in orders in not required ❌
deleted at in products is required ✅
STATUS in orderDetails is needed ? ✅
UPDATED AT in orderDetails, attributes is needed ✅
address_line is not needed in shops (use addresses instead) ✅
product_ratings to product_reviews ✅
remove created at in product_stats ❌
active address id is needed in users table ❌
order_display in addresses ✅
Comments are important
*******************************************
full calendar, react day picker, PReact, Shadcn Appwrite, DND kit, Pasthup in React
https://www.dynx.pro/

---------------------- Oracle DB ----------------------
discount logic should wait
order_remaining_balances is for multi payment methods.

AtiveJ

ATTRIBUTE.ID.INVALID
ORDER.ID.INVALID
PRODUCT_MEDIA.ID.INVALID
PAYMENT.ID.INVALID
PRODUCT.ID.INVALID
SMS.ID.INVALID

Global AuthenticationManager configured with an AuthenticationProvider bean. UserDetailsService beans will not be used by Spring Security for automatically configuring username/password login. Consider removing the AuthenticationProvider bean. Alternatively, consider using the UserDetailsService in a manually instantiated DaoAuthenticationProvider. If the current configuration is intentional, to turn off this warning, increase the logging level of 'org.springframework.security.config.annotation.authentication.configuration.InitializeUserDetailsBeanManagerConfigurer' to ERROR

Attribute type should be dynamic but due to fast deployment, its hardcoded in Attribute Repository

v0 - bold AI

org.springframework.web.servlet.NoHandlerFoundException: No endpoint GET /categories/.
at org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1305) ~[spring-webmvc-6.2.11.jar:6.2.11]
at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067) ~[spring-webmvc-6.2.11.jar:6.2.11]
at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979) ~[spring-webmvc-6.2.11.jar:6.2.11]
at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.2.11.jar:6.2.11]
at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.2.11.jar:6.2.11]

| Purpose                 | Best Choice            |
| ----------------------- | ---------------------- |
| 3D visuals / scenes     | **Three.js**           |
| Full 3D game engine     | **Babylon.js**         |
| 2D WebGL graphics       | **PixiJS**             |
| Vector drawing          | **Paper.js**           |
| Creative art / sketches | **p5.js**              |
| Charts / data viz       | **D3.js**, **ECharts** |



<!--<?xml version="1.0" encoding="UTF-8"?>-->
<!--<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"-->
<!--         xmlns="http://maven.apache.org/POM/4.0.0"-->
<!--         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">-->
<!--    <modelVersion>4.0.0</modelVersion>-->
<!--    <parent>-->
<!--        <groupId>org.springframework.boot</groupId>-->
<!--        <artifactId>spring-boot-starter-parent</artifactId>-->
<!--        <version>3.5.8</version>-->
<!--        <relativePath/>-->
<!--    </parent>-->
<!--    <groupId>com.atrastones</groupId>-->
<!--    <artifactId>sashia-atrastones</artifactId>-->
<!--    <version>1.0.0-SNAPSHOT</version>-->
<!--    <name>Atra Stones Accessory</name>-->
<!--    <description>E-commerce platform for accessories</description>-->

<!--    <properties>-->
<!--        <java.version>21</java.version>-->
<!--        <lombok.version>1.18.34</lombok.version>-->
<!--        <java-jwt.version>4.5.0</java-jwt.version>-->
<!--        <maven-compiler-plugin.version>3.13.0</maven-compiler-plugin.version>-->
<!--    </properties>-->

<!--    <dependencies>-->
<!--        &lt;!&ndash; Spring Boot Starters &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-web</artifactId>-->
<!--        </dependency>-->

<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-security</artifactId>-->
<!--        </dependency>-->

<!--        &lt;!&ndash; Database &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>com.mysql</groupId>-->
<!--            <artifactId>mysql-connector-j</artifactId>-->
<!--            <version>9.4.0</version>-->
<!--            <scope>runtime</scope>-->
<!--        </dependency>-->

<!--        &lt;!&ndash; Lombok &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>org.projectlombok</groupId>-->
<!--            <artifactId>lombok</artifactId>-->
<!--            <version>${lombok.version}</version>-->
<!--            <scope>provided</scope>-->
<!--        </dependency>-->

<!--        &lt;!&ndash; Validation &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-validation</artifactId>-->
<!--        </dependency>-->

<!--        &lt;!&ndash; JWT &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>com.auth0</groupId>-->
<!--            <artifactId>java-jwt</artifactId>-->
<!--            <version>${java-jwt.version}</version>-->
<!--        </dependency>-->

<!--        &lt;!&ndash; Development Tools &ndash;&gt;-->
<!--        &lt;!&ndash;        <dependency>&ndash;&gt;-->
<!--        &lt;!&ndash;            <groupId>org.springframework.boot</groupId>&ndash;&gt;-->
<!--        &lt;!&ndash;            <artifactId>spring-boot-devtools</artifactId>&ndash;&gt;-->
<!--        &lt;!&ndash;            <scope>runtime</scope>&ndash;&gt;-->
<!--        &lt;!&ndash;            <optional>true</optional>&ndash;&gt;-->
<!--        &lt;!&ndash;        </dependency>&ndash;&gt;-->

<!--        &lt;!&ndash; Testing &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-test</artifactId>-->
<!--            <scope>test</scope>-->
<!--        </dependency>-->

<!--        <dependency>-->
<!--            <groupId>org.springframework.security</groupId>-->
<!--            <artifactId>spring-security-test</artifactId>-->
<!--            <scope>test</scope>-->
<!--        </dependency>-->

<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-actuator</artifactId>-->
<!--        </dependency>-->

<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-data-jpa</artifactId>-->
<!--        </dependency>-->

<!--        <dependency>-->
<!--            <groupId>com.h2database</groupId>-->
<!--            <artifactId>h2</artifactId>-->
<!--            <scope>test</scope>-->
<!--        </dependency>-->

<!--        <dependency>-->
<!--            <groupId>org.liquibase</groupId>-->
<!--            <artifactId>liquibase-core</artifactId>-->
<!--            <scope>test</scope>-->
<!--        </dependency>-->

<!--    </dependencies>-->
<!--    &lt;!&ndash;    <build>&ndash;&gt;-->
<!--    &lt;!&ndash;        <plugins>&ndash;&gt;-->
<!--    &lt;!&ndash;            <plugin>&ndash;&gt;-->
<!--    &lt;!&ndash;                <groupId>org.springframework.boot</groupId>&ndash;&gt;-->
<!--    &lt;!&ndash;                <artifactId>spring-boot-maven-plugin</artifactId>&ndash;&gt;-->
<!--    &lt;!&ndash;            </plugin>&ndash;&gt;-->
<!--    &lt;!&ndash;        </plugins>&ndash;&gt;-->
<!--    &lt;!&ndash;    </build>&ndash;&gt;-->
<!--    <build>-->
<!--        <plugins>-->
<!--            <plugin>-->
<!--                <groupId>org.apache.maven.plugins</groupId>-->
<!--                <artifactId>maven-compiler-plugin</artifactId>-->
<!--                <version>${maven-compiler-plugin.version}</version>-->
<!--                <configuration>-->
<!--                    <source>${java.version}</source>-->
<!--                    <target>${java.version}</target>-->
<!--                    <annotationProcessorPaths>-->
<!--                        <path>-->
<!--                            <groupId>org.projectlombok</groupId>-->
<!--                            <artifactId>lombok</artifactId>-->
<!--                            <version>${lombok.version}</version>-->
<!--                        </path>-->
<!--                    </annotationProcessorPaths>-->
<!--                </configuration>-->
<!--            </plugin>-->
<!--            <plugin>-->
<!--                <groupId>org.springframework.boot</groupId>-->
<!--                <artifactId>spring-boot-maven-plugin</artifactId>-->
<!--                <version>${project.parent.version}</version>-->
<!--                <executions>-->
<!--                    <execution>-->
<!--                        <goals>-->
<!--                            <goal>repackage</goal>-->
<!--                        </goals>-->
<!--                    </execution>-->
<!--                </executions>-->
<!--            </plugin>-->
<!--        </plugins>-->
<!--    </build>-->
<!--</project>-->
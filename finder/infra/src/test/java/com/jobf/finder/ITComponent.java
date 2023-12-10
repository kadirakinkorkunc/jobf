package com.jobf.finder;

import org.springframework.boot.test.context.SpringBootTest;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Retention(RetentionPolicy.RUNTIME)
public @interface ITComponent {
}

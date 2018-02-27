/*
 * This program is an api service written with spring-boot.
 * Copyright (c) 2018 Andreas Sekulski, Dimitri Kotlovsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.companion.api.documentation

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.util.UriComponentsBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.paths.AbstractPathProvider
import springfox.documentation.spring.web.paths.Paths
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Configuration class for swagger-ui.
 *
 * @author Andreas Sekulski, Dimitri Kotlovsky
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {

    /**
     * API bean to create swagger-ui.
     */
    @Bean
    fun api(): Docket {

        return Docket(DocumentationType.SWAGGER_2)
                .pathProvider(BasePathAwareRelativePathProvider("/"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
    }
}

/**
 * Base path provider to fix error uris.
 *
 * @author Andreas Sekulski, Dimitri Kotlovsky
 */
internal class BasePathAwareRelativePathProvider(private val basePath: String) : AbstractPathProvider() {

    /**
     * @return Get application base path.
     */
    override fun applicationPath(): String {
        return basePath
    }

    /**
     * @return Get documentation base path.
     */
    override fun getDocumentationPath(): String {
        return "/"
    }

    /**
     * @return Generates operation path for examples.
     */
    override fun getOperationPath(operationPath: String): String {
        val uriComponentsBuilder = UriComponentsBuilder.fromPath("/")
        return Paths.removeAdjacentForwardSlashes(
                uriComponentsBuilder.path(operationPath.replaceFirst(basePath.toRegex(), "")).build().toString())
    }
}

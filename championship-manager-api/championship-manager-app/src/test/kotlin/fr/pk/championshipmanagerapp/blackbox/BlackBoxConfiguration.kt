package fr.pk.championshipmanagerapp.blackbox

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

@TestConfiguration
@Profile("blackbox")
class BlackBoxConfiguration {

    @Value("classpath:graphql/query-wrapper.json")
    private lateinit var queryWrapperFile: Resource

    @Bean
    fun queryWrapper(): String {
        return StreamUtils.copyToString(queryWrapperFile.inputStream, StandardCharsets.UTF_8)
    }
}

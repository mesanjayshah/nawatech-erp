package nawatech.io.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua_parser.Parser;

import java.io.IOException;

@Configuration
public class LoginNotificationConfig {

    @Bean
    public Parser uaParser() throws IOException {
        return new Parser();
    }

 /*   @Bean(name="GeoIPCity")
    public DatabaseReader databaseReader() throws IOException {
        final File resource = new File(Objects.requireNonNull(this.getClass()
                        .getClassLoader()
                        .getResource("maxmind/GeoLite2-City.mmdb"))
                .getFile());
        return new DatabaseReader.Builder(resource).build();
    }*/

/*    @Bean(name="GeoIPCity")
    public DatabaseReader databaseReader() throws IOException {
        File database = ResourceUtils
                .getFile("classpath:maxmind/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(database)
                .build();
    }*/

/*    @Bean(name = "GeoIPCountry")
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        final File resource = new File(this.getClass()
                .getClassLoader()
                .getResource("maxmind/GeoLite2-Country.mmdb")
                .getFile());
        return new DatabaseReader.Builder(resource).build();
    }*/

}

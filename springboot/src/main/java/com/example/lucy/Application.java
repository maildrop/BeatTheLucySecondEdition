package com.example.lucy;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

// com.example.lucy.Application
@SpringBootApplication
@Configuration
public class Application {

    @org.springframework.beans.factory.annotation.Autowired
        private ConfProp confProp;
    
    public static void main(String[] args) {
        final org.springframework.context.ConfigurableApplicationContext cac = 
            SpringApplication.run( Application.class, args);
        {
            final var ite = cac.getBeanFactory().getBeanNamesIterator();
            while( ite.hasNext() ){
                System.out.println( "\""+java.util.Objects.toString( ite.next()+"\"" ));
            }
        }
        {
            final com.example.lucy.ConfProp confProp = cac.getBeanFactory().getBean( com.example.lucy.ConfProp.class );
            System.out.println( "-->" + Objects.toString( confProp ));
        }
    }

}

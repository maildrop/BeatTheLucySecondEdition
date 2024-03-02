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
            // Application Context に登録されている名前一覧を取得する
            final var ite = cac.getBeanFactory().getBeanNamesIterator();
            while( ite.hasNext() ){
                System.out.println( "\""+java.util.Objects.toString( ite.next()+"\"" ));
            }
        }
        {
            // confProp を実際に取得する
            final com.example.lucy.ConfProp confProp = cac.getBeanFactory().getBean( com.example.lucy.ConfProp.class );
            System.out.println( "-->" + Objects.toString( confProp ));
        }

        {  
            final Object obj = cac.getBeanFactory().getBean( "application" );
              
            System.out.println( Objects.toString( obj ));

            final Application instance = Application.class.cast( obj );
            // wiredされているのなら == である
            if( instance.confProp == cac.getBeanFactory().getBean( com.example.lucy.ConfProp.class ) ){
                System.out.println( "hello world" );
            }
            
            
        }
    }

}

package com.example.lucy;

import java.lang.*;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

// com.example.lucy.Application
@SpringBootApplication
@Configuration
public class Application {
    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger("com.example.lucy.Application");

    @Autowired
    private ConfProp confProp;
    
    public static void main(final String[] args) {

        // AtomReference を使っているが、何でもよい。 条件変数として利用 
        final java.util.concurrent.atomic.AtomicReference< org.springframework.context.ConfigurableApplicationContext > contextRef
            = new java.util.concurrent.atomic.AtomicReference<>();

        assert ( Objects.isNull( contextRef.get() )) : "初期値は null" ;

        synchronized( contextRef ){

            /*
              今、SpringApplication のインスタンス application が持つ context の BeanFactory() を取得したいので、
              SpringApplicationの Hook を利用して、準備が完了した時に、 contextRef に ConfigurableApplicationContext を設定する。
            */

            /*
              SpringApplication にフックをかける。 
            */
            final SpringApplication application = new SpringApplication( Application.class );

            SpringApplication.withHook( (springApplication) -> new org.springframework.boot.SpringApplicationRunListener(){
                    @Override
                    public final void ready( final org.springframework.context.ConfigurableApplicationContext context ,
                                             final java.time.Duration timeTaken ){
                        synchronized( contextRef ){
                            contextRef.set( context );
                            contextRef.notifyAll();
                        }
                        return ;
                    }
                } ,
                ()-> {application.run( args );} );

            try{
                // 条件変数 contextRef が null を指している間は、wait() で待機する
                while( Objects.isNull( contextRef.get() ) ){
                    contextRef.wait();
                }

                {
                    final org.springframework.context.ConfigurableApplicationContext context 
                        = contextRef.get();
                    
                    assert ( Objects.nonNull( context ));
                    
                    if( Objects.nonNull( context )){
                        {
                            // Application Context に登録されている名前一覧を取得する
                            final var ite = context.getBeanFactory().getBeanNamesIterator();
                            while( ite.hasNext() ){
                                System.out.println( "\""+java.util.Objects.toString( ite.next()+"\"" ));
                            }
                        }
                        
                        {
                            // confProp を実際に取得する
                            final com.example.lucy.ConfProp confProp = context.getBeanFactory().getBean( com.example.lucy.ConfProp.class );
                            synchronized( confProp ){
                                System.out.println( "-->" + Objects.toString( confProp ));
                            }
                        }
                        
                        {
                            // confProp は application に Autowired された Bean である。
                            final Object obj = context.getBeanFactory().getBean( "application" );
                            
                            System.out.println( Objects.toString( obj ));
                                
                            final Application instance = Application.class.cast( obj );
                            // wiredされているのなら == である
                            if( instance.confProp == context.getBeanFactory().getBean( com.example.lucy.ConfProp.class ) ){
                                System.out.println( "hello world" );
                            }
                        }
                    }
                }
            }catch( final InterruptedException ie ){
                
            }
        }
    }
}

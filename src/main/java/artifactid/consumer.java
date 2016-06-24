package artifactid;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@RestController
@EnableAutoConfiguration
@EnableJms
/**
 * Clase que produce una entrada en la cola JBoss EAP AMQ
 * @author pedro.alonso.garcia
 *
 */


public class consumer implements ExceptionListener {
	//Variables Globales
	Connection conn = null;
	Session session = null;
	MessageConsumer consumidor = null;
	Gson gson = new GsonBuilder().create();
	
	@JmsListener(destination = "Consumer.A.VirtualTopic.PruebaAlex")
	public void receiveQueue(String text) {
		
		System.out.println(text);
		
		Evento myEvento;
        
        myEvento = gson.fromJson(text, Evento.class);
	}
	
	@RequestMapping("/")
	String home() {
		
		return "<strong>Consumer</strong> <br>Recibiendo mensajes</br>";
	}

    public static void main(String[] args) throws Exception {
        SpringApplication.run(consumer.class, args);
        
    }
 
    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}
	
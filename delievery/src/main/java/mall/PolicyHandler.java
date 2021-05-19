package mall;

import mall.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

//    @StreamListener(KafkaProcessor.INPUT)
//    public void onStringEventListener(@Payload String eventString){
//
//    }

    @Autowired
    DelieveryRepository delieveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderPlaced_PrepareShip(@Payload OrderPlaced orderPlaced){

        if(orderPlaced.isMe()){
            // SNS Send
            // CJ Payload
            // Delievery Aggregate Save
            Delievery delievery = new Delievery();
            delievery.setOrderId(orderPlaced.getId());
            delievery.setStatus("DelieveryStarted");

            delieveryRepository.save(delievery);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_DeleteCancelledOrder(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            System.out.println("##### listener DeleteCancelledOrder : " + orderCancelled.toJson());
            Delievery delievery = delieveryRepository.findByOrderId(orderCancelled.getId());
            delieveryRepository.delete(delievery);
        }
    }

}

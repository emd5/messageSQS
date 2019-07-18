package messageSQS;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class SendReceiveMessages
{
    private static final String QUEUE_A = "https://sqs.us-west-2.amazonaws.com/374121160624/QueueA";
    private static final String QUEUE_B = "https://sqs.us-west-2.amazonaws.com/374121160624/QueueB";
    private static final String QUEUE_C = "https://sqs.us-west-2.amazonaws.com/374121160624/QueueC";

    public static void main(String[] args)
    {
        sendReceive("Hello World for A" , QUEUE_A);
        sendReceive("Hello World for B" , QUEUE_B);
        sendReceive("Hello World for C" , QUEUE_C);
    }

    public static void sendReceive(String msg, String queue){
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        String queueUrl = queue;

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(msg);
//                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);

        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        while(messages.size() > 0 ){
//
//        // delete messages from the queue
            for (Message m : messages) {
                System.out.println(m.getBody());
                sqs.deleteMessage(queueUrl, m.getReceiptHandle());
            }
            messages = sqs.receiveMessage(queueUrl).getMessages();
        }
    }
}
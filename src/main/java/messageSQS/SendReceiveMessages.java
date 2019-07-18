package messageSQS;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class SendReceiveMessages
{
    private static final String QUEUE_A = "https://sqs.us-west-2.amazonaws.com/374121160624/QueueA";
    private static final String QUEUE_B = "https://sqs.us-west-2.amazonaws.com/374121160624/QueueB";
    private static final String QUEUE_C = "https://sqs.us-west-2.amazonaws.com/374121160624/QueueC";

    public static void main(String[] args)
    {
        sendreceive("Hello World for A" , QUEUE_A);
        sendreceive("Hello World for B" , QUEUE_B);
        sendreceive("Hello World for C" , QUEUE_C);

    }

    public static void sendreceive(String msg, String queue){
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        String queueUrl = queue;

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(msg)
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);

        // receive messages from the queue
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        // delete messages from the queue
        for (Message m : messages) {
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
        }

    }
}
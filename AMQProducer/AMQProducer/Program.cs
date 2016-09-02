using Apache.NMS;
using System;

namespace AMQProducer
{
    class Program
    {
        static void Main(string[] args)
        {
            // Example connection strings:
            //    activemq:tcp://activemqhost:61616
            //    stomp:tcp://activemqhost:61613
            //    ems:tcp://tibcohost:7222
            //    msmq://localhost

            Uri connecturi = new Uri("activemq:tcp://192.168.122.1:36917");

            Console.WriteLine("About to connect to " + connecturi);

            // NOTE: ensure the nmsprovider-activemq.config file exists in the executable folder.
            IConnectionFactory factory = new NMSConnectionFactory(connecturi);

            using (IConnection connection = factory.CreateConnection())
            using (ISession session = connection.CreateSession())
            {
                IDestination destination = session.GetQueue("divisas");
                Console.WriteLine("Using destination: " + destination);

                using (IMessageProducer producer = session.CreateProducer(destination))
                {
                    // Start the connection so that messages will be processed.
                    connection.Start();
                    producer.DeliveryMode = MsgDeliveryMode.Persistent;

                    // Send a message
                    ITextMessage request = session.CreateTextMessage("nombreDivisa:USD|precio:14.30");

                    producer.Send(request);
                }
            }
        }
    }
}

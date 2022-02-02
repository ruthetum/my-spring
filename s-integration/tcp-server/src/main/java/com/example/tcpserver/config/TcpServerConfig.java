package com.example.tcpserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class TcpServerConfig {

    @Value("${tcp.server.port}")
    private int port;

    @Bean
    public CustomTcpSerializer serializer() {
        return new CustomTcpSerializer();
    }

    @Bean
    public AbstractServerConnectionFactory connectionFactory(CustomTcpSerializer serializer) {
        TcpNioServerConnectionFactory connectionFactory = new TcpNioServerConnectionFactory(port);
        connectionFactory.setSerializer(serializer);
        connectionFactory.setDeserializer(serializer);
        connectionFactory.setSingleUse(true);
        return connectionFactory;
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }

    @Bean
    public TcpInboundGateway inboundGateway(AbstractServerConnectionFactory connectionFactory, MessageChannel inboundChannel, MessageChannel replyChannel) {
        TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
        tcpInboundGateway.setConnectionFactory(connectionFactory);
        tcpInboundGateway.setRequestChannel(inboundChannel);
        tcpInboundGateway.setReplyChannel(replyChannel);
        return tcpInboundGateway;
    }
}

package com.example.tcpclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class TcpClientConfig {

    @Value("${tcp.server.host")
    private String host;

    @Value("${tcp.client.port}")
    private int port;

    @Bean
    public CustomTcpSerializer serializer() {
        return new CustomTcpSerializer();
    }

    @Bean
    public AbstractClientConnectionFactory connectionFactory(CustomTcpSerializer serializer) {
        TcpNioClientConnectionFactory connectionFactory = new TcpNioClientConnectionFactory(host, port);
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
    public TcpOutboundGateway outboundGateway(AbstractClientConnectionFactory connectionFactory, MessageChannel inboundChannel, MessageChannel replyChannel) {
        TcpOutboundGateway tcpOutboundGateway = new TcpOutboundGateway();
        tcpOutboundGateway.setConnectionFactory(connectionFactory);
        tcpOutboundGateway.setReplyChannel(replyChannel);
        return tcpOutboundGateway;
    }
}

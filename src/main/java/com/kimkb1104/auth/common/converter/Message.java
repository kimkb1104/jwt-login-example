package com.kimkb1104.auth.common.converter;

import lombok.*;

@Getter
public class Message {

    static Message convert(Object message) {
        if (message instanceof FailureMessage)
            return (FailureMessage) message;
        else
            return SuccessMessage.builder()
                    .data(message)
                    .build();
    }

    @Getter
    static class DefaultMessage extends Message {
        private Long timestamp = System.currentTimeMillis();
    }

    @Getter
    @Builder
    private static class SuccessMessage extends DefaultMessage {
        private Object data;
    }

    @Getter
    @Builder
    public static class FailureMessage extends DefaultMessage {
        String errorCode;
    }
}

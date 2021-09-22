package com.kimkb1104.auth.common.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
public class MessageMappingJacksonMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.writeInternal(Message.convert(o), type, outputMessage);
    }
}

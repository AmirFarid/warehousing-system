package com.parchenegar.capsule.common.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageByLocaleServiceImpl implements Translator
{
    MessageSource messageSource;

    @Autowired
    public MessageByLocaleServiceImpl(MessageSource messageSource) {this.messageSource = messageSource;}

    @Override
    public String translate(String id)
    {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, null, locale);
    }

    @Override
    public String get(String id)
    {
        return translate(id);
    }
}
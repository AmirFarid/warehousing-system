package com.parchenegar.capsule.controller;

import com.parchenegar.capsule.common.i18n.Translator;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseRestController
{
    @Autowired
    protected Translator translator;
}

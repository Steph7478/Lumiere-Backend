package com.lumiere.presentation.controllers.base;

import org.springframework.web.bind.annotation.RequestMapping;

import com.lumiere.infrastructure.config.api.ApiConfig;

@RequestMapping(ApiConfig.API_PREFIX)
public abstract class BaseController {
}

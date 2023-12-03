package com.jobf.finder.common.rest.dto;

import com.jobf.finder.common.model.UseCase;

public abstract class AbstractRequest {

    public abstract UseCase toUseCase();
}

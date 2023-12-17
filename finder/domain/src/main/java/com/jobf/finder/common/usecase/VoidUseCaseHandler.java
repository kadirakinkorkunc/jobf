package com.jobf.finder.common.usecase;

import com.jobf.finder.common.model.UseCase;

public interface VoidUseCaseHandler<U> {

    void handle(U useCase);
}

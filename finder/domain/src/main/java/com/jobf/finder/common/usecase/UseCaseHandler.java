package com.jobf.finder.common.usecase;

import com.jobf.finder.common.model.UseCase;

public interface UseCaseHandler<R, U extends UseCase> {

    R handle(U useCase);
}

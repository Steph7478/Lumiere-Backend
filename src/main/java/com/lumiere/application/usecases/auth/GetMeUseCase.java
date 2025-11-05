package com.lumiere.application.usecases.auth;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.auth.GetMeRequest;
import com.lumiere.application.dtos.auth.GetMeResponse;
import com.lumiere.application.exceptions.UserNotFoundException;
import com.lumiere.application.interfaces.IGetMeUseCase;
import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.shared.annotations.logs.Loggable;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMeUseCase implements IGetMeUseCase {
    private final AuthRepository authRepository;

    public GetMeUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public GetMeResponse execute(GetMeRequest request) {
        Auth auth = authRepository.findByIdWithRelations(request.userId(), "user")
                .orElseThrow(UserNotFoundException::new);

        return new GetMeResponse(auth.getUser().getId(), auth.getName(), auth.isAdmin());
    }
}

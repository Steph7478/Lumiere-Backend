package com.lumiere.application.usecases.auth;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.auth.GetMeRequest;
import com.lumiere.application.dtos.auth.GetMeResponse;
import com.lumiere.application.exceptions.UserNotFoundException;
import com.lumiere.application.interfaces.IGetMeUseCase;
import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.shared.annotations.logs.Loggable;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMeUseCase implements IGetMeUseCase {
    private final UserRepository userRepository;

    public GetMeUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public GetMeResponse execute(GetMeRequest request) {
        AuthInfoView user = userRepository.findAuthInfoByAuthId(request.userId())
                .orElseThrow(UserNotFoundException::new);

        return new GetMeResponse(user);
    }
}

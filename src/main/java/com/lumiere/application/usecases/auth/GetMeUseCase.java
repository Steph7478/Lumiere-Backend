package com.lumiere.application.usecases.auth;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.auth.query.me.GetMeInput;
import com.lumiere.application.dtos.auth.query.me.output.GetMeOutput;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.interfaces.auth.IGetMeUseCase;
import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMeUseCase implements IGetMeUseCase {
    private final UserRepository userRepository;

    public GetMeUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public GetMeOutput execute(GetMeInput request) {
        AuthInfoView user = userRepository.findAuthInfoByAuthId(request.userId())
                .orElseThrow(UserNotFoundException::new);

        return new GetMeOutput(user);
    }
}

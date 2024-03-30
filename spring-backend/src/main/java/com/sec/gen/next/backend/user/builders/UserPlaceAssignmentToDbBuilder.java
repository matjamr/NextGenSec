package com.sec.gen.next.backend.user.builders;

import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.user.mapper.UserPlaceAssignmentMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class UserPlaceAssignmentToDbBuilder implements Function<List<UserPlaceAssignmentModel>, List<UserPlaceAssignment>> {

    private final UserPlaceAssignmentRepository repository;
    private final UserPlaceAssignmentMapper userPlaceAssignmentMapper;

    @Override
    public List<UserPlaceAssignment> apply(List<UserPlaceAssignmentModel> userPlaceAssignmentModels) {
        return repository.saveAll(userPlaceAssignmentModels.stream()
                .map(userPlaceAssignmentMapper::from)
                .toList());
    }
}

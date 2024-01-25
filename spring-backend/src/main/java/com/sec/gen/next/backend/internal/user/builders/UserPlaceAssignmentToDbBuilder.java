package com.sec.gen.next.backend.internal.user.builders;

import com.sec.gen.next.backend.internal.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.internal.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.internal.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.internal.user.mapper.UserPlaceAssignmentMapper;
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

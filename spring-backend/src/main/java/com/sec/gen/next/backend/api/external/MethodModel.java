package com.sec.gen.next.backend.api.external;

import com.sec.gen.next.backend.api.internal.VerificationStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@Data
public class MethodModel {

    private Integer id;

    private String name;
    private ProductModel product;
    private UserModel user;
    private List<ImageModel> images;
    private VerificationStage verificationStage;
}

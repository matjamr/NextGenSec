package com.sec.gen.next.backend.api.external;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@Data
public class HistoryEntranceModel {
    private Integer id;
    private PlacesModel places;
    private UserModel user;
    private ProductModel product;
    private LocalDateTime date;
}

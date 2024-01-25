package com.sec.gen.next.backend.internal.places.builder.common;

import com.sec.gen.next.backend.internal.api.internal.VerificationStage;
import com.sec.gen.next.backend.internal.places.PlacesContext;

import java.util.Optional;
import java.util.function.Consumer;

public class DynamicStatusUpdater implements Consumer<PlacesContext> {

    @Override
    public void accept(PlacesContext placesContext) {
        Optional.of(placesContext)
                .map(PlacesContext::getPlacesModel)
                .ifPresent(placesModel -> Optional.ofNullable(placesModel.getVerificationStage())
                        .ifPresentOrElse(
                                stage -> placesModel.setVerificationStage(VerificationStage.values()[stage.ordinal() + 1]
                                ),
                                () -> placesContext.getPlacesModel().setVerificationStage(VerificationStage.values()[0])
                                ));
    }

}

package com.sec.gen.next.serviceorchestrator.external.nominatim;

import com.next.gen.api.Address;
import com.next.gen.sec.model.AddressModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NominatimQueryBuilder implements Function<AddressModel, String> {
    private static final String pattern = "%s %s %s %s";
    @Override
    public String apply(AddressModel address) {
        return pattern.formatted(address.getStreetName(), address.getHomeNumber(), address.getCity(), "Polska");
    }
}

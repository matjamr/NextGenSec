package com.sec.gen.next.serviceorchestrator.external.nominatim;

import com.next.gen.sec.model.NominatimPlace;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.external.SimpleErrorDecoder;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "nominatimClient", url = "${services.nominatim.url}")
public interface NominatimClient {

    @GetMapping(value = "/search", params = {"q={q}", "format=json"})
    List<NominatimPlace> getPlaceCoords(@PathParam("q") String encodedQuery);
}

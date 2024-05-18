package com.sec.gen.next.serviceorchestrator.external;

import com.next.gen.sec.model.NominatimPlace;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.external.SimpleErrorDecoder;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "nominatimClient", url = "${services.nominatim.url}", configuration = FeignConfig.class)
public interface NominatimClient {

    @GetMapping(value = "/search")
    List<NominatimPlace> getPlaceCoords(@RequestParam("q") String q, @RequestParam("format") String format);
}

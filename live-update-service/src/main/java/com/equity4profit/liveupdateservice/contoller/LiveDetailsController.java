package com.equity4profit.liveupdateservice.contoller;


import com.equity4profit.liveupdateservice.LiveDetailMergeService;
import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.livedetail.LiveDetailService;
import com.equity4profit.liveupdateservice.livedetail.LiveDetailsResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("details")
public class LiveDetailsController {

    private final LiveDetailMergeService liveDetailService;

    public LiveDetailsController(LiveDetailMergeService liveDetailService) {
        this.liveDetailService = liveDetailService;
    }

    @PostMapping
    public List<LiveDetailsResponse> getLivePriceResponses(@RequestBody List<String> symbols) throws LiveUpdateException {
        return liveDetailService.getLivePriceResponses(symbols);
    }

    @PostMapping("all")
    public List<LiveDetailsResponse> getAllLivePriceResponses() throws LiveUpdateException {
        return liveDetailService.getAllLivePriceResponses();
    }
}

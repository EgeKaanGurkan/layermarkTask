/*
* This package and route is used to populate the database.
*/

package com.example.layermarktask.populate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/populate")
public class PopulateController {

    private PopulateService populateService;

    public PopulateController(PopulateService populateService) {
        this.populateService = populateService;
    }

    @PostMapping
    public void populate() {
        populateService.populate();
    }

}

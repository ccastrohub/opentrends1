package com.challenge.opentrends.controller;

import com.challenge.opentrends.exception.BadRequestException;
import com.challenge.opentrends.exception.ResourceNotFoundException;
import com.challenge.opentrends.model.CreateRepoRequestTemplate;
import com.challenge.opentrends.service.GitLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EndPointsController {

    @Autowired
    GitLabService gitLabService;

    /*
    This endpoint creates a repository with an starter tag and an optional develop branch.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/startRepo")
    public String initializeRepo(@RequestBody CreateRepoRequestTemplate createRepoRequestTemplate) throws ResourceNotFoundException, BadRequestException {
        return gitLabService.initializeRepo(createRepoRequestTemplate);
    }

}

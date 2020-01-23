package com.challenge.opentrends.service;

import com.challenge.opentrends.exception.BadRequestException;
import com.challenge.opentrends.exception.ResourceNotFoundException;
import com.challenge.opentrends.model.CreateRepoRequestTemplate;

public interface GitLabService {

    String initializeRepo(CreateRepoRequestTemplate name) throws ResourceNotFoundException, BadRequestException;

}

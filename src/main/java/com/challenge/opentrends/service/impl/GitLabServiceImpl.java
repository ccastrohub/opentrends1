package com.challenge.opentrends.service.impl;

import com.challenge.opentrends.exception.BadRequestException;
import com.challenge.opentrends.exception.ResourceNotFoundException;
import com.challenge.opentrends.model.CreateBranchRequestTemplate;
import com.challenge.opentrends.model.CreateBranchResponseTemplate;
import com.challenge.opentrends.model.CreateRepoResponseTemplate;
import com.challenge.opentrends.model.CreateRepoRequestTemplate;
import com.challenge.opentrends.service.GitLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GitLabServiceImpl implements GitLabService {
    /*
    Otra opcion para el user y el pass hubiera sido hacer una clase configucion con la informacion para pegarle a gitlab
     */

    @Value("${gitlab.accessToken}")
    private String gitLabAccessToken;

    @Value("${gitlab.baseURL}")
    String gitLabBaseURL;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String initializeRepo(CreateRepoRequestTemplate createRepoRequestTemplate) throws ResourceNotFoundException, BadRequestException {
        try {
            CreateRepoResponseTemplate responseObject = restTemplate.postForObject(gitLabBaseURL, createRequestObject(createRepoRequestTemplate), CreateRepoResponseTemplate.class);
            if (createRepoRequestTemplate.getDevelopBranch() != null){
                addBranch(responseObject.getId(), createRepoRequestTemplate.getDevelopBranch());
            }
            return "Great! all done!";
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            if (HttpStatus.NOT_FOUND.equals(httpClientOrServerExc.getStatusCode())) {
               throw new ResourceNotFoundException(httpClientOrServerExc.getMessage());
            } else if (HttpStatus.BAD_REQUEST.equals(httpClientOrServerExc.getStatusCode())){
                throw new BadRequestException(httpClientOrServerExc.getMessage());
            }
            return httpClientOrServerExc.getMessage();
        }

    }

    public void addBranch(String id, String name) {
        CreateBranchRequestTemplate createBranchRequestTemplate = new CreateBranchRequestTemplate();
        createBranchRequestTemplate.setId(id);
        createBranchRequestTemplate.setBranch(name);
        createBranchRequestTemplate.setRef("master");
        //En caso de ser necesario se podria guardar el objeto y realizar algun checkeo.
        restTemplate.postForObject(getCreateBranchGLURL(id), createRequestObject(createBranchRequestTemplate), CreateBranchResponseTemplate.class);
    }

    private String getCreateBranchGLURL(String id) {
        String createBranchURL =  String.join("",gitLabBaseURL,id,"/repository/branches");
        return createBranchURL;
    }

    private HttpEntity<CreateRepoRequestTemplate> createRequestObject(CreateRepoRequestTemplate createRepoRequestTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", gitLabAccessToken);
        HttpEntity<CreateRepoRequestTemplate> request = new HttpEntity<>(createRepoRequestTemplate, headers);
        return request;
    }

    private HttpEntity<CreateBranchRequestTemplate> createRequestObject(CreateBranchRequestTemplate createBranchRequestTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", gitLabAccessToken);
        HttpEntity<CreateBranchRequestTemplate> request = new HttpEntity<>(createBranchRequestTemplate, headers);
        return request;
    }

}

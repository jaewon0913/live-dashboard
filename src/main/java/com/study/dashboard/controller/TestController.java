package com.study.dashboard.controller;

import lombok.NoArgsConstructor;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class TestController {

    public void test(){

        try {
            GitHub gitHub = new GitHubBuilder().withOAuthToken("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").build();
            GHRepository repository = gitHub.getRepository("whiteship/live-study");


        } catch (IOException e){

        }
    }
}

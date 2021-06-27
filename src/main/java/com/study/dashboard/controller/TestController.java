package com.study.dashboard.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@NoArgsConstructor
public class TestController {

    @GetMapping("/gitTest")
    public String test(){
        try {
            GitHub gitHub = new GitHubBuilder().withOAuthToken("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").build();
            GHRepository repository = gitHub.getRepository("jaewon0913/live-dashboard");

            List<GHIssue> ghIssueList = repository.getIssues(GHIssueState.ALL);

            System.err.println(ghIssueList.get(0));

        } catch (IOException e){
            e.printStackTrace();
        }

        return "gitTest";
    }
}

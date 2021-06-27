package com.study.dashboard.GitApi;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;

public class GHConnect {
    @Value("${gitapi.key}")
    private static String gitKey;

    private static final String personalToken = gitKey;
    private static GHConnect con = new GHConnect();
    private static GitHub github;

    private GHConnect() {
        try {// 깃허브 객체 생성
            this.github = new GitHubBuilder().withOAuthToken(personalToken).build();
        } catch (Exception e) {
        }
    }

    public static GitHub getConnection() {
        return github;
    }
}

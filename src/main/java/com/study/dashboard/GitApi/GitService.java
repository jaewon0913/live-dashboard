package com.study.dashboard.GitApi;

import java.io.IOException;
import java.util.Map;

public class GitService {

    private GitDataManagement GitDataManagement;

    public GitService() throws IOException {
        this.GitDataManagement = new GitDataManagement();
    }

    // 리포지토리 세팅
    public boolean setRepo(String repo) throws IOException {
        return this.GitDataManagement.setRepository(repo);
    }

    // userid로 출석율 검색
    public void findByName(String name) {
        Double rate = this.GitDataManagement.getAttendenceRateByName(name);
        if (rate == 0) return;
        System.out.println(name + " : " + String.format("%.2f", rate) + "%" + "\n");
    }

    // 모든 유저의 출석율 검색, 출석율 높은 순으로 정렬
    public void findAll() {
        Map<String, Double> allRate = this.GitDataManagement.getAllAttendenceRate();
        int idx = 1;
        for (String id : allRate.keySet()) {
            Double rate = allRate.get(id);
            System.out.print(idx + ".");
            String r = String.format("%20s", id);
            System.out.println(r + " -> " + rate + "%");
            idx += 1;
        }
        System.out.println("\n");
    }
}

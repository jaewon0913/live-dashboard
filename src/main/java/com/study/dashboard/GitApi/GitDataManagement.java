package com.study.dashboard.GitApi;

import org.kohsuke.github.*;

import java.io.IOException;
import java.util.*;

public class GitDataManagement {

    private GitHub gitHub;
    private GHRepository ghRepository;

    // 참여율
    private Map<String, Integer> participation;
    // 총 이슈 수
    private int totalIssue;

    public GitDataManagement() throws IOException{
        this.gitHub = GHConnect.getConnection();
        this.participation = new HashMap<String, Integer>();
    }

    // 리포지토리(저장소) 셋팅
    public boolean setRepository(String repository) throws IOException{
        boolean connectCheck = false;

        try{
            // 사용자로부터 입력받은 repository 이름을 이용하여 GHRepository 객체 생성
            this.ghRepository = gitHub.getRepository(repository).getSource();

            setAttendance();
            connectCheck = true;
        } catch (Exception e) {
            e.printStackTrace();
            connectCheck = false;
        }

        return connectCheck;
    }

    // 세팅된 리포지토리 유저별 출석정보 세팅
    public void setAttendance() throws IOException {
        // 세팅된 리포지토리의 전체 issue
        List<GHIssue> allTheIssues = ghRepository.getIssues(GHIssueState.ALL);

        // 하나의 issue에 코멘트를 남긴 user를 담기 위한 set (중복제거)
        Set<String> userList = new HashSet<String>();

        // 출석율 계산을 위한 이슈 총 개수
        this.totalIssue = allTheIssues.size();

        for (GHIssue issueForAWeek : allTheIssues) {				// 이슈를 1주차씩 가져와서
            for (GHIssueComment comment : issueForAWeek.getComments()) {	// 해당 이슈의 전체 코멘트 가져오기
                userList.add(comment.getUser().getLogin()); 		// 코멘트의 user id를 namelist(임시 set)에 삽입
            }
            // map<id, count>의 value(출석횟수) 증가시키기
            insertUser(userList);
            userList.clear();
        }
    }

    // 만들어진 임시 set으로 map의 count(출석횟수) 증가시키기
    public void insertUser(Set<String> nameList) {
        nameList.forEach((name) -> {
            if (this.participation.containsKey(name)) {			// 이미 map에 존재하는 id일 경우
                this.participation.put(name, participation.get(name) + 1);
            } else {							// map에 존재하지 않는 id일 경우
                this.participation.put(name, 1);
            }
        });
    }

    // userid로 출석횟수 검색
    public Double getAttendenceRateByName(String name) {
        int count = 1;
        try {
            count = this.participation.get(name);
            return (double) ((count * 100) / this.totalIssue);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


    // 모든 참여자의 출석횟수 검색
    public Map<String, Double> getAllAttendenceRate() {
        Map<String, Double> allRate = new HashMap<String, Double>();
        this.participation.forEach((name, count) -> {
            allRate.put(name, (double) ((count * 100) / this.totalIssue));
        });
        return sortMapByValue(allRate); // 출석율을 기준으로 내림차순 정렬
    }

    public static LinkedHashMap<String, Double> sortMapByValue(Map<String, Double> allRate) {
        List<Map.Entry<String, Double>> entries = new LinkedList<>(allRate.entrySet());
        Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        LinkedHashMap<String, Double> result = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

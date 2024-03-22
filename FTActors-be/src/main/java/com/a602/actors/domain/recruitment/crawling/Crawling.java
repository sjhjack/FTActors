package com.a602.actors.domain.recruitment.crawling;

import com.a602.actors.domain.recruitment.entity.Recruitment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Crawling {

    public List<Recruitment> getRecruitmentDatas(LocalDate time) {
        final String RECRUITMENT_URL = "https://www.filmmakers.co.kr/filmmakersWanted/category/";
        String yesterday = time.toString();
        int[] urlCode = {63707, 63711, 8295195, 2361256, 63816}; // 장편영화 / 단편영화 / 웹드라마 / 뮤비/CF / 유튜브/기타
        List<Recruitment> recruitmentList = new ArrayList<>();
        try {
            for (int index = 0; index < urlCode.length; index++) {
                Document document = Jsoup.connect(RECRUITMENT_URL + urlCode[index]).get(); // 주소뒤에 100, 101 이런식
                Elements content1 = document.select("div.content.date"); // 날짜 불러옴
                Elements content2 = document.select("table.ui.table a.block"); // 주소 불러옴

                for (int i = 0; i < content1.size(); i++) {
                    String date = content1.get(i).text(); // 제목은 text만 추출
                    String url = content2.get(i).absUrl("href"); // url은 a태그 추출
                    // 날짜비교해서 전날에 올라온 공고만 크롤링
                    if (!date.substring(0, 10).equals(yesterday)) {
                        break;
                    }
                    // url로 이동해서 크롤링
                    document = Jsoup.connect(url).get();
                    String title = document.select("th[colspan=2] h2").text();
                    String content = document.select(".xe_content p").text();
                    String director = document.select("table.ui.unstackable tbody tr:nth-of-type(3) td:nth-of-type(2)").text();
                    String category = getCategory(urlCode[index]);
                    String startDate = date;
                    String endDate = document.select("table.ui.unstackable tbody tr:nth-of-type(12) td:nth-of-type(2)").text();
                    System.out.println(endDate);
                    Recruitment recruitment = Recruitment.builder()
                            .title(title);
                        .content(content);
                        .postMember(director);
                         .category(category);
                        .startDate(startDate);
                         .endDate(endDate);
                           .build();
                    recruitmentList.add(recruitment);
                }
            }
        } catch (IOException e) {
            // 크롤링 중에 예외가 발생할 경우
            e.printStackTrace(); // 예외를 출력하거나 로깅하도록 수정해도 좋습니다.
        }
        return recruitmentList;
    }

    public String getCategory(int code) {
        if (code == 63707) {
            return "장편영화";
        } else if (code == 63711) {
            return "단편영화";
        } else if (code == 8295195) {
            return "웹드라마";
        } else if (code == 2361256) {
            return "뮤비/CF";
        } else if (code == 63816) {
            return "유튜브/기타";
        }
        return null;
    }
}

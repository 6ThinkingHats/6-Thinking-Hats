package com.ssafy.sixhats.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.UserDAO;
import com.ssafy.sixhats.util.RandomStringMaker;
import com.ssafy.sixhats.vo.UserVO;
import com.ssafy.sixhats.vo.type.Gender;
import com.ssafy.sixhats.vo.type.Job;
import com.ssafy.sixhats.vo.type.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RandomStringMaker randomStringMaker;

    public Map<String, String> getKaKaoAccessToken(String code) {
        Map<String, String> map = new HashMap<>();
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=519439ce954029ab868883d1f092d2dc"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/kakaologinpage"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            map.put("access-token", access_Token);
            map.put("refresh-token", refresh_Token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Transactional
    public UserVO createKakaoUser(String token) throws IOException{

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token); // 전송할 header 작성, access_token전송

        // 결과 코드가 200이라면 성공
        int responseCode = conn.getResponseCode();
        // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        // Gson 라이브러리로 JSON파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
        // Social Login
        // email로 user를 찾는다
        UserVO userVO = userDAO.findByEmail(email).orElse(null);

        if (userVO == null) {
            // user가 없다면 user를 만들어주고 저장하고 return
            // name
            String name = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            // gender
            String genderTmp = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("gender").getAsString();
            Gender gender = Gender.WOMAN;
            if(genderTmp.equals("male")){
                gender = Gender.MAN;
            }

            // job
            Job job = Job.OTHER;

            // password
            String password = randomStringMaker.getRandomString(10);

            // profile image
            String profileImageUrl = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();

            // UserVO 생성
            userVO = UserVO.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .job(job)
                    .gender(gender)
                    .build();

            // login type 추가
            userVO.updateLoginType(LoginType.KAKAO);
            userVO.updateProfileImageUrl(profileImageUrl);

            userVO = userDAO.save(userVO);
        }

        return userVO;
    }

}

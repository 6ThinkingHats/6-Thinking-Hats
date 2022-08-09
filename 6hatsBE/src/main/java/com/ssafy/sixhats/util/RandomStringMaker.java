package com.ssafy.sixhats.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomStringMaker {
    public String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    sb.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    sb.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    sb.append((rnd.nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }
}

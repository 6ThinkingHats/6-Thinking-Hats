package com.ssafy.sixhats.controller;

import com.ssafy.sixhats.dto.file.TxtPostRequestDTO;
import com.ssafy.sixhats.util.RandomStringMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("file")
public class FIleController {

    private final RandomStringMaker randomStringMaker;

    private final String txtPath = "/home/ubuntu/static/txt/";
    private final String imagePath = "/home/ubuntu/static/img/";
    private final String videoPath = "/opt/openvidu/recordings/";

    // 의견 창구 파일 저장
    @PostMapping("txt")
    public ResponseEntity postTxt(@RequestBody TxtPostRequestDTO txtPostRequestDTO) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;

        String filePath = txtPath + txtPostRequestDTO.getSessionId() + ".txt";
        File file = new File(filePath);
        if(!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        System.out.println(txtPostRequestDTO.getSessionId());
        System.out.println(txtPostRequestDTO.getContents());
        writer.write(txtPostRequestDTO.getContents());

        writer.flush();
        writer.close();

        return new ResponseEntity(resultMap, status);
    }

    // 의견 창구 파일 불러오기
    @GetMapping("txt")
    public StreamingResponseBody getTxt(String opinionFileUrl) throws FileNotFoundException {
        File file = new File(txtPath + opinionFileUrl + ".txt");
        System.out.println(file);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }

    // 이미지 저장
    @PostMapping("image")
    public ResponseEntity postImage(String name, MultipartFile image, HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        
        // 우선 이름은 random string으로 저장하자
        if(name == null) { // 없다는 거니까 이름 임의로 정해주기
            name = randomStringMaker.getRandomString(10);
        }

        // image 저장
        image.transferTo(new File( imagePath + name + ".png"));
        
        // response로 이름 보내줘야 함
        resultMap.put("fileName", name);

        return new ResponseEntity(resultMap, status);
    }

    // 이미지 전송
    @GetMapping("image")
    public StreamingResponseBody getImage(String profileImageUrl) throws FileNotFoundException {
        File file = new File(imagePath + profileImageUrl + ".png");
        System.out.println(file);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }

    // 동영상 전송
    @GetMapping("video")
    public StreamingResponseBody getVideo(String videoUrl) throws FileNotFoundException {
        File file = new File(videoPath + videoUrl + "/" + videoUrl + ".mp4");
        System.out.println(file);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }

    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}
